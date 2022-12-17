#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <sys/types.h>

#define LEITURA 20
#define BUFFER 100
#define SIZE_COD 6
#define SIZE_REF 8
#define SIZE_PRECO 7
#define SIZE_QTA 6

int algarismos(int n){
	int conta = 0;
	while(n != 0){
		n /= 10;
		conta++;
	}
	return conta;
}

int main(){
	char aux[LEITURA];
	char* exec;
	char* token;
	char* words[3];
	int i, j;

	int fd1 = open("stocks", O_CREAT, 0666);
	if(fd1 < 0)
		perror("CV - Erro a abrir o ficheiro de stocks.");

	int fd2 = open("vendas", O_CREAT, 0666);
	if(fd2 < 0)
		perror("CV - Erro a abrir o ficheiro de vendas.");

	int fd3 = open("erros.txt", O_CREAT | O_WRONLY, 0666);
	if(fd3 < 0)
		perror("CV - Erro a abrir o ficheiro de erros.");

	int fifoCliM = open("clienteMostrarAux", O_WRONLY);
	if(fifoCliM < 0)
		perror("CV - Erro a abrir o fifo de clienteMostrar.");

	int fifoCliA = open("clienteAlterarAux", O_WRONLY);
	if(fifoCliA < 0)
		perror("CV - Erro a abrir o fifo de clienteAlterar.");

	int pid = getpid();
	char* pidAux = malloc((algarismos(pid))*(sizeof(char)));
	sprintf(pidAux, "%d", pid);

	mkfifo(pidAux, 0666);
	int fifoPid = open(pidAux, O_RDWR);
	if(fifoPid < 0)
		perror("CV - Erro a abrir o fifo de pid.");

	bzero(&aux, LEITURA);

	while(read(STDIN_FILENO, &aux, sizeof(aux)) != 0){
		exec = strdup(aux);
		token = strtok(exec, " \n");
		i = 0;

		while(token != NULL && i < 3){
			words[i] = strdup(token);
			token = strtok(NULL, " \n");
			i++;
		}
		for(j = i; j < 3; j++)
			words[j] = NULL;

		if(i == 1 && words[1] == NULL){

			if(strlen(words[0]) > SIZE_COD){
				lseek(fd3, 0, SEEK_END);
				write(fd3, "CV - Codigo de produto demasiado extenso. - ", strlen("CV - Codigo de produto demasiado extenso. - "));
				write(fd3, words[0], strlen(words[0]));
				write(fd3, "\n", strlen("\n"));
			}

			else{
				if(strlen(words[0]) < SIZE_COD){
					char* codigo = malloc(SIZE_COD*sizeof(char));

					for(i = 0; i < (SIZE_COD-(strlen(words[0]))); i++)
						codigo[i] = '0';

					for(int j = 0; i < SIZE_COD && j < strlen(words[0]); i++, j++)
						codigo[i] = words[0][j];

					words[0] = strdup(codigo);
				}
				
				sprintf(words[0], "%s %s\n", words[0], pidAux);
				write(fifoCliM, words[0], strlen(words[0]));

				while(1){
					if(read(fifoPid, exec, (SIZE_COD + SIZE_QTA + SIZE_PRECO + 35)) > 0){
						write(STDOUT_FILENO, exec, (SIZE_COD + SIZE_QTA + SIZE_PRECO + 35));
						break;
					}
				}
			}
		}

		else if(i == 2 && words[2] == NULL){

			if(strlen(words[0]) > SIZE_COD){
				lseek(fd3, 0, SEEK_END);
				write(fd3, "CV - Codigo de produto demasiado extenso. - ", strlen("CV - Codigo de produto demasiado extenso. - "));
				write(fd3, words[0], strlen(words[0]));
				write(fd3, "\n", strlen("\n"));
			}

			else if(strlen(words[1]) > SIZE_QTA){
				lseek(fd3, 0, SEEK_END);
				write(fd3, "CV - Quantidade demasiado elevada. - ", strlen("CV - Quantidade demasiado elevada. - "));
				write(fd3, words[1], strlen(words[1]));
				write(fd3, "\n", strlen("\n"));
			}

			else{
				if(strlen(words[0]) < SIZE_COD){
					char* codigo1 = malloc(SIZE_COD*sizeof(char));

					for(i = 0; i < (SIZE_COD-(strlen(words[0]))); i++)
						codigo1[i] = '0';

					for(int j = 0; i < SIZE_COD && j < strlen(words[0]); i++, j++)
						codigo1[i] = words[0][j];

					words[0] = strdup(codigo1);
				}

				if(strlen(words[1]) < SIZE_QTA){
					char* codigo2 = malloc(SIZE_QTA*sizeof(char));

					for(i = 0; i < (SIZE_QTA-(strlen(words[1]))); i++)
						codigo2[i] = '0';

					for(int j = 0; i < SIZE_QTA && j < strlen(words[1]); i++, j++)
						codigo2[i] = words[1][j];

					words[1] = strdup(codigo2);
				}
				
				sprintf(exec, "%s %s %s\n", words[0], words[1], pidAux);
				write(fifoCliA, exec, strlen(exec));

				while(1){
					if(read(fifoPid, exec, (SIZE_COD + SIZE_QTA + 25)) > 0){
						write(STDOUT_FILENO, exec, (SIZE_COD + SIZE_QTA + 25));
						break;
					}
				}
			}
		}

		else{
			lseek(fd3, 0, SEEK_END);
			write(fd3, "CV - Demasiados argumentos recebidos. - ", strlen("CV - Demasiados argumentos recebidos. - "));
			write(fd3, &aux, strlen(aux));
		}

		bzero(&aux, LEITURA);

	}

	if(close(fd1) < 0)
		perror("CV - Erro a fechar o ficheiro de stocks.");

	if(close(fd2) < 0)
		perror("CV - Erro a fechar o ficheiro de vendas.");

	if(close(fd3) < 0)
		perror("CV - Erro a fechar o ficheiro de erros.");

	if(close(fifoCliM) < 0)
		perror("CV - Erro a fechar o ficheiro de erros.");

	if(close(fifoCliA) < 0)
		perror("CV - Erro a fechar o fifo de clienteAlterar.");

	return 0;
}