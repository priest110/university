#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include "estado.h"
#include "bot.h"
#include "estado.h"
#include "listas.h"


int strategy(int x, int y){
    if((x == 0 && y == 0) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 7 && y == 7))
        return 99;
    else if ((x == 0 && y == 1) || (x == 0 && y == 6) || (x == 7 && y == 1) || (x == 7 && y == 6) || (x == 1 && y == 0) || (x == 1 && y == 7) || (x == 6 && y == 0) || (x == 6 && y == 7))
        return -10;
    else if ((x == 0 && y == 2) || (x == 0 && y == 5) || (x == 7 && y == 2) || (x == 7 && y == 5) || (x == 2 && y == 0) || (x == 2 && y == 7) || (x == 5 && y == 0) || (x == 5 && y == 7))
        return 10;
    else if ((x == 0 && y == 3) || (x == 0 && y == 4) || (x == 7 && y == 3) || (x == 7 && y == 4) || (x == 3 && y == 0) || (x == 3 && y == 7) || (x == 4 && y == 0) || (x == 4 && y == 7))
        return 7;
    else if ((x == 1 && y == 1) || (x == 1 && y == 6) || (x == 6 && y == 1) || (x == 6 && y == 6))
        return -27;
    else if ((x == 1 && y == 2) || (x == 1 && y == 5) || (x == 6 && y == 2) || (x == 6 && y == 5) || (x == 2 && y == 1) || (x == 5 && y == 1) || (x == 2 && y == 6) || (x == 5 && y == 6))
        return -4;
    else if ((x == 1 && y == 3) || (x == 1 && y == 4) || (x == 6 && y == 3) || (x == 6 && y == 4) || (x == 3 && y == 1) || (x == 4 && y == 1) || (x == 3 && y == 6) || (x == 4 && y == 6))
        return -3;
    else if ((x == 2 && y == 2) || (x == 2 && y == 5) || (x == 5 && y == 2) || (x == 5 && y == 5))
        return 8;
    else if ((x == 2 && y == 3) || (x == 2 && y == 4) || (x == 5 && y == 3) || (x == 5 && y == 4) || (x == 3 && y == 2) || (x == 4 && y == 2) || (x == 3 && y == 5) || (x == 4 && y == 5))
        return 4;
    else
        return 0;

}

int minmax(int maximizador, ESTADO e, int x, int y, int depth){
    int ganho = 0;
    int max_ganho = 0, ganhoflips;
    int min_ganho = 0;
    int *r;

    ganhoflips = strategy(x, y);

    //Alteramos o estado com a peça com coordenadas (x,y)
    r = valida(x, y, e);
    e.grelha[x][y] = e.peca; //alteramos a peça que estamos a avaliar
    e = validado(r, x, y, e);

    //Usamos um estado auxiliar para podermos fazer "undo" conforme vemos cada elemento da lista de ajuda
    ESTADO a = e;

    e.peca = XToO(e.peca);
    Lista l = coordAjuda(e,e.peca);

    if(maximizador == 1){
        while(l && depth >= 0) {
            ganho = minmax(0, e, l->x, l->y, depth - 1) + ganhoflips;

            if (ganho > max_ganho )
                max_ganho = ganho;
            l = l -> prox;
            e = a;

        }
        return max_ganho;
    }
    else{
        while(l && depth >= 0) {
            ganho = minmax(1, e, l->x, l->y, depth - 1) - ganhoflips;

            if (ganho < min_ganho )
                min_ganho = ganho;
            l = l -> prox;
            e = a;

        }
        return min_ganho;
    }

}

ESTADO bot(ESTADO e, char nivel){
    int max = -1, ganho = 0, xx = 0, yy = 0;
    int *r;
    int n = nivel - '0';
    Lista l = coordAjuda(e, e.peca);

    while(l){
        ganho = minmax(1, e, l->x, l->y, n);
        if(ganho > max){
            max = ganho;
            xx = l -> x;
            yy = l -> y;

        }
        l = l -> prox;
    }

    r = valida(xx, yy, e);
    e.grelha[xx][yy] = e.peca;
    e = validado(r, xx, yy, e);

    e.peca = XToO(e.peca);
    return  e;
}

ESTADO sugestao(ESTADO e, char nivel){
    int max = -1, ganho = 0, xx = 0, yy = 0;
    int *r;
    int n = nivel - '0';
    Lista l = coordAjuda(e, e.peca);

    while(l){
        ganho = minmax(1, e, l->x, l->y, n);
        if(ganho > max){
            max = ganho;
            xx = l -> x;
            yy = l -> y;

        }
        l = l -> prox;
    }
    e.grelha[xx][yy] = SUGESTAO;
    return e;

}