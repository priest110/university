#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <ctype.h>
#include <limits.h>
#include <math.h>
#include <omp.h>

/**
 * @file poisson.h
 * 
 * @author Eduardo Conceição
 * @author Rui Oliveira
 * 
 * 
 * This file contains the header for the various algorithms implemented for
 * this work assignement
 * 
 */


/**
 * @brief Set the number of Threads
 * 
 * @param[in] t number of threads
 * 
 */
void set_no_threads(unsigned int t);

/**
 * @brief Dump the result stored in M, a square matrix of size NxN in the file fp
 *  
 * @param[in] M Square Matrix of floats (double pointer)
 * @param[in] N Size of the Matrix
 * @param[in] fp File pointer to the output file. If it is NULL, it will default to STDIN
 * 
 */
void dump_result(double** M, size_t N, FILE* fp);

/**
 * @brief Calculate the approximate best tolerance for a given grain
 * 
 * @param[in] N Mesh grain
 * 
 * @return Approximate best tolerance
 */
double get_tolerance(size_t N);

/**
 * @brief Finds the steady-state solution for the temperature distribution on a square plate,
 * for the given boundary conditions, using the Jacobi Method
 * 
 * @param[inout] U Input boundary conditions
 * @param[in] N Length/Width of the square matrix U
 * @param[in] tolerance tolerance for the stopping criteria
 * @param[inout] iter pointer to where we'll store the number of iterations
 * 
 */
void poisson_jac(double** u, size_t N, double TOLERANCE, unsigned long* iter);

/**
 * @brief Finds the steady-state solution for the temperature distribution on a square plate,
 * for the given boundary conditions, using the Gauss Seidel Method
 * 
 * @param[inout] U Input boundary conditions
 * @param[in] N Length/Width of the square matrix U
 * @param[in] tolerance tolerance for the stopping criteria
 * @param[inout] iter pointer to where we'll store the number of iterations
 * 
 */
void poisson_gs(double** u, size_t N, double TOLERANCE, unsigned long* iter);

/**
 * @brief Finds the steady-state solution for the temperature distribution on a square plate,
 * for the given boundary conditions, using the Gauss Seidel Method using Red-Black Ordering
 * 
 * @param[inout] U Input boundary conditions
 * @param[in] N Length/Width of the square matrix U
 * @param[in] tolerance tolerance for the stopping criteria
 * @param[inout] iter pointer to where we'll store the number of iterations
 * 
 */
void poisson_gs_rb(double** u, size_t N, double TOLERANCE, unsigned long* iter);

/**
 * @brief Finds the steady-state solution for the temperature distribution on a square plate,
 * for the given boundary conditions, using the Sucessive Over-Relaxation
 * 
 * @param[inout] U Input boundary conditions
 * @param[in] N Length/Width of the square matrix U
 * @param[in] tolerance tolerance for the stopping criteria
 * @param[inout] iter pointer to where we'll store the number of iterations
 * @param[in] omega relaxation parameter
 * 
 */
void poisson_sor(double** u, size_t N, double TOLERANCE, unsigned long* iter, double omega);

/**
 * @brief Finds the steady-state solution for the temperature distribution on a square plate,
 * for the given boundary conditions, using the Sucessive Over-Relaxation with Red-Black Ordering
 * 
 * @param[inout] U Input boundary conditions
 * @param[in] N Length/Width of the square matrix U
 * @param[in] tolerance tolerance for the stopping criteria
 * @param[inout] iter pointer to where we'll store the number of iterations
 * @param[in] omega relaxation parameter
 * 
 */
void poisson_sor_rb(double** u, size_t N, double TOLERANCE, unsigned long* iter, double omega);