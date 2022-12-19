#!/bin/sh

module load gcc/5.3.0
         
#cd $PBS_O_WORKDIR

#seq
echo 'Sequential Times (Linha-a-Linha):'
for ((i = 0; i < 10; i++))
do
    ./bin/seql $1 $2
done

#seq
echo 'Sequential Times (Diagonais):'
for ((i = 0; i < 10; i++))
do
    ./bin/seqw $1 $2
done

#par lines
echo 'Parallel Times (Lines) (T:2)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2l 2 $1 $2
done

#par lines
echo 'Parallel Times (Lines) (T:4)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2l 4 $1 $2
done

#par lines
echo 'Parallel Times (Lines) (T:8)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2l 8 $1 $2
done

#par lines
echo 'Parallel Times (Lines) (T:16)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2l 16 $1 $2
done

#par lines
echo 'Parallel Times (Lines) (T:32)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2l 32 $1 $2
done

echo 'Parallel Times (Lines) (T:64)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2l 64 $1 $2
done

#par lines
echo 'Parallel Times (Lines) (T:128)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2l 128 $1 $2
done

#par lines
echo 'Parallel Times (Lines) (T:256)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2l 256 $1 $2
done

