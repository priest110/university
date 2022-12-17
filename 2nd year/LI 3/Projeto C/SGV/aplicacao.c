#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "catClientes.h"
#include "catProd.h"
#include "faturacao.h"
#include "gestaoDeFiliais.h"
#include "queries.h"
#include "navegador.h"

#define NUMLETRAS 26
#define NUMMESES 12
#define NUMFILIAIS 3
#define TAMCLI 6
#define TAMCATCLI 26
#define TAMPROD 7
#define TAMCATPROD 26
#define BUFFER 10
#define BUFFERV 50
#define BUFFERVENDAS 2000000

typedef struct sgv{
	CatProd catp;
	CatCli catc;
	FatGlobal fat;
	VendasTotal vendas;
} *SGV;

/**
Funcao de inicializacao
*/
int main(){
	int opcao;
	int r = 1;
	char* aux1;
	char* aux2;
	char* aux3;
	char* aux4;
	SGV sgv;

	aux4 = malloc(sizeof(char));

	printf("Pretende analisar ficheiros específicos? (s/n)\n");
	scanf("%s", aux4);

	if ((strcmp(aux4, "S")) == 0 || (strcmp(aux4, "s")) == 0){
		aux1 = malloc(50*sizeof(char));
		aux2 = malloc(50*sizeof(char));
		aux3 = malloc(50*sizeof(char));
		printf("Indique, por favor, o ficheiro de Produtos.\n");
		scanf("%s",aux1);
		printf("Indique, por favor, o ficheiro de Clientes.\n");
		scanf("%s",aux2);
		printf("Indique, por favor, o ficheiro de Vendas.\n");
		scanf("%s",aux3);
	}

	else if ((strcmp(aux4, "N")) == 0 || (strcmp(aux4, "n")) == 0){
		aux1 = "Produtos.txt";
		aux2 = "Clientes.txt";
		aux3 = "Vendas_1M.txt";
	}

	else 
		main();

	sgv = malloc(sizeof(struct sgv));

/*
 abrir o ficheiro com os codigos dos produtos
*/
	sgv -> catp = queryUmProd(aux1);

/*
 abrir o ficheiro com os codigos dos clientes
*/
	sgv -> catc = queryUmCli(aux2);

/*
 abrir o ficheiro com os codigos das vendas e insercao destes (apenas os validos) num array e num novo ficheiro
*/
	queryUmVendas(aux3, sgv -> catp, sgv -> catc, &(sgv -> fat), &(sgv -> vendas));

/*
 inicio do menu de opcoes
*/

	while(r){
		opcao = menu();

		switch(opcao){
    		case 1 : {
    			printf("Que ficheiro pretendes ler?\n");
    			printf(" 1. Produtos \n");
    			printf(" 2. Clientes \n");
    			printf(" 3. Vendas \n");
    			scanf("%d",&opcao);

	    		if(opcao == 1){
	    			aux1 = malloc(50*sizeof(char));
	    			printf("Indique o nome do ficheiro que pretende ler.\n");
	    			scanf("%s", aux1);
	    		
					sgv -> catp = queryUmProd(aux1);
					queryUmVendas(aux3, sgv -> catp, sgv -> catc, &(sgv -> fat), &(sgv -> vendas));
    				break;
    			}
    			else if(opcao == 2){
    				aux2 = malloc(50*sizeof(char));
    				printf("Indique o nome do ficheiro que pretende ler.\n");
	    			scanf("%s", aux2);
    			
					sgv -> catc = queryUmCli(aux2);
					queryUmVendas(aux3, sgv -> catp, sgv -> catc, &(sgv -> fat), &(sgv -> vendas));
    				break;
    			}
	    		else if(opcao == 3){
	    			aux3 = malloc(50*sizeof(char));
	    			printf("Indique o nome do ficheiro que pretende ler.\n");
	    			scanf("%s", aux3);
	    		
					queryUmVendas(aux3, sgv -> catp, sgv -> catc, &(sgv -> fat), &(sgv -> vendas));
    				break;
    			
	    		}
	    		else 
	    			printf("Opção inválida.\n");
	    		break;
	    	}	

    		case 2 :{
    			queryDois(sgv -> catp);
				break;
    		}

    		case 3 :{
    			queryTres(sgv -> fat);
    			break;
    		}

    		case 4 : {
    			queryQuatro(sgv -> catp, sgv -> fat);
    			break;
    		}

    		case 5 :{
    			queryCinco(sgv -> catc, sgv -> vendas);
    			break;
    		}

    		case 6 :{
    			querySeis(sgv -> catp, sgv -> catc, sgv -> fat, sgv -> vendas);
    			break;
    		}

    		case 7 :{
    			querySete(sgv -> vendas);
    			break;
    		}

    		case 8 :{
    			queryOito(sgv -> fat);
    			break;
    		}

    		case 9 :{
    			queryNove(sgv -> catc, sgv -> vendas);
    			break;
    		}

    		case 10:{
    			queryDez(sgv -> vendas);
    			break;
    		}

    		case 11:{
    			queryOnze(sgv -> vendas, sgv -> catp);
    			break;
    		}

    		case 12:{
    			queryDoze(sgv -> vendas);
    			break;
    		}

    		case 13: {
    			printf("See you later!\n");
    			r = 0;
    			break;
    		}

    		default:
    			break;
    	}
	}

	return 0;
}
