#include <stdio.h>
#include <stdlib.h>
#include "estado.h"
#include "listas.h"

void newJogada (Jogada t){
    t = NULL;
}

Jogada push(ESTADO e, Jogada head){
    Jogada aux = (Jogada)malloc(sizeof(struct jogada));
    if(aux == NULL)
    {
        exit(0);
    }
    aux-> est = e;
    aux-> prox = head;
    head = aux;
    return head;
}

Jogada pop(Jogada head){
    Jogada aux = head;
    head = head -> prox;
    aux -> prox = NULL;
    free(aux);
    return head;
}

Lista newLista (int a, int b, Lista t){
    Lista new = (Lista) malloc (sizeof (struct lista));

    if (new!=NULL) {
        new-> x = a;
        new -> y = b;
        new->prox  = t;
    }
    return new;
}

void insere (Lista *l, int a, int b){
    while(*l){
        l = &((*l) -> prox);
    }
    *l = newLista(a, b, *l);
}

Lista coordAjuda(ESTADO e, VALOR m) {
    Lista aux =  NULL;
    int i, j, c;

    int* r;
    for(i = 0; i < 8; i++){
        for(j = 0; j < 8; j++){
            r = valida(i,j,e);

            for(c = 0; c < 8; c++) {
                if (r[c] == 1) {
                    insere(&aux, i, j);
                    r[c] = 0;
                    break;
                }

            }
        }
    }
    return aux;
}

ESTADO ajuda (Lista help, ESTADO e){
    int i, j;
    for(i = 0; i < 8; i++){
        for(j = 0; j < 8; j++){
            if(help) {
                if (i == help->x && j == help->y) {
                    e.grelha[i][j] = AJUDA;
                    help = help->prox;
                }
            }
        }
    }
    return e;
}