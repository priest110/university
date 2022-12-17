#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "catClientes.h"

#define TAMCLI 6
#define TAMCATCLI 26
#define BUFFER 10

/* ESTRUTURAS DE DADOS */

/*
 tipo "Cliente"
*/
typedef char* Cliente;

/*
Árvore para guardar clientes
*/
typedef struct nodoCli{
	Cliente cliente;
	struct nodoCli *dir, *esq;
} *ArvCli;

/*
Catalogo de clientes composto por diversas árvores,
sendo que cada uma suporta os clientes comecados por uma mesma letra
*/
typedef ArvCli* CatCli;

/*
Lista de clientes
*/
typedef struct listaCli{
	Cliente* clientes;
	int quantos;
} *ListaCli;


/* FUNCOES "ELEMENTARES" */

/* 
Funcao de validacao dos clientes, i.e., verifica se um dado cliente possui as caracteristicas que fazem do seu codigo valido
(Se possui 5 caracteres, sendo o primeiro uma letra maiuscula e os ultimos 4 representam um numero entre 1000 e 5000)
*/
int valClientes(char *cliente){
	int n;

	if (strlen(cliente) == 5){
		if(cliente[0] >= 'A' && cliente[0] <= 'Z'){
			n = atoi(cliente + 1);
			if(n >= 1000 && n <= 5000)
				return 1;
		}
	}
	return 0;
}

/*
Funcao que transforma um codigo de caracteres num cliente
*/
Cliente criaCli(char* codCli){
	Cliente c = malloc(sizeof(Cliente));
	int i;

	for(i = 0; i < TAMCLI - 1; i++)
		c[i] = codCli[i];
	c[i] = '\0';

	return c;
}

/*
Funcao que transforma um cliente num codigo de caraceteres
*/
char* getCodCli(Cliente c){
	char* aux = malloc(sizeof(char*));
	int i;

	for(i = 0; i < TAMCLI; i++)
		aux[i] = c[i];

	return aux;
}

/*
Funcao que duplica um cliente
*/
Cliente dupCli(Cliente c){
	Cliente aux = malloc(sizeof(Cliente));
	int i;

	for(i = 0; i < TAMCLI; i++)
		aux[i] = c[i];

	return aux;
}

/*
Funcao que compara dois clientes, a ver se sao iguais
*/
int compCli(Cliente c1, Cliente c2){
	int i = 0;
	int flag = 0;
	while(flag == 0){
		if(c1[i] < c2[i])
			flag = -1;
		if(c1[i] > c2[i])
			flag = 1;
		if(c1[i] == '\0')
			break;
		i++;
	}

	return flag;
}

/*
Funcao que insere um dado cliente numa arvore
*/
ArvCli insereCliArv(ArvCli arv, Cliente c){
	if(arv == NULL){
		arv = malloc(sizeof(struct nodoCli));
		arv -> cliente = dupCli(c);
		arv -> dir = NULL;
		arv -> esq = NULL;
	}

	else if((compCli(c, arv->cliente)) < 0)
		arv -> esq = insereCliArv(arv -> esq, c);

	else if((compCli(c, arv->cliente)) > 0)
		arv -> dir = insereCliArv(arv -> dir, c);

	return arv;
}

/*
Funcao que verifica se existe um dado cliente numa arvore
*/
int existeCliArv(ArvCli arv, Cliente c){
	int r;

	if(arv == NULL)
		return 0;

	r = compCli(c, arv->cliente);

	if(r == 0)
		return 1;

	else if(r > 0)
		r = existeCliArv(arv->dir, c);

	else if(r < 0)
		r = existeCliArv(arv->esq, c);

	return r;
}

/*
Funcao que indica quantos clientes estao inseridos numa arvore
*/
int quantosCliArv(ArvCli arv){
	int total = 0;

	if(arv == NULL)
		return total;

	else{
		total += quantosCliArv(arv->esq);
		total += quantosCliArv(arv->dir);
		total++;
	}

	return total;
}

/*
Funcoes que criam uma lista a partir de uma arvore
*/
ListaCli listaPorArvCliAux(ArvCli arv, ListaCli l){
	if(arv != NULL){
		l = listaPorArvCliAux(arv->esq, l);

		l -> clientes[l -> quantos] = dupCli(arv->cliente);
		(l -> quantos)++;
		
		l = listaPorArvCliAux(arv->dir, l);
	}
	return l;
}

ListaCli listaPorArvCli(ArvCli arv){
	ListaCli l = initListaCli();
	int q = quantosCliArv(arv);
	l -> clientes = malloc(q*sizeof(Cliente));
	l = listaPorArvCliAux(arv, l);
	return l;
}


/* FUNCOES "COMPLEXAS" */

/*
Funcao que cria um novo catalogo de clientes
*/
CatCli initCatCli(){
	CatCli cat = malloc(TAMCATCLI*(sizeof(ArvCli)));
	int i;

	for(i = 0; i < TAMCATCLI; i++)
		cat[i] = NULL;

	return cat;
}

/*
Funcao que insere um dado cliente num catalogo
*/
CatCli insereCliCat(CatCli cat, Cliente c){
	int i = (int) (c[0] - 'A');
	cat[i] = insereCliArv(cat[i], c);
	return cat;
}

/*
Funcao que verifica se existe um dado cliente num catalogo
*/
int existeCliCat(CatCli cat, Cliente c){
	int i = (int) (c[0] - 'A');
	int r = existeCliArv(cat[i], c);
	return r;
}

/*
Funcao que inicializa uma lista de clientes
*/
ListaCli initListaCli (){
	ListaCli l = malloc(sizeof(struct listaCli));
	l -> quantos = 0;
	l -> clientes = NULL;
	return l;
}

/*
Funcao que retorna quantos elementos ha numa lista
*/
int quantosCliLista(ListaCli l){
	return (l -> quantos);
}

/*
Funcao que, dada uma lista e uma posicao, devolve o cliente nessa posicao
*/
Cliente cliPorPos(ListaCli lis, int pos){
	return (lis -> clientes[pos]);
}

/*
Funcao que, dada uma lista e um cliente, insere esse cliente na lista
*/
ListaCli insereCliLis(ListaCli lis, Cliente c){
	lis -> clientes[lis->quantos] = dupCli(c);
	(lis -> quantos)++;
	return lis;
}

/*
Funcao que, dada uma lista e um cliente, indica se esse cliente existe nessa lista
*/
int existeCliLista(ListaCli lista, Cliente c){
	int i, comp, flag = 0;

	for(i = 0; i < (lista -> quantos) && flag == 0; i++){
		comp = compCli((lista -> clientes)[i], c);
		if(comp == 0)
			flag = 1;
	}

	return flag;
}

/*
Funcao que cria uma lista com todos os elementos comecados por uma dada letra
*/
ListaCli listaPorLetraCli(CatCli cat, char letra){
	int i = (int) (letra - 'A');
	ListaCli l;
	l = listaPorArvCli(cat[i]);
	return l;
}


/*
Funcao que, dadas duas listas, junta as
*/
ListaCli fundeListasCli(ListaCli l1, ListaCli l2){
	ListaCli aux = initListaCli();
	int i, j;

	aux -> quantos = (l1 -> quantos) + (l2 -> quantos);
	aux -> clientes = malloc((aux -> quantos)*(sizeof(Cliente)));

	for(i = 0; i < (l1 -> quantos); i++)
		(aux -> clientes)[i] = (l1 -> clientes)[i];

	for(j = 0; i < (aux -> quantos); j++, i++)
		(aux -> clientes)[i] = (l2 -> clientes)[j];
	
	return aux;
}

/*
Funcao que, dado um tamanho N, alloca o espaco necessario para suportar N clientes
*/
ListaCli listaPorTamanhoCli(int tamanho){
	ListaCli lis = initListaCli();
	lis -> clientes = malloc(tamanho*(sizeof(Cliente)));
	return lis;
}

/*
Funcao que, dada uma lista de clientes, transforma numa lista de strings
*/
char** listaCliParaString(ListaCli lista){
	int i, quantos = lista -> quantos;
	char** strings = malloc(quantos*sizeof(char*));

	for(i = 0; i < quantos; i++)
		strings[i] = getCodCli((lista -> clientes)[i]);

	return strings;
}

/*
Funcao que le os codigos dos clientes guardados num ficheiro, e os guarda no array "global", apos verificada a sua validade
*/
CatCli lerClientes(FILE *fp, CatCli catc){
	char* aux;
	char str[BUFFER];
	Cliente c;
	int clientesVal, clientesLidos;

	clientesVal = clientesLidos = 0;

/*
 ciclo que insere todos os codigos dos clientes num array
*/
	while(fgets(str, BUFFER, fp)){
		clientesLidos++;
		aux = strtok(str, "\n\r");
		if(valClientes(aux)){
			clientesVal++;
			c = criaCli(aux);
			catc = insereCliCat(catc, c);
		}
	}

	printf("Número de linhas lidas: %d\n", clientesLidos);
	printf("Número de linhas inseridas: %d\n", clientesVal);
	
	return catc;
}
