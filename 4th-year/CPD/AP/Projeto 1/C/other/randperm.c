#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <time.h>

int** randperm(size_t N)
{
    srand(time(NULL));

    // faster to divide by two this way
    // For N >= 0 and N is even, we can make 
    // the generalization that:
    // N / 2 == N >> 1, where the second one
    // should be faster
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

        // For x >= 0, we can make the generalization that:
        // x % 2 == x & 1, being that the second one should
        // be faster
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

    return rooms;
}

int main(int argc, char** argv)
{
    //int** ret = randperm(10);
    //for(unsigned i = 0; i < 5; i++)
    //{
    //    printf("(%d,%d)\n", ret[i][0], ret[i][1]);
    //}

    unsigned wrong = 0;

    for(unsigned i = 0; i < 10000; i++)
    {
        int** r = randperm(1000);
        int accum = 0;
        for(unsigned j = 0; j < 500; j++){
            if(r[j][0] == r[j][1]){
                wrong++;
                break;
            }
            accum += r[j][0] + r[j][1];
        }
        wrong = accum != 499500 ? wrong + 1 : wrong;
    }

    printf("Unnacceptable answers: %d\n", wrong);
}