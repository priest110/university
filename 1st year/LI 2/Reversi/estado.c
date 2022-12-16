#include <stdio.h>
#include <stdlib.h>
#include "estado.h"
#include "listas.h"

ESTADO inicializar (ESTADO e){
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
           e.grelha[i][j]= VAZIA;
        }
    }
    e.grelha[3][4] = VALOR_X;
    e.grelha[4][3] = VALOR_X;
    e.grelha[3][3] = VALOR_O;
    e.grelha[4][4] = VALOR_O;
    e.modo = '0';
    return e;
}

VALOR XToO(VALOR r){
    if(r == VALOR_X) return  VALOR_O;
    return  VALOR_X;
}

ESTADO validado(int* r, int x, int y, ESTADO e){
    int i = x, j = y, flag = 1;
    while(flag == 1){
        if(r[0] == 1)
            for(i, j; e.grelha[i][j+1] == XToO(e.peca); j++)
                e.grelha[i][j+1] = e.peca;
        if(r[1] == 1)
            for(i = x, j = y; e.grelha[i][j-1] == XToO(e.peca); j--)
                e.grelha[i][j-1] = e.peca;
        if(r[2] == 1)
            for(i = x, j = y; e.grelha[i+1][j] == XToO(e.peca); i++)
                e.grelha[i+1][j] = e.peca;
        if(r[3] == 1)
            for(i = x, j = y; e.grelha[i-1][j] == XToO(e.peca); i--)
                e.grelha[i-1][j] = e.peca;
        if(r[4] == 1)
            for(i = x, j = y; e.grelha[i-1][j+1] == XToO(e.peca); i--,j++)
                e.grelha[i-1][j+1] = e.peca;
        if(r[5] == 1)
            for(i = x, j = y; e.grelha[i-1][j-1] == XToO(e.peca); i--,j--)
                e.grelha[i-1][j-1] = e.peca;
        if(r[6] == 1)
            for(i = x, j = y; e.grelha[i+1][j-1] == XToO(e.peca); i++,j--)
                e.grelha[i+1][j-1] = e.peca;
        if(r[7] == 1)
            for(i = x, j = y; e.grelha[i+1][j+1] == XToO(e.peca); i++,j++)
                e.grelha[i+1][j+1] = e.peca;
        flag = 0;
    }
    return e;
}

int* valida(int x, int y, ESTADO e){
    int* r = malloc((8*sizeof(int)));
    for(int j = 0; j < 8; j++){
        r[j] = 0;
    }
    int l, c;
    int i = x, j = y;
    if((e.grelha[x][y] == VAZIA || e.grelha[x][y] == AJUDA) && (x >= 0 && x < 8) && (y >= 0 && y < 8)) {
        if (e.grelha[i][j + 1] == XToO(e.peca)) {
            for (c = j + 1; e.grelha[i][c] == XToO(e.peca); c++) {
                if (e.grelha[i][c + 1] == e.peca && ((c + 1) < 8)) {
                    r[0] = 1;
                    break;
                }
            }
        }
        if (e.grelha[i][j - 1] == XToO(e.peca)) {
            for (c = j - 1; e.grelha[i][c] == XToO(e.peca); c--) {
                if (e.grelha[i][c - 1] == e.peca && ((c - 1) > -1)) {
                    r[1] = 1;
                    break;
                }
            }
        }
        if (e.grelha[i + 1][j] == XToO(e.peca)) {
            for (l = i + 1; e.grelha[l][j] == XToO(e.peca); l++) {
                if (e.grelha[l + 1][j] == e.peca && ((l + 1) < 8)) {
                    r[2] = 1;
                    break;
                }
            }
        }
        if (e.grelha[i - 1][j] == XToO(e.peca)) {
            for (l = i - 1; e.grelha[l][j] == XToO(e.peca); l--) {
                if (e.grelha[l - 1][j] == e.peca && ((l - 1) > -1)) {
                    r[3] = 1;
                    break;
                }
            }
        }
        if (e.grelha[i - 1][j + 1] == XToO(e.peca)) {
            for (l = i - 1, c = j + 1; e.grelha[l][c] == XToO(e.peca); l--, c++){
                if (e.grelha[l - 1][c + 1] == e.peca && ((c + 1) < 8) && ((l - 1) > -1)) {
                    r[4] = 1;
                    break;
                }
            }
        }
        if (e.grelha[i - 1][j - 1] == XToO(e.peca)) {
            for (l = i - 1, c = j - 1; e.grelha[l][c] == XToO(e.peca); l--, c--) {
                if (e.grelha[l - 1][c - 1] == e.peca && ((c - 1) > -1) && ((l - 1) > -1)) {
                    r[5] = 1;
                    break;
                }
            }
        }
        if (e.grelha[i + 1][j - 1] == XToO(e.peca)) {
            for (l = i + 1, c = j - 1; e.grelha[l][c] == XToO(e.peca); l++, c--) {
                if (e.grelha[l + 1][c - 1] == e.peca && ((c - 1) > -1) && ((l + 1) < 8)) {
                    r[6] = 1;
                    break;
                }
            }
        }
        if (e.grelha[i + 1][j + 1] == XToO(e.peca)) {
            for (l = i + 1, c = j + 1; e.grelha[l][c] == XToO(e.peca); l++, c++)
                if (e.grelha[l + 1][c + 1] == e.peca && ((l+1) < 8) && ((c+1) < 8)){
                    r[7] = 1;
                    break;
                }
        }
    }
    return r;
}

ESTADO lerFicheiro(char *ficheiro){
    char buffer[15];
    ESTADO e = {0};
    char modo, peca, nivel;
    FILE *fp = fopen(ficheiro,"r");
    if (fp == NULL){
        printf("Erro a abrir o ficheiro!");
        exit(1);
    }
    fscanf(fp,"%c %c ",&modo,&peca);

    if(modo == 'A'){
        fscanf(fp, "%c", &nivel);
        e.modo = nivel;
    }

    else e.modo = '0';

    if(peca == 'O')
        e.peca = VALOR_O;
    else e.peca = VALOR_X;

    for(int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++){
            fscanf(fp, "%s ", buffer);
             switch (buffer[0]) {
                 case '-' :{
                     e.grelha[i][j] = VAZIA;
                     break;
                }case 'X' :{
                     e.grelha[i][j] = VALOR_X;
                     break;
                }
                case 'O' :{
                     e.grelha[i][j] = VALOR_O;
                     break;
                }
                 default:
                     break;
             }
         }
    }
    fclose(fp);
    return e;
}

void guardarFicheiro(ESTADO e, char *ficheiro){
    FILE *fp = fopen(ficheiro, "w");
    if (fp == NULL){
        printf("Erro ao abrir ficheiro!\n");
        exit(1);
    }
    char a;
    if(e.peca == VALOR_O)
        a = 'O';
    else a = 'X';

    if(e.modo != '0')
        fprintf(fp, "A %c %c\n", a, e.modo);
    else
        fprintf(fp,"M %c\n", a);
    for(int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++){
            switch (e.grelha[i][j]) {
                case VALOR_O: {
                    a = 'O';
                    break;
                }
                case VALOR_X: {
                    a = 'X';

                    break;
                }
                case VAZIA: {
                    a = '-';
                    break;
                }
                case AJUDA: {
                    a = '-';
                    break;
                }
                default:
                    break;
            }
            fprintf(fp, "%c ", a);
        }
        fprintf(fp, "\n");
    }
    fclose(fp);
}

int passaJogada(ESTADO e){
    int* r;
    int c, flag = 0;
    for(int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            //Vemos se ha jogadas validas disponiveis, se caso forem "." e porque ja sao validas
            r = valida(i, j, e);
            for (c = 0; c < 8; c++) {
                if (r[c] == 1) {
                    flag = 1;
                    break;
                }
            }
        }
    }
    return flag;
}

int finalJogo(ESTADO e){
    int i, j, c, flag = 0, flag3 = 0;
    int* r;
    int *r2;
    ESTADO a = {0};
    a = e;
    a.peca = XToO(e.peca);
    for(i = 0; i < 8; i++){
        for(j = 0; j < 8; j++) {
            //Vemos se ha jogadas validas disponiveis
            r = valida(i, j, e);
            r2 = valida(i, j, a);
            for(c = 0; c < 8; c++) {
                if (r[c] == 1 || r2[c] == 1) {
                    flag = 1;
                    break;
                }
            }
            //Vemos so o tabuleiro esta completamente preenchido ou se tem peças só de uma cor
            if(e.grelha[i][j] == VALOR_X)
                flag3 = 1;
            if(e.grelha[i][j] == VALOR_O)
                flag3 = 2;
            if((flag3 == 1 && e.grelha[i][j] == VALOR_O) || (flag3 == 2 && e.grelha[i][j] == VALOR_X))
                flag3 = 3;
        }
        if (flag == 1)
            break;
    }
    if (flag == 1 || flag3 == 3)
        flag = 1;
    return flag;
}

ESTADO retirarAjudas(ESTADO e){
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            if(e.grelha[i][j] == AJUDA || e.grelha[i][j] == SUGESTAO)
                e.grelha[i][j] = VAZIA;
        }
    }
    return e;
}

void quemGanhou(ESTADO e){
    int rX = 0, rO = 0, x;
    char c;
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            switch (e.grelha[i][j]) {
                case VALOR_O: {
                    rO++;
                    break;
                }
                case VALOR_X: {
                    rX++;
                    break;
                }
                default:
                    break;
            }
        }
    }
    if(rO < rX){
        x = rX;
        c = 'X';
    }
    else {
        x = rO;
        c = 'O';
    }
    printf("Ganhou a peça %c, com %d peças.\n", c, x);
}

void printa(ESTADO e){
    char c = ' ';
    int rX = 0, rO = 0;
    printf("  1 2 3 4 5 6 7 8\n");
    for (int i = 0; i < 8; i++) {
        printf("%d ",i+1);
        for (int j = 0; j < 8; j++) {
            switch (e.grelha[i][j]) {
                case VALOR_O: {
                    c = 'O';
                    rO++;
                    break;
                }
                case VALOR_X: {
                    c = 'X';
                    rX++;
                    break;
                }
                case VAZIA: {
                    c = '-';
                    break;
                }
                case AJUDA: {
                    c = '.';
                    break;
                }
                case SUGESTAO: {
                    c = '?';
                    break;
                }
                default:
                    break;
            }
            printf("%c ", c);
        }
        printf("\n");
    }
    printf("Score: X=%d, O=%d", rX, rO);
}

