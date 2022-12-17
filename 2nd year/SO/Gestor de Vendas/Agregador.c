#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <time.h> //para determinar data e hora de execução

#define SIZE_COD 6
#define SIZE_QTA 6
#define SIZE_PRECO 7
#define SIZE_VENDA 22
#define SIZE_TOTAL 40 //a agregação de vendas pode ultrapassar o número de digitos de por exemplo o definido para o preço(SIZE_PRECO 7)


ssize_t readln (int fildes, void *buf, size_t nbyte){
	int r;
	ssize_t bytesRead = 0;

	while((r = read(fildes, buf+bytesRead, 1)) && (((char*)buf)[bytesRead] != '\n'))
		bytesRead++;

	if(((char*)buf)[bytesRead] == '\n')
		bytesRead++;


	return bytesRead;
}

void tokenizeLinha (char* linha, char* campos[]){
	int i = 0;
	char* token = strdup (linha);
	token = strtok (token, " ");  
	while (token != NULL){
		campos[i] = strdup (token);
		token = strtok (NULL, " ");
		i++;
	}
	free(token);
}

void mystrrev (char s[]){
	int i,j=strlen(s)-1;
	char v;
	for(i=0; i<=j; i++,j--){
		v=s[j];
		s[j]=s[i];
		s[i]=v;
	}
}

void myitoa(int n, char s[]){
     int i, sign;
 
     if ((sign = n) < 0)  /* record sign */
         n = -n;          /* make n positive */
     i = 0;
     do {       /* generate digits in reverse order */
         s[i++] = n % 10 + '0';   /* get next digit */
     } while ((n /= 10) > 0);     /* delete it */
     if (sign < 0)
         s[i++] = '-';
     s[i] = '\0';
     mystrrev(s);
}

void data_tempo(char data[]){
	time_t time_data;
	struct tm *info;

	time(&time_data);
	info = localtime(&time_data);
    strftime(data, 24,"%d.%m.%Y-%X", info);
}

void procuraCodigoIgual(char* campos[], int fd, char total[]){
	char aux[SIZE_VENDA];
	char* auxCampos[3];
	int x = atoi(campos[1]), y = atoi(campos[2]);
	char* aux1 = malloc(sizeof(char) * SIZE_QTA);
	char* aux2 = malloc(sizeof(char) * SIZE_PRECO);

	while (readln(fd, aux, sizeof(aux)) != 0){

		tokenizeLinha(aux, auxCampos);

		if (strcmp(campos[0], auxCampos[0]) == 0){
			x += atoi(auxCampos[1]);
			y += atoi(auxCampos[2]);
		}
	}

	myitoa(x, aux1);
	myitoa(y, aux2);

	strcpy(total, campos[0]);
	strcat(total, " ");
	strcat(total, aux1);
	strcat(total, " ");
	strcat(total, aux2);
	strcat(total, "\n");
}

int procuraCodigoExistente (char codigo[], char data[]){
	char buf[SIZE_TOTAL];
	char* cod;
	int existe = 0;
	int fd = open(data, O_RDONLY, 0666);

	while (readln(fd, buf, sizeof(buf)) != 0) {

		cod = strtok(buf, " ");
		if (strcmp(codigo, cod) == 0) {
			existe = 1;
			break;
		}
	}

	if(close(fd) < 0)
		perror("AG - Erro a fechar o ficheiro.");

	return existe;
}

int main(int argc, char* argv[]){
	char buffer[SIZE_VENDA];
	char total[SIZE_TOTAL];
	char* campos[3];
	size_t armazena = 0;
	
	char data[24];
	data_tempo(data);

	int fd1 = open("vendas", O_RDONLY, 0666);

	if(fd1 == -1)
		perror("AG - Erro ao abrir o ficheiro vendas");

	int fd2 = open(data, O_CREAT | O_WRONLY, 0666);

	if (fd2==-1)
		perror("AG - Criação do ficheiro de agregação falhou");

	bzero(buffer, sizeof(buffer));

	if(argc == 2){
		int linhas = atoi(argv[1]);
		lseek(fd1, linhas*(SIZE_COD+SIZE_QTA+SIZE_PRECO+3), SEEK_SET);

		while (readln(fd1, buffer, sizeof(buffer)) != 0) {

			tokenizeLinha(buffer, campos);
			if(procuraCodigoExistente(campos[0], data) == 0){

				procuraCodigoIgual(campos, fd1, total);
				write(fd2, total, strlen(total));
				armazena += strlen(buffer);
				lseek(fd1, (linhas*(SIZE_COD+SIZE_QTA+SIZE_PRECO+3))+armazena, SEEK_SET);
			}

			bzero(buffer, sizeof(buffer));
		}
	}

	else{

		while (readln(fd1, buffer, sizeof(buffer)) != 0) {

			tokenizeLinha(buffer, campos);
			if(procuraCodigoExistente(campos[0], data) == 0){

				procuraCodigoIgual(campos, fd1, total);
				write(fd2, total, strlen(total));
				armazena += strlen(buffer);
				lseek(fd1, armazena, SEEK_SET);
			}

			bzero(buffer, sizeof(buffer));
		}
	}

	printf("agregacao terminada\n");

	close(fd1);
	close(fd2);
	return 0;
}