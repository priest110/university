#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

#define MAX_ITER 100
#define TEST_SIZE 8

/** rooms.c
 * 
 * This module aims to implement a Monte Carlo solution to the Room Assignement Problem, based on the
 * initial MATLAB implementation created by professor Rui Ralha. 
 * 
 * @author Eduardo Conceição
 * @author Rui Oliveira
 */


// Create a random permutation of N elements in pairs
int** randperm(size_t N)
{
    srand(time(NULL));

    // half the size of N, should possibly be faster in -O0
    size_t n2 =  N >> 1;

    int** rooms = (int**)malloc(sizeof(int*) * n2);

    // create the initial (N/2)x2 matrix
    for(unsigned i = 0; i < n2; i++)
    {
        rooms[i]    = (int*)malloc(sizeof(int) * 2);
        rooms[i][0] = i;
        rooms[i][1] = i + n2;
    }

    // randomly swap elements of the matrix in pairs
    for(unsigned i = 0; i < n2; i++) 
    {
        int x0 = rand() % n2;
        int x1 = rand() % n2;

        // equivalent to rand() % 2, should possibly be faster in -O0
        int y0 = rand() & 1;
        int y1 = rand() & 1;

        // swap rooms[i][0] with rooms[x1][y1]
        int tmp       = rooms[i][0];
        rooms[i][0]   = rooms[x0][y0];
        rooms[x0][y0] = tmp;

        // swap rooms[i][0] with rooms[x1][y1]
        tmp           = rooms[i][1];
        rooms[i][1]   = rooms[x1][y1];
        rooms[x1][y1] = tmp;
    }
    
    // return the initial solution
    return rooms;
}

// Using a Monte Carlo method, find a heuristic solution to the Room Assignement Problem
int** roomsSA(int D[TEST_SIZE][TEST_SIZE], size_t N, int* cost, int* steps)
{
    // initialize random floating point number generator
    srand48(time(NULL));

    // half the size (as to not calculate it every step of the way)
    int n2 = N >> 1;

    // Assign rooms to students in a random way (Nx2 matrix)
    int **rooms = randperm(N);

    // initialize the cost and number of steps
    (*cost)  = 0;
    (*steps) = 0;

    // other useful variables
    unsigned i = 0, tmp;

    // Initial "temperature"
    double T = 1.0;

    // Calculate the cost of the initial distribution
    for(unsigned i = 0; i < n2; i++)

        (*cost) += D[rooms[i][0]][rooms[i][1]];

    // at most, we will perform MAX_ITER without changes
    while ( i < MAX_ITER ) 
    {
        (*steps)++;

        // get two candidates to swap in order to make
        // a new heuristic solution
        int c = rand() % n2;
        int d = c == n2 - 1 ? 1 : c + 1;

        // calculate the difference in cost between the previous
        // solution and the current candidate
        int delta =   D[rooms[c][0]][rooms[d][1]]
                    + D[rooms[d][0]][rooms[c][1]]
                    - D[rooms[c][0]][rooms[c][1]]
                    - D[rooms[d][0]][rooms[d][1]];

        // check if we'll accept the new arrengement
        if( delta < 0 && exp(-(double)delta/T) >= drand48() )
        {
            // if so, swap room(c,1) and room(d,1)
            tmp = rooms[c][1];
            rooms[c][1] = rooms[d][1];
            rooms[d][1] = tmp;

            // add the delta to the cost (should always be negative)
            (*cost) += delta;
            // new different solution, carry on
            i = 0;
        }
        else
            // no new different solution, increment i
            i++;
        
        T *= 0.999;
    }

    // return the heuristic solution
    // both the step count and the cost are passed by reference
    return rooms;
}


int main(int argc, char** argv)
{                                   //0  1  2  3  4  5  6  7
    int D[TEST_SIZE][TEST_SIZE] = { { 0, 1, 2, 3, 4, 5, 6, 7 }, //0
                                    { 1, 0, 3, 123, 5, 6, 7, 8 }, //1
                                    { 2, 3, 0, 5, 6, 70, 8, 9}, //2
                                    { 3,123, 5, 0, 7, 8, 9, 10}, //3
                                    { 4, 5, 6, 7, 0, 9,10, 11}, //4
                                    { 5, 6, 70, 8, 9, 0,11,12}, //5
                                    { 6, 7, 8, 9,10,11, 0, 89}, //6
                                    { 7, 8, 9,10,11,12,89, 0 } };//7

    int steps, cost;

    int** solution = roomsSA(D, TEST_SIZE, &cost, &steps);

    for(unsigned i = 0; i < TEST_SIZE/2; i++){
        printf("(%d,%d)\n", solution[i][0], solution[i][1]);
    }

    printf("Solution cost: %d, steps: %d\n", cost, steps);

    return 0;
}
