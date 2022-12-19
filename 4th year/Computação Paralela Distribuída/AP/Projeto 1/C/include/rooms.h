#ifndef __ROOMS_H__
#define __ROOMS_H__

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <limits.h>


#define MAX_ITER            100
#define INITIAL_TEMPERATURE 1.0
#define TEMP_MULTIPLIER     0.999


/** rooms.h
 * 
 * This module functions as the header for the file containing the implementation
 * of the solution for the Room Assignement Problem
 * 
 * @author Eduardo Conceição
 * @author Rui Oliveira
 */

// Just an auxiliary function to print out a matrix, for
// testing purposes
void show_matrix(int** D, size_t N);

// Create an NxN, diagonally simmetric, with random values
// ranging from 1 to cap
int** randmatrix(size_t N, int cap);

// Create a random permutation of an even number of elements, N, in pairs
// Used to create the initial solution to the Room Assignement Problem in
// a random fashion
int** randperm(size_t N);

// Using a Monte Carlo method, find a heuristic solution to the Room Assignement 
// Problem.
int** rooms(int** D, size_t N, int* cost, int* steps, int max_iter);

// Using a Monte Carlo method, find a heuristic solution to the Room Assignement
// Problem. This version will use Simulated Annealing, in order to, in theory,
// produce a more well rounded and optimal solution
int** roomsSA(int** D, size_t N, int* cost, int* steps, int max_iter, double temp_multiplier);

// Using a very simple Greedy Algorithm, find a heuristic solution to the
// Room Assignement Problem.
int** roomsGreedy(int** D, size_t N, int* cost, int* steps);

#endif 