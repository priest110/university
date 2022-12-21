#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "catProd.h"
#include "catClientes.h"
#include "faturacao.h"
#include "gestaoDeFiliais.h"

#define TAMPROD 7
#define TAMCATPROD 26
#define TAMCATCLI 26
#define NUMLETRAS 26
#define NUMMESES 12
#define NUMFILIAIS 3
#define BUFFER 10
#define BUFFERV 50
#define NUMPRODS 200000
#define NUMCLIENTES 20000

/*ESTRUTURAS DE DADOS*/

/*
tipo "Vendas"
*/
typedef struct venda{
	Produto produto;
	Cliente cliente;
	int quantidade;
	char* tipo;
	double preco;
} *Venda;

/*
Árvore para guardar vendas
*/
typedef struct nodoVenda{
	Venda venda;
	struct nodoVenda *dir, *esq;
} *ArvVenda;

/*
Todas as vendas da empresa, divididas em várias árvores - 
3 filiais/ 12 meses/ 26 clientes/ 26 produtos
*/
typedef ArvVenda**** VendasTotal;

/*
Par de produtos/int
*/
typedef struct parProdInt{
	Produto produto;
	int quantidade;
} *ParProdInt;

/*
Lista de pares de produtos/int e a quantidade de pares existente
*/
typedef struct listaProdIntQuant{
	ParProdInt* pares;
	int quantos;
} *ListaParesInt;

/*
Par de produtos/double
*/
typedef struct parProdDouble{
	Produto produto;
	double quantidade;
} *ParProdDouble;

/*
Lista de pares de produtos/double e a quantidade de pares existente
*/
typedef struct listaProdDoubleQuant{
	ParProdDouble* pares;
	int quantos;
} *ListaParesDouble;

/*
Triplo de produto/nºde clientes/quantidade vendida
*/
typedef struct triploPCQ{
	Produto produto;
	int nCli;
	int quantidade;
} *TriploPCQ; 

/*
Lista de triplos
*/
typedef struct listaTriplos{
	TriploPCQ* triplos;
	int quantos;
} *ListaTriplos;

/*FUNCOES "ELEMENTARES"*/

/*
Funcao que transforma um codigo de caracteres numa fatura
*/
Venda criaVenda(VendaInicial vendaI){
	Venda venda = malloc(sizeof(struct venda));
	Produto p = produtoVendaInicial(vendaI);
	Cliente c = clienteVendaInicial(vendaI);
	int quantidade = quantidadeVendaInicial(vendaI);
	double preco = precoVendaInicial(vendaI);
	char* tipo = tipoVendaInicial(vendaI);

	venda -> produto = dupProd(p);
	venda -> cliente = dupCli(c);
	venda -> quantidade = quantidade;
	venda -> tipo = strdup(tipo);
	venda -> preco = preco;

	return venda;
}

/*
Funcao que duplica uma venda
*/
Venda dupVenda(Venda v){
	Venda aux = malloc(sizeof(struct venda));

	aux -> produto = dupProd(v -> produto);
	aux -> cliente = dupCli(v -> cliente);
	aux -> quantidade = v -> quantidade;
	aux -> tipo = strdup(v -> tipo);
	aux -> preco = v -> preco;

	return aux;
}

/*
Funcao que insere uma venda numa arvore
*/          
ArvVenda insereVendaAux(ArvVenda arv, Venda v){
	if(arv == NULL){
		arv = malloc(sizeof(struct nodoVenda));
		arv -> venda = dupVenda(v);
		arv -> dir = NULL;
		arv -> esq = NULL;
	}

	else if((compProd((arv -> venda) -> produto, (v -> produto))) > 0){
		arv -> esq = insereVendaAux(arv -> esq, v);
	}

	else if((compProd((arv -> venda) -> produto, (v -> produto))) <= 0){
		arv -> dir = insereVendaAux(arv -> dir, v);
	}
	return arv;
}

/*
Funcao que, dada uma arvore de vendas, verifica se um dado cliente comprou um dado produto,
tendo por base essas vendas
*/
int existeCLiProdArv(ArvVenda arv, Cliente c, Produto p, char letter){
	Cliente Caux;
	Produto Paux;
	char* letra;
	int existeC = 0, existeP = 0, existeNP = 0,existe = 0;

	if(arv != NULL){
		Caux = (arv -> venda) -> cliente;
		Paux = (arv -> venda) -> produto;
		letra = (arv -> venda) -> tipo;
		
		if(letra[0] == letter)
			existeNP = 1;
		existeC = compCli(Caux, c);
		existeP = compProd(Paux, p);


		if(existeC == 0 && existeP == 0 && existeNP == 1)
			existe = 1;

		else{
			existe = existeCLiProdArv(arv -> esq, c, p, letter);
			
			if(existe == 1);

			else
				existe = existeCLiProdArv(arv -> dir, c, p, letter);
		}
	}

	return existe;
}

/*
Funcao que, dada uma arvore de vendas, verifica se um dado cliente fez alguma compra,
tendo por base essas vendas
*/
int existeCliArvVendas(ArvVenda arv, Cliente c){
	Cliente aux;
	int existe = 0;

	if(arv != NULL){
		aux = (arv -> venda) -> cliente;
		existe = compCli(aux, c);

		if(existe == 0)
			existe = 1;

		else{
			existe = existeCliArvVendas(arv -> esq, c);
			
			if(existe == 1);

			else
				existe = existeCliArvVendas(arv -> dir, c);
		}
	}

	return existe;
}

/*
Funcao que, dado um conjunto de vendas,um cliente , um prouto e uma filial
verifica se esse cliente comprou essse produto
*/
int existeCliComprouProd(VendasTotal vendas, Cliente c, Produto p, int f, char letter){
	int j, existe, pos1, pos2;
	pos1 = (int) (c[0] - 'A');
	pos2 = (int) (p[0] - 'A');
	existe = 0;

	for(j = 0; j < NUMMESES && (existe == 0); j++)
		existe = existeCLiProdArv(vendas[f-1][j][pos1][pos2], c, p, letter);

	return existe;
}

/*
Funcao que, dado um conjunto de vendas e um cliente,
verifica se esse cliente fez alguma compra
*/

int existeCliente(VendasTotal vendas, Cliente c){
	int i, j, k, existe, pos;
	pos = (int) (c[0] - 'A');
	existe = 0;

	for(i = 0; i < NUMFILIAIS && (existe == 0); i++){
		for(j = 0; j < NUMMESES && (existe == 0); j++){
			for(k = 0; k < NUMLETRAS && (existe == 0); k++)
				existe = existeCliArvVendas(vendas[i][j][pos][k], c);
		}
	}

	return existe;
}

/*
Funcao que, dada uma lista de clientes, indica aqueles que compraram certo produto,
para um dado conjunto de vendas, numa determinada filial
*/
ListaCli clientesQueCompraramCertoProdutoAux(ListaCli lis, Produto p, VendasTotal vendas, int f, char letter){
	int quantos = quantosCliLista(lis);
	ListaCli aux = listaPorTamanhoCli(quantos);
	int existe, i;
	Cliente c;

	for(i = 0; i < quantos; i++){
		c = cliPorPos(lis, i);
		existe = existeCliComprouProd(vendas, c, p, f, letter);
		if(existe == 1)
			aux = insereCliLis(aux, c);
	}

	return aux;
}

/*
Funcao que, dada uma lista de clientes, indica aqueles que nunca fizeram compras,
para um dado conjunto de vendas
*/
ListaCli clientesQueNaoCompraramAux(ListaCli lis, VendasTotal vendas){
	int quantos = quantosCliLista(lis);
	ListaCli aux = listaPorTamanhoCli(quantos);
	int existe, i;
	Cliente c;

	for(i = 0; i < quantos; i++){
		c = cliPorPos(lis, i);
		existe = existeCliente(vendas, c);
		if(existe == 0){
			aux = insereCliLis(aux, c);
		}
	}

	return aux;
}

/*
Funcao que, dado um conjunto de vendas e um cliente,
verifica se esse cliente fez compras em todas as filiais
*/

int existeCliTodasFiliais(VendasTotal vendas, Cliente c){
	int i, j, k, existe, pos;
	pos = (int) (c[0] - 'A');

	for(i = 0; i < NUMFILIAIS; i++){
		existe = 0;
		for(j = 0; j < NUMMESES && (existe == 0); j++){
			for(k = 0; k < NUMLETRAS && (existe == 0); k++)
				existe = existeCliArvVendas(vendas[i][j][pos][k], c);
		}
		if(existe == 0)
			break;
	}

	return existe;
}

/*
Funcao que, dada uma lista de clientes e um conjunto de vendas,
devolve uma lista com todos os clientes que fizeram compras em todas as filiais
*/
ListaCli clientesEmTodasAsFiliaisAux(ListaCli lis, VendasTotal vendas){
	int quantos = quantosCliLista(lis);
	ListaCli aux = listaPorTamanhoCli(quantos);
	int existe, i;
	Cliente c;

	for(i = 0; i < quantos; i++){
		c = cliPorPos(lis, i);
		existe = existeCliTodasFiliais(vendas, c);
		if(existe == 1)
			aux = insereCliLis(aux, c);
	}

	return aux;
}

/*
Funcao que, dada uma arvore de vendas e um cliente, 
determina o total de compras feitas por esse cliente
*/
int totalComprasArv(ArvVenda arv, Cliente c){
	Cliente aux;
	int total = 0;
	int comp;

	if(arv != NULL){
		aux = (arv -> venda) -> cliente;
		comp = compCli(aux, c);

		if(comp == 0)
			total += (arv -> venda) -> quantidade;

		total += totalComprasArv(arv -> dir, c);
		total += totalComprasArv(arv -> esq, c);

	}

	return total;
}

/*
Funcao que, dado um conjunto de vendas, um cliente e um mes, 
determina o total de compras efetuadas por esse cliente nesse mes
*/
int totalComprasPorMesAux(VendasTotal vendas, Cliente c, int filial, int mes){
	int total, j, pos;
	pos = (int) (c[0] - 'A');
	total = 0;

		for(j = 0; j < NUMLETRAS; j++)
			total += totalComprasArv(vendas[filial-1][mes-1][pos][j], c);

	return total;
}

/*
Funcao que, dado um par, insere o numa lista, de forma ordenada (decrescente)
*/
ListaParesInt ordenaListaParesIntAux(ListaParesInt lista, ParProdInt par){
	int i, flag;
	ParProdInt aux = malloc(sizeof(struct parProdInt));

	flag = 0;
	for(i = 0; i < (lista -> quantos) && (flag == 0); i++){
		if(par -> quantidade > (lista -> pares)[i] -> quantidade){
			aux -> quantidade = (lista -> pares)[i] -> quantidade;
			aux -> produto = dupProd((lista -> pares)[i] -> produto);
			(lista -> pares)[i] -> quantidade = par -> quantidade;
			(lista -> pares)[i] -> produto = dupProd(par -> produto);
			lista = ordenaListaParesIntAux(lista, aux);
			flag = 1;
		}
	}

	if(i == (lista -> quantos) && flag == 0){
		(lista -> pares)[i] = malloc(sizeof(struct parProdInt));
		(lista -> pares)[i] -> produto = dupProd(par -> produto);
		(lista -> pares)[i] -> quantidade = par -> quantidade;
		(lista -> quantos)++;
	}

	return lista;
}

/*
Funcao que, dada uma lista de pares, ordena os do de maior quantidade, para o de menor
*/
ListaParesInt ordenaListaParesInt(ListaParesInt lista){
	int i;
	ListaParesInt aux = malloc(sizeof(struct listaProdIntQuant));
	aux -> pares = malloc((lista -> quantos)*sizeof(struct parProdInt));
	aux -> quantos = 0;

	for(i = 0; i < (lista -> quantos); i++){
		aux = ordenaListaParesIntAux(aux, (lista -> pares)[i]);
	}

	free(lista);

	return aux;
}

/*
Funcao que, dado um produto e a sua quantidade, o insere numa lista de pares produto/int
*/
ListaParesInt insereProduto(ListaParesInt lista, Produto p, double quantidade){
	int i, comp, flag;
	flag = 0;

	for(i = 0; i < (lista -> quantos) && flag == 0; i++){
		comp = compProd(p, (lista -> pares)[i] -> produto);

		if(comp == 0){
			(lista -> pares)[i] -> quantidade += quantidade;
			flag = 1;
		}
	}

	if(i == (lista -> quantos) && flag == 0){
		(lista -> pares)[i] = malloc(sizeof(struct parProdDouble));
		(lista -> pares)[i] -> produto = dupProd(p);
		(lista -> pares)[i] -> quantidade = quantidade;
		(lista -> quantos)++;
	}

	return lista;
}

/*
Funcao que, dada uma arvore de vendas, um cliente, e uma lista de pares/int,
insere os produtos que foram comprados por um cliente
*/
ListaParesInt insereProdQuantLista(ArvVenda arv, Cliente c, ListaParesInt lista){
	int comp;

	if(arv != NULL){
		comp = compCli(((arv -> venda) -> cliente), c);

		if(comp == 0){
			lista = insereProduto(lista, ((arv -> venda) -> produto), ((arv -> venda) -> quantidade));
		}

		lista = insereProdQuantLista(arv -> esq, c, lista);
		lista = insereProdQuantLista(arv -> dir, c, lista);
	}

	return lista;
}

/*
Funcao que, dado um triplo, insere o numa lista, de forma ordenada (decrescente)
*/
ListaTriplos insereTriploSeMaior(ListaTriplos lista, TriploPCQ triplo, int N){
	int i, flag;
	TriploPCQ aux = malloc(sizeof(struct triploPCQ));

	flag = 0;
	for(i = 0; i < (lista -> quantos) && i < N && (flag == 0); i++){
		if(triplo -> quantidade > (lista -> triplos)[i] -> quantidade){
			aux -> quantidade = (lista -> triplos)[i] -> quantidade;
			aux -> produto = dupProd((lista -> triplos)[i] -> produto);
			aux -> nCli = (lista -> triplos)[i] -> nCli;
			(lista -> triplos)[i] -> quantidade = triplo -> quantidade;
			(lista -> triplos)[i] -> produto = dupProd(triplo -> produto);
			(lista -> triplos)[i] -> nCli = triplo -> nCli;
			lista = insereTriploSeMaior(lista, aux, N);
			flag = 1;
		}
	}

	if(i == (lista -> quantos) && flag == 0 && i < N){
		(lista -> triplos)[i] = malloc(sizeof(struct triploPCQ));
		(lista -> triplos)[i] -> produto = dupProd(triplo -> produto);
		(lista -> triplos)[i] -> quantidade = triplo -> quantidade;
		(lista -> triplos)[i] -> nCli = triplo -> nCli;
		(lista -> quantos)++;
	}

	return lista;
}

/*
*/
TriploPCQ atualizaTriplo(ArvVenda arv, ListaCli listaC, TriploPCQ triplo, Produto p){
	int compP, existeC;

	if(arv != NULL){
		compP = compProd((arv -> venda) -> produto, p);
		
		if(compP == 0){
			existeC = existeCliLista(listaC, (arv -> venda) -> cliente);

			if(existeC == 0){
				listaC = insereCliLis(listaC, (arv -> venda) -> cliente);
				(triplo -> nCli)++;
			}

			triplo -> quantidade += (arv -> venda) -> quantidade;

			triplo = atualizaTriplo(arv -> dir, listaC, triplo, p);
		}

		else if(compP > 0)
			triplo = atualizaTriplo(arv -> esq, listaC, triplo, p);

		else if(compP < 0)
			triplo = atualizaTriplo(arv -> dir, listaC, triplo, p);

	}

	return triplo;
}

/*
Funcao que, dado um par, insere o numa lista, de forma ordenada (decrescente)
*/
ListaParesDouble ordenaListaParesDoubleAux(ListaParesDouble lista, ParProdDouble par){
	int i, flag;
	ParProdDouble aux = malloc(sizeof(struct parProdDouble));

	flag = 0;
	for(i = 0; i < (lista -> quantos) && (flag == 0); i++){
		if(par -> quantidade > (lista -> pares)[i] -> quantidade){
			aux -> quantidade = (lista -> pares)[i] -> quantidade;
			aux -> produto = dupProd((lista -> pares)[i] -> produto);
			(lista -> pares)[i] -> quantidade = par -> quantidade;
			(lista -> pares)[i] -> produto = dupProd(par -> produto);
			lista = ordenaListaParesDoubleAux(lista, aux);
			flag = 1;
		}
	}

	if(i == (lista -> quantos) && flag == 0){
		(lista -> pares)[i] = malloc(sizeof(struct parProdDouble));
		(lista -> pares)[i] -> produto = dupProd(par -> produto);
		(lista -> pares)[i] -> quantidade = par -> quantidade;
		(lista -> quantos)++;
	}

	return lista;
}

/*
Funcao que, dada uma lista de pares, ordena os do de maior quantidade, para o de menor
*/
ListaParesDouble ordenaListaParesDouble(ListaParesDouble lista){
	int i;
	ListaParesDouble aux = malloc(sizeof(struct listaProdDoubleQuant));
	aux -> pares = malloc((lista -> quantos)*sizeof(struct parProdDouble));
	aux -> quantos = 0;

	for(i = 0; i < (lista -> quantos); i++){
		aux = ordenaListaParesDoubleAux(aux, (lista -> pares)[i]);
	}

	free(lista);

	return aux;
}

/*
Funcao que, dado um produto e o total gasto nele, o insere numa lista de pares produto/double
*/
ListaParesDouble insereProduto2(ListaParesDouble lista, Produto p, double quantidade){
	int i, comp, flag;
	flag = 0;

	for(i = 0; i < (lista -> quantos) && flag == 0; i++){
		comp = compProd(p, (lista -> pares)[i] -> produto);

		if(comp == 0){
			(lista -> pares)[i] -> quantidade += quantidade;
			flag = 1;
		}
	}

	if(i == (lista -> quantos) && flag == 0){
		(lista -> pares)[i] = malloc(sizeof(struct parProdDouble));
		(lista -> pares)[i] -> produto = dupProd(p);
		(lista -> pares)[i] -> quantidade = quantidade;
		(lista -> quantos)++;
	}

	return lista;
}

/*
Funcao que, dada uma arvore de vendas, um cliente, e uma lista de pares produto/double,
insere os produtos que foram comprados por um cliente
*/
ListaParesDouble insereProdGastoLista(ArvVenda arv, Cliente c, ListaParesDouble lista){
	int comp;
	double aux;

	if(arv != NULL){
		comp = compCli(((arv -> venda) -> cliente), c);

		if(comp == 0){
			aux = ((arv -> venda) -> quantidade)*((arv -> venda) -> preco);
			lista = insereProduto2(lista, ((arv -> venda) -> produto), aux);
		}

		lista = insereProdGastoLista(arv -> esq, c, lista);
		lista = insereProdGastoLista(arv -> dir, c, lista);
	}

	return lista;
}

/*
*/
ListaParesDouble topTresProdMaisCompradosAux(ListaParesDouble lista){
	ListaParesDouble aux = malloc(sizeof(struct listaProdDoubleQuant));
	aux -> pares = malloc(3*(sizeof(struct parProdDouble)));
	aux -> quantos = 0;
	int i;

	for(i = 0; i < 3 && i < (lista -> quantos); i++){
		(aux -> pares)[i] = malloc(sizeof(struct parProdDouble));
		(aux -> pares)[i] -> produto = dupProd((lista -> pares)[i] -> produto);
		(aux -> pares)[i] -> quantidade = (lista -> pares)[i] -> quantidade;
		(aux -> quantos)++;
	}

	free(lista);

	return aux;
}


/* FUNCOES "COMPLEXAS" */

/*
Funcao que cria um novo conjunto de vendas
*/
VendasTotal initVendasTotal(){
	VendasTotal vendas;
	int i, j, k, h;

	vendas = malloc((sizeof(ArvVenda***))*NUMFILIAIS);

	for(i = 0; i < NUMFILIAIS; i++)
		vendas[i] = malloc(sizeof(ArvVenda**)*NUMMESES);

	for(i = 0; i < NUMFILIAIS; i++){
		for(j = 0; j < NUMMESES; j++)
			vendas[i][j] = malloc(sizeof(ArvVenda*)*NUMLETRAS);
	}

	for(i = 0; i < NUMFILIAIS; i++){
		for(j = 0; j < NUMMESES; j++){
			for(k = 0; k < NUMLETRAS; k++)
				vendas[i][j][k] = malloc(sizeof(ArvVenda)*NUMLETRAS);
		}
	}

	for(i = 0; i < NUMFILIAIS; i++){
		for(j = 0; j < NUMMESES; j++){
			for(k = 0; k < NUMLETRAS; k++){
				for(h = 0; h < NUMLETRAS; h++)
					vendas[i][j][k][h] = NULL;
			}
		}
	}

	return vendas;
}

/*
Funcao que insere uma dada venda numa pagina de vendas
*/
VendasTotal insereVenda(VendasTotal vendas, VendaInicial vendaI){
	Venda venda = criaVenda(vendaI);
	int aux = (int) (venda -> cliente[0] - 'A');
	int aux2 = (int) (venda -> produto[0] - 'A');
	int mes = mesVendaInicial(vendaI);
	int filial = filialVendaInicial(vendaI);

	vendas[filial-1][mes-1][aux][aux2] = insereVendaAux(vendas[filial-1][mes-1][aux][aux2], venda);
	
	return vendas;
}

/*
Funcao que, dado um conjunto de Vendas,
indica quais os clientes que fizeram compras em todas as filiais
*/
ListaCli clientesEmTodasAsFiliais(CatCli cat, VendasTotal vendas){
	ListaCli l = initListaCli();
	ListaCli aux1, aux2;
	int i;
	char letra;

	for(i = 0; i < TAMCATCLI; i++){
		letra = (char) ('A' + i);
		aux1 = listaPorLetraCli(cat, letra);
		aux2 = clientesEmTodasAsFiliaisAux(aux1, vendas);
		l = fundeListasCli(l, aux2);
	}

	return l;
}

/*
Funcao que, dado um conjunto de vendas, um catalogo de clientes, um produto e uma filial,
determina aqueles que nunca fizeram compras
*/
ListaCli clientesQueCompraramCertoProduto(CatCli cat, VendasTotal vendas, Produto p, int f, char letter){
	ListaCli l = initListaCli();
	ListaCli aux, aux1;
	int i;
	char letra;
	for(i = 0; i < TAMCATCLI; i++){
		letra = (char) ('A' + i);
		aux = listaPorLetraCli(cat, letra);
		aux1 = clientesQueCompraramCertoProdutoAux(aux, p, vendas, f, letter);
		l = fundeListasCli(l, aux1);
	}

	return l;
}

/*
Funcao que, dado um conjunto de vendas e um catalogo de clientes,
determina aqueles que nunca fizeram compras
*/
ListaCli clientesQueNaoCompraram(CatCli cat, VendasTotal vendas){
	ListaCli l = initListaCli();
	ListaCli aux1, aux2;
	int i;
	char letra;

	for(i = 0; i < TAMCATCLI; i++){
		letra = (char) ('A' + i);
		aux1 = listaPorLetraCli(cat, letra);
		aux2 = clientesQueNaoCompraramAux(aux1, vendas);
		l = fundeListasCli(l, aux2);
	}

	return l;
}

/*
Funcao que, dado um cliente e um conjunto de vendas, 
determina o total de produtos comprados, em cada mes, por esse cliente
*/
int** totalComprasPorMes(VendasTotal vendas, Cliente c){
	int** totais;
	int i, j;

	totais = malloc(NUMFILIAIS*sizeof(int*));
	for(i = 0; i < NUMFILIAIS; i++)
		totais[i] = malloc(NUMMESES*sizeof(int));

	for(i = 0; i < NUMFILIAIS; i++){
		for(j = 0; j < NUMMESES; j++)
			totais[i][j] = totalComprasPorMesAux(vendas, c, (i+1), (j+1));
	}

	return totais;
}

/*
Funcao que, dado um cliente, um mes e um conjunto de vendas,
determina a lista dos produtos comprados por esse cliente
(ordenados de forma descendente pela quantidade adquirida)
*/
ListaParesInt listaProdComprados(VendasTotal vendas, int mes, Cliente c){
	ListaParesInt lista = malloc(sizeof(struct listaProdIntQuant));
	lista -> pares = malloc(NUMPRODS*sizeof(struct parProdInt));
	lista -> quantos = 0;
	int i, j, pos;
	pos = (int) (c[0] - 'A');

	for(i = 0; i < NUMFILIAIS; i++){
		for(j = 0; j < NUMLETRAS; j++){
			lista = insereProdQuantLista(vendas[i][mes-1][pos][j], c, lista);
		}
	}

	lista = ordenaListaParesInt(lista);

	return lista;
}

/*
Funcao que, dada uma filial e uma quantidade de produtos N,
indica o top N de produtos mais vendidos, nessa filial
*/
ListaTriplos topNProdutosMaisVendidos(VendasTotal vendas, CatProd cat, int N, int filial){
	TriploPCQ triplo = malloc(sizeof(struct triploPCQ));

	ListaTriplos listaT = malloc(sizeof(struct listaTriplos));
	listaT -> triplos = malloc(N*sizeof(struct triploPCQ));
	listaT -> quantos = 0;
	
	ListaCli listaC;	
	ListaProd listaP = initListaProd();
	int i, j, k, l, tamanho, pos;
	char letra;
	Produto p;

	for(k = 0; k < TAMCATPROD; k++){
		letra = (char) ('A' + k);
		listaP = listaPorLetraProd(cat, letra);

		tamanho = quantosProdLista(listaP);

		for(l = 0; l < tamanho; l++){
			p = prodPorPos(listaP, l);
			pos = (int) (p[0] - 'A');
			listaC = listaPorTamanhoCli(NUMCLIENTES);

			for(i = 0; i < NUMMESES; i++){
				for(j = 0; j < NUMLETRAS; j++){
					triplo = malloc(sizeof(struct triploPCQ));
					triplo -> produto = dupProd(p);
					triplo -> nCli = 0;
					triplo -> quantidade = 0;
					triplo = atualizaTriplo(vendas[filial-1][i][j][pos], listaC, triplo, p);
				}
			}

			listaT = insereTriploSeMaior(listaT, triplo, N);

			free(listaC);
		}
	}

	free(listaP);

	return listaT;
}


/*
Funcao que, dado um cliente e um conjunto de vendas,
determina os 3 produtos nos quais gastou mais dinheiro
*/
ListaParesDouble topTresProdMaisComprados(VendasTotal vendas, Cliente c){
	ListaParesDouble lista = malloc(sizeof(struct listaProdDoubleQuant));
	lista -> pares = malloc(NUMPRODS*sizeof(struct parProdDouble));
	lista -> quantos = 0;
	int i, j, k, pos;
	pos = (int) (c[0] - 'A');

	for(i = 0; i < NUMFILIAIS; i++){
		for(j = 0; j < NUMMESES; j++){
			for(k = 0; k < NUMLETRAS; k++){
				lista = insereProdGastoLista(vendas[i][j][pos][k], c, lista);
			}
		}
	}

	lista = ordenaListaParesDouble(lista);

	lista = topTresProdMaisCompradosAux(lista);

	return lista;
}

/*
Funcao que, dada uma lista de pares, indica quantos pares existem
*/
int quantosProdQuantidade(ListaParesInt lista){
	return (lista -> quantos);
}


/*
Funcao que, dada uma lista de pares, indica quantos pares existem
*/
int quantosProdQuantidade2(ListaParesDouble lista){
	return (lista -> quantos);
}

/*
Funcao que, dada uma lista de produtos e as suas quantidades,
transforma numa lista de strings, com os produtos;
*/
char** getListaStringsProdQuantos(ListaParesInt lista){
	int quantos = lista -> quantos;
	char** strings = malloc(quantos*sizeof(char*));
	int i;

	for(i = 0; i < quantos; i++){
		strings[i] = malloc(BUFFER*sizeof(char));
		strings[i] = getCodProd((lista -> pares)[i] -> produto);
	}

	return strings;
}

/*
Funcao que, dada uma lista de produtos e as suas quantidades,
transforma numa lista de strings, com os produtos;
*/
char** getListaStringsProdQuantos2(ListaParesDouble lista){
	int quantos = lista -> quantos;
	char** strings = malloc(quantos*sizeof(char*));
	int i;

	for(i = 0; i < quantos; i++){
		strings[i] = malloc(BUFFER*sizeof(char));
		strings[i] = getCodProd((lista -> pares)[i] -> produto);
	}

	return strings;
}

/*
Funcao que, dada uma lista de produtos e as suas quantidades,
transforma numa lista de strings, com os produtos;
*/
char** getListaStringsProdQuantos3(ListaTriplos lista){
	int quantos = lista -> quantos;
	char** strings = malloc(quantos*sizeof(char*));
	int i;

	for(i = 0; i < quantos; i++){
		strings[i] = malloc(BUFFER*sizeof(char));
		strings[i] = getCodProd((lista -> triplos)[i] -> produto);
	}

	return strings;
}

/*
Funcao que, dada uma lista de produtos e as suas quantidades,
devolve uma lista com as quantidades compradas
*/
int* getListaQuantidadesProdQuantos(ListaParesInt lista){
	int quantos = lista -> quantos;
	int* quantidades = malloc(quantos*sizeof(int));
	int i;

	for(i = 0; i < quantos; i++){
		quantidades[i] = (lista -> pares)[i] -> quantidade;
	}

	return quantidades;
}

/*
Funcao que, dada uma lista de produtos e as suas quantidades,
devolve uma lista com as quantidades compradas
*/
double* getListaQuantidadesProdQuantos2(ListaParesDouble lista){
	int quantos = lista -> quantos;
	double* quantidades = malloc(quantos*sizeof(double));
	int i;

	for(i = 0; i < quantos; i++){
		quantidades[i] = (lista -> pares)[i] -> quantidade;
	}

	return quantidades;
}

/*
Funcao que, dada uma lista de produtos e as suas quantidades,
devolve uma lista com as quantidades compradas
*/
int* getListaQuantidadesProdQuantos3(ListaTriplos lista){
	int quantos = lista -> quantos;
	int* quantidades = malloc(quantos*sizeof(int));
	int i;

	for(i = 0; i < quantos; i++){
		quantidades[i] = (lista -> triplos)[i] -> quantidade;
	}

	return quantidades;
}

/*
Funcao que, dada uma lista de produtos e as suas quantidades,
devolve uma lista com as quantidades compradas
*/
int* getListaNumCli(ListaTriplos lista){
	int quantos = lista -> quantos;
	int* clientes = malloc(quantos*sizeof(int));
	int i;

	for(i = 0; i < quantos; i++){
		clientes[i] = (lista -> triplos)[i] ->nCli;
	}

	return clientes;
}

/*
Funcao que le os codigos das vendas guardados num ficheiro, e os guarda no array "global", apos verificada a sua validade
*/
void lerVendas(FILE *fp, CatProd catp, CatCli catc, FatGlobal *fat, VendasTotal *vendas){
	char* aux;
	char venda[BUFFERV];
	int r;
	VendaInicial vendaI;
	int vendasLidas, vendasVal;

	vendasLidas = vendasVal = 0;

/*
 ciclo que insere as vendas validas no array global e no novo ficheiro
*/
	while(fgets(venda, BUFFERV, fp)){
		vendasLidas++;
		aux = strtok(venda, "\n\r");
		r = valVendas(aux, catp, catc);
		if(r){
			vendasVal++;
			vendaI = criaVendaInicial(aux);
			(*fat) = insereFat((*fat), vendaI);
			(*vendas) = insereVenda((*vendas), vendaI);
		}
	}

	printf("Número de linhas lidas: %d\n", vendasLidas);
	printf("Número de linhas inseridas: %d\n", vendasVal);
}
