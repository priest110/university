#include "../include/rooms.h"

/** rooms.c
 * 
 * This module aims to implement a Monte Carlo solution to the Room Assignement Problem, based on the
 * initial MATLAB implementation created by professor Rui Ralha, both with and without Simulated Annealing. 
 * 
 * @author Eduardo Conceição
 * @author Rui Oliveira
 */


// (Failed) attempt at creating a faster e^(-x/T)
static inline
double fast_inverse_exp_256(double x, double T)
{
    x = 1.0 - ( x  / ( T * 256.0) );
    x *= x; x *= x; x *= x; x *= x;
    x *= x; x *= x; x *= x; x *= x;

    return x;
}

void show_matrix(int** D, size_t N)
{
    for(unsigned i = 0; i < N; i++)
    {
        printf("[");
        for(unsigned j = 0; j < N; j++)
        {
            printf("%3d ", D[i][j]);
        }
        printf("]\n");
    }
}

// RANDOM MATRIX GENERATION //

int** randmatrix(size_t N, int cap)
{
    unsigned int seed = clock();

    int** r = (int**)malloc(sizeof(int*) * N), next_val;

    for(unsigned i = 0; i < N; i++)
    {
        r[i] = (int*)malloc(sizeof(int) * N);
    }

    for(unsigned i = 0; i < N; i++)
    {
        r[i][i] = 0;
        for(unsigned j = i + 1; j < N; j++)
        {
            // next_val cannot, at all costs, be nil
            // so it varies in the interval [1, cap]
            next_val = ( rand_r(&seed) % cap ) + 1;

            r[i][j] = next_val;
            r[j][i] = next_val;
        }
    }

    return r;
}

int** randperm(size_t N)
{
    unsigned int seed = clock();

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
        int x0 = rand_r(&seed) % n2;
        int x1 = rand_r(&seed) % n2;

        // equivalent to rand() % 2, should possibly be faster in -O0
        int y0 = rand_r(&seed) & 1;
        int y1 = rand_r(&seed) & 1;

        // swap rooms[i][0] with rooms[x1][y1]
        int tmp       = rooms[i][0];
        rooms[i][0]   = rooms[x0][y0];
        rooms[x0][y0] = tmp;

        // swap rooms[i][1] with rooms[x1][y1]
        tmp           = rooms[i][1];
        rooms[i][1]   = rooms[x1][y1];
        rooms[x1][y1] = tmp;
    }
    
    // return the initial solution
    return rooms;
}

// ROOM ASSIGNEMENT PROBLEM SOLUTIONS //

int** rooms(int** D, size_t N, int* cost, int* steps, int max_iter)
{

    unsigned int seed = clock();

    // half the size (as to not calculate it every step of the way)
    int n2 = N >> 1;

    // Assign rooms to students in a random way (Nx2 matrix)
    int **rooms = randperm(N);

    // initialize the cost and number of steps
    (*cost)  = 0;
    (*steps) = 0;

    // Calculate the cost of the initial distribution
    for(unsigned i = 0; i < n2; i++)

        (*cost) += D[rooms[i][0]][rooms[i][1]];

    // other useful variables
    unsigned i = 0, tmp;

    // at most, we will perform MAX_ITER without changes
    while ( i < max_iter ) 
    {
        (*steps)++;

        // get two candidates to swap in order to make
        // a new heuristic solution
        int c = rand_r(&seed) % n2;
        int d = c == n2 - 1 ? 1 : c + 1;

        // calculate the difference in cost between the previous
        // solution and the current candidate
        int delta =   D[rooms[c][0]][rooms[d][1]]
                    + D[rooms[d][0]][rooms[c][1]]
                    - D[rooms[c][0]][rooms[c][1]]
                    - D[rooms[d][0]][rooms[d][1]];

        // check if we'll accept the new arrengement
        if( delta < 0 )
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
        {
            // no new different solution, increment i
            i++;
        }
    }

    // return the heuristic solution
    // both the step count and the cost are passed by reference
    return rooms;
}

int** roomsSA(int** D, size_t N, int* cost, int* steps, int max_iter, double temp_multiplier)
{
    // initialize random floating point number generator
    srand48(clock());
    //srand(clock());

    unsigned int seed = clock();

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
    double T = INITIAL_TEMPERATURE;

    // Calculate the cost of the initial distribution
    for(unsigned i = 0; i < n2; i++)

        (*cost) += D[rooms[i][0]][rooms[i][1]];

    // at most, we will perform MAX_ITER without changes
    while ( i < max_iter ) 
    {
        (*steps)++;

        // get two candidates to swap in order to make
        // a new heuristic solution
        int c = rand_r(&seed) % n2;
        int d = (c == n2 - 1) ? 0 : c + 1;

        // calculate the difference in cost between the previous
        // solution and the current candidate
        int delta =   D[rooms[c][0]][rooms[d][1]]
                    + D[rooms[d][0]][rooms[c][1]]
                    - D[rooms[c][0]][rooms[c][1]]
                    - D[rooms[d][0]][rooms[d][1]];

        // Get the value that will be used to implement Simulated
        // Annealing. The check to T is made in order to prevent
        // divide by 0 exceptions, and since T = 0 would lead to
        // e^-inf, we'll say it's equal to 0
        double expDT = T != 0.0 ? exp(-(double)delta/T) : 0.0;

        // check if we'll accept the new arrengement
        if( delta < 0 || expDT >= drand48() )
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
        {
            // no new different solution, increment i
            i++;
        }
        // Update T
        // The reason this check is made is in order to bypass
        // rounding issues that requently cropped up in testing.
        // When T gets sufficiently low, we can consider it equal
        // to 0 and, thus, get the lowest chance of accepting the
        // change to a higher value possible
        T = T < 0.00000001 ? 0.0 : T * temp_multiplier;
    }
    // return the heuristic solution
    // both the step count and the cost are passed by reference
    return rooms;
}

int** roomsGreedy(int** D, size_t N, int* cost, int* steps)
{
    int n2 = N >> 1;

    (*cost)  = 0;
    (*steps) = 0;

    // initialize solution matrix and auxiliary "hash" list
    int** solution = (int**)malloc(sizeof(int*) * n2);
    int*  aux      = (int*)malloc(sizeof(int) * N);

    for(unsigned i = 0; i < N; i++) aux[i] = 0;
    for(unsigned i = 0; i < n2; i++) solution[i] = (int*)malloc(sizeof(int) * 2);

    // initialize the line minimum at +inf and the minimum x index at -1
    // v will let us know when a solution has been found
    int line_min = INT_MAX, v = 0, s = 0, x_min = -1;

    // go through each line in sequence. 
    for(unsigned i = 0; i < N; i++)
    {
        // don't check any possible pairing for i if they're already paired up
        if( !aux[i] )
        {
            // go through each value in the line. When we find a combo (i,j) where neither
            // of them have been paired up, their mutual cost is the lowest found yet and
            // it's different from 0 (as it would mean they're the same person), we store
            // j and cost D(i,j), in order to find the minimum for this line, if applicable
            for(unsigned j = 0; j < N; j++)
            {
                if( i != j && line_min > D[i][j] && !aux[j] )
                {
                    line_min = D[i][j];
                    x_min    = j;
                    s++;
                }
            }
        }

        // update step count
        (*steps)+= 1;

        // Check if we detected a new solution in this line
        if( s )
        {
            // if so, update the solution with (i,x_min)
            solution[v/2][0] = i;
            solution[v/2][1] = x_min;

            // update cost
            (*cost) += line_min;

            // update the auxiliary list. If we found a valid pair for
            // i and x_min, we don't check them again in the future
            aux[i]++;
            aux[x_min]++;

            // Two more people found a room, update v to reflect this
            v += 2;
            // also, reset s and line_min (x_min doens't need to be reset)
            s  = 0;
            line_min = INT_MAX;

            // if we found a pair for everyone, stop
            if( v == N ) break;
        }
    }

    free(aux);

    return solution;
}