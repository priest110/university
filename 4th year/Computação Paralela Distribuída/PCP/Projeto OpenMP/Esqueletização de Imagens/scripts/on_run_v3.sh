#!/bin/sh

cd $PBS_O_WORKDIR

./run_v3.sh 100 100 > results_v3/results_v3_100_100.txt
./run_v3.sh 1000 1000 > results_v3/results_v3_1k_1k.txt
./run_v3.sh 10000 1000 > results_v3/results_v3_10k_1k.txt
./run_v3.sh 1000 10000 > results_v3/results_v3_1k_10k.txt
./run_v3.sh 10000 10000 > results_v3/results_v3_10k_10k.txt