#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <math.h>

// header file with the RAP related functions 
#include "../include/rooms.h"
#include "../include/time_m.h"


#define CAP 10

/** main.c
 * 
 * @author Eduardo Conceição
 * @author Rui Oliveira
 */

int main(int argc, char** argv)
{
    if( argc < 4 ){
        printf("Not enough arguments given!\n");
        return 0;
    }

    // Input Matrix Size
    int N = atoi(argv[1]);

    // Number of processes
    int P = atoi(argv[2]);

    // Number of attempts
    int A = atoi(argv[3]);

    int* stepsv = (int*)malloc(sizeof(int) * A);
    int* costv  = (int*)malloc(sizeof(int) * A);

    // Create random matrix (cap is irrelevant)
    int** D = randmatrix(N, CAP);

    int steps, cost;

    // ROOMS
    startTime();    

    for(unsigned i = 0; i < A; i++)
    {
        rooms(D, N, &cost, &steps);
        costv[i]  = cost;
        stepsv[i] = steps;
    }
    
    stopTime();

    printf("Rooms, %d, %d, %d\n", N, A, P);
    execTime();
    for(unsigned i = 0; i < A; i++)
    {
        steps += stepsv[i];
        cost  += costv[i];
    }
    printf("Avg. Cost: %.1f\nAvg. Steps: %.1f\n", (float)cost/A, (float)steps/A);

    steps = 0; cost = 0;

    // ROOMS SA
    startTime();    

    for(unsigned i = 0; i < A; i++)
    {
        roomsSA(D, N, &cost, &steps);
        costv[i]  = cost;
        stepsv[i] = steps;
    }
    cost = 0; steps = 0;
    stopTime();

    printf("RoomsSA, %d, %d, %d\n", N, A, P);
    execTime();
    for(unsigned i = 0; i < A; i++)
    {
        steps += stepsv[i];
        cost  += costv[i];
    }
    printf("Avg. Cost SA: %.1f\nAvg. Steps SA: %.1f\n", ((float)cost)/A, ((float)steps)/A);

    steps = 0; cost = 0;

    // ROOMS GREEDY
    startTime();

    for(unsigned i = 0; i < A; i++)
    {
        roomsGreedy(D, N, &cost, &steps);
        costv[i]  = cost;
        stepsv[i] = steps;
    }
    cost = 0; steps = 0;

    stopTime();

    printf("RoomsGreedy, %d, %d, %d\n", N, A, P);
    execTime();
    for(unsigned i = 0; i < A; i++)
    {
        steps += stepsv[i];
        cost  += costv[i];
    }
    printf("Avg. Cost G: %.1f\nAvg. Steps G: %.1f\n", (float)cost/A, (float)steps/A);


    return 0;

    return 0;
}

