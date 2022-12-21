#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

#define SIZE          2000
#define LIMIT         1999
#define MAX_ITER      1000
#define ALIGNEMENT    32
#define NUM_THREADS   32
#define BLOCK_SIZE    64
#define CC_SIZE       30000000

#define min(A,B) (A < B ? A : B)

double clearchache[CC_SIZE];

double A[SIZE][SIZE] __attribute__((aligned(ALIGNEMENT)));

void clear_cache()
{
    for(int i = 0; i < CC_SIZE; i++)
        clearchache[i] = i;
}

int main()
{
    // Initialize the input
    for(unsigned i = 0; i < SIZE; i++)
        for(unsigned j = 0; j < SIZE; j++)
        {
            A[i][j] = 1;
        }
    
    // clear cache
    clear_cache();

    unsigned iter = 0;
    const double multiplier = 0.2;
    unsigned i, j, ii, jj;

    double start = omp_get_wtime();

    // stencil (rb)
    while(iter < 1000)
    {
        #pragma omp parallel for private(i,j,ii,jj) schedule(static)
        for(ii = 1; ii < LIMIT; ii += BLOCK_SIZE)
        {
            for(jj = 1 + (ii & 1); jj < LIMIT; jj += BLOCK_SIZE )
            {
                for(i = ii; i < min(ii + BLOCK_SIZE, LIMIT); i++)
                {
                    for(j = jj ; j < min(jj + BLOCK_SIZE, LIMIT); j += 2)
                    {
                        A[i][j] = multiplier * (A[i][j] + A[i+1][j] + A[i-1][j] + A[i][j+1] + A[i][j-1]);
                    }
                }
            }
        }
        #pragma omp parallel for private(i,j,ii,jj) schedule(static)
        for(ii = 1; ii < LIMIT; ii += BLOCK_SIZE)
        {
            for(jj = 1 + ((ii + 1) & 1); jj < LIMIT; jj += BLOCK_SIZE ) 
            {
                for(i = ii; i < min(ii + BLOCK_SIZE, LIMIT); i++)
                {
                    for(j = jj ; j < min(jj + BLOCK_SIZE, LIMIT); j += 2)
                    {
                        A[i][j] = multiplier * (A[i][j] + A[i+1][j] + A[i-1][j] + A[i][j+1] + A[i][j-1]);
                    }
                }
            }
        }
        iter++;
    }

    double end = omp_get_wtime();
	double exec_time = (end - start) /** 1000*/;
    printf("Execution time for matrix of size %dx%d, for %d iterations: %2f\n", SIZE, SIZE, MAX_ITER, exec_time);

}
