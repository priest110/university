#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ioctl.h>

/**
Funcao que cria o menu principal do programa
*/
int menu(){
    int opcao;

    printf("Menu: \n");
    printf(" 1. Ler Ficheiro.\n");
    printf(" 2. Determinar a lista dos produtos iniciados por uma dada letra.\n");
    printf(" 3. Determinar o nºtotal de vendas e o total facturado com um produto em determinado mês.\n");
    printf(" 4. Determinar a lista ordenada dos códigos dos produtos que ninguém comprou.\n");
    printf(" 5. Determinar a lista ordenada de códigos de clientes que realizaram compras em todas as filiais.\n");
    printf(" 6. Determinar o nº de clientes registados que não realizaram compras e o nº de produtos que ninguém comprou.\n");
    printf(" 7. Criar uma tabela com o nºtotal de produtos comprados por um dado cliente.\n");
    printf(" 8. Determinar o total de vendas registadas num determinado intervalo de meses e o total facturado.\n");
    printf(" 9. Determinar os clientes que compraram determinado produto numa determinada filial.\n");
    printf(" 10.Determinar a lista dos produtos que um dado cliente mais comprou (por quantidade).\n");
    printf(" 11.Criar uma lista dos N produtos mais vendidos em todo o ano.\n");
    printf(" 12.Dado um cliente determinar quais os 3 produtos em que mais gastou dinheiro durante o ano.\n");
    printf(" 13.Sair \n");
    scanf("%d",&opcao);

    return opcao;   
}

/*
Funcao que, dada uma lista, imprime no ecra uma dada seccao da mesma
*/
void imprimeStrings(char** strings, int quantos, int i1, int i2){
	int i;
	for(i = i1; i < i2 && i < quantos; i++){
		printf("%s\n", strings[i]);
	}
}

/*
Funcao que, dada uma lista, imprime no ecra uma dada seccao da mesma
*/
void imprimeDuasStringsNP(char** strings1, char** strings2, int quantos1, int quantos2, int i1, int i2){
	int i;

	for(i = i1; i < i2 && i < quantos1 && i < quantos2; i++){
		printf("%s 'N' | %s 'P'\n", strings1[i], strings2[i]);
	}

	if((i <= i2) && (i < quantos1)){
		for(; i < i2 && i < quantos1; i++)
			printf("%s 'N'\n", strings1[i]);
	}

	else if((i <= i2) && (i < quantos2)){
		for(; i < i2 && i < quantos2; i++)
			printf("            %s 'P'\n", strings2[i]);
	}
}

/*
Funcao que, dada uma lista e a sua dimensao, cria um navegador para esta
*/
void navegadorListas(char** strings, int quantos){
	int numPags, pagina, flag, aux, i1, i2;
	struct winsize win;

	ioctl(0, TIOCGWINSZ, &win);
	flag = 1;
	numPags = (quantos/(win.ws_row));
	
   	printf("Que pagina pretende ler? (0 a %d)\n", numPags);
	scanf("%d", &pagina);

    while(flag){
    	if(pagina < 0 || pagina > numPags)
    		flag = 0;
    	else{
    		printf("Página %d\n", pagina);
    		i1 = pagina*((win.ws_row)-3);
    		i2 = (pagina+1)*((win.ws_row)-3);
    		imprimeStrings(strings, quantos, i1, i2);

    		printf("Acabar a leitura(0)/ Próxima Página(1)/ Página Anterior(2)\n");
    		scanf("%d", &aux);
    		if(aux == 1)
    			pagina++;
    		else if(aux == 2)
    			pagina--;
    		else 
    			flag = 0;
    	}
    }

    free(strings);
}

/*
Funcao que, dada uma lista e a sua dimensao, cria um navegador para esta
*/
void navegadorDuasListasNP(char** strings1, char** strings2, int quantos1, int quantos2){
	int numPags, pagina, flag, aux, i1, i2;
	struct winsize win;

	ioctl(0, TIOCGWINSZ, &win);
	flag = 1;

	if(quantos1 > quantos2)
		numPags = (quantos1/(win.ws_row));
	else
		numPags = (quantos2/(win.ws_row));
	
   	printf("Que pagina pretende ler? (0 a %d)\n", numPags);
	scanf("%d", &pagina);

    while(flag){
    	if(pagina < 0 || pagina > numPags)
    		flag = 0;
    	else{
    		printf("Página %d\n", pagina);
    		i1 = pagina*((win.ws_row)-3);
    		i2 = (pagina+1)*((win.ws_row)-3);
    		imprimeDuasStringsNP(strings1, strings2, quantos1, quantos2, i1, i2);

    		printf("Acabar a leitura(0)/ Próxima Página(1)/ Página Anterior(2)\n");
    		scanf("%d", &aux);
    		if(aux == 1)
    			pagina++;
    		else if(aux == 2)
    			pagina--;
    		else 
    			flag = 0;
    	}
    }

    free(strings1);
    free(strings2);
}