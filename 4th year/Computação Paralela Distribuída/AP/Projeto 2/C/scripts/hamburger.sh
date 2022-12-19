#!/bin/sh

#PBS -l walltime=2:00:00
#PBS -l nodes=1:r662:ppn=8

cd $PBS_O_WORKDIR

echo $PBS_O_WORKDIR

module load gcc/5.3.0

./scripts/run_gs.sh 0 -1   > output/output_gs.txt
./scripts/run_gsrb.sh 1 -1   > output/output_gsrb.txt
./scripts/run_sorrb.sh 2 -1   > output/output_sorrb.txt

#./scripts/run.sh 1.01 > output/output_101.txt
#./scripts/run.sh 1.1  > output/output_110.txt
#./scripts/run.sh 1.4  > output/output_140.txt
#./scripts/run.sh 1.6  > output/output_160.txt
#./scripts/run.sh 1.8  > output/output_180.txt
#./scripts/run.sh 1.9  > output/output_190.txt
#./scripts/run.sh 1.99 > output/output_199.txt
