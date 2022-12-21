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
echo 'Parallel Times (Diagonais) (T:2)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2w 2 $1 $2
done

#par lines
echo 'Parallel Times (Diagonais) (T:4)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2w 4 $1 $2
done

#par lines
echo 'Parallel Times (Diagonais) (T:8)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2w 8 $1 $2
done

#par lines
echo 'Parallel Times (Diagonais) (T:16)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2w 16 $1 $2
done

#par lines
echo 'Parallel Times (Diagonais) (T:32)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2w 32 $1 $2
done

echo 'Parallel Times (Diagonais) (T:64)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2w 64 $1 $2
done

#par lines
echo 'Parallel Times (Diagonais) (T:128)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2w 128 $1 $2
done

#par lines
echo 'Parallel Times (Diagonais) (T:256)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_2w 256 $1 $2
done

