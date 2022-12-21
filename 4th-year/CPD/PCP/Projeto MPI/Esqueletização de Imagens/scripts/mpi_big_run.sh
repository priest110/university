#!/bin/sh

#PBS -l walltime=2:00:00
#PBS -l nodes=2:r641:ppn=32
#PBS -q mei
#PBS -e /home/a83870/PCP_TP/MPI/MPI/err/results_A.err

cd /home/a83870/PCP_TP/MPI/MPI

module load gnu/openmpi_eth/1.8.4
module load gcc/5.3.0

#./scripts/beeg_run.sh 100 100 > results/results_100_100.txt
#./scripts/beeg_run.sh 1000 1000 > results/results_1000_1000.txt
#./scripts/beeg_run.sh 10000 1000 > results/results_10000_1000.txt
./scripts/beeg_run.sh 1000 10000 > results/results_1000_10000.txt
./scripts/beeg_run.sh 10000 10000 > results/results_10000_10000.txt