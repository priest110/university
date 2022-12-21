#!/bin/sh

#PBS -l walltime=1:00:00
#PBS -l nodes=2:r641:ppn=32
#PBS -q cpar

module load gnu/openmpi_eth/1.8.4

#Puramente para correr a versão MPI uma vez
mpirun -np $1 -report-bindings -mca btl self,sm,tcp bin/mpi_a $2 $3