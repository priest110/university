#!/bin/sh

#PBS -l walltime=2:00:00
#PBS -l nodes=1:r662:ppn=8

cd $PBS_O_WORKDIR

module load gcc/5.3.0

# Vanilla
./scripts/run.sh 100 0.999 > output_100_999.txt

# Fiddle with MAX_ITER
./scripts/run.sh  50 0.999 > output_50_999.txt
./scripts/run.sh 200 0.999 > output_200_999.txt
./scripts/run.sh 500 0.999 > output_500_999.txt

# Fiddle with TEMP_MULTIPLIER
./scripts/run.sh 100 0.8 > output_100_8.txt
./scripts/run.sh 100 0.5 > output_100_5.txt
./scripts/run.sh 100 0.2 > output_100_2.txt

