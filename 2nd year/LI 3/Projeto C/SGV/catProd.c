#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "catProd.h"

#define TAMPROD 7
#define TAMCATPROD 26
#define BUFFER 10

/* ESTRUTURAS DE DADOS */

/*
 tipo "Produto"
*/
typedef char* Produto;

/*
árvore para guardar produtos
*/
typedef struct nodoProd{
	Produto produto;
	struct nodoProd *dir, *esq;
} *ArvProd;

/*
Catalogo de produtos composto por diversas árvores,
sendo que cada uma suporta os produtos comecados por uma mesma letra
*/
typedef ArvProd* CatProd;

/*
Lista de produtos
*/
typedef struct listaProd{
	Produto* produtos;
	int quantos;
} *ListaProd;


/* FUNCOES "ELEMENTARES" */

/* 
Funcao de validacao dos produtos, i.e., verifica se um dado produto possui as caracteristicas que fazem do seu codigo valido
(Se possui 6 caracteres, sendo os dois primeiros duas letras maiusculas e os ultimos 4 representam um numero entre 1000 e 9999)
*/
int valProdutos(char *produto){
	int n;

	if (strlen(produto) == 6){
		if(produto[0] >= 'A' && produto[0] <= 'Z' && produto[1] >= 'A' && produto[1] <= 'Z'){
			n = atoi(produto + 2);
			if(n >= 1000 && n <= 9999)
				return 1;
		}
	}
	return 0;
}

/*
Funcao que transforma um codigo de caracteres num produto
*/
Produto criaProd(char* codProd){
	Produto p = malloc(sizeof(Produto));
	int i;

	for(i = 0; i < TAMPROD - 1; i++)
		p[i] = codProd[i];
	p[i] = '\0';

	return p;
}

/*
Funcao que transforma um produto num codigo de caraceteres
*/
char* getCodProd(Produto p){
	char* aux = malloc(sizeof(char*));
	int i;

	for(i = 0; i < TAMPROD; i++)
		aux[i] = p[i];

	return aux;
}

/*
Funcao que duplica um produto
*/
Produto dupProd(Produto p){
	Produto aux = malloc(sizeof(Produto));
	int i;

	for(i = 0; i < TAMPROD; i++)
		aux[i] = p[i];

	return aux;
}

/*
Funcao que compara dois produtos, a ver se sao iguais
*/
int compProd(Produto p1, Produto p2){
	int i = 0;
	int flag = 0;
	while(flag == 0){
		if(p1[i] < p2[i])
			flag = -1;
		if(p1[i] > p2[i])
			flag = 1;
		if(p1[i] == '\0')
			break;
		i++;
	}

	return flag;
}

/*
Funcao que insere um dado produto numa arvore
*/
ArvProd insereProdArv(ArvProd arv, Produto p){
	if(arv == NULL){
		arv = malloc(sizeof(struct nodoProd));
		arv -> produto = dupProd(p);
		arv -> dir = NULL;
		arv -> esq = NULL;
	}

	else if((compProd(p, arv->produto)) < 0)
		arv -> esq = insereProdArv(arv -> esq, p);

	else if((compProd(p, arv->produto)) > 0)
		arv -> dir = insereProdArv(arv -> dir, p);

	return arv;
}

/*
Funcao que verifica se existe um dado produto numa arvore
*/
int existeProdArv(ArvProd arv, Produto p){
	int r;

	if(arv == NULL)
		return 0;

	r = compProd(p, arv->produto);

	if(r == 0)
		return 1;

	else if(r > 0)
		r = existeProdArv(arv->dir, p);

	else if(r < 0)
		r = existeProdArv(arv->esq, p);

	return r;
}

/*
Funcao que indica quantos produtos estao inseridos numa arvore
*/
int quantosProdArv(ArvProd arv){
	int total = 0;

	if(arv == NULL)
		return total;

	else{
		total += quantosProdArv(arv->esq);
		total += quantosProdArv(arv->dir);
		total++;
	}

	return total;
}

/*
Funcoes que criam uma lista a partir de uma arvore
*/
ListaProd listaPorArvProdAux(ArvProd arv, ListaProd l){
	if(arv != NULL){
		l = listaPorArvProdAux(arv->esq, l);

		l -> produtos[l -> quantos] = dupProd(arv->produto);
		(l -> quantos)++;
		
		l = listaPorArvProdAux(arv->dir, l);
	}
	return l;
}

ListaProd listaPorArvProd(ArvProd arv){
	ListaProd l = initListaProd();
	int q = quantosProdArv(arv);
	l -> produtos = malloc(q*sizeof(Produto));
	l = listaPorArvProdAux(arv, l);
	return l;
}


/* FUNCOES "COMPLEXAS" */

/*
Funcao que cria um novo catalogo de produtos
*/
CatProd initCatProd(){
	CatProd cat = malloc(TAMCATPROD*(sizeof(ArvProd)));
	int i;

	for(i = 0; i < TAMCATPROD; i++)
		cat[i] = NULL;

	return cat;
}

/*
Funcao que insere um dado produto num catalogo
*/
CatProd insereProdCat(CatProd cat, Produto p){
	int i = (int) (p[0] - 'A');
	cat[i] = insereProdArv(cat[i], p);
	return cat;
}

/*
Funcao que verifica se existe um dado produto num catalogo
*/
int existeProdCat(CatProd cat, Produto p){
	int i = (int) (p[0] - 'A');
	int r = existeProdArv(cat[i], p);
	return r;
}

/*
Funcao que inicializa uma lista de produtos
*/
ListaProd initListaProd (){
	ListaProd l = malloc(sizeof(struct listaProd));
	l -> quantos = 0;
	l -> produtos = NULL;
	return l;
}

/*
Funcao que cria uma lista com todos os elementos comecados por uma dada letra
*/
ListaProd listaPorLetraProd(CatProd cat, char letra){
	int i = (int) (letra - 'A');
	ListaProd l;
	l = listaPorArvProd(cat[i]);
	return l;
}

/*
Funcao que, dada uma lista de produtos, transforma numa lista de strings
*/
char** listaProdParaString(ListaProd lista){
	int i, quantos = lista -> quantos;
	char** strings = malloc(quantos*sizeof(char*));

	for(i = 0; i < quantos; i++)
		strings[i] = getCodProd((lista -> produtos)[i]);

	return strings;
}

/*
Funcao que retorna quantos elementos ha numa lista
*/
int quantosProdLista(ListaProd l){
	return (l -> quantos);
}

/*
Funcao que, dada uma lista e uma posicao, devolve o produto nessa posicao
*/
Produto prodPorPos(ListaProd lis, int pos){
	Produto p = dupProd((lis -> produtos)[pos]);
	return p;
}

/*
Funcao que, dada uma lista e um produto, insere esse produto na lista
*/
ListaProd insereProdLis(ListaProd lis, Produto p){
	lis -> produtos[lis->quantos] = dupProd(p);
	(lis -> quantos)++;
	return lis;
}

/*
Funcao que, dado um tamanho N, alloca o espaco necessario para suportar N produtos
*/
ListaProd listaPorTamanhoProd(int tamanho){
	ListaProd lis = initListaProd();
	lis -> produtos = malloc(tamanho*(sizeof(Produto)));
	return lis;
}

/*
Funcao que, dadas duas listas, junta as
*/
ListaProd fundeListasProd(ListaProd l1, ListaProd l2){
	ListaProd aux = initListaProd();
	int i, j;

	aux -> quantos = (l1 -> quantos) + (l2 -> quantos);
	aux -> produtos = malloc((aux -> quantos)*(sizeof(Produto)));

	for(i = 0; i < (l1 -> quantos); i++)
		(aux -> produtos)[i] = (l1 -> produtos)[i];

	for(j = 0; i < (aux -> quantos); j++, i++)
		(aux -> produtos)[i] = (l2 -> produtos)[j];
	
	return aux;
}

/*
Funcao que le os codigos dos produtos guardados num ficheiro, e os guarda no array "global", apos verificada a sua validade
*/
CatProd lerProdutos(FILE *fp, CatProd catp){
	char* aux;
	char str[BUFFER];
	Produto p;
	int produtosLidos, produtosVal;

	produtosLidos = produtosVal = 0;

/*
 ciclo que insere todos os codigos dos produtos num array
*/
	while(fgets(str, BUFFER, fp)){
		produtosLidos++;
		aux = strtok(str, "\n\r");
		if(valProdutos(aux)){
			produtosVal++;
			p = criaProd(aux);
			catp = insereProdCat(catp, p);
		}
	}

	printf("Número de linhas lidas: %d\n", produtosLidos);
	printf("Número de linhas inseridas: %d\n", produtosVal);

	return catp;
}