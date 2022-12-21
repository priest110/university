#ifndef CATCLIENTES_H
#define CATCLIENTES_H

/* ESTRUTURAS DE DADOS */

/*
 tipo "Cliente"
*/
typedef char* Cliente;

/*
Árvore para guardar clientes
*/
typedef struct nodoCli *ArvCli;

/*
Catalogo de clientes composto por diversas árvores,
sendo que cada uma suporta os clientes comecados por uma mesma letra
*/
typedef ArvCli* CatCli;

/*
Lista de clientes
*/
typedef struct listaCli *ListaCli;


/* FUNCOES "ELEMENTARES" */

/*
Funcao que transforma um codigo de caracteres num cliente
*/
Cliente criaCli(char* codCli);

/*
Funcao que duplica um cliente
*/
Cliente dupCli(Cliente c);

/*
Funcao que compara dois clientes, a ver se sao iguais
*/
int compCli(Cliente c1, Cliente c2);


/* FUNCOES "COMPLEXAS" */

/*
Funcao que cria um novo catalogo de clientes
*/
CatCli initCatCli();

/*
Funcao que verifica se existe um dado cliente num catalogo
*/
int existeCliCat(CatCli cat, Cliente c);

/*
Funcao que inicializa uma lista de clientes
*/
ListaCli initListaCli();

/*
Funcao que retorna quantos elementos ha numa lista
*/
int quantosCliLista(ListaCli l);

/*
Funcao que, dada uma lista e uma posicao, devolve o produto nessa posicao
*/
Cliente cliPorPos(ListaCli lis, int pos);

/*
Funcao que, dada uma lista e um produto, insere esse produto na lista
*/
ListaCli insereCliLis(ListaCli lis, Cliente c);

/*
Funcao que, dada uma lista e um cliente, indica se esse cliente existe nessa lista
*/
int existeCliLista(ListaCli lista, Cliente c);

/*
Funcao que cria uma lista com todos os elementos comecados por uma dada letra
*/
ListaCli listaPorLetraCli(CatCli cat, char letra);

/*
Funcao que, dadas duas listas, junta as
*/
ListaCli fundeListasCli(ListaCli l1, ListaCli l2);

/*
Funcao que, dado um tamanho N, alloca o espaco necessario para suportar N clientes
*/
ListaCli listaPorTamanhoCli(int tamanho);

/*
Funcao que, dada uma lista de produtos, transforma numa lista de strings
*/
char** listaCliParaString(ListaCli lista);

/*
Funcao que le os codigos dos clientes guardados num ficheiro, e os guarda no array "global", apos verificada a sua validade
*/
CatCli lerClientes(FILE *fp, CatCli catc);

#endif