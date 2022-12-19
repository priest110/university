
/* Para medir o tempo */
#include "../include/time_m.h"
#include <stdio.h>

struct timeval t;
long long unsigned cpu_time;
int bp = 0;

double begin = 0.0, end = 0.0, comm_time = 0.0;

void startTime (void) 
{
        gettimeofday(&t, NULL);
        cpu_time = t.tv_sec * TIME_RESOLUTION + t.tv_usec;
}

void lap (void)
{
        gettimeofday(&t, NULL);
        long long unsigned final_time = t.tv_sec * TIME_RESOLUTION + t.tv_usec;

        final_time -= cpu_time;

        printf("(%d) %llu us have passed...\n", bp, final_time);  

        bp++;    
}

void stopTime (int iter) 
{
        gettimeofday(&t, NULL);
        long long unsigned final_time = t.tv_sec * TIME_RESOLUTION + t.tv_usec;

        final_time -= cpu_time;

        printf("After %d iterations, ", iter);
        printf("%llu us have passed...\n", final_time);

        cpu_time = 0;
        bp = 0;
}

void stopTimeW (void) 
{
        gettimeofday(&t, NULL);
        long long unsigned final_time = t.tv_sec * TIME_RESOLUTION + t.tv_usec;

        final_time -= cpu_time;

        double d_time = (double)final_time / 1000; //in ms

        printf("%f ms have passed...\n", d_time);

        cpu_time = 0;
        bp = 0;
}

/* FUNÇÕES DE MEDIÇÃO DO T_COMM */

//Inciar a contagem do tempo para uma comunicação
void beginComm()
{
  begin = MPI_Wtime();
}

//Finalizar a contagem do tempo para uma comunicação
void endComm()
{
  end = MPI_Wtime();
  comm_time += (end - begin) * 1000.0;
  begin = end = 0.0;
}

//Printar o tempo de Comunicação
void showCommTime()
{
        printf("Comm Time: %f us\n", comm_time);
}