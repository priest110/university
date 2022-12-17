#ifndef GESTAODEFILIAIS_H
#define GESTAODEFILIAIS_H

/*ESTRUTURAS DE DADOS*/

/*
tipo "Vendas"
*/
typedef struct venda *Venda;

/*
Árvore para guardar vendas
*/
typedef struct nodoVenda *ArvVenda;

/*
Todas as vendas da empresa, divididas em várias árvores - ha 12 conjuntos de árvores diferentes,
um para cada mes, sendo que cada conjunto possui árvores onde estao inseridos as faturas, ordenadas pelo cliente
*/
typedef ArvVenda**** VendasTotal;

/*
Par de produtos e a respetiva quantidade comprada
*/
typedef struct parProdInt *ParProd;

/*
Lista de pares de produtos e quantidades
*/
typedef struct listaProdIntQuant *ListaParesInt;

/*
Par de produtos/double
*/
typedef struct parProdDouble *ParProdDouble;

/*
Lista de pares de produtos/double e a quantidade de pares existente
*/
typedef struct listaProdDoubleQuant *ListaParesDouble;

/*
Triplo de produto/nºde clientes/quantidade vendida
*/
typedef struct triploPCQ *TriploPCQ; 

/*
Lista de triplos
*/
typedef struct listaTriplos *ListaTriplos;


/* FUNCOES "COMPLEXAS" */

/*
Funcao que cria um novo conjunto de vendas
*/
VendasTotal initVendasTotal();

/*
Funcao que, dado um conjunto de Vendas,
indica quais os clientes que fizeram compras em todas as filiais
*/
ListaCli clientesEmTodasAsFiliais(CatCli cat, VendasTotal vendas);

/*
Funcao que, dado um conjunto de vendas e um catalogo de clientes,
determina aqueles que nunca fizeram compras
*/
ListaCli clientesQueNaoCompraram(CatCli cat, VendasTotal vendas);

/*
Funcao que, dado um conjunto de vendas, um catalogo de clientes, um produto e uma filial,
determina aqueles que nunca fizeram compras
*/
ListaCli clientesQueCompraramCertoProduto(CatCli cat, VendasTotal vendas, Produto p, int f, char letter);

/*
Funcao que, dado um cliente e um conjunto de vendas, 
determina o total de produtos comprados, em cada mes, por esse cliente
*/
int** totalComprasPorMes(VendasTotal vendas, Cliente c);

/*
Funcao que, dado um cliente, um mes e um conjunto de vendas,
determina a lista dos produtos comprados por esse cliente
(ordenados de forma descendente pela quantidade adquirida)
*/
ListaParesInt listaProdComprados(VendasTotal vendas, int mes, Cliente c);

/*
Funcao que, dada uma filial e uma quantidade de produtos N,
indica o top N de produtos mais vendidos, nessa filial
*/
ListaTriplos topNProdutosMaisVendidos(VendasTotal vendas, CatProd cat, int N, int filial);

/*
Funcao que, dado um cliente e um conjunto de vendas,
determina os 3 produtos nos quais gastou mais dinheiro
*/
ListaParesDouble topTresProdMaisComprados(VendasTotal vendas, Cliente c);

/*
Funcao que, dada uma lista de pares, indica quantos pares existem
*/
int quantosProdQuantidade(ListaParesInt lista);

/*
Funcao que, dada uma lista de pares, indica quantos pares existem
*/
int quantosProdQuantidade2(ListaParesDouble lista);

/*
Funcao que, dada uma lista de produtos e as suas quantidades,
transforma numa lista de strings;
*/
char** getListaStringsProdQuantos(ListaParesInt lista);

/*
Funcao que, dada uma lista de produtos e as suas quantidades,
transforma numa lista de strings, com os produtos;
*/
char** getListaStringsProdQuantos2(ListaParesDouble lista);

/*
Funcao que, dada uma lista de produtos e as suas quantidades,
transforma numa lista de strings, com os produtos;
*/
char** getListaStringsProdQuantos3(ListaTriplos lista);

/*
Funcao que, dada uma lista de produtos e as suas quantidades,
devolve uma lista com as quantidades compradas;
*/
int* getListaQuantidadesProdQuantos(ListaParesInt lista);

/*
Funcao que, dada uma lista de produtos e as suas quantidades,
devolve uma lista com as quantidades compradas
*/
double* getListaQuantidadesProdQuantos2(ListaParesDouble lista);

/*
Funcao que, dada uma lista de produtos e as suas quantidades,
devolve uma lista com as quantidades compradas
*/
int* getListaQuantidadesProdQuantos3(ListaTriplos lista);

/*
Funcao que, dada uma lista de produtos e as suas quantidades,
devolve uma lista com as quantidades compradas
*/
int* getListaNumCli(ListaTriplos lista);

/*
Funcao que le os codigos das vendas guardados num ficheiro, e os guarda no array "global", apos verificada a sua validade
*/
void lerVendas(FILE *fp, CatProd catp, CatCli catc, FatGlobal *fat, VendasTotal *vendas);

#endif