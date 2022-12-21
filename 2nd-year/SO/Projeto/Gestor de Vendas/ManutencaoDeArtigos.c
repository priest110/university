#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <sys/types.h>

#define BUFFER 100
#define SIZE_COD 6
#define SIZE_REF 8
#define SIZE_PRECO 7

void incChar(char* string, int n){
	int i;

	char* aux = malloc(n*sizeof(char));
	for(i = 0; i < n; i++)
		aux[i] = '9';

	int cod = atoi(string);
	int max = atoi(aux);

	if(cod > max);

	else{
		for(i = n-1; i >= 0; i--){

			if(string[i] == '9'){
				string[i] -= 9;
			}

			else{
				string[i]++;
				break;
			}
		}
	}
}

int quantasLinhas(char* ficheiro){
	int fd = open(ficheiro, O_RDONLY, 0666);
	if(fd < 0)
			perror("MA - Erro a abrir o ficheiro.");

	int size_linha = SIZE_COD + SIZE_REF + SIZE_PRECO + 3;
	int linhas = 0;
	char* aux = malloc(size_linha*sizeof(char));

	while(read(fd, aux, size_linha*sizeof(char)) != 0)
		linhas++;

	if(close(fd) < 0)
		perror("MA - Erro a fechar o ficheiro.");

	return linhas;
}

char* proximaReferencia(){
	int fd1 = open("strings", O_RDONLY, 0666);
	if(fd1 < 0)
		perror("MA - Erro a abrir o ficheiro de strings.");

	char* ref = malloc(SIZE_REF*sizeof(char));
	char* aux = malloc(sizeof(char));
	int i;

	lseek(fd1, 0, SEEK_SET);

	for(i = 0; i < SIZE_REF-1; i++)
		ref[i] = '0';
	ref[i] = '1';

	while(read(fd1, aux, sizeof(char)) != 0){
		if(strcmp(aux, "\n") == 0)
			incChar(ref, SIZE_REF);
	}

	if(close(fd1) < 0)
		perror("MA - Erro a fechar o ficheiro de strings.");

	return ref;
}

char* proximoCodigo(){
	int fd1 = open("artigos", O_RDONLY, 0666);
	if(fd1 < 0)
		perror("MA - Erro a abrir o ficheiro de artigos.");

	char* cod = malloc(SIZE_COD*sizeof(char));
	char* aux = malloc(sizeof(char));
	int i;

	for(i = 0; i < SIZE_COD-1; i++)
		cod[i] = '0';
	cod[i] = '1';

	lseek(fd1, 0, SEEK_SET);

	while(read(fd1, aux, sizeof(char)) != 0){
		if(strcmp(aux, "\n") == 0)
			incChar(cod, SIZE_COD);
	}
	
	if(close(fd1) < 0)
		perror("MA - Erro a fechar o ficheiro de artigos.");

	return cod;
}

int insereArtigo(char* words[], char* cod, char* ref){
	pid_t pid;
	int i, status;

	int fd1 = open("artigos", O_WRONLY, 0666);
	if(fd1 < 0)
		perror("MA - Erro a abrir o ficheiro de artigos.");

	int fd2 = open("strings", O_WRONLY, 0666);
	if(fd2 < 0)
		perror("MA - Erro a abrir o ficheiro de strings.");

	lseek(fd1, 0, SEEK_END);
	lseek(fd2, 0, SEEK_END);

	char* aux1 = strdup(cod);
	char* aux2 = strdup(ref);

	pid = fork();

	if(pid == 0){
		write(fd1, aux1, strlen(aux1));
		write(fd1, " ", strlen(" "));
		write(fd1, aux2, strlen(aux2));
		write(fd1, " ", strlen(" "));

		if(strlen(words[2]) < SIZE_PRECO){
			char* preco = malloc(SIZE_PRECO*sizeof(char));

			for(i = 0; i < (SIZE_PRECO-(strlen(words[2]))); i++)
				preco[i] = '0';

			for(int j = 0; i < SIZE_PRECO && j < strlen(words[2]); i++, j++)
				preco[i] = words[2][j];

			words[2] = strdup(preco);
		}

		write(fd1, words[2], strlen(words[2]));
		write(fd1, "\n", strlen("\n"));

		_exit(0);
	}

	else{
		write(fd2, words[1], strlen(words[1]));
		write(fd2, "\n", strlen("\n"));

		wait(&status);
	}

	if(close(fd1) < 0)
		perror("MA - Erro a fechar o ficheiro de artigos.");

	if(close(fd2) < 0)
		perror("MA - Erro a fechar o ficheiro de strings.");

	return 1;
}

void insereNome(char* nome){

	int fd = open("strings", O_WRONLY, 0666);
	if(fd < 0)
		perror("MA - Erro a abrir o ficheiro de strings.");

	lseek(fd, 0, SEEK_END);

	write(fd, nome, strlen(nome));
	write(fd, "\n", strlen("\n"));

	if(close(fd) < 0)
		perror("MA - Erro a fechar o ficheiro de strings.");
}

void atualizaNome(char* codigo, char* referencia){
	int cod = atoi(codigo);
	int size_linha = SIZE_COD + SIZE_REF + SIZE_PRECO + 3;
	int linhas = quantasLinhas("artigos");
	int fd;

	if(cod <= linhas){
		fd = open("artigos", O_WRONLY, 0666);
		if(fd < 0)
			perror("MA - Erro a abrir o ficheiro de artigos.");

		lseek(fd, (cod-1)*size_linha, SEEK_SET);
		lseek(fd, SIZE_COD + 1, SEEK_CUR);

		write(fd, referencia, strlen(referencia));

		if(close(fd) < 0)
			perror("MA - Erro a fechar o ficheiro de artigos.");

	}
}

void atualizaPreco(char* codigo, char* preco){
	int cod = atoi(codigo);
	int size_linha = SIZE_COD + SIZE_REF + SIZE_PRECO + 3;
	int linhas = quantasLinhas("artigos");
	int fd;

	if(cod <= linhas){
		fd = open("artigos", O_WRONLY, 0666);
		if(fd < 0)
			perror("MA - Erro a abrir o ficheiro de artigos.");

		lseek(fd, (cod-1)*size_linha, SEEK_SET);
		lseek(fd, SIZE_COD + SIZE_REF + 2, SEEK_CUR);

		write(fd, preco, strlen(preco));

		if(close(fd) < 0)
			perror("MA - Erro a fechar o ficheiro de artigos.");
	}
}


int main(){
	char aux[BUFFER];
	int i, status;
	char* exec;
	char* token;
	char* words[3];

	int fd1 = open("artigos", O_CREAT, 0666);
	if(fd1 < 0)
		perror("MA - Erro a abrir o ficheiro de artigos.");

	int fd2 = open("strings", O_CREAT, 0666);
	if(fd2 < 0)
		perror("MA - Erro a abrir o ficheiro de strings.");

	int fd3 = open("erros.txt", O_CREAT | O_WRONLY, 0666);
	if(fd3 < 0)
		perror("MA - Erro a abrir o ficheiro de erros.");

	int fifoArt = open("artigosAux", O_WRONLY);
	if(fifoArt < 0)
		perror("MA - Erro a abrir o fifo de artigos.");

	int fifoAgr = open("agregadorAux", O_WRONLY);
	if(fifoArt < 0)
		perror("MA - Erro a abrir o fifo de agregador.");

	char* ref = proximaReferencia();
	char* cod = proximoCodigo();

	bzero(&aux, BUFFER);
	
	while(read(STDIN_FILENO, &aux, sizeof(aux)) != 0){
		
		exec = strdup(aux);
		token = strtok(exec, " \n");
		i = 0;

		while(token != NULL && i < 3){
			words[i] = strdup(token);
			token = strtok(NULL, " \n");
			i++;
		}

		if(strcmp(words[0], "i") == 0){

			int ret = insereArtigo(words, cod, ref);

			write(STDOUT_FILENO, cod, strlen(cod));
			write(STDOUT_FILENO, "\n", strlen("\n"));
			write(fifoArt, cod, strlen(cod));

			if(ret == 1){
				incChar(cod, SIZE_COD);
				incChar(ref, SIZE_REF);
			}
		}

		else if(strcmp(words[0], "n") == 0){
			int cod = atoi(words[1]);
			int linhas = quantasLinhas("artigos");

			if(cod <= linhas){

				if(strlen(words[1]) > SIZE_COD){
					lseek(fd3, 0, SEEK_END);
					write(fd3, "MA - Codigo de produto demasiado extenso. - ", strlen("MA - Codigo de produto demasiado extenso. - "));
					write(fd3, words[1], strlen(words[1]));
					write(fd3, "\n", strlen("\n"));
				}

				else if(strlen(words[1]) < SIZE_COD){
					char* codigo = malloc(SIZE_COD*sizeof(char));

					for(i = 0; i < (SIZE_COD-(strlen(words[1]))); i++)
						codigo[i] = '0';

					for(int j = 0; i < SIZE_COD && j < strlen(words[1]); i++, j++)
						codigo[i] = words[1][j];

					words[1] = strdup(codigo);
				}

				if(strlen(words[1]) == SIZE_COD){

					pid_t pidN = fork();

					if(pidN == 0){
						insereNome(words[2]);
						_exit(0);
					}

					else{
						atualizaNome(words[1], ref);
						wait(&status);
					}

					incChar(ref, SIZE_REF);
				}
			}

			else{
				lseek(fd3, 0, SEEK_END);
				write(fd3, "MA - O código ", strlen("MA - O código "));
				write(fd3, words[1], strlen(words[1]));
				write(fd3, " e invalido. (tentativa de atualizacao de nome)\n", strlen(" e invalido. (tentativa de atualizacao de nome)\n"));
			}
		}

		else if(strcmp(words[0], "p") == 0){
			int cod = atoi(words[1]);
			int linhas = quantasLinhas("artigos");

			if(cod <= linhas){
				if(strlen(words[1]) > SIZE_COD){
					lseek(fd3, 0, SEEK_END);
					write(fd3, "MA - Codigo de produto demasiado extenso. - ", strlen("MA - Codigo de produto demasiado extenso. - "));
					write(fd3, words[1], strlen(words[1]));
					write(fd3, "\n", strlen("\n"));
				}

				else if(strlen(words[1]) < SIZE_COD){
					char* codigo = malloc(SIZE_COD*sizeof(char));

					for(i = 0; i < (SIZE_COD-(strlen(words[1]))); i++)
						codigo[i] = '0';

					for(int j = 0; i < SIZE_COD && j < strlen(words[1]); i++, j++)
						codigo[i] = words[1][j];

					words[1] = strdup(codigo);
				}

				if(strlen(words[2]) > SIZE_PRECO){
					lseek(fd3, 0, SEEK_END);
					write(fd3, "MA - Preco demasiado extenso. - ", strlen("MA - Preco demasiado extenso. - "));
					write(fd3, words[2], strlen(words[2]));
					write(fd3, "\n", strlen("\n"));
				}

				else if(strlen(words[2]) < SIZE_PRECO){
					char* preco = malloc(SIZE_PRECO*sizeof(char));

					for(i = 0; i < (SIZE_PRECO-(strlen(words[2]))); i++)
						preco[i] = '0';

					for(int j = 0; i < SIZE_PRECO && j < strlen(words[2]); i++, j++)
						preco[i] = words[2][j];

					words[2] = strdup(preco);
				}

				if(strlen(words[1]) == SIZE_COD && strlen(words[2]) == SIZE_PRECO)
					atualizaPreco(words[1], words[2]);
			}

			else{
				lseek(fd3, 0, SEEK_END);
				write(fd3, "MA - O código ", strlen("MA - O código "));
				write(fd3, words[1], strlen(words[1]));
				write(fd3, " e invalido. (tentativa de atualizacao de preco)\n", strlen(" e invalido. (tentativa de atualizacao de preco)\n"));
			}
		}

		else if(strcmp(words[0], "a") == 0){

			write(fifoAgr, "a\n", strlen("a\n"));
			
		}

		else{
			lseek(fd3, 0, SEEK_END);
			write(fd3, "MA - Pedido de operacao invalido - ", strlen( "MA - Pedido de operacao invalido - "));
			write(fd3, words[0], strlen(words[0]));
			write(fd3, "\n", strlen("\n"));
		}

		bzero(&aux, BUFFER);
	}

	if(close(fd1) < 0)
		perror("MA - Erro a fechar o ficheiro de artigos.");

	if(close(fd2) < 0)
		perror("MA - Erro a fechar o ficheiro de strings.");

	if(close(fd3) < 0)
		perror("MA - Erro a fechar o ficheiro de erros.");

	if(close(fifoArt) < 0)
		perror("MA - Erro a fechar o fifo de artigos.");

	if(close(fifoAgr) < 0)
		perror("MA - Erro a fechar o fifo de agregador.");

	return 0;
}
