#!/bin/sh

#PBS -l walltime=2:00:00
#PBS -l nodes=1:r662:ppn=8

#cd $PBS_O_WORKDIR

#module load gcc/5.3.0

# number of processes
#for ((i=1; i <= 32; i *= 2))
#do
#size of the matrix
for ((j=100; j <= 500; j += 100))
do
    ./bin/poisson 2 1 $j 5 -1
done
#done