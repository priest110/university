#ifndef CATPROD_H
#define CATPROD_H

/* ESTRUTURAS DE DADOS */

/*
 tipo "Produto"
*/
typedef char* Produto;

/*
árvore para guardar produtos
*/
typedef struct nodoProd *ArvProd;

/*
Catalogo de produtos composto por diversas árvores,
sendo que cada uma suporta os produtos comecados por uma mesma letra
*/
typedef ArvProd* CatProd;

/*
Lista de produtos
*/
typedef struct listaProd *ListaProd;


/* FUNCOES "ELEMENTARES" */

/*
Funcao que transforma um codigo de caracteres num produto
*/
Produto criaProd(char* codProd);

/*
Funcao que transforma um produto num codigo de caraceteres
*/
char* getCodProd(Produto p);

/*
Funcao que duplica um produto
*/
Produto dupProd(Produto p);

/*
Funcao que compara dois produtos, a ver se sao iguais
*/
int compProd(Produto p1, Produto p2);


/* FUNCOES "COMPLEXAS" */

/*
Funcao que cria um novo catalogo
*/
CatProd initCatProd();

/*
Funcao que verifica se existe um dado produto num catalogo
*/
int existeProdCat(CatProd cat, Produto p);

/*
Funcao que inicializa uma lista de produtos
*/
ListaProd initListaProd ();

/*
Funcao que cria uma lista com todos os elementos comecados por uma dada letra
*/
ListaProd listaPorLetraProd(CatProd cat, char letra);

/*
Funcao que, dada uma lista de produtos, transforma numa lista de strings
*/
char** listaProdParaString(ListaProd lista);

/*
Funcao que retorna quantos elementos ha numa lista
*/
int quantosProdLista(ListaProd l);

/*
Funcao que, dada uma lista e uma posicao, devolve o produto nessa posicao
*/
Produto prodPorPos(ListaProd lis, int pos);

/*
Funcao que, dada uma lista e um produto, insere esse produto na lista
*/
ListaProd insereProdLis(ListaProd lis, Produto p);

/*
Funcao que, dado um tamanho N, alloca o espaco necessario para suportar N produtos
*/
ListaProd listaPorTamanhoProd(int tamanho);

/*
Funcao que, dadas duas listas, junta as
*/
ListaProd fundeListasProd(ListaProd l1, ListaProd l2);

/*
Funcao que le os codigos dos produtos guardados num ficheiro, e os guarda no array "global", apos verificada a sua validade
*/
CatProd lerProdutos(FILE *fp, CatProd catp);

#endif