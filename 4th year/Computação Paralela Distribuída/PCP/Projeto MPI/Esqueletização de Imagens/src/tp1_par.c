#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <time.h>
#include <math.h>

#include "../include/time_m.h"

#include <omp.h>

#define MAX_BUF_LEN 1024

int num_threads;


//Struct que guarda a matriz e suas dimensões
struct imagem_pgm
{
  int** m;
  int linhas, colunas;
};

struct imagem_pgm generate_random(int linhas, int colunas)
{
  time_t t;
   
  /* Intializes random number generator (code excerpt from StackOverflow) */
  srand((unsigned) time(&t));

  struct imagem_pgm img;

  img.colunas = colunas;
  img.linhas = linhas;

  img.m = (int**)malloc(sizeof(int*) * linhas);
  for(int i = 0; i < linhas; i++)
  {
    (img.m)[i] = (int*)malloc(sizeof(int) * colunas);
    for(int j = 0; j < colunas; j++)
    {
      img.m[i][j] = rand() % 2;
    }
  }

  return img;
}

void clear_imagem_pgm(struct imagem_pgm img)
{
  for(int i = 0; i < img.linhas; i++){
    free(img.m[i]);
  }
  free(img.m);
}

/* I/O */

struct imagem_pgm read_matrix(const char* path)
{
    FILE* fp = fopen(path, "r");

    if(!fp){
      printf("Unable to open file\n");
      exit(0);
    }
    

    char buf[MAX_BUF_LEN];

    struct imagem_pgm img;

    //Ignorar a linha que tem o número mágico
    fgets(buf, MAX_BUF_LEN, fp);

    fgets(buf, MAX_BUF_LEN, fp);
    sscanf(buf, "%d %d", &img.colunas, &img.linhas);

    img.m = (int**)malloc(img.linhas * sizeof(int*));
    for(int i = 0; i < img.linhas; i++)
    {
      (img.m)[i] = (int*)malloc(sizeof(int) * img.colunas);
    }

    //Ignorar linha que tem o BRANCO
    fgets(buf, MAX_BUF_LEN, fp);

    int i = 0, j = 0, c = 1;

    //As seguintes linhas vão ter a matriz de valores
    while(fgets(buf, MAX_BUF_LEN, fp))
    {
      while(j < img.colunas)
      {
        sscanf(buf + j*2, "%d ", &(img.m)[i][j]);
        j++;
      }
      i++; j=0;
    }

    fclose(fp);

    return img;
}

void write_matrix(const char* path, struct imagem_pgm img)
{
  FILE* fp = fopen(path, "w");

  fprintf(fp, "P2\n%d %d\n1\n", img.colunas, img.linhas);

  for(int i = 0; i < img.linhas; i++)
  {
      for(int j = 0; j < img.colunas-1; j++)
      {
        fprintf(fp, "%d ", (img.m)[i][j]);
      }
      fprintf(fp, "%d\n",(img.m)[i][img.colunas-1]);
      free((img.m)[i]);
  }
  fclose(fp);
  free((img.m));
}

/* IMPLEMENTAÇÃO DO ALGORITMO */

//Número de vizinhos a '1'
int N(int i, int j, int** m)
{
  return m[i-1][j-1] + m[i-1][j] + m[i-1][j+1] + m[i][j-1] + m[i][j+1] + m[i+1][j-1] + m[i+1][j] + m[i+1][j+1];
}

//Número de transições 0-1 na seq. p2,p3...p9
int S(int i, int j, int** m)
{
  int r= (!m[i-1][j] && m[i-1][j+1]) +
         (!m[i-1][j+1] && m[i][j+1]) +
         (!m[i][j+1] && m[i+1][j+1]) +
         (!m[i+1][j+1] && m[i+1][j]) + 
         (!m[i+1][j] && m[i+1][j-1]) +
         (!m[i+1][j-1] && m[i][j-1]) +
         (!m[i][j-1] && m[i-1][j-1]) +
         (!m[i-1][j-1] && m[i-1][j]);

  return r;
}

//Teste da primeira passagem (retorna se foi mudado (1) ou não (0))
void testeB1(int **m, int i, int j, int *changed)
{
  int n = N(i, j, m);
  (m)[i][j] && n <= 6 && n >= 2 && S(i, j, m) == 1 && 
  !(m)[i+1][j] + !(m)[i][j+1] + !(m)[i-1][j] * !(m)[i][j-1] == 1 ? (m)[i][j] = 0, *changed=1 : 0;
}

//Teste da segunda passagem (retorna se foi mudado (1) ou não (0))
void testeB2(int **m, int i, int j, int *changed)
{
  int n = N(i, j, m);
  (m)[i][j] && n <= 6 && n >= 2 && S(i, j, m) == 1 && 
  !(m)[i-1][j] + !(m)[i][j-1] + !(m)[i][j+1] * !(m)[i+1][j] == 1 ? (m[i][j] = 0, *changed=1) : 0;
}


//Algoritmo de Esqueletização (Sequencial)
int skeleton_alg(int **m, int linhas, int colunas)
{
  int n, s, changed, iter = 0;
  int i, j;

  //Repetir até não remover mais píxeis
  do
  {
    changed = 0;

    //1ª Passagem
    #pragma omp parallel for num_threads(num_threads) private(j)
    for(i = 1; i < linhas-1; i++)
    {
      for(j = 1; j < colunas-1; j++)
      {
        m[i][j] ? testeB1(m, i, j, &changed) : 0; 
      }
    }
    //2ª Passagem
    #pragma omp parallel for num_threads(num_threads) private(j)
    for(i = 1; i < linhas-1; i++)
    {
      for(j = 1; j < colunas-1; j++)
      {
        m[i][j] ? testeB2(m, i, j, &changed) : 0;
      }
    }
    iter++;
  }
  while(changed && iter <= 3);

  return iter;
}

/* MAIN */

int main(int argc, char** argv)
{
  if(!argv[1])
  {
      printf("Not enough arguments!\n");
      return 0;
  }
  num_threads = atoi(argv[1]);
  if(!argv[2])
  {   
    struct imagem_pgm img = read_matrix("input/snake.pgm");   
    startTime();  
    int iter = skeleton_alg(img.m, img.linhas, img.colunas);  
    stopTime(iter);   
    write_matrix("output/snake_op_line_v2.pgm", img); 
  }
  else
  {   
    struct imagem_pgm img = generate_random(atoi(argv[2]), atoi(argv[3]));    
    startTime();  
    int iter = skeleton_alg(img.m, img.linhas, img.colunas);  
    stopTime(iter);   
    clear_imagem_pgm(img);    
  }
}
