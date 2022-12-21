#!/bin/sh

./run.sh 100 100 > results/results_100_100.txt
./run.sh 1000 1000 > results/results_1k_1k.txt
./run.sh 10000 1000 > results/results_10k_1k.txt
./run.sh 1000 10000 > results/results_1k_10k.txt
./run.sh 10000 10000 > results/results_10k_10k.txt
./run.sh 100000 100 > results/results_1M_100.txt
./run.sh 100 100000 > results/results_100_1M.txt