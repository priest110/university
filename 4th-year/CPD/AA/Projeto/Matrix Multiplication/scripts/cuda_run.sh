#!/bin/sh

#PBS -l nodes=1:k20:ppn=1
#PBS -l walltime=2:00:00
#PBS -q mei
#PBS -o /home/a83870/AA_TP/cuda_logs/output_cuda.txt

module load gcc/5.3.0
module load cuda/10.1

cd $PBS_O_WORKDIR

./bin/aa_tp_cuda