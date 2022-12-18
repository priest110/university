#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <fcntl.h> /* O_RDONLY, O_WRONLY, O_CREAT, O_* */
#include <sys/wait.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <string.h>

#define SIZE_QTA 6
#define SIZE_COD 6
#define SIZE_REF 8
#define SIZE_PRECO 7
#define SIZE 24 //SIZE_REF + SIZE_COD + SIZE_PRECO + 2 (espaços) + 1 (\n)


int quantasLinhasVendas(){
	int fd = open("vendas", O_RDONLY, 0666);
	if(fd < 0)
			perror("SV - Erro a abrir o ficheiro de vendas.");

	int size_linha = SIZE_COD + SIZE_QTA + SIZE_PRECO + 3;
	int linhas = 0;
	char* aux = malloc(size_linha*sizeof(char));

	while(read(fd, aux, size_linha*sizeof(char)) != 0)
		linhas++;

	if(close(fd) < 0)
		perror("SV - Erro a fechar o ficheiro de vendas.");

	return linhas;
}

int quantasLinhasStocks(){
	int fd = open("stocks", O_RDONLY, 0666);
	if(fd < 0)
			perror("SV - Erro a abrir o ficheiro de stocks.");

	int size_linha = SIZE_COD + SIZE_QTA + 2;
	int linhas = 0;
	char* aux = malloc(size_linha*sizeof(char));

	while(read(fd, aux, size_linha*sizeof(char)) != 0)
		linhas++;

	if(close(fd) < 0)
		perror("SV - Erro a fechar o ficheiro de stocks.");

	return linhas;
}

int quantasLinhasArtigos(){
	int fd = open("artigos", O_RDONLY, 0666);
	if(fd < 0)
			perror("SV - Erro a abrir o ficheiro de artigos.");

	int size_linha = SIZE_COD + SIZE_QTA + 2;
	int linhas = 0;
	char* aux = malloc(size_linha*sizeof(char));

	while(read(fd, aux, size_linha*sizeof(char)) != 0)
		linhas++;

	if(close(fd) < 0)
		perror("SV - Erro a fechar o ficheiro de artigos.");

	return linhas;
}

char* mostrarStock(char cod[]){
	int c = atoi(cod);
	int linhas = quantasLinhasStocks();
	int size_linha = SIZE_COD + SIZE_QTA + 2;
	char buffer[1];
	char* quantidade = malloc(sizeof(char*));
	char* preco = malloc(sizeof(char*));
	char* retorna = malloc(sizeof(char*));
	
	if(c <= linhas){
		int fd = open("stocks", O_RDONLY, 0666);
		if(fd < 0)
			perror("SV - Erro a abrir o ficheiro de stocks.");

		lseek(fd, (c-1)*size_linha, SEEK_SET);
		lseek(fd, SIZE_COD + 1, SEEK_CUR);

		int i = 0;	
		while(i < SIZE_QTA){
			read(fd, buffer, 1);
			quantidade[i] = buffer[0];
			i++;
		}

		int fd2 = open("artigos", O_RDONLY, 0666);
		if(fd2 < 0)
			perror("SV - Erro a abrir o ficheiro de artigos.");

		lseek(fd2, (c-1)*(SIZE_COD+SIZE_REF+SIZE_PRECO+3), SEEK_SET);
		lseek(fd2, SIZE_COD + SIZE_REF + 2, SEEK_CUR);

		i = 0;	
		while(i < SIZE_PRECO){
			read(fd2, buffer, 1);
			preco[i] = buffer[0];
			i++;
		}

		c = atoi(quantidade);
		sprintf(retorna, "Codigo - %s, Quantidade - %s, Preco - %s\n", cod, quantidade, preco);
		
		if(close(fd) < 0)
			perror("SV - Erro a fechar o ficheiro de stocks.");

	}
	else
		sprintf(retorna, "Codigo de produto inexistente (%s).              \n", cod);

	return retorna;
}

int algarismos(int n){
	int conta = 0;
	while(n != 0){
		n /= 10;
		conta++;
	}
	return conta;
}

void removeChar(char *s){ 
  	int i;
    int j, n = strlen(s); 
    for (i=j=0; i<n; i++){ 
       if (s[i] != '0')
       	break;
    }
    for (; i < n; i++)
    {
    	s[j++] = s[i]; 
    }
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

void alterarVendas(char cod[], int quantia){
	int c = atoi(cod);
	int linhas = quantasLinhasArtigos();
	int size_linha = SIZE_COD + SIZE_REF + SIZE_PRECO + 3;
	char buffer[1];
	int aux = algarismos(quantia);
	char* aux2 = malloc(sizeof(char)*aux+1);
	char* quantidade = malloc(sizeof(char*) * 8);

	if(c <= linhas){
		int fd = open("artigos", O_RDONLY, 0666);
		if(fd < 0)
			perror("SV - Erro a abrir o ficheiro de artigos.");
		lseek(fd, (c-1)*size_linha, SEEK_SET);
		lseek(fd, 16, SEEK_CUR);

		int i = 0;	
		while(i < SIZE_PRECO){
			read(fd, buffer, 1);
			quantidade[i] = buffer[0];
			i++;
		}

		int fd1 = open("vendas", O_WRONLY, 0666);
		if(fd1 < 0)
			perror("SV - Erro a abrir o ficheiro de vendas.");
		lseek(fd1, 0, SEEK_END);

		write(fd1, cod, strlen(cod));
		write(fd1, " ", strlen(" "));

		if(quantia < 0)
			quantia = 0-quantia;
		myitoa(quantia, aux2);
		if(strlen(aux2) < SIZE_QTA){
			char* codigo = malloc(SIZE_QTA*sizeof(char));

			for(i = 0; i < (SIZE_QTA-(strlen(aux2))); i++)
				codigo[i] = '0';

			for(int j = 0; i < SIZE_QTA && j < strlen(aux2); i++, j++)
				codigo[i] = aux2[j];
			
			aux2 = strdup(codigo);
		}

		write(fd1, aux2, strlen(aux2));
		write(fd1, " ", strlen(" "));

		int preco = atoi(quantidade);
		preco = quantia*preco;
		i = algarismos(preco);
		quantidade = malloc(sizeof(char)*(i+1));
		myitoa(preco, quantidade);

		if(strlen(quantidade) < SIZE_PRECO){
			char* montante = malloc(SIZE_PRECO*sizeof(char));

			for(i = 0; i < (SIZE_PRECO-(strlen(quantidade))); i++)
				montante[i] = '0';

			for(int j = 0; i < SIZE_PRECO && j < strlen(quantidade); i++, j++)
				montante[i] = quantidade[j];

			quantidade = strdup(montante);
		}

		write(fd1, quantidade, strlen(quantidade));
		write(fd1 , "\n", strlen("\n"));

		if(close(fd) < 0)
			perror("SV - Erro a fechar o ficheiro de artigos.");

		if(close(fd1) < 0)
			perror("SV - Erro a fechar o ficheiro de vendas.");

	}
}

char* alterarStock(char cod[], int num){
	int c = atoi(cod);
	int linhas = quantasLinhasStocks();
	int size_linha = SIZE_COD + SIZE_QTA + 2;
	char buffer[1];
	char* quantidade = malloc(sizeof(char) * SIZE_QTA);
	char* retorna = malloc(sizeof(char*));
	
	if(c <= linhas){
		int fd = open("stocks", O_RDWR, 0666);
		if(fd < 0)
			perror("SV - Erro a abrir o ficheiro de stocks.");
		lseek(fd, (c-1)*size_linha, SEEK_SET);
		lseek(fd, (SIZE_COD+1), SEEK_CUR);
	
		int i = 0;	
		while(i < SIZE_QTA){
			read(fd, buffer, 1);
			quantidade[i] = buffer[0];
			i++;
		}
		int novo = atoi(quantidade) + num;

		if(novo >= 0){
			int aux = algarismos(novo);
			char* aux2 = malloc(sizeof(char)*aux+1);
			myitoa(novo, aux2);
	
			lseek(fd, (c-1)*size_linha, SEEK_SET);
			lseek(fd, (SIZE_COD+1), SEEK_CUR);

			if(novo > 0)
				c = SIZE_QTA - algarismos(novo);
			else 
				c = SIZE_QTA - 1;
		
			while(c > 0){
				write(fd, "0", strlen("0"));
				c--;
			}

			write(fd, aux2, strlen(aux2));
			write(fd, "\n", strlen("\n"));

			if(strlen(aux2) < SIZE_QTA){
				char* montante = malloc(SIZE_QTA*sizeof(char));

				for(i = 0; i < (SIZE_QTA-(strlen(aux2))); i++)
					montante[i] = '0';

				for(int j = 0; i < SIZE_QTA && j < strlen(aux2); i++, j++)
					montante[i] = aux2[j];

				aux2 = strdup(montante);
			}

			sprintf(retorna, "Codigo - %s, Quantidade - %s\n", cod, aux2);

			if(num < 0)
				alterarVendas(cod, num);
		}
		else{
			int fd4 = open("erros.txt", O_WRONLY, 0666);
			if(fd4 < 0)
				perror("SV - Erro a abrir o ficheiro de erros.");
			lseek(fd4, 0, SEEK_END);
			write(fd4, "SV - Não há stock suficiente para vender.\n", strlen("SV - Não há stock suficiente para vender.\n"));

			sprintf(retorna, "Nao ha stock suficiente para vender.\n");
		}
	

		close(fd);
	}
	else{
		sprintf(retorna, "Codigo de produto inexistente %s\n", cod);
	}

	return retorna;
}

int main(int argc, char* argv[]){
	int status, i;
	pid_t pid;

	int fd2 = open("stocks", O_CREAT | O_WRONLY, 0666);
	if(fd2 < 0)
		perror("SV - Erro a abrir o ficheiro de stocks.");

	int fd3 = open("vendas", O_CREAT, 0666);
	if(fd3 < 0)
		perror("SV - Erro a abrir o ficheiro de vendas.");

	int fd4 = open("erros.txt", O_CREAT | O_WRONLY, 0666);
	if(fd4 < 0)
		perror("SV - Erro a abrir o ficheiro de erros.");

	for(i = 0; i < 4; i++){

		pid = fork();

		if(pid == 0 && i == 0){
			if(close(fd3) < 0)
				perror("SV - Erro a fechar o ficheiro de vendas.");

			if(close(fd4) < 0)
				perror("SV - Erro a fechar o ficheiro de erros.");

			mkfifo("artigosAux", 0666);
			int fifoArt = open("artigosAux", O_RDONLY);
			if(fifoArt < 0)
				perror("SV - Erro a abrir o fifo de artigos.");

			char* buffer = malloc(sizeof(char)*SIZE_COD);

			lseek(fd2, 0, SEEK_END);

			while(read(fifoArt, buffer, SIZE_COD) > 0){
				write(fd2, buffer, SIZE_COD);
				write(fd2, " 000000", (SIZE_QTA+1));
  				write(fd2, "\n", strlen("\n"));
			}

			if(close(fd2) < 0)
				perror("SV - Erro a fechar o ficheiro de stocks.");

			if(close(fifoArt) < 0)
				perror("SV - Erro a fechar o fifo de artigos.");

			_exit(1);
		}

		else if(pid == 0 && i == 1){

			if(close(fd2) < 0)
				perror("SV - Erro a fechar o ficheiro de stocks.");

			if(close(fd3) < 0)
				perror("SV - Erro a fechar o ficheiro de vendas.");

			if(close(fd4) < 0)
				perror("SV - Erro a fechar o ficheiro de erros.");

			mkfifo("agregadorAux", 0666);
			int fifoAgr = open("agregadorAux", O_RDONLY);
			if(fifoAgr < 0)
				perror("SV - Erro a abrir o fifo de agregador.");

			char agr[2];

			int linhas = 0;
			int alg = algarismos(linhas);
			char* li = malloc(sizeof(char)*alg);
			myitoa(linhas, li);

			while(read(fifoAgr, agr, sizeof(agr)) > 0){
				pid_t aux = fork();

				if(aux == 0){
					execl("ag", "ag", li, NULL);
					_exit(0);
				}
				else{
					wait(&status);
					linhas = quantasLinhasVendas();
					alg = algarismos(linhas);
					li = malloc(sizeof(char)*alg);
					myitoa(linhas, li);
				}
			}

			if(close(fifoAgr) < 0)
				perror("SV - Erro a fechar o fifo de agregador.");

			_exit(2);
		}

		else if(pid == 0 && i == 2){
			if(close(fd2) < 0)
				perror("SV - Erro a fechar o ficheiro de stocks.");

			if(close(fd3) < 0)
				perror("SV - Erro a fechar o ficheiro de vendas.");

			if(close(fd4) < 0)
				perror("SV - Erro a fechar o ficheiro de erros.");

			mkfifo("clienteMostrarAux", 0666);
			int fifoCliM = open("clienteMostrarAux", O_RDONLY);
			if(fifoCliM < 0)
				perror("SV - Erro a abrir o fifo de clienteMostrar.");

			char* leitor = malloc(sizeof(char*));
			char le;
			int i = 0;

			while(read(fifoCliM, &le, sizeof(le)) > 0){

				if(le != '\n'){
					leitor[i] = le;
					i++;
				}

				else{

					char* exec = strdup(leitor);
					char* token = strtok(exec, " \n");
					char* words[2];
					i = 0;

					while(token != NULL && i < 2){
						words[i] = strdup(token);
						token = strtok(NULL, " \n");
						i++;
					}

					char* retorna = mostrarStock(words[0]);

					int fifoPid = open(words[1], O_WRONLY);
					if(fifoPid < 0)
						perror("SV - Erro a abrir o fifo de pid.");

					write(fifoPid, retorna, strlen(retorna));

					free(retorna);
					bzero(leitor, strlen(leitor));
					i = 0;
				}

			}

			if(close(fifoCliM) < 0)
				perror("SV - Erro a fechar o fifo de clienteMostrar.");

			_exit(3);

		}

		else if(pid == 0 && i == 3){
			if(close(fd2) < 0)
				perror("SV - Erro a fechar o ficheiro de stocks.");

			if(close(fd3) < 0)
				perror("SV - Erro a fechar o ficheiro de vendas.");

			if(close(fd4) < 0)
				perror("SV - Erro a fechar o ficheiro de erros.");

			mkfifo("clienteAlterarAux", 0666);
			int fifoCliA = open("clienteAlterarAux", O_RDONLY);
			if(fifoCliA < 0)
				perror("SV - Erro a abrir o fifo de clienteAlterar.");

			char* altera = malloc(sizeof(char*));
			char* retorna;
			int n = 0;
			char le;

			while(read(fifoCliA, &le, sizeof(le)) > 0){

				if(le != '\n'){
					altera[n] = le;
					n++;
				}

				else{
					char* exec = strdup(altera);
					char* token = strtok(exec, " \n");
					char* words[3];

					n = 0;
					while(token != NULL && n < 3){
						words[n] = strdup(token);
						token = strtok(NULL, " \n");
						n++;
					}
					n = 0;
					while(words[1][n] == '0'){
						words[1] = strdup(words[1]+1);
					}
					
					n = atoi(words[1]);
					retorna = alterarStock(words[0], n);

					int fifoPid = open(words[2], O_WRONLY);
					if(fifoPid < 0)
						perror("SV - Erro a abrir o fifo de pid.");

					write(fifoPid, retorna, strlen(retorna));

					free(retorna);
					bzero(altera, strlen(altera));
					n = 0;
				}
			}

			if(close(fifoCliA) < 0)
				perror("SV - Erro a fechar o fifo de clienteAlterar.");

			_exit(4);

		}
	}

	pid_t filho;
	while((filho = wait(&status)) > 0){

		if(WIFEXITED(status)){
			status = WEXITSTATUS(status);
		}
	}

	if(close(fd2) < 0)
		perror("SV - Erro a fechar o ficheiro de stocks.");

	if(close(fd3) < 0)
		perror("SV - Erro a fechar o ficheiro de vendas.");

	if(close(fd4) < 0)
		perror("SV - Erro a fechar o ficheiro de erros.");

	return 0;
}