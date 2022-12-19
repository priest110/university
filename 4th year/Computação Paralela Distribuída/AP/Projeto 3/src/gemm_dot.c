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

void transpose_B()
{
    double tmp;
    // we only want to transpose matrix B
    for(unsigned i = 0; i < SIZE; i++)
        for(unsigned j = 0; j < i; j++)
        {
            tmp = B[i][j];
            B[i][j] = B[j][i];
            B[j][i] = tmp;
        }
}



void gemm()
{
    // transpose matrix B
    transpose_B();
    int ii, jj, kk, i, j, k; double c0, c1, c2, c3;
    #pragma omp parallel for private(c0, c1, c2, c3, ii, jj, kk, i, j, k) schedule(dynamic)
    for(ii = 0; ii < SIZE; ii += BLOCK_SIZE)
    {
        for(jj = 0; jj < SIZE; jj += BLOCK_SIZE)
        {
            for(kk = 0; kk < SIZE; kk += BLOCK_SIZE)
            {
                for(i = ii; i < ii + BLOCK_SIZE; i+=2)
                {
                    for(j = jj; j < jj + BLOCK_SIZE; j+=2)
                    {
                        c0 = C[i][j];
                        c1 = C[i+1][j];
                        c2 = C[i][j+1];
                        c3 = C[i+1][j+1];
                    
                        #pragma omp simd
                        for(k = kk; k < kk + BLOCK_SIZE; k++)
                        {
                            c0 += A[i][k]   * B[j][k];
                            c1 += A[i][k]   * B[j+1][k];
                            c2 += A[i+1][k] * B[j][k];
                            c3 += A[i+1][k] * B[j+1][k];
                        }

                        C[i][j]     = c0;
                        C[i+1][j]   = c1;
                        C[i][j+1]   = c2;
                        C[i+1][j+1] = c3;   

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