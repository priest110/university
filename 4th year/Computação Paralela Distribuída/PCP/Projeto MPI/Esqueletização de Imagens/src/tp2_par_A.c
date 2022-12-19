#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <time.h>
#include <math.h>


#include "../include/time_m.h"

#define ROOT 0
#define MAX_ITER 5
#define MAX_BUF_LEN 1024


/* ESTRUTURA DE DADOS PARA GUARDAR A MATRIZ */

//Struct que guarda a matriz e suas dimensões
struct imagem_pgm
{
  int* m;
  int linhas, colunas;
};

//Gerar uma matriz com valores aleatórios
struct imagem_pgm generate_random(int linhas, int colunas)
{
  time_t t;
   
  /* Intializes random number generator (code excerpt from StackOverflow) */
  srand((unsigned) time(&t));

  struct imagem_pgm img;

  img.colunas = colunas;
  img.linhas = linhas;

  img.m = (int*)malloc(sizeof(int) * linhas * colunas);
  for(int i = 0; i < linhas; i++)
  {
    for(int j = 0; j < colunas; j++)
    {
      img.m[i * colunas + j] = rand() % 2;
    }
  }

  return img;
}

//Fazer Free da estrutura
void clear_imagem_pgm(struct imagem_pgm img)
{
  free(img.m);
}

//Fazer pretty printing da estrutura
void show_image(struct imagem_pgm img)
{
  for(int i = 0; i < img.linhas; i++)
  {
    for(int j = 0; j < img.colunas; j++)
    {
      if(img.m[i * img.colunas + j]) printf("\x1B[31m%d \x1B[0m", img.m[i*img.colunas+j]);
      else printf("%d ", img.m[i * img.colunas + j]);
    }
    printf("\n");
  }
}

/* I/O */

//Ler a matriz de um ficheiro
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

    img.m = (int*)malloc(img.linhas * img.colunas * sizeof(int));

    //Ignorar linha que tem o BRANCO
    fgets(buf, MAX_BUF_LEN, fp);

    int i = 0, j = 0, c = 1;

    //As seguintes linhas vão ter a matriz de valores
    while(fgets(buf, MAX_BUF_LEN, fp))
    {
      while(j < img.colunas)
      {
        sscanf(buf + j*2, "%d ", &(img.m)[i * img.colunas + j]);
        j++;
      }
      i++; j=0;
    }

    fclose(fp);

    return img;
}

//Escrever a matriz num ficheiro (e apaga a memória alocada)
void write_matrix(const char* path, struct imagem_pgm img)
{
  FILE* fp = fopen(path, "w");

  fprintf(fp, "P2\n%d %d\n1\n", img.colunas, img.linhas);

  for(int i = 0; i < img.linhas; i++)
  {
      for(int j = 0; j < img.colunas; j++)
      {
        fprintf(fp, "%d ", (img.m)[i * img.colunas + j]);
      }
      fprintf(fp, "\n");
  }
  fclose(fp);
  free((img.m));
}

/* IMPLEMENTAÇÃO DO ALGORITMO */

//Número de vizinhos a '1' (faz 7 somas (sem contar com as dos índices), com 8 acessos à memória, potencialmente)
int N(int i, int j, int* m, int col)
{
  return m[(i-1) * col + (j-1)] + 
         m[(i-1) * col + j] +
         m[(i-1) * col + (j+1)] +
         m[i * col + (j-1)] +
         m[i * col + (j+1)] +
         m[(i+1) * col + (j+1)] +
         m[(i+1) * col + j] +
         m[(i+1) * col + (j+1)];
}

//Número de transições 0-1 na seq. p2,p3...p9 (faz 7 somas (sem contas as dos índices), com 16 acessos à memória, potencialmente)
int S(int i, int j, int* m, int col)
{
  return (!m[(i-1) * col + j] && m[(i-1) * col + j+1]) +
         (!m[(i-1) * col + (j+1)] && m[i * col + (j+1)]) +
         (!m[i * col + (j+1)] && m[(i+1) * col + (j+1)]) +
         (!m[(i+1) * col + (j+1)] && m[(i+1) * col + j]) + 
         (!m[(i+1) * col + j] && m[(i+1) * col + (j-1)]) +
         (!m[(i+1) * col + (j-1)] && m[i * col + (j-1)]) +
         (!m[i * col + (j-1)] && m[(i-1) * col + (j-1)]) +
         (!m[(i-1) * col + (j-1)] && m[(i-1) * col + j]);
}

//Teste da primeira passagem (retorna se foi mudado (1) ou não (0))
void teste_p1(int *m, int i, int j, int *changed, int col)
{
  int n = N(i, j, m, col);
  (m)[i * col + j] && n <= 6 && n >= 2 && S(i, j, m, col) == 1 && 
  !(m)[(i+1) * col + j] + !(m)[i * col + (j+1)] + !(m)[(i-1) * col + j] * !(m)[i * col + (j-1)] == 1 ? (m[i * col + j] = 0, *changed=1) : 0;
}

//Teste da segunda passagem (retorna se foi mudado (1) ou não (0))
void teste_p2(int *m, int i, int j, int *changed, int col)
{
  int n = N(i, j, m, col);
  (m)[i * col + j] && n <= 6 && n >= 2 && S(i, j, m, col) == 1 && 
  !(m)[(i-1) * col + j] + !(m)[i * col + (j-1)] + !(m)[i * col + (j+1)] * !(m)[(i+1) * col + j] == 1 ? (m[i * col + j] = 0, *changed=1) : 0;
}

/* MAIN */

int main(int argc, char** argv)
{
    int nprocesses, rank; 
    int changed = 1, temp = changed, iter = 0;
    int l, c;
    int *m;

    //Para calcular o tempo de comunicação em cada proces

    MPI_Status status;

    struct imagem_pgm img;

    MPI_Init(&argc, &argv);

    MPI_Comm_size(MPI_COMM_WORLD, &nprocesses);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    /* Leitura da Imagem/Geração de Matriz Aleatória */
    if(rank == 0)
    {
      if(argc == 1)
      {
          img = read_matrix("input/snake.pgm");
          l = img.linhas;
          c = img.colunas;
      }
      else
      {
          l = atoi(argv[1]);
          c = atoi(argv[2]);
          img = generate_random(l,c);
      }
      m = img.m;
    }

    if(!rank) beginComm();

    //Temos de fazer broadcast do tamanho da matriz caso não estejamos a ler de ficheiro
    MPI_Bcast(&l, 1, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Bcast(&c, 1, MPI_INT, 0, MPI_COMM_WORLD);

    if(!rank) endComm();

    /*Este bloco com a distribuição do payload por todos os processos deve ser ignorado*/
    
    /* Divisão de Tarefas */

    if(!rank) startTime();

    //Número de linhas base por processo (base number of lines per process)
    int bnlpp = floor(l/nprocesses);
    //Número de elementos por processo
    int *sendcount = (int*)malloc(sizeof(int) * nprocesses);
    //Ponto de partida na matriz para cada processo
    int *displs = (int*)malloc(sizeof(int) * nprocesses);
    //Número de linhas de cada processo
    int *lines = (int*)malloc(sizeof(int) * nprocesses);

    int last_elem_atr = 0;
    //Temos de ter em conta que a primeira e a última linha não são processadas
    int rest = l % nprocesses;

    //Atribuir o número de elementos aos processos e dividir os elementos da matriz
    for(int i = 0; i < nprocesses; i++)
    {
        sendcount[i] = i < rest ? (bnlpp+3) * c : (bnlpp+2) * c;
        lines[i] = i < rest ? bnlpp + 3 : bnlpp + 2; 

        if(!i || i == nprocesses-1) { sendcount[i] -= c; lines[i]--; }

        displs[i] = last_elem_atr;
        last_elem_atr += sendcount[i] - (2 * c);
    }

    int *sub_m = (int*)malloc(sizeof(int) * (sendcount[rank] + (2*c)));

    if(!rank) { stopTimeW(); printf("(Workload division)"); }

    if(!rank) beginComm();

    MPI_Scatterv(m, sendcount, displs, MPI_INT, sub_m, sendcount[rank], MPI_INT, 0, MPI_COMM_WORLD);

    if(!rank) endComm();

    if(!rank) startTime();

    /* ALGORITMO DE ESQUELETIZAÇÃO */
    while(changed && iter < MAX_ITER)
    {
      changed = 0;
      
      if(rank == 0)
      {
        //1º passagem

        beginComm();

        //Manda a penúltima linha
        MPI_Send(sub_m + sendcount[rank] - (2 * c), c, MPI_INT, 1, ROOT, MPI_COMM_WORLD);
        //Recebe a última linha
        MPI_Recv(sub_m + sendcount[rank] - c     , c, MPI_INT, 1, ROOT, MPI_COMM_WORLD, &status);

        endComm();

        for(int i = 1; i < lines[rank]-1; i++)
        {
          for(int j = 1; j < c; j++)
          {
            sub_m[i * c + j] ? teste_p1(sub_m, i, j, &changed, c) : 0;
          }
        }

        //2ª Passagem

        beginComm();

        //Manda a penúltima linha
        MPI_Send(sub_m + sendcount[rank] - (2 * c), c, MPI_INT, 1, 0, MPI_COMM_WORLD);
        //Recebe a última linha
        MPI_Recv(sub_m + sendcount[rank] - c      , c, MPI_INT, 1, 0, MPI_COMM_WORLD, &status);

        endComm();

        for(int i = 1; i < lines[rank]-1; i++)
        {
          for(int j = 1; j < c; j++)
          {
            sub_m[i * c + j] ? teste_p2(sub_m, i, j, &changed, c) : 0;
          }
        }
      }
      else
      {
        if(rank == nprocesses-1)
        {
          //1ª Passagem
          //Envia a segunda linha
          MPI_Send(sub_m + c, c, MPI_INT, rank - 1, 0, MPI_COMM_WORLD);
          //Recebe a primeira
          MPI_Recv(sub_m    , c, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &status);            

          for(int i = 1; i < lines[rank]-1; i++)
          {
            for(int j = 1; j < c; j++)
            {
              sub_m[i * c + j] ? teste_p1(sub_m, i, j, &changed, c) : 0;
            }
          }

          //2ª Passagem
          //Envia a segunda linha
          MPI_Send(sub_m + c, c, MPI_INT, rank - 1, 0, MPI_COMM_WORLD);
          //Recebe a primeira
          MPI_Recv(sub_m    , c, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &status);

          for(int i = 1; i < lines[rank]-1; i++)
          {
            for(int j = 1; j < c; j++)
            {
              sub_m[i * c + j] ? teste_p2(sub_m, i, j, &changed, c) : 0;
            }
          }
        }
        else /* Processos [1:nprocesses-2] */
        {
          //1ª Passagem
          //Envia a segunda linha
          MPI_Send(sub_m + c, c, MPI_INT, rank - 1, 0, MPI_COMM_WORLD);
          //Recebe a primeira
          MPI_Recv(sub_m    , c, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &status);

          //Manda a penúltima linha
          MPI_Send(sub_m + sendcount[rank] - (2 * c), c, MPI_INT, rank + 1, ROOT, MPI_COMM_WORLD);
          //Recebe a última linha
          MPI_Recv(sub_m + sendcount[rank] - c      , c, MPI_INT, rank + 1, ROOT, MPI_COMM_WORLD, &status);

          for(int i = 1; i < lines[rank]-1; i++)
          {
            for(int j = 1; j < c; j++)
            {
              sub_m[i * c + j] ? teste_p1(sub_m, i, j, &changed, c) : 0;
            }
          }

          //2ª Passagem
          //Envia a segunda linha
          MPI_Send(sub_m + c, c, MPI_INT, rank - 1, 0, MPI_COMM_WORLD);
          //Recebe a primeira
          MPI_Recv(sub_m    , c, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &status);

          //Manda a penúltima linha
          MPI_Send(sub_m + sendcount[rank] - (2 * c), c, MPI_INT, rank + 1, ROOT, MPI_COMM_WORLD);
          //Recebe a última linha
          MPI_Recv(sub_m + sendcount[rank] - c      , c, MPI_INT, rank + 1, ROOT, MPI_COMM_WORLD, &status);

          for(int i = 1; i < lines[rank]-1; i++)
          {
            for(int j = 1; j < c; j++)
            {
              sub_m[i * c + j] ? teste_p2(sub_m, i, j, &changed, c) : 0;
            }
          }
        }
      }

      if(!rank) beginComm();

      MPI_Allreduce(&changed, &temp, 1, MPI_INT, MPI_SUM, MPI_COMM_WORLD);

      if(!rank) endComm();

      changed = temp;
      iter++;
    }

    if(!rank) stopTimeW();


    if(!rank) beginComm();

    /* Agregação dos resultados */
    MPI_Gatherv(sub_m, sendcount[rank], MPI_INT, m, sendcount, displs, MPI_INT, 0, MPI_COMM_WORLD);

    if(!rank) endComm();

    /* Escrita da nova imagem/free da matriz */
    if(rank == 0)
    {
        if(argc == 1)
        {
            show_image(img);
            write_matrix("output/snake_mpi_a.pgm", img);
        }
        else
        {
            clear_imagem_pgm(img);
        }
    }

    if(!rank)
    {
      showCommTime();
    }
    
    MPI_Finalize();
    return 0;
}