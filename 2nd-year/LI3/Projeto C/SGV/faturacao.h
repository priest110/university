#ifndef FATURACAO_H
#define FATURACAO_H

/* ESTRUTURAS DE DADOS */

/*
tipo "Venda Inicial"
*/
typedef struct vendaInicial *VendaInicial;

/*
tipo "Fatura"
*/
typedef struct fatura *Fatura;

/*
Árvore para guardar faturas
*/
typedef struct nodoFat *ArvFat;

/*
Faturacao global da empresa, dividida em várias árvores - ha 12 conjuntos de árvores diferentes,
um para cada mes, sendo que cada conjunto possui árvores onde estao inseridos as faturas, ordenadas pelo produto
*/
typedef ArvFat*** FatGlobal;

/*
Estrutura de dados que define um par de inteiro/double
*/
typedef struct par *Par;


/* FUNCOES ELEMENTARES */

/* 
Funcao de validacao das vendas, i.e, verifica se todos os parametros desta sao validos
*/
int valVendas(char *venda, CatProd catp, CatCli catc);

/*
Funcao que transforma um codigo de caracteres numa venda inicial
*/
VendaInicial criaVendaInicial(char *codVenda);

/*
Funcao que, dada uma venda inicial, devolve o seu mes
*/
int mesVendaInicial(VendaInicial venda);

/*
Funcao que, dada uma venda inicial, devolve a sua filial
*/
int filialVendaInicial(VendaInicial venda);

/*
Funcao que, dada uma venda inicial, devolve o seu produto
*/
Produto produtoVendaInicial(VendaInicial venda);

/*
Funcao que, dada uma venda inicial, devolve o seu cliente
*/
Cliente clienteVendaInicial(VendaInicial venda);

/*
Funcao que, dada uma venda inicial, devolve o seu preco
*/
double precoVendaInicial(VendaInicial venda);

/*
Funcao que, dada uma venda inicial, devolve a sua quantidade
*/
int quantidadeVendaInicial(VendaInicial venda);

/*
Funcao que, dada uma venda inicial, devolve o seu tipo
*/
char* tipoVendaInicial(VendaInicial venda);

/*
Funcao que transforma um codigo de caracteres numa fatura
*/
Fatura criaFatura(VendaInicial venda);

/*
Funcao que, dado um par, retorna o inteiro
*/
void abrePar(Par par, int *i, double *d);


/* FUNCOES "COMPLEXAS" */

/*
Funcao que cria uma nova faturacao
*/
FatGlobal initFatGlobal();

/*
Funcao que insere uma dada fatura numa pagina de faturacao
*/
FatGlobal insereFat(FatGlobal fat, VendaInicial venda);

/*
Funcao que, dada uma filial, um mes, um tipo de venda, e um produto,
devolve o total faturado com as vendas desse produto, nessa situacao
*/
double faturadoProd(FatGlobal fat, int filial, int mes, char *tipo, Produto p);

/*
Funcao que, dada uma filial, um mes, um tipo de venda, e um produto,
devolve o total  de vendas desse produto, nessa situacao
*/
int quantosProdVendidos(FatGlobal fat, int filial, int mes, char *tipo, Produto p);

/*
Funcao que, para uma dada faturacao e um dado catalogo de produtos,
determina quais os produtos que nunca foram vendidos, devolvendo uma lista com estes
*/
ListaProd produtosNaoVendidos(CatProd cat, FatGlobal fat);

/*
Funcao que, para uma dada faturacao, um dado catalogo de produtos, e uma dada filial,
determina quais os produtos que nunca foram vendidos nessa filial, devolvendo uma lista com estes
*/
ListaProd produtosNaoVendidosFilial(CatProd cat, FatGlobal fat, int filial);

/*
Funcao que, para um dado intervalo de meses e uma dada faturacao, determina o numero total de vendas e o total faturado
*/
Par totalEmIntervalo(FatGlobal fat, int mes1, int mes2);

#endif