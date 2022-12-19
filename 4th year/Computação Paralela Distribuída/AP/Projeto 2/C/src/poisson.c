#include "../include/poisson.h"

/**
 * @file poisson.c
 * 
 * @author Eduardo Conceição
 * @author Rui Oliveira
 * 
 * This file contains the implementation of the various Algorithms
 * used to solve the Poisson Equation
 * 
 */

unsigned int no_threads;

/* AUXILIARY FUNCTIONS */

void set_no_threads (unsigned int t)
{
    no_threads = t;
}

void dump_result    (double** M, size_t N, FILE* fp)
{
    if(fp == NULL) fp = stdout;

    for(int i = N - 1; i >= 0; i--)
    {
        for(unsigned j = 0; j < N; j++)
        {
            fprintf(fp, "%3.2f, ", M[i][j]);
        }
        fprintf(fp, "\n");
    }
}

double get_tolerance(size_t N)
{
    return 1.0 / (N * N);
}

/* IMPLEMENTATION OF THE POISSON EQUATION SOLUTION */

void poisson_jac    (double** U, size_t N, double TOLERANCE, unsigned long* iter)
{
    // Initialize auxiliary matrix W
    // this will be used to store the values of U in a new iteration
    double** W = (double**)malloc(sizeof(double*) * N);

    for(unsigned i = 0; i < N; i++)
    {
        W[i]  = (double*)malloc(sizeof(double) * N);

        for(unsigned j = 0; j < N; j++)
        {
            W[i][j]  = U[i][j];
        }
    }

    double diff = TOLERANCE + 1.0;

    (*iter) = 0;

    while( diff > TOLERANCE )
    {

        // Update matrix W by calculating a new approximation
        // O(N^2)
        for(unsigned i = 1; i < N-1; i++)
        {
            for(unsigned j = 1; j < N-1; j++)
            {
                W[i][j]  = ( U[i-1][j] + U[i][j-1] + U[i][j+1] + U[i+1][j] ) / 4.0;
                
            }
        }

        diff = 0;

        // Update matrix U
        // We won't need to update the borders of the "plate"
        // Also, update difference with max(max(u-w))
        // O(N^2)
        for(unsigned i = 1; i < N-1; i++)
        {
            for(unsigned j = 1; j < N-1; j++)
            {
                // Update maximum difference
                diff = diff > fabs(W[i][j] - U[i][j]) ? diff : fabs(W[i][j] - U[i][j]);

                // Update U
                U[i][j] = W[i][j];
                
            }

        }
        (*iter)++;
    }
}

void poisson_gs     (double** U, size_t N, double TOLERANCE, unsigned long* iter)
{
    double diff = TOLERANCE + 1.0;

    (*iter) = 0;

    while( diff > TOLERANCE )
    {
        diff = 0;
        // Update matrix W by calculating a new approximation
        // O(N^2)
        for(unsigned i = 1; i < N-1; i++)
        {
            for(unsigned j = 1; j < N-1; j++)
            {
                // store the old value of U(i,j) (so we can calculate the maximum difference)
                double tmp = U[i][j];
                // update U(i,j)
                U[i][j]  = ( U[i-1][j] + U[i][j-1] + U[i][j+1] + U[i+1][j] ) / 4.0;
                // update maximum difference
                diff = diff > fabs(U[i][j] - tmp) ? diff : fabs(U[i][j] - tmp);
                
            }
        }
        (*iter)++;
    }
}

void poisson_gs_rb  (double** U, size_t N, double TOLERANCE, unsigned long* iter)
{
    double diff = TOLERANCE + 1.0;

    (*iter) = 0;

    while( diff > TOLERANCE )
    {
        diff = 0;
        double tmp_diff = 0;
        // Update matrix W by calculating a new approximation
        // O(N^2)
        
        #pragma omp parallel for num_threads(no_threads) firstprivate(tmp_diff) reduction(max:diff)
        for(unsigned i = 1; i < N-1; i++)
        {
            for(unsigned j = 1 + ((i+1) % 2); j < N-1; j += 2)
            {
                // store the old value of U(i,j) (so we can calculate the maximum difference)
                double tmp = U[i][j];
                // update U(i,j)
                U[i][j]  = ( U[i-1][j] + U[i][j-1] + U[i][j+1] + U[i+1][j] ) / 4.0;
                // update maximum difference
                tmp_diff = tmp_diff > fabs(U[i][j] - tmp) ? tmp_diff : fabs(U[i][j] - tmp);
            }
            diff = tmp_diff;
        }

        #pragma omp parallel for num_threads(no_threads) firstprivate(tmp_diff) reduction(max:diff)
        for(unsigned i = 1; i < N-1; i++)
        {
            for(unsigned j = 1 + (i % 2); j < N-1; j += 2)
            {
                // store the old value of U(i,j) (so we can calculate the maximum difference)
                double tmp = U[i][j];
                // update U(i,j)
                U[i][j]  = ( U[i-1][j] + U[i][j-1] + U[i][j+1] + U[i+1][j] ) / 4.0;
                // update maximum difference
                tmp_diff = tmp_diff > fabs(U[i][j] - tmp) ? tmp_diff : fabs(U[i][j] - tmp);
            }
            diff = tmp_diff;
        }
        (*iter)++;
    }
}

void poisson_sor    (double** U, size_t N, double TOLERANCE, unsigned long* iter, double omega)
{
    // if omega is negative, we'll use the optimal value for it
    if(omega < 0)
        omega = 2.0 / (1.0 + sin(M_PI/ (double)(N - 1) ));


    double diff = TOLERANCE + 1.0;

    (*iter) = 0;

    while( diff > TOLERANCE )
    {
        diff = 0;
        // Update matrix W by calculating a new approximation
        // O(N^2)
        for(unsigned i = 1; i < N-1; i++)
        {
            for(unsigned j = 1; j < N-1; j++)
            {
                // store the old value of U(i,j) (so we can calculate the maximum difference)
                double tmp = U[i][j];
                // update U(i,j)
                U[i][j]  =   (1.0 - omega) * U[i][j] 
                           + omega * (( U[i-1][j] + U[i][j-1] + U[i][j+1] + U[i+1][j] ) / 4.0);
                // update maximum difference
                diff = diff > fabs(U[i][j] - tmp) ? diff : fabs(U[i][j] - tmp);
                
            }
        }
        (*iter)++;
    }
}

void poisson_sor_rb (double** U, size_t N, double TOLERANCE, unsigned long* iter, double omega)
{
    // if omega is negative, we'll use the optimal value for it
    if(omega < 0)
        omega = 2.0 / (1.0 + sin(M_PI/ (double)(N - 1) ));

    double diff = TOLERANCE + 1.0;

    (*iter) = 0;

    while( diff > TOLERANCE )
    {
        diff = 0;
        double tmp_diff = 0;
        // Update matrix W by calculating a new approximation
        // O(N^2)
        #pragma omp parallel for num_threads(no_threads) firstprivate(tmp_diff) reduction(max:diff)
        for(unsigned i = 1; i < N-1; i++)
        {
            for(unsigned j = 1 + ((i+1) % 2); j < N-1; j += 2)
            {
                // store the old value of U(i,j) (so we can calculate the maximum difference)
                double tmp = U[i][j];
                // update U(i,j)
                U[i][j]  =   (1.0 - omega) * U[i][j] 
                           + omega * (( U[i-1][j] + U[i][j-1] + U[i][j+1] + U[i+1][j] ) / 4.0);
                // update maximum difference
                tmp_diff = tmp_diff > fabs(U[i][j] - tmp) ? tmp_diff : fabs(U[i][j] - tmp);
                
            }
            diff = tmp_diff;
        }
        #pragma omp parallel for num_threads(no_threads) firstprivate(tmp_diff) reduction(max:diff)
        for(unsigned i = 1; i < N-1; i++)
        {
            for(unsigned j = 1 + (i % 2); j < N-1; j += 2)
            {
                // store the old value of U(i,j) (so we can calculate the maximum difference)
                double tmp = U[i][j];
                // update U(i,j)
                U[i][j]  =   (1.0 - omega) * U[i][j] 
                           + omega * (( U[i-1][j] + U[i][j-1] + U[i][j+1] + U[i+1][j] ) / 4.0);
                // update maximum difference
                tmp_diff = tmp_diff > fabs(U[i][j] - tmp) ? tmp_diff : fabs(U[i][j] - tmp);
                
            }
            diff = tmp_diff;
        }
        (*iter)++;
    }
}