#!/bin/sh

#PBS -l walltime=2:00:00
#PBS -l nodes=1:r662:ppn=8

cd $PBS_O_WORKDIR

module load gcc/5.3.0

#size of the matrix
for ((j=50; j <= 1000; j += 50))
do
    ./bin/poisson 0 1 $j 5 -1
done
