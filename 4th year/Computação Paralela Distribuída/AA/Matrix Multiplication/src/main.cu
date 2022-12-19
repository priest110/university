//#include <cstdlib.h>
//#include <iostream.h>
#include <stdio.h>
#include <sys/time.h>
#include <stdlib.h>

cudaEvent_t start, stop;

#define TIME_RESOLUTION 1000000

#define NUM_BLOCKS 10
#define NUM_THREAD_PER_BLOCK 10
#define TILE_SIZE 16


struct timeval t;
long long unsigned cpu_time;

long long unsigned tt = 0;

void startTime() 
{
        gettimeofday(&t, NULL);
        cpu_time = t.tv_sec * TIME_RESOLUTION + t.tv_usec;
}

void stopTime() 
{
        gettimeofday(&t, NULL);
        long long unsigned final_time = t.tv_sec * TIME_RESOLUTION + t.tv_usec;

        final_time -= cpu_time;

        printf("%llu us\n", final_time);

        tt += final_time;

        cpu_time = 0;
}

//Do Lab2
// These are specific to measure the execution of only the kernel execution - might be useful
void startKernelTime (void) 
{
    cudaEventCreate(&start);
    cudaEventCreate(&stop);
    cudaEventRecord(start);
}

//Do Lab2
void stopKernelTime (void) 
{
    cudaEventRecord(stop);
    cudaEventSynchronize(stop);
    float milliseconds = 0;
    cudaEventElapsedTime(&milliseconds, start, stop);

    printf("%f us have elapsed for the CUDA execution\n", milliseconds);
}

__global__
void multiplication_kernel(float *a, float *b, float *c, int N)
{
    __shared__ float tile_a[TILE_SIZE][TILE_SIZE];
    __shared__ float tile_b[TILE_SIZE][TILE_SIZE];

    int lin = blockIdx.y * blockDim.y + threadIdx.y;
    int col = blockIdx.x * blockDim.x + threadIdx.x;

    float sum = 0.0;

    int x;

    int size = N*N;

    if(col < N && lin < N)
    {
        for(int sub = 0; sub < gridDim.x; sub++)
        {
            x = lin * N + sub * TILE_SIZE + threadIdx.x;

            tile_a[threadIdx.y][threadIdx.x] = x >= size ? 0 : a[x];

            x = (sub * TILE_SIZE + threadIdx.y) * N + col;

            tile_b[threadIdx.y][threadIdx.x] = x >= size ? 0 : b[x];
            
            __syncthreads();

            for(int k = 0; k < TILE_SIZE; k++)
                sum += tile_a[threadIdx.y][k] * tile_b[k][threadIdx.x];

            __syncthreads();
        }

        c[lin * N + col] = sum;

    }
}

void fill_matrices(float **a, float **b, float **c, int N)
{
    int size = N * N;
    (*a) = (float*)malloc(sizeof(float) * size);
    (*b) = (float*)malloc(sizeof(float) * size);
    (*c) = (float*)malloc(sizeof(float) * size);

    srand(127);

    for(unsigned i = 0; i < size; i++)
    {
        (*b)[i] = 1;
        (*a)[i] = ((float)rand()/(float)RAND_MAX);
    }
}

void multiplication_stencil(float *a, float *b, float *c, int N)
{
    float *devA, *devB, *devC;
    int size = N * N;

    cudaMalloc((float**) &devA, size * sizeof(float));
    cudaMalloc((float**) &devB, size * sizeof(float));
    cudaMalloc((float**) &devC, size * sizeof(float));

    startTime();

    cudaMemcpy(devA, a, size * sizeof(float), cudaMemcpyHostToDevice);
    cudaMemcpy(devB, b, size * sizeof(float), cudaMemcpyHostToDevice);

    stopTime();

    dim3 dimGrid(128,128);
    dim3 dimBlock(16,16);

    startKernelTime();

    multiplication_kernel<<<dimGrid,dimBlock>>>(devA, devB, devC, N);

    stopKernelTime();

    startTime();

    cudaMemcpy(c, devC, size * sizeof(float), cudaMemcpyDeviceToHost);

    stopTime();

    cudaFree(devA);
    cudaFree(devB);

    printf("Mem. Transfers took %lu us\n", tt);

    if(cudaSuccess != cudaGetLastError())
    {
        printf("Matrix Multiplication failed!");
        cudaFree(devC);
        exit(-1);
    }
    cudaFree(devC);
}

int main(int argc, char** argv)
{
    int N = 2048; //Largest data set size in 2.4
    float *a, *b, *c;
    for(int i = 0; i < 8; i++)
    {
        fill_matrices(&a,&b,&c,N);
        multiplication_stencil(a,b,c,N);
    }
}