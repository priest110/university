#!/bin/sh

cd $PBS_O_WORKDIR

./run_v2.sh 100 100 > results_v2/results_v2_100_100.txt
./run_v2.sh 1000 1000 > results_v2/results_v2_1k_1k.txt
./run_v2.sh 10000 1000 > results_v2/results_v2_10k_1k.txt
./run_v2.sh 1000 10000 > results_v2/results_v2_1k_10k.txt
./run_v2.sh 10000 10000 > results_v2/results_v2_10k_10k.txt