#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <time.h>

#define SIZE          4096
#define BLOCK_SIZE    64 / sizeof(double)
#define NUM_THREADS   32
#define CACHE_FILLER_SIZE 30000000

double cache_filler[CACHE_FILLER_SIZE]; 

double A[SIZE][SIZE] __attribute__((aligned(32))); 
double B[SIZE][SIZE] __attribute__((aligned(32))); 
double C[SIZE][SIZE] __attribute__((aligned(32))); 



void gemm()
{
    int ii, jj, kk, i, j, k; double a00;
    #pragma omp parallel for private(a00, ii, jj, kk, i, j, k) schedule(dynamic)
    for(ii = 0; ii < SIZE; ii+=BLOCK_SIZE)
    {
        for(kk = 0; kk < SIZE; kk+=BLOCK_SIZE)
        {
            for(jj = 0; jj < SIZE; jj+=BLOCK_SIZE)
            {
                for(i = ii; i < ii+BLOCK_SIZE; i++)
                {
                    for(k = kk; k < kk+BLOCK_SIZE; k++)
                    {
                        a00 = A[i][k];
                        //a01 = A[i+1][k];
                        //a02 = A[i][k+1];
                        //a03 = A[i+1][k+1];
                    
                        #pragma omp simd
                        for(j = jj; j < jj+BLOCK_SIZE; j++)
                        {
                            C[i][j]   += a00 * B[k][j];
                            //C[i][j]   += a02 * B[k+1][j];
                            //C[i+1][j] += a01 * B[k][j];
                            //C[i+1][j] += a03 * B[k+1][j];
                        }
                    }
                }
            }
        }
    }
}

void clear_cache()
{
    for(unsigned i = 0; i < CACHE_FILLER_SIZE; i++) cache_filler[i] = i;
}

int main(int argc, char** argv)
{ 
    srand(time(NULL));   

    for(unsigned i = 0; i < SIZE; i++)
        for(unsigned j = 0; j < SIZE; j++)
        {
            A[i][j] = 1.0;
            B[i][j] = 1.0;
            C[i][j] = 0.0;
        }

    clear_cache();

    double start = omp_get_wtime();

    gemm();

    double end = omp_get_wtime();
	double exec_time = (end - start);
    printf("Exec. time: %f\n", exec_time);
}