#include "../include/poisson.h"
#include "../include/time_m.h"

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <limits.h>

/**
 * @file main.c
 * 
 * @author Eduardo Conceição
 * @author Rui Oliveira
 * 
 * This file contains the call to main, using the sequential version of the
 * algorithms.
 * 
 */


#define GS    0
#define GSRB  1
#define SORRB 2

int main(int argc, char* argv[])
{
    if ( argc != 6 )
    {
        printf("Invalid Arguments!\n");
        printf("Usage: ");
        printf("poisson <ALG> <NUM_THREADS> <MESH_GRAIN> <REPS> <OMEGA>\n");
        return 0;
    }

    int SIZE, NO_THREADS, REPS, ALG;
    double OMEGA;

    ALG        = atoi(argv[1]);
    NO_THREADS = atoi(argv[2]);
    SIZE       = atoi(argv[3]);
    REPS       = atoi(argv[4]);
    OMEGA      = atof(argv[5]);

    // Set the number of threads that will run the code
    set_no_threads(NO_THREADS);
    // Get the tolerance for the input size
    double tolerance = get_tolerance(SIZE);

    double** u = (double**)malloc(sizeof(double*) * SIZE);
    for(unsigned i = 0; i < SIZE; i++)
    {   
        u[i] = (double*)malloc(sizeof(double) * SIZE);
        for(unsigned j = 0; j < SIZE; j++)
        {
            if(i == 0 || j == 0 || j == SIZE - 1) u[i][j] = 100;
            else if(i == SIZE - 1) u[i][j] = 0;
            else u[i][j] = 50;
        }
    }

    unsigned long iter = 0;

    switch ( ALG )
    {
        // Gauss Seidel
        case GS: 
        {
            unsigned long long min_exec_time = ULLONG_MAX;

            for(unsigned i = 0; i < REPS; i++)
            {
                startTime();
        
                poisson_gs(u, SIZE, tolerance, &iter);
                
                long long unsigned t = stopTime();
        
                min_exec_time = min_exec_time < t ? min_exec_time : t;
        
                // Reset the input matrix
                for(unsigned i = 0; i < SIZE; i++)
                {   
                    for(unsigned j = 0; j < SIZE; j++)
                    {
                        if(i == 0 || j == 0 || j == SIZE - 1) u[i][j] = 100;
                        else if(i == SIZE - 1) u[i][j] = 0;
                        else u[i][j] = 50;
                    }
                }
        
            }
        
            printf("GS, %d, %d, %llu, %lu\n", NO_THREADS, SIZE, min_exec_time, iter);
            break;
        }
        // Gauss Seidel Red-Black
        case GSRB :
        {
            unsigned long long min_exec_time = ULLONG_MAX;

            for(unsigned i = 0; i < REPS; i++)
            {
                startTime();

                poisson_gs_rb(u, SIZE, tolerance, &iter);

                long long unsigned t = stopTime();

                min_exec_time = min_exec_time < t ? min_exec_time : t;

                // Reset the input matrix
                for(unsigned i = 0; i < SIZE; i++)
                {   
                    for(unsigned j = 0; j < SIZE; j++)
                    {
                        if(i == 0 || j == 0 || j == SIZE - 1) u[i][j] = 100;
                        else if(i == SIZE - 1) u[i][j] = 0;
                        else u[i][j] = 50;
                    }
                }

            }
            printf("GSRB, %d, %d, %llu, %lu\n", NO_THREADS, SIZE, min_exec_time, iter);
            break;
        }
        // Successive Over-Relaxation Red-Black
        case SORRB:
        {
            unsigned long long min_exec_time = ULLONG_MAX;

            for(unsigned i = 0; i < REPS; i++)
            {
                startTime();

                poisson_sor_rb(u, SIZE, tolerance, &iter, OMEGA);

                long long unsigned t = stopTime();

                min_exec_time = min_exec_time < t ? min_exec_time : t;

                // Reset the input matrix
                for(unsigned i = 0; i < SIZE; i++)
                {   
                    for(unsigned j = 0; j < SIZE; j++)
                    {
                        if(i == 0 || j == 0 || j == SIZE - 1) u[i][j] = 100;
                        else if(i == SIZE - 1) u[i][j] = 0;
                        else u[i][j] = 50;
                    }
                }

            }

            printf("SORRB, %d, %d, %llu, %lu, %f\n", NO_THREADS, SIZE, min_exec_time, iter, OMEGA);
            break;
        }
        default:
        {
            printf("Invalid Algorithm Id!\n");
            printf("Gauss-Seidel           - 0\n");
            printf("Gauss-Seidel Red-Black - 1\n");
            printf("S.O.R. Red-Black       - 2\n");
        }
    }

    return 0;
}