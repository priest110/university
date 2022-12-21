#!/bin/sh

# number of processes
for ((i=1; i <= 32; i *= 2))
do
    #size of the matrix
    for ((j=100; j <= 100000; j += 100))
    do
        ./bin/par $j 20 $i $1 $2
    done
done