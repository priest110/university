#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#define MAX_ITER 100000


static inline
double fast_inverse_exp_256(double x, double T)
{
    x = 1.0 - ( x  / ( T * 256.0) );
    x *= x; x *= x; x *= x; x *= x;
    x *= x; x *= x; x *= x; x *= x;

    return x;
}

int main()
{
    clock_t t = clock();
    double b = 0.0;
    for(unsigned i = 0; i < MAX_ITER; i++)
    {
        b = fastexp(- (double)i / 0.989);
        //printf("%f\n", b);
    } 
    t = clock() - t;

    printf("Fast: %f\n", ((double)t)/CLOCKS_PER_SEC);

    clock_t t2 = clock();
    double a = 0.0;
    for(unsigned i = 0; i < MAX_ITER; i++)
    {
        a = exp(-(double)i/0.989);
        //printf("%f\n", a);
    } 
    t2 = clock() - t2;

    printf("Normal: %f\n", ((double)t2)/CLOCKS_PER_SEC);

}