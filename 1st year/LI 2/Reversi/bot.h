#ifndef LI2REVERSI_BOT_H
#define LI2REVERSI_BOT_H

/**
@file bot.h
@author Grupo 24
@version 1.0

Define funções responsáveis pelo bot e o seu algoritmo
*/


/**
 *@brief Estratégia do bot: a cada valor do tabuleiro dá um valor numérico conforme seja um bom sítio ou não para jogar
 *
 *@param x Coordenada x
 *@param y Coordenada y
 *
 *@return Inteiro que varia conforme seja um bom sítio para jogar ou não
 */
int strategy(int x, int y);

/**
 *@brief Esta função representa o algoritmo minmax
 *
 *@param maximizador Jogador maximizador ou não
 *@param e Estado do jogo
 *@param x Coordenada x
 *@param y Coordenada y
 *@param depth Profundidade do algoritmo, está relacionada com o nível
 *
 *@return Inteiro que é tanto maior ou menor conforme seja o minimizador ou maximizador(bot) a jogar
 */
int minmax(int maximizador, ESTADO e, int x, int y, int depth);

/**
 *@brief Esta função, através de uma lista de jogadas iniciais disponíveis, vê a melhor solução para o bot.
 *
 *@param e Estado do jogo
 *@param nivel Nivel de jogo
 *
 *@return Estado de jogo após o bot jogar
 */
ESTADO bot(ESTADO e, char nivel);

/**
 *@brief Esta função, devolve uma sugestão(encontrada pelo bot) para ajudar o utilizador
 *
 *@param e Estado do jogo
 *@param nivel Nivel de jogo
 *
 *@return Estado de jogo após o bot dar a sugestão de jogada, com indicação da sugestão
 */
ESTADO sugestao(ESTADO e, char nivel);

#endif //LI2REVERSI_BOT_H
