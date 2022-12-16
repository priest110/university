#ifndef LI2REVERSI_LISTAS_H
#define LI2REVERSI_LISTAS_H

/**
@file listas.h
@author Grupo 24
@version 1.0

Define estruturas e funções responsáveis pelas operações de desfazer comandos e guardar jogadas
*/

/**
 * @brief Estrutura que armazena as coordenadas das jogadas válidas
 *
 * Guarda as coordenadas de todas as jogadas válidas de um determinado estado de jogo para um determinado utilizador
*/
typedef struct lista{
    int x;///< Coordenada x
    int y;///< Coordenada y
    struct lista *prox; ///< Apontador para as próximas coordenadas válidas
}*Lista;

/**
 * @brief Estrutura que armazena as coordenadas de todas as jogadas
 *
 * Guarda todas as jogadas que provoquem alterações ao estado do jogo
 */
typedef struct jogada{
    ESTADO est;///< Estado do jogo
    struct jogada *prox;///< Apontador para o próximo estado do jogo guardado
}*Jogada;

/**
 *@brief Inicializa uma lista
 *
 *@param t Jogada
 */
void newJogada (Jogada t);

/**
 *@brief Faz push numa lista, sempre que fazemos alguma alteração ao estado do jogo
 *
 *@param ESTADO Estado do jogo
 *@param head Jogada a guardar
 *
 *@return Lista das jogadas guardadas
 */
Jogada push(ESTADO, Jogada head);

/**
 *@brief Faz pop numa lista, ou seja, quando queremos fazer undo
 *
 *@param head Lista das jogadas guardadas
 *
 *@return Lista das jogadas guardadas atualizada
 */
Jogada pop(Jogada head);

/**
 *@brief Inicializa uma lista(ajudas)
 *
 *@param a Coordenada a
 *@param b Coordenada b
 *@param t Lista de ajudas
 *
 *@return Lista de ajudas inicializada
 */
Lista newLista (int a, int b, Lista t);

/**
 *@brief Insere duas cordenadas numa lista(ajudas)
 *
 *@param l Lista de ajudas
 *@param a Coordenada a
 *@param b Coordenada b
 */
void insere (Lista *l, int a, int b);

/**
 *@brief Devolve uma lista com as jogadas válidas para determinado estado de jogo e jogador
 *
 *@param ESTADO Estado do jogo
 *@param x Valor da peça
 *
 *@return Lista de ajudas
 */
Lista coordAjuda(ESTADO, VALOR x);

/**
 *@brief Altera o estado com peças do tipo AJUDA
 *
 *@param help Lista de ajudas
 *@param ESTADO Estado do jogo
 *
 *@return Estado de jogo com indicação das ajudas
 */
ESTADO ajuda (Lista help, ESTADO e);

#endif //LI2REVERSI_LISTAS_H
