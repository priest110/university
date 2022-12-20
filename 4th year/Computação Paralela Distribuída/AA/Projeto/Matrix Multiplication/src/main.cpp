#include <stdio.h>
#include <iostream>
#include <cstdlib>
#include <malloc.h> 
#include <iostream>
#include <immintrin.h>
#include <time.h> 
#include <omp.h>
#include <papi.h>
#include <sys/time.h>

#define TIME_RESOLUTION 1000000 // time measuring resolution (us)

/*

L1 : 32x32
L2 : 128x128
L3 : 1024x1024
RAM: 2048x2048

*/


#define BLOCKSIZE 16
#define SIZE 4096  // Nº de linhas e Nº de colunas da matriz
#define NUM_EVENTS 4

double clearcache [30000000];
long long unsigned initial_time;
timeval t;

long long values[NUM_EVENTS];
int Events[NUM_EVENTS] = 
{
    PAPI_L2_DCR,
    PAPI_L3_DCR,
    PAPI_L2_TCM,
    PAPI_L3_TCM
    //PAPI_FP_OPS,
    //PAPI_TOT_INS,
    //PAPI_LD_INS
};
int EventSet = PAPI_NULL;


using namespace std;


void clearCache (void) {
    for (unsigned i = 0; i < 30000000; ++i)
        clearcache[i] = i;
}

void start (void) 
{
    gettimeofday(&t, NULL);
    initial_time = t.tv_sec * TIME_RESOLUTION + t.tv_usec;
}

long long unsigned stop (void) 
{
    gettimeofday(&t, NULL);
    long long unsigned final_time = t.tv_sec * TIME_RESOLUTION + t.tv_usec;

    return final_time - initial_time;
}


// Printa os valores de uma matriz
void print_matrix (float c[][SIZE]) {
    for (int i=0; i<SIZE; i++) {
        for(int j=0; j<SIZE; j++) 
            printf("%f ",c[i][j]);
    printf("\n");
    }
}

// Transposta de uma matriz
void transpose(float m[][SIZE]){
    for(unsigned i = 0 ; i< SIZE ; i++){
        for(unsigned j = i+1 ; j< SIZE; j++){
            float temp = m[i][j]; 
            m[i][j] = m[j][i];
            m[j][i] = temp;
        }
    }
}

// Implementação i-j-k (2.2)
void ijk(float a[][SIZE], float b[][SIZE], float c[][SIZE], int N){
    for (unsigned i = 0; i < N; ++i) {
        for (unsigned j = 0; j < N; ++j) {
            for (unsigned k = 0; k < N; ++k)
                c[i][j]  += a[i][k] * b[k][j];
        }
    }
}

// Implementação i-j-k transposta
void ijk_tr(float a[][SIZE], float b[][SIZE], float c[][SIZE], int N){
    transpose(b);
    for (unsigned i = 0; i < N; ++i) {
        for (unsigned j = 0; j < N; ++j) {
            // #pragma vector always
            float temp = 0.0;
            for (unsigned k = 0; k < N; ++k)
                temp += a[i][k] * b[j][k];
            c[i][j] = temp;
        }
    }
}

// Implementação i-k-j (2.3.1)
void ikj(float a[][SIZE], float b[][SIZE], float c[][SIZE], int N){
    for (unsigned i = 0; i < N; ++i) {
        for (unsigned k = 0; k < N; ++k) {
            // #pragma vector always
            for (unsigned j = 0; j < N; ++j)
                c[i][j] += a[i][k] * b[k][j];
        }
    }
}

// Implementação j-k-i (2.3.2)
void jki(float a[][SIZE], float b[][SIZE], float c[][SIZE], int N){
    for (unsigned j = 0; j < N; ++j) {
        for (unsigned k = 0; k < N; ++k) {
            // #pragma vector always
            for (unsigned i = 0; i < N; ++i)
                c[i][j] += a[i][k] * b[k][j];           
        }
    }
} 

// Implementação j-k-i transposta
void jki_tr(float a[][SIZE], float b[][SIZE], float c[][SIZE], int N){
    transpose(a);
    transpose(b);
    for (unsigned j = 0; j < N; ++j)
    {
        for (unsigned k = 0; k < N; ++k)
        {
            // #pragma vector always
            for (unsigned i = 0; i < N; ++i)
            {
                c[j][i] += a[k][i] * b[j][k];
            }
        }
    }
    transpose(c);
} 

// Implementação i-j-k por Blocos
void ijk_block(float a[][SIZE], float b[][SIZE], float c[][SIZE], int N)
{
    for(unsigned br = 0; br < N; br += BLOCKSIZE)
    {
        for(unsigned bc = 0; bc < N; bc += BLOCKSIZE)
        {
            for(unsigned i = 0; i < N; i++)
            {
                for(unsigned j = br; j < br + BLOCKSIZE && j < N; j++)
                {
                    for(unsigned k = bc; k < bc + BLOCKSIZE && j < N; k++)
                    {
                        c[i][j] += a[i][k] * b[k][j];
                    }
                }
            }
        }       
    }
}

// Implementação i-j-k por Blocos com a Transposta
void ijk_block_tr(float a[][SIZE], float b[][SIZE], float c[][SIZE], int N)
{
    transpose(b);
    
    for(unsigned br = 0; br < N; br += BLOCKSIZE)
    {
        for(unsigned bc = 0; bc < N; bc += BLOCKSIZE)
        {
            for(unsigned i = 0; i < N; i++)
            {
                for(unsigned j = br; j < br + BLOCKSIZE; j++)
                {
                    for(unsigned k = bc; k < bc + BLOCKSIZE; k++)
                    {
                        c[i][j] += a[i][k] * b[j][k];
                    }
                }
            }
        }       
    }
}

// Implementação j-k-i por Blocos com a Transposta
void jki_block_tr(float a[][SIZE], float b[][SIZE], float c[][SIZE], int N)
{
    transpose(a);
    transpose(b);

    for(unsigned br = 0; br < N; br += BLOCKSIZE)
    {
        for(unsigned bc = 0; bc < N; bc += BLOCKSIZE)
        {
            for(unsigned j = 0; j < N; j++)
            {
                for(unsigned k = br; k < br + BLOCKSIZE && k < N; k++)
                {
                    for(unsigned i = bc; i < bc + BLOCKSIZE && i < N; i++)
                    {
                        c[j][i] += a[k][i] * b[j][k];
                    }
                }
            }
        }
    }

    transpose(c);
}


// Free da memória das matrizes
int clean_matrix(float **a, float **b, float **c)
{
    if (*a != NULL)
        free(*a);
    if (*b != NULL)
        free(*b);
    if (*c != NULL)
        free(*c);
    return 0;
}

// Aloca memória para as matrizes e inicializa-as
void fillMatrices (float a[][SIZE], float b[][SIZE], float c[][SIZE], int N) {
    //time_t t;
    //srand((unsigned) time(&t));

    for (unsigned i = 0; i < N; ++i) {
        for (unsigned j = 0; j < N; ++j) {
            a[i][j] = ((float) rand()) / ((float) RAND_MAX);
            b[i][j] = 1.0;            
            c[i][j] = 0.0;
        }
    }
}

//Coloca todos os elementos da matriz a 0
void nullMatrix(float c[][SIZE])
{
    for (unsigned i = 0; i < SIZE; ++i) {
        for (unsigned j = 0; j < SIZE; ++j) {
            c[i][j] = 0.0;
        }
    }
}



int main(int argc, char const *argv[]){
    float a[SIZE][SIZE], b[SIZE][SIZE], c[SIZE][SIZE]; // matrizes

    PAPI_library_init(PAPI_VER_CURRENT);
    PAPI_create_eventset(&EventSet);
    PAPI_add_events(EventSet,Events,NUM_EVENTS);

    long long unsigned t1=0,t2,t3,t4,t5,t6,t7,t8;
    cout <<"NoVec"<< endl;

    cout <<"alg;tempo; L2 DCR; L3 DCR; L2 TCM; L3 TCM;" << endl;
    //cout <<"alg;tempo; FP OPS; TOT INS; LD INS; " << endl;

    clearCache();
    fillMatrices(a,b,c,SIZE);

    //ijk
    for(unsigned i = 0; i < 8; i++)
    {
        nullMatrix(c);
        PAPI_start(EventSet);
        start();
        ijk(a,b,c,SIZE);
        t1=stop();
    
        PAPI_stop(EventSet,values);
        cout << "i-j-k;"<< t1 << ";";

        for(int i = 0; i < NUM_EVENTS; i++)
            cout  << values [i] << ";";
        cout << endl;

        //clearCache(); 
    }

    //ikj
    for(unsigned i = 0; i < 8; i++)
    {
        nullMatrix(c);
        PAPI_start(EventSet);
        start();
        ikj(a,b,c,SIZE);
        t2=stop();
        PAPI_stop(EventSet,values);
        cout << "i-k-j;"<< t2 <<";";
        for(int i = 0; i < NUM_EVENTS; i++)
            cout  << values [i] << ";";
        cout << endl;
        clearCache(); 
    }

    //jki
    for(unsigned i = 0; i < 8; i++)
    {
        nullMatrix(c);
        PAPI_start(EventSet);
        start();
        jki(a,b,c,SIZE);
        t3=stop();
        PAPI_stop(EventSet,values);
        cout << "j-k-i;"<< t3 <<";";
        for(int i = 0; i < NUM_EVENTS; i++)
            cout  << values [i] << ";";
        cout << endl;
        clearCache(); 
    }

    //ijk trans
    for(unsigned i = 0; i < 8; i++)
    {
        nullMatrix(c);
        PAPI_start(EventSet);
        start();
        ijk_tr(a,b,c,SIZE);
        t4=stop();
        PAPI_stop(EventSet,values);
        cout << "i-j-k trans;"<< t4 <<";";
        for(int i = 0; i < NUM_EVENTS; i++)
            cout  << values [i] << ";";
        cout << endl;
        clearCache(); 
    }

    //jki trans
    for(unsigned i = 0; i < 8; i++)
    {
        PAPI_start(EventSet);
        start();
        jki_tr(a,b,c,SIZE);
        t5=stop();  
        PAPI_stop(EventSet,values);
        cout << "j-k-i trans;"<< t5 <<";";
        for(int i = 0; i < NUM_EVENTS; i++)
            cout  << values [i] << ";";
        cout << endl;
        clearCache();
    }

    //ijk block
    for(unsigned i = 0; i < 8; i++)
    {
        PAPI_start(EventSet);
        start();
        ijk_block(a,b,c,SIZE);
        t6=stop();  
        PAPI_stop(EventSet,values);
        cout << "i-j-k block;"<< t6 <<";";
        for(int i = 0; i < NUM_EVENTS; i++)
            cout  << values [i] << ";";
        cout << endl;
        clearCache();
    }

    //ijk block trans
    for(unsigned i = 0; i < 8; i++)
    {
        PAPI_start(EventSet);
        start();
        ijk_block_tr(a,b,c,SIZE);
        t7=stop();  
        PAPI_stop(EventSet,values);
        cout << "i-j-k block trans;"<< t7 <<";";
        for(int i = 0; i < NUM_EVENTS; i++)
            cout  << values [i] << ";";
        cout << endl;
        clearCache();
    }

    //jki block trans
    for(unsigned i = 0; i < 8; i++)
    {
        PAPI_start(EventSet);
        start();
        jki_block_tr(a,b,c,SIZE);
        t8=stop();  
        PAPI_stop(EventSet,values);
        cout << "j-k-i block trans;"<< t8 <<";";
        for(int i = 0; i < NUM_EVENTS; i++)
            cout  << values [i] << ";";
        cout << endl;
        clearCache();
    }
}