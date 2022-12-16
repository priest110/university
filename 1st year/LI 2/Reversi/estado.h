#ifndef PROJ_ESTADO_H
#define PROJ_ESTADO_H

/**
@file estado.h
@author Grupo 24
@version 1.0

Define estruturas e funções responsáveis pelo estado do tabuleiro
*/

/**
 * @brief Definição de valores possiveis no tabuleiro
 *
 */
typedef enum {VAZIA, VALOR_X, VALOR_O, AJUDA, SUGESTAO} VALOR;

/**
 * @brief Esta estrutura é responsável por guardar o estado do jogo
 *
 * Guarda o valor da peça a jogar, o valor de cada elemento da grelha/tabuleiro e o modo de jogo
*/
typedef struct estado {
    VALOR peca; ///< Peça do jogador que vai jogar
    VALOR grelha[8][8]; ///< Grelha de jogo
    char modo; ///< Modo em que se está a jogar! 0-> manual, 1-> contra computador nivel básico, 3-> contra computador nivel médio, 5 -> contra computador nivel avançado
} ESTADO;

/**
 *@brief Estado inicial do tabuleiro(inicio do jogo)
 *
 *@param ESTADO Estado vazio
 *
 *@return Estado incial
 */
ESTADO inicializar (ESTADO);

/**
 *@brief Muda o valor da peca 'X' para 'O' ou vice versa.
 *
 *@param Valor da peça
 *
 *@return O valor da peça oposto
 */
VALOR XToO(VALOR peca);

/**
 *@brief Muda o valor das peças que estão no meio de duas peças 'X',para 'X', ou 'O', para 'O'. Isto para jogada válidas
 *
 *@param r Array de inteiros
 *@param x Coordenada x
 *@param y Coordenada y
 *@param ESTADO Estado do jogo
 *
 *@return Estado do jogo atualizado
 */
ESTADO validado(int* r, int x, int y, ESTADO);

/**
 *@brief Valida uma jogada x y
 *
 *@param x Coordenada x
 *@param y Coordenada y
 *@param ESTADO Estado do jogo
 *
 *@return Um array de inteiros que nos diz em quantos sentidos é uma jogada válida
 */
int* valida(int x, int y, ESTADO);


/**
 *@brief Lê um ficheiro e converte o seu conteúdo num estado
 *
 *@param ficheiro Ficheiro que vamos converter
 *
 *@return O estado do jogo convertido do ficheiro lido
 */
ESTADO lerFicheiro(char *ficheiro);

/**
 *@brief Grava o estado do tabuleiro em ficheiro
 *
 *@param e Estado do jogo
 *@param ficheiro Ficheiro onde vamos guardar o estado
 *
 */
void guardarFicheiro(ESTADO e, char *ficheiro);

/**
 *@brief Caso não haja jogadas válidas por parte de um jogador, passa a jogada ao outro utilizador
 *
 *@param e Estado do jogo
 *
 *@return 0 ou 1, que nos diz se temos de passar a vez(0) ou não(1)
 */
int passaJogada(ESTADO e);


/**
 *@brief Vê se há uma situação final de jogo
 *
 *@param e Estado do jogo
 *
 *@return 0 ou 1, que nos diz se é uma situação de final de jogo(0) ou não(1)
 */
int finalJogo(ESTADO e);

/**
 *@brief Reinicia as ajudas ou sugestão do bot, para uma nova jogada
 *
 *@param e Estado do jogo
 *
 *@return Estado do jogo atualizado
 */
ESTADO retirarAjudas(ESTADO e);

/**
 *@brief Vê que jogador ganhou e com quantos pontos
 *
 *@param e Estado do jogo
 *
 */
void quemGanhou(ESTADO e);

/**
 *@brief Imprime estado/tabuleiro
 *
 *@param e Estado do jogo
 *
 */
void printa(ESTADO);



#endif //PROJ_ESTADO_H