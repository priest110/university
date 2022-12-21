#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <math.h>
#include <limits.h>
#include <omp.h>

#define CAP 10

// header file with the RAP related functions 
#include "../include/rooms.h"
#include "../include/time_m.h"

/** main
 * 
 * @author Eduardo Conceição
 * @author Rui Oliveira
 */

int main(int argc, char** argv)
{
    if( argc < 6 ){
        fprintf(stderr, "Not enough arguments given!\n");
        return 0;
    }

    // Input Matrix Size
    int N = atoi(argv[1]);

    // Number of processes
    int P = atoi(argv[3]);

    // Number of attempts
    int A = atoi(argv[2]);

    // MAX_ITER (rooms and roomsSA)
    int max_iter = atoi(argv[4]);

    // TEMP_MULTIPLIER (roomsSA)
    double temp_multiplier = atof(argv[5]);

    int* stepsv = (int*)malloc(sizeof(int) * A);
    int* costv  = (int*)malloc(sizeof(int) * A);
    int* texecv = (int*)malloc(sizeof(int) * A);

    // Create random matrix (cap is irrelevant)
    int** D = randmatrix(N, 10);

    int steps, cost;
    long long unsigned texec_min = ULONG_MAX, stepsc, costc;

    // ROOMS
    long long unsigned t = getTime();
    #pragma omp parallel for num_threads(P) private(steps, cost) schedule(static)
    for(unsigned i = 0; i < A; i++)
    {
        rooms(D, N, &cost, &steps, max_iter);
        costv [i] = cost;
        stepsv[i] = steps;
    }
    t = getTime() - t;

    printf("Rooms, %d, %d, %d, ", N, A, P);
    for(unsigned i = 0; i < A; i++)
    {
        stepsc     += stepsv[i];
        costc     += costv[i];
    }
    printf("%llu, %.1f, %.1f\n", t, (float)costc/A, (float)stepsc/A);

    steps = 0; cost = 0; texec_min = ULONG_MAX;
    stepsc = 0; costc = 0;

    // ROOMS SA
    t = getTime();
    #pragma omp parallel for num_threads(P) private(steps, cost) schedule(dynamic)
    for(unsigned i = 0; i < A; i++)
    {
        roomsSA(D, N, &cost, &steps, max_iter, temp_multiplier);
        costv [i] = cost;
        stepsv[i] = steps;
    }
    t = getTime() - t;

    printf("RoomsSA, %d, %d, %d, ", N, A, P);
    for(unsigned i = 0; i < A; i++)
    {
        stepsc     += stepsv[i];
        costc      += costv[i];
    }
    printf("%llu, %.1f, %.1f\n", t, (float)costc/A, (float)stepsc/A);

    steps = 0; cost = 0; texec_min = ULONG_MAX;
    stepsc = 0; costc = 0;


    // ROOMS GREEDY
    t = getTime();
    #pragma omp parallel for num_threads(P) private(steps, cost) schedule(static)
    for(unsigned i = 0; i < A; i++)
    {
        roomsGreedy(D, N, &cost, &steps);
        costv [i] = cost;
        stepsv[i] = steps;  
    }
    t = getTime() - t;

    printf("RoomsGreedy, %d, %d, %d, ", N, A, P);
    for(unsigned i = 0; i < A; i++)
    {
        stepsc     += stepsv[i];
        costc      += costv[i];
    }
    printf("%llu, %.1f, %.1f\n", t, (float)costc/A, (float)stepsc/A);

    free(costv);
    free(stepsv);

    return 0;
}

