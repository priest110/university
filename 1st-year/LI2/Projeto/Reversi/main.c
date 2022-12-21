#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <ctype.h>
#include "estado.h"
#include "listas.h"
#include "bot.h"

int main()
{
    int x, y, flag = 0, bot_ativado = 0, bot_a_jogar = 0;
    int* r;
    Lista new;
    Jogada nova = (Jogada) malloc (sizeof (struct jogada));
    newJogada(nova);
    char line[30];
    char ficheiro[50];
    char c1, c2, nivel;
    VALOR after_player = VAZIA;
    fgets(line, 30, stdin);
    ESTADO e = {0};
    e = inicializar(e);

    while(toupper(line[0]) != 'Q') {
        switch (toupper(line[0])) {
            case 'L':
                sscanf(line, "%c %s", &c1,ficheiro);
                e = lerFicheiro(ficheiro);
                bot_ativado = 0;
                if(e.modo != '0'){
                    bot_ativado = 1;
                    bot_a_jogar = 3;
                    after_player = XToO(e.peca);
                }
                nova = push(e, nova);
                printa(e);
                break;
            case 'E':
                sscanf(line, "%c %s", &c1,ficheiro);
                guardarFicheiro(e, ficheiro);
                printa(e);
                break;
            case 'N':
                sscanf(line, "%c %c", &c1, &c2);
                if (toupper(c2) == 'X') e.peca = VALOR_X;
                else e.peca = VALOR_O;
                e = inicializar(e);
                bot_ativado = 0;
                nova = push(e, nova);
                printa(e);
                break;
            case 'C' :
                sscanf(line, "%c %s", &c1,ficheiro);
                bot_ativado = 0;
                FILE *file = fopen(ficheiro, "r");
                if (file != NULL) {
                    e = lerFicheiro(ficheiro);
                    bot_ativado = 6;
                    //if(e.modo != '0'){
                      //  bot_a_jogar = 4;
                        //after_player = e.peca;
                    //}
                } else {
                    file = fopen(ficheiro, "w");
                    if (file != NULL) {
                        fputs("A X 3\n", file);
                        fputs("- - - - - - - -\n", file);
                        fputs("- - - - - - - -\n", file);
                        fputs("- - - - - - - -\n", file);
                        fputs("- - - O X - - -\n", file);
                        fputs("- - - X O - - -\n", file);
                        fputs("- - - - - - - -\n", file);
                        fputs("- - - - - - - -\n", file);
                        fputs("- - - - - - - -\n", file);
                        fclose(file);
                        e = lerFicheiro(ficheiro);
                        bot_ativado = 3;
                        //if(e.modo != '0'){
                          //  bot_ativado = 1;
                           // bot_a_jogar = 3;
                           // after_player = e.peca;
                        //}
                        guardarFicheiro(e, ficheiro);

                    } else {
                        printf("Erro ao criar o ficheiro %s. Certifique-se que tem permissões.\n", ficheiro);
                    }
                }

                //if(e.modo != '0'){
                  //  bot_ativado = 1;
                   // bot_a_jogar = 3;
                    //after_player = e.peca;
                //}
                nova = push(e, nova);
                printa(e);
                break;
            case 'J' :
                sscanf(line, "%c %d %d", &c1, &x, &y);
                r = valida(x-1, y-1, e);
                flag = 0;
                for(int i = 0; i < 8; i++){
                    if(r[i] == 1) flag = 1;
                }
                if(flag == 0) printf("Jogada Inválida!");
                else{
                    e.grelha[x-1][y-1] = e.peca;
                    e = validado(r, x-1, y-1, e);
                    e.peca = XToO(e.peca);
                    nova = push(e, nova);
                    printa(e);
                }
                break;
            case 'S' :
                new = coordAjuda(e, e.peca);
                e = ajuda(new, e);
                printa(e);
                break;
            case 'H' :
                e = sugestao(e,'3');
                printa(e);
                break;
            case 'U' :
                if (((nova->prox)->prox) != NULL) {
                    nova = pop(nova);
                    e = nova->est;
                }
                printa(e);
                break;
            case 'A' :
                sscanf(line, "%c %c %c", &c1, &c2, &nivel);
                e = lerFicheiro(ficheiro);
               // if (toupper(c2) == 'X')
                  //  bot_a_jogar = 1;
                //else if(toupper(c2) == 'O')
                    //bot_a_jogar = 2;
                if(nivel == '1' || nivel == '3' || nivel == '5') {
                    if(bot_ativado == 3)
                    e.modo = nivel;
                    //bot_ativado = 1;
                    if(c2 == 'O')
                        e.peca = VALOR_O;
                    else
                        e.peca = VALOR_X; //começa sempre o jogador com 'X';
                    printa(e);
                }
                else
                    printf("Nivel errado!\nOs níveis disponíveis são:\n-Básico(1)\n-Médio(3)\n-Avançado(5)");
                break;
            default:
                printf("Comando errado!");
                break;
        }
        printf("\n");

        //Reininciamos as ajudas
        e = retirarAjudas(e);

        //Vemos se ha situacao de final de jogo
        int final = finalJogo(e);
        if(final == 0){
            printf("Acabou o jogo!\n");
            quemGanhou(e);
        }

        //Vemos se ha situacao de passar a jogada
        int passar = passaJogada(e);
        if(final != 0 && passar == 0){
            char c;
            if (e.peca == VALOR_X) c = 'X';
            else c = 'O';
            printf("O jogador '%c' tem de passar jogada, pois nao existem jogadas válidas disponiveis!\n", c);
            e.peca = XToO(e.peca);
        }

        //Vemos se estamos a jogar contra o bot e senão ocorre situação de final de jogo
        if(bot_ativado == 1 && final != 0){
            if((bot_a_jogar == 2 && e.peca == VALOR_O) || (bot_a_jogar == 1 && e.peca == VALOR_X)) {
                e = bot(e, e.modo);
                printa(e);
                printf("\n");
            }
            else if(bot_a_jogar == 3){
                if(e.peca == after_player){
                    e = bot(e, e.modo);
                    printa(e);
                    printf("\n");
                }
            }
        }
        if(bot_ativado == 6){
            e = bot(e, e.modo);
            guardarFicheiro(e,ficheiro);
            printa(e);
            printf("\n");
        }
        if(bot_ativado == 3 && final != 0){
            if(bot_a_jogar == 5) {
                e = bot(e, e.modo);
                guardarFicheiro(e,ficheiro);
                printa(e);
                printf("\n");
            }
            bot_a_jogar = 5;
        }
        fgets(line, 30, stdin);
    }

    return 0;
}