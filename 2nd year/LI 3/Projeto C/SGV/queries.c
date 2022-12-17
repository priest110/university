#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "catClientes.h"
#include "catProd.h"
#include "faturacao.h"
#include "gestaoDeFiliais.h"
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


/*
Funcao principal da 1ª query - Produtos
*/
CatProd queryUmProd(char *ficheiro){
	FILE *fp;
	CatProd catp;

	fp = fopen(ficheiro, "r");
	if(fp == NULL){
	printf("I/O error");
		exit(1);
	}
	catp = initCatProd();

	printf("O ficheiro lido é o %s\n", ficheiro);
	catp = lerProdutos(fp, catp);
	
	fclose(fp);

    return catp;
}

/*
Funcao principal da 1ª query - Clientes
*/
CatCli queryUmCli(char *ficheiro){
	FILE *fp;
	CatCli catc;

	fp = fopen(ficheiro, "r");
	if(fp == NULL){
	printf("I/O error");
		exit(1);
	}
	catc = initCatCli();

	printf("O ficheiro lido é o %s\n", ficheiro);
	catc = lerClientes(fp, catc);
	
	fclose(fp);

    return catc;
}

/*
Funcao principal da 1ª query - Vendas
*/
void queryUmVendas(char *ficheiro, CatProd catp, CatCli catc, FatGlobal *fat, VendasTotal *vendas){
	FILE *fp;

	fp = fopen(ficheiro, "r");
	if(fp == NULL){
		printf("I/O error");
		exit(1);
	}
	(*fat) = initFatGlobal();
	(*vendas) = initVendasTotal();

	printf("O ficheiro lido é o %s\n", ficheiro);
	lerVendas(fp, catp, catc, fat, vendas);
	
	fclose(fp);
}

/*
Funcao principal da 2ª query
*/
void queryDois(CatProd catp){
	char* aux = malloc(sizeof(char));
	char** strings;
	ListaProd lista;
	char ajuda;
	int q;

	printf("Indique a letra que pretende analisar.\n");
	scanf("%s", aux);
   	ajuda = aux[0];

    lista = listaPorLetraProd(catp, ajuda);
    q = quantosProdLista(lista);
    strings = listaProdParaString(lista);
    printf("Há %d produtos iniciados pela letra %s.\n", q, aux);
    navegadorListas(strings, q);

    free(lista);
}

/*
Funcao principal da 3ª query
*/
void queryTres(FatGlobal fat){
	char *aux = malloc(TAMPROD*sizeof(char));
	int mes, opcao;
	float total1N, total2N, total3N, total1P, total2P, total3P;
	int quantos1N, quantos2N, quantos3N, quantos1P, quantos2P, quantos3P;
	Produto p;

	printf("Indique o produto e o mês que pretende analisar. ('produto' 'mes')\n");
	scanf("%s %d", aux, &mes);

	p = criaProd(aux);

	total1N = faturadoProd(fat, 1, mes, "N", p);
	total2N = faturadoProd(fat, 2, mes, "N", p);
	total3N = faturadoProd(fat, 3, mes, "N", p);
	total1P = faturadoProd(fat, 1, mes, "P", p);
	total2P = faturadoProd(fat, 2, mes, "P", p);
	total3P = faturadoProd(fat, 3, mes, "P", p);

	quantos1N = quantosProdVendidos(fat, 1, mes, "N", p);
	quantos2N = quantosProdVendidos(fat, 2, mes, "N", p);
	quantos3N = quantosProdVendidos(fat, 3, mes, "N", p);
	quantos1P = quantosProdVendidos(fat, 1, mes, "P", p);
	quantos2P = quantosProdVendidos(fat, 2, mes, "P", p);
	quantos3P = quantosProdVendidos(fat, 3, mes, "P", p);

	printf("Pretende receber os resultados totais(0), ou por filial(1)?\n");
	scanf("%d", &opcao);

	if(opcao == 0){
		total1N = total1N + total2N + total3N;
		total1P = total1P + total2P + total3P;
		quantos1N = quantos1N + quantos2N + quantos3N;
		quantos1P = quantos1P + quantos2P + quantos3P;

    	printf("Mês: %d\n", mes);
    	printf("Produto: %s\n", aux);
    	printf("Total faturado em vendas 'normais': %f (%d vendas)\n", total1N, quantos1N);
    	printf("Total faturado em vendas 'promocionais': %f (%d vendas)\n", total1P, quantos1P);
    }

	else if(opcao == 1){

		printf("Mês: %d\n", mes);
    	printf("Produto: %s\n", aux);
    	printf("Total faturado em vendas 'normais' na filial 1: %f (%d vendas)\n", total1N, quantos1N);
    	printf("Total faturado em vendas 'promocionais' na filial 1: %f (%d vendas)\n", total1P, quantos1P);
    	printf("Mês: %d\n", mes);
    	printf("Produto: %s\n", aux);
    	printf("Total faturado em vendas 'normais' na filial 2: %f (%d vendas)\n", total2N, quantos2N);
    	printf("Total faturado em vendas 'promocionais' na filial 2: %f (%d vendas)\n", total2P, quantos2P);
    	printf("Mês: %d\n", mes);
    	printf("Produto: %s\n", aux);
    	printf("Total faturado em vendas 'normais' na filial 3: %f (%d vendas)\n", total3N, quantos3N);
    	printf("Total faturado em vendas 'promocionais' na filial 3: %f (%d vendas)\n", total3P, quantos3P);
	}
	else;
}

/*
Funcao principal da 4ª query
*/
void queryQuatro(CatProd catp, FatGlobal fat){
	ListaProd aux;
	char** strings;
	int q, filial;

	printf("Pretende obter resultados globais(0), ou de uma dada filial(1)?\n");
	scanf("%d", &q);

	if(q == 0){
		aux = produtosNaoVendidos(catp, fat);
		q = quantosProdLista(aux);
	}

	else if(q == 1){
		printf("Qual filial pretende analizar?(1, 2 ou 3)\n");
		scanf("%d", &filial);

		aux = produtosNaoVendidosFilial(catp, fat, filial);
		printf("Filial %d:\n", filial);
		q = quantosProdLista(aux);
	}

	else
		queryQuatro(catp, fat);

	strings = listaProdParaString(aux);
	printf("Há %d produtos que nunca foram comprados.\n", q);
	
	navegadorListas(strings, q);

	free(aux);
}

/*
Funcao principal da 5ª query
*/
void queryCinco(CatCli cat, VendasTotal vendas){
	ListaCli aux;
	char** strings;
	int q;

	aux = clientesEmTodasAsFiliais(cat, vendas);
	q = quantosCliLista(aux);
	strings = listaCliParaString(aux);

	printf("Há %d clientes que compraram produtos em todas as filiais.\n", q);
	
	navegadorListas(strings, q);

	free(aux);
}

/*
Funcao principal da 6ª query
*/
void querySeis(CatProd catp, CatCli catc, FatGlobal fat, VendasTotal vendas){
	ListaProd lisP;
	ListaCli lisC;
	int quantosP, quantosC;

	lisP = produtosNaoVendidos(catp, fat);
	lisC = clientesQueNaoCompraram(catc, vendas);

	quantosP = quantosProdLista(lisP);
	quantosC = quantosCliLista(lisC);

	printf("Houve %d clientes que nunca realizaram compras, bem como %d produtos que nunca foram comprados.\n", quantosC, quantosP);

	free(lisP);
	free(lisC);
}

/*
Funcao principal da 7ª query
*/
void querySete(VendasTotal vendas){
	int** totais;
	int i, j, total;
	char* aux = malloc(TAMCLI*sizeof(char));
	Cliente c;

	printf("Indique o cliente que pretende analisar.\n");
	scanf("%s", aux);

	c = criaCli(aux);
	totais = totalComprasPorMes(vendas, c);

	total = 0;
	for(i = 0; i < NUMFILIAIS; i++){
		for(j = 0; j < NUMMESES; j++)
			total += totais[i][j];
	}

	printf("No total, o cliente comprou %d produtos.\n", total);
	for(i = 0; i < NUMFILIAIS; i++){
		printf("Filial %d:\n", (i+1));
		for(j = 0; j < NUMMESES; j++)
			printf("Mês %d - Total de Produtos Comprados %d\n", (j+1), totais[i][j]);
	}

}

/*
Funcao principal da 8ª query
*/
void queryOito(FatGlobal fat){
	int mes1, mes2, i;
	double d;
	Par par;
	printf("Insira os meses que pretende analisar(1 a 12)\n");
	scanf("%d %d", &mes1, &mes2);

	if((mes1 < mes2) && (mes1 <= 12) && (mes1 >= 1) && (mes2 <=12) && (mes2 >= 1)){
		par = totalEmIntervalo(fat, mes1, mes2);
		printf("Resultados entre o %dº e %dº mês:\n", mes1, mes2);
		abrePar(par, &i, &d);
		printf("Total de vendas: %d\n", i);
		printf("Total faturado: %f\n", d);
	}

	else if((mes1 >= mes2) && (mes1 <= 12) && (mes1 >= 1) && (mes2 <=12) && (mes2 >= 1)){
		par = totalEmIntervalo(fat, mes2, mes1);
		printf("Resultados entre o %dº e %dº mês:\n", mes2, mes1);
		abrePar(par, &i, &d);
		printf("Total de vendas: %d\n", i);
		printf("Total faturado: %f\n", d);
	}

	else
		queryOito(fat);
}

/*
Funcao principal da 9ª query
*/
void queryNove(CatCli cat, VendasTotal vendas){
	char *aux = malloc(TAMPROD*sizeof(char));
	char** strings1;
	char** strings2;
	int filial, q1, q2, q;
	ListaCli lista, lista1;
	Produto p;

	printf("Indique o produto e a filial que pretende analisar. ('produto' 'filial')\n");
	scanf("%s %d", aux, &filial);
	p = criaProd(aux);

	lista = clientesQueCompraramCertoProduto(cat, vendas, p, filial, 'N'); 
	lista1 =  clientesQueCompraramCertoProduto(cat, vendas, p, filial, 'P');

	strings1 = listaCliParaString(lista);
	strings2 = listaCliParaString(lista1);
	q1 = quantosCliLista(lista);
	q2 = quantosCliLista(lista1);
	q = q1 + q2;
	printf("%d\n", q1);
	printf("%d\n", q2);
	printf("Ha %d clientes que compraram o produto %s na filial %d.\n", q, aux, filial);

	navegadorDuasListasNP(strings1, strings2, q1, q2);

	free(lista);
	free(lista1);
}

/*
Funcao principal da 10ª query
*/
void queryDez(VendasTotal vendas){
	char* aux = malloc(TAMCLI*sizeof(char));
	int mes, i, quantos;
	ListaParesInt lista;
	Cliente c;
	char** strings;
	int* quantidades;

	printf("Indique o cliente e o mês que pretende analisar.\n");
	scanf("%s %d", aux, &mes);

	c = criaCli(aux);
	lista = listaProdComprados(vendas, mes, c);
	quantos = quantosProdQuantidade(lista);
	strings = getListaStringsProdQuantos(lista);
	quantidades = getListaQuantidadesProdQuantos(lista);

	for(i = 0; i < quantos; i++){
		printf("Produto: %s - Nº de produtos comprados: %d\n", strings[i], quantidades[i]);
	}

	free(lista);
	free(strings);
}

/*
Funcao principal da 11ª query
*/
void queryOnze(VendasTotal vendas, CatProd cat){
	int i, N, filial;
	ListaTriplos lista;
	char** strings;
	int* nCli;
	int* quantidades;

	printf("Indique o número de produtos que pretende receber e a filial a analisar.\n");
	scanf("%d %d", &N, &filial);

	lista = topNProdutosMaisVendidos(vendas, cat, N, filial);

	strings = getListaStringsProdQuantos3(lista);
	quantidades = getListaQuantidadesProdQuantos3(lista);
	nCli = getListaNumCli(lista);

	for(i = 0; i < N; i++){
		printf("Produto: %s\n", strings[i]);
		printf("Nº de Clientes que o compraram: %d\n", nCli[i]);
		printf("Nº de Produtos Comprados: %d\n", quantidades[i]);
	}

}

/*
Funcao principal da 12ª query
*/
void queryDoze(VendasTotal vendas){
	char* aux = malloc(TAMCLI*sizeof(char));
	int i, quantos;
	ListaParesDouble lista;
	Cliente c;
	char** strings;
	double* totais;

	printf("Indique o cliente que pretende analisar.\n");
	scanf("%s", aux);

	c = criaCli(aux);
	lista = topTresProdMaisComprados(vendas, c);
	quantos = quantosProdQuantidade2(lista);
	strings = getListaStringsProdQuantos2(lista);
	totais = getListaQuantidadesProdQuantos2(lista);

	for(i = 0; i < quantos; i++){
		printf("Produto: %s - Total gasto no produto: %f\n", strings[i], totais[i]);
	}

	free(lista);
	free(strings);
}