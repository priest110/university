#ifndef NAVEGADOR_H
#define NAVEGADOR_H

/**
Funcao que cria o menu principal do programa
*/
int menu();

/*
Funcao que, dada uma lista e a sua dimensao, cria um navegador para esta
*/
void navegadorListas(char** strings, int quantos);

/*
Funcao que, dada uma lista e a sua dimensao, cria um navegador para esta
*/
void navegadorDuasListasNP(char** strings1, char** strings2, int quantos1, int quantos2);

#endif