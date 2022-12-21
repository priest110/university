#!/bin/sh
#PBS -l nodes=1:ppn=24:r662
#PBS -l walltime=2:00:00
#PBS -q mei
#PBS -o /home/a83870/AA_TP/no_vec_logs/output4096A.txt

#module load intel/2020
module load gcc/5.3.0
module load papi/5.5.0

cd /home/a83870/AA_TP

source /share/apps/intel/parallel_studio_xe_2019/compilers_and_libraries_2019/linux/bin/compilervars.sh intel64

./bin/aa_tp