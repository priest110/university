#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "catProd.h"
#include "catClientes.h"
#include "faturacao.h"

#define TAMCATPROD 26
#define NUMLETRAS 26
#define NUMMESES 12
#define NUMFILIAIS 3
#define BUFFER 10

/* ESTRUTURAS DE DADOS */

/*
tipo "Venda Inicial"
*/
typedef struct vendaInicial{
	Produto produto;
	Cliente cliente;
	int quantidade;
	double preco;
	char* tipo;
	int mes;
	int filial;	
} *VendaInicial;

/*
tipo "Fatura"
*/
typedef struct fatura{
	Produto produto;
	double preco;
	int quantidade;
	char* tipo;
} *Fatura;

/*
Árvore para guardar faturas
*/
typedef struct nodoFat{
	Fatura fatura;
	struct nodoFat *dir, *esq;
} *ArvFat;

/*
Faturacao global da empresa, dividida em várias árvores - ha 12 conjuntos de árvores diferentes,
um para cada mes, sendo que cada conjunto possui árvores onde estao inseridos as faturas, ordenadas pelo produto
*/
typedef ArvFat*** FatGlobal;

/*
Estrutura de dados que define um par de inteiro/double
*/
typedef struct par{
	int elemI;
	double elemD;
} *Par;

/* FUNCOES ELEMENTARES */

/* 
Funcao de validacao das vendas, i.e, verifica se todos os parametros desta sao validos
*/
int valVendas(char *venda, CatProd catp, CatCli catc){
	char* aux = malloc(sizeof(char*));
	char str[BUFFER];
	int num;
	int i = 0;
	int j = 0;
	int r;
	double preco;
	Produto p;
	Cliente c;

	for(i = 0; i < BUFFER; i++)
		aux[i] = '\0';

	for(j = 0; venda[j] != ' '; j++)
		aux[j] = venda[j];

	p = criaProd(aux);
	r = existeProdCat(catp, p);

	if(r){ /* so entram as vendas com codigos de produtos validos, ou seja, que existam */

		for(i = 0; i < BUFFER; i++)
			str[i] = '\0';

		for(i = 0, j = j + 1; venda[j] != ' '; j++, i++)
			str[i] = venda[j];

		preco = atof(str);
		if(preco < 0 || preco > 999.99)
			r = 0;

		if(r){ /* so entram as vendas com precos validos (compreendidos entre 0 e 999.99 euros) */

			for(i = 0; i < BUFFER; i++)
				str[i] = '\0';

			for(i = 0, j = j + 1; venda[j] != ' '; j++, i++)
				str[i] = venda[j];

			num = atoi(str);
			if(num < 1 || num > 200)
				r = 0;

			if (r){ /* so entram as vendas com um numero de produtos vendidos validos (entre 1 e 200 produtos) */

				j++;
				if(venda[j] != 'N' && venda[j] != 'P')
					r = 0;
				j++;

				if (r){ /* so entram as vendas com codigos de venda validos (i.e, vendas efetuadas em "epoca" Normal ou de Promocao) */

					for(i = 0; i < BUFFER; i++)
						aux[i] = '\0';

					for(i = 0, j = j + 1; venda[j] != ' '; i++, j++)
						aux[i] = venda[j];

					c = criaCli(aux);
					r = existeCliCat(catc, c);

					if(r){ /* so entram as vendas associadas a clientes existentes */

						for(i = 0; i < BUFFER; i++)
							str[i] = '\0';

						for(i = 0, j = j + 1; venda[j] != ' '; j++, i++)
							str[i] = venda[j];

						num = atoi(str);
						if(num < 1 || num > 12)
							r = 0;

						if(r){ /* so entram as vendas efetuadas no mesmo valido (ou seja, entre 1 e 12) */
			
							j++;
							num = atoi(venda + j);
							if(num != 1 && num != 2 && num != 3)
								r = 0;
						}
					}
					else 
						return 0;
				}
			}
		}
	}
	else 
		return 0;
	return r;
}

/*
Funcao que transforma um codigo de caracteres numa venda inicial
*/
VendaInicial criaVendaInicial(char *codVenda){
	VendaInicial venda;
	char* aux[7];
	int i = 0;
	Produto p;
	Cliente c;

	venda = malloc(sizeof(struct vendaInicial));
	char* token = strtok(codVenda, " ");
	while(!(token == NULL)){
		aux[i] = strdup(token);
		token = strtok(NULL, " ");
		i++;
	}

	p = criaProd(aux[0]);
	c = criaCli(aux[4]);

	venda -> produto = dupProd(p);
	venda -> cliente = dupCli(c);
	venda -> preco = atof(aux[1]);
	venda -> quantidade = atoi(aux[2]);
	venda -> tipo = strdup(aux[3]);
	venda -> mes = atoi(aux[5]);
	venda -> filial = atoi(aux[6]);

	return venda;
}

/*
Funcao que, dada uma venda inicial, devolve o seu mes
*/
int mesVendaInicial(VendaInicial venda){
	return (venda -> mes);
}

/*
Funcao que, dada uma venda inicial, devolve a sua filial
*/
int filialVendaInicial(VendaInicial venda){
	return (venda -> filial);
}

/*
Funcao que, dada uma venda inicial, devolve o seu produto
*/
Produto produtoVendaInicial(VendaInicial venda){
	Produto p = dupProd(venda -> produto);
	return p;
}

/*
Funcao que, dada uma venda inicial, devolve o seu cliente
*/
Cliente clienteVendaInicial(VendaInicial venda){
	Cliente c = dupCli(venda -> cliente);
	return c;
}

/*
Funcao que, dada uma venda inicial, devolve o seu preco
*/
double precoVendaInicial(VendaInicial venda){
	return (venda -> preco);
}

/*
Funcao que, dada uma venda inicial, devolve a sua quantidade
*/
int quantidadeVendaInicial(VendaInicial venda){
	return (venda -> quantidade);
}

/*
Funcao que, dada uma venda inicial, devolve o seu tipo
*/
char* tipoVendaInicial(VendaInicial venda){
	char* tipo = strdup(venda -> tipo);
	return tipo;
}

/*
Funcao que transforma um codigo de caracteres numa fatura
*/
Fatura criaFatura(VendaInicial venda){
	Fatura fatura = malloc(sizeof(struct fatura));

	fatura -> produto = dupProd(venda -> produto);
	fatura -> preco = venda -> preco;
	fatura -> quantidade = venda -> quantidade;
	fatura -> tipo = strdup(venda -> tipo);

	return fatura;
}

/*
Funcao que duplica uma fatura
*/
Fatura dupFatura(Fatura f){
	Fatura aux = malloc(sizeof(struct fatura));

	aux -> produto = dupProd(f -> produto);
	aux -> preco = f -> preco;
	aux -> quantidade = f -> quantidade;
	aux -> tipo = strdup(f -> tipo);

	return aux;
}

/*
Funcao que, dado um par, retorna o inteiro
*/
void abrePar(Par par, int *i, double *d){
	(*i) = par -> elemI;
	(*d) = par -> elemD;
}

/*
Funcao que insere uma fatura numa arvore
*/          
ArvFat insereFatAux(ArvFat arv, Fatura f){
	if(arv == NULL){
		arv = malloc(sizeof(struct nodoFat));
		arv -> fatura = dupFatura(f);
		arv -> dir = NULL;
		arv -> esq = NULL;
	}

	else if((compProd((arv -> fatura) -> produto, (f -> produto))) > 0){
		arv -> esq = insereFatAux(arv -> esq, f);
	}

	else if((compProd((arv -> fatura) -> produto, (f -> produto))) <= 0){
		arv -> dir = insereFatAux(arv -> dir, f);
	}
	return arv;
}

/*
Funcao que dada uma arvore de faturas, procura as faturas associadas a um dado produto,
e calcula o total faturado nas vendas desse produto, para um dado tipo de venda
*/
double faturadoProdAux(ArvFat arv, char *tipo, Produto p){
	double total = 0;

	if(arv != NULL){
		int aux = compProd((arv -> fatura) -> produto, p);
		int comp = strcmp((arv -> fatura) -> tipo, tipo);

		if(aux == 0 && comp == 0){
			total = (double) ((arv -> fatura) -> quantidade)*((arv -> fatura) -> preco);
			total += faturadoProdAux((arv -> dir), tipo, p);
		}

		else if(aux <= 0)
			total = faturadoProdAux((arv -> dir), tipo, p);

		else if(aux > 0)
			total = faturadoProdAux((arv -> esq), tipo, p);
	}

	return total;
}

/*
Funcao que dada uma arvore de faturas, procura as faturas associadas a um dado produto,
e calcula o total de vendas desse produto, para um dado tipo de venda
*/
int quantosProdVendidosAux(ArvFat arv, char *tipo, Produto p){
	int total = 0;

	if(arv != NULL){
		int aux = compProd((arv -> fatura) -> produto, p);
		int comp = strcmp((arv -> fatura) -> tipo, tipo);

		if(aux == 0 && comp == 0){
			total = ((arv -> fatura) -> quantidade);
			total += quantosProdVendidosAux((arv -> dir), tipo, p);
		}

		else if(aux <= 0)
			total = quantosProdVendidosAux((arv -> dir), tipo, p);

		else if(aux > 0)
			total = quantosProdVendidosAux((arv -> esq), tipo, p);
	}

	return total;
}

/*
Funcao que, dada uma arvore de faturas e um produto, verifica se ha alguma fatura associada a esse produto
*/
int existeProdArvFat(ArvFat arv, Produto p){
	Produto aux;
	int existe = 0;

	if(arv != NULL){
		aux = (arv -> fatura) -> produto;
		existe = compProd(aux, p);

		if(existe == 0)
			existe = 1;

		else if(existe > 0)
			existe = existeProdArvFat(arv -> esq, p);

		else if(existe < 0)
			existe = existeProdArvFat(arv -> dir, p);
	}

	return existe;
}

/*
Funcao que, dada uma faturacao e um produto, verifica se esse produto foi vendido
*/
int existeProdFat(FatGlobal fat, Produto p){
	int i, j, existe, pos;
	pos = (int) (p[0] - 'A');
	existe = 0;

	for(i = 0; i < NUMFILIAIS && (existe == 0); i++){
		for(j = 0; j < NUMMESES && (existe == 0); j++){
				existe = existeProdArvFat(fat[i][j][pos], p);
		}
	}

	return existe;
}

/*
Funcao que, dada uma faturacao, um produto e uma filial, verifica se esse produto foi vendido nessa filial
*/
int existeProdFatFilial(FatGlobal fat, Produto p, int filial){
	int j, existe, pos;
	pos = (int) (p[0] - 'A');
	existe = 0;

	for(j = 0; j < NUMMESES && (existe == 0); j++){
		existe = existeProdArvFat(fat[filial-1][j][pos], p);
	}

	return existe;
}

/*
Funcao que, para uma dada faturacao e uma dada lista de produtos,
determina quais os produtos que nunca foram vendidos, devolvendo uma lista com estes
*/
ListaProd produtosNaoVendidosAux(ListaProd lis, FatGlobal fat){
	int quantos = quantosProdLista(lis);
	ListaProd aux = listaPorTamanhoProd(quantos);
	int existe, i;
	Produto p;

	for(i = 0; i < quantos; i++){
		p = prodPorPos(lis, i);
		existe = existeProdFat(fat, p);
		if(existe == 0)
			aux = insereProdLis(aux, p);
	}

	return aux;
}

/*
Funcao que, para uma dada faturacao, uma dada lista de produtos e uma dada filial,
determina quais os produtos que nunca foram vendidos nessa filial, devolvendo uma lista com estes
*/
ListaProd produtosNaoVendidosFilialAux(ListaProd lis, FatGlobal fat, int filial){
	int quantos = quantosProdLista(lis);
	ListaProd aux = listaPorTamanhoProd(quantos);
	int existe, i;
	Produto p;

	for(i = 0; i < quantos; i++){
		p = prodPorPos(lis, i);
		existe = existeProdFatFilial(fat, p, filial);
		if(existe == 0)
			aux = insereProdLis(aux, p);
	}

	return aux;
}

/*
Funcao que, determina o total de vendas e o total faturado numa determinada arvore
*/
Par totalEmIntervaloArv(ArvFat arv){
	Par par, aux;
	par = malloc(sizeof(struct par));
	par -> elemD = 0;
	par -> elemI = 0;

	if(arv != NULL){
		par -> elemI = (arv -> fatura) -> quantidade;
		par -> elemD = ((arv -> fatura) -> preco)*(par -> elemI);

		aux = totalEmIntervaloArv(arv -> dir);

		par -> elemI += aux -> elemI;
		par -> elemD += aux -> elemD;

		aux = totalEmIntervaloArv(arv -> esq);

		par -> elemI += aux -> elemI;
		par -> elemD += aux -> elemD;
	}

	return par;
}


/* FUNCOES "COMPLEXAS" */

/*
Funcao que cria uma nova faturacao
*/
FatGlobal initFatGlobal(){
	FatGlobal faturacao;
	int i, j, k;

	faturacao = malloc((sizeof(ArvFat**))*NUMFILIAIS);

	for(i = 0; i < NUMFILIAIS; i++)
		faturacao[i] = malloc(sizeof(ArvFat*)*NUMMESES);

	for(i = 0; i < NUMFILIAIS; i++){
		for(j = 0; j < NUMMESES; j++)
			faturacao[i][j] = malloc(sizeof(ArvFat)*NUMLETRAS);
	}

	for(i = 0; i < NUMFILIAIS; i++){
		for(j = 0; j < NUMMESES; j++){
			for(k = 0; k < NUMLETRAS; k++){
				faturacao[i][j][k] = NULL;
			}
		}
	}

	return faturacao;
}

/*
Funcao que insere uma dada fatura numa pagina de faturacao
*/
FatGlobal insereFat(FatGlobal fat, VendaInicial venda){
	Fatura f = criaFatura(venda);
	int mes = mesVendaInicial(venda);
	int filial = filialVendaInicial(venda);
	int aux = (int) (venda -> produto[0] - 'A');

	fat[filial-1][mes-1][aux] = insereFatAux(fat[filial-1][mes-1][aux], f);

	return fat;
}

/*
Funcao que, dada uma filial, um mes, um tipo de venda, e um produto,
devolve o total faturado com as vendas desse produto, nessa situacao
*/
double faturadoProd(FatGlobal fat, int filial, int mes, char *tipo, Produto p){
	int aux = (int) (p[0] - 'A');
	double total = 0;

	total = faturadoProdAux(fat[filial-1][mes-1][aux], tipo, p);

	return total;
}

/*
Funcao que, dada uma filial, um mes, um tipo de venda, e um produto,
devolve o total  de vendas desse produto, nessa situacao
*/
int quantosProdVendidos(FatGlobal fat, int filial, int mes, char *tipo, Produto p){
	int aux = (int) (p[0] - 'A');
	int total = 0;
	total = quantosProdVendidosAux(fat[filial-1][mes-1][aux], tipo, p);
	return total;
}

/*
Funcao que, para uma dada faturacao e um dado catalogo de produtos,
determina quais os produtos que nunca foram vendidos, devolvendo uma lista com estes
*/
ListaProd produtosNaoVendidos(CatProd cat, FatGlobal fat){
	ListaProd l = initListaProd();
	ListaProd aux1, aux2;
	int i;
	char letra;

	for(i = 0; i < TAMCATPROD; i++){
		letra = (char) ('A' + i);
		aux1 = listaPorLetraProd(cat, letra);
		aux2 = produtosNaoVendidosAux(aux1, fat);
		l = fundeListasProd(l, aux2);
	}

	return l;
}

/*
Funcao que, para uma dada faturacao, um dado catalogo de produtos, e uma dada filial,
determina quais os produtos que nunca foram vendidos nessa filial, devolvendo uma lista com estes
*/
ListaProd produtosNaoVendidosFilial(CatProd cat, FatGlobal fat, int filial){
	ListaProd l = initListaProd();
	ListaProd aux1, aux2;
	int i;
	char letra;

	for(i = 0; i < TAMCATPROD; i++){
		letra = (char) ('A' + i);
		aux1 = listaPorLetraProd(cat, letra);
		aux2 = produtosNaoVendidosFilialAux(aux1, fat, filial);
		l = fundeListasProd(l, aux2);
	}

	return l;
}

/*
Funcao que, para um dado intervalo de meses e uma dada faturacao, determina o numero total de vendas e o total faturado
*/
Par totalEmIntervalo(FatGlobal fat, int mes1, int mes2){
	Par par, aux;
	par = malloc(sizeof(struct par));
	int i, j, k;
	par -> elemI = 0;
	par -> elemD = 0;

	for(i = 0; i < NUMFILIAIS; i++){
		for(j = mes1-1; j < mes2; j++){
			for(k = 0; k < NUMLETRAS; k++){
				aux = totalEmIntervaloArv(fat[i][j][k]);
				par -> elemI += aux -> elemI;
				par -> elemD += aux -> elemD;
			}
		}
	}

	return par;
}