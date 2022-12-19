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

#par col
echo 'Parallel Times (Columns) (T:2)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_col 2 $1 $2
done

#par col
echo 'Parallel Times (Columns) (T:4)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_col 4 $1 $2
done

#par col
echo 'Parallel Times (Columns) (T:8)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_col 8 $1 $2 
done

#par col
echo 'Parallel Times (Columns) (T:16)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_col 16 $1 $2
done

#par col
echo 'Parallel Times (Columns) (T:32)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_col 32 $1 $2
done

#par col
echo 'Parallel Times (Columns) (T:64)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_col 64 $1 $2
done

#par col
echo 'Parallel Times (Columns) (T:128)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_col 128 $1 $2
done

#par col
echo 'Parallel Times (Columns) (T:256)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_col 256 $1 $2
done

#par lines
echo 'Parallel Times (Lines) (T:2)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_line 2 $1 $2
done

#par lines
echo 'Parallel Times (Lines) (T:4)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_line 4 $1 $2
done

#par lines
echo 'Parallel Times (Lines) (T:8)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_line 8 $1 $2
done

#par lines
echo 'Parallel Times (Lines) (T:16)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_line 16 $1 $2
done

#par lines
echo 'Parallel Times (Lines) (T:32)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_line 32 $1 $2
done

echo 'Parallel Times (Lines) (T:64)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_line 64 $1 $2
done

#par lines
echo 'Parallel Times (Lines) (T:128)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_line 128 $1 $2
done

#par lines
echo 'Parallel Times (Lines) (T:256)'
for ((i = 0; i < 10; i++))
do
    ./bin/par_line 256 $1 $2
done

###par tasks
##echo 'Parallel Times (Tasks) (T:2)'
##for ((i = 0; i < 10; i++))
##do
##    ./bin/par_tasks 2 $1 $2
##done
##
###par tasks
##echo 'Parallel Times (Tasks) (T:4)'
##for ((i = 0; i < 10; i++))
##do
##    ./bin/par_tasks 4 $1 $2
##done
##
###par tasks
##echo 'Parallel Times (Tasks) (T:8)'
##for ((i = 0; i < 10; i++))
##do
##    ./bin/par_tasks 8 $1 $2
##done
##
###par tasks
##echo 'Parallel Times (Tasks) (T:16)'
##for ((i = 0; i < 10; i++))
##do
##    ./bin/par_tasks 16 $1 $2
##done
##
###par tasks
##echo 'Parallel Times (Tasks) (T:32)'
##for ((i = 0; i < 10; i++))
##do
##    ./bin/par_tasks 32 $1 $2
##done
##
###par tasks
##echo 'Parallel Times (Tasks) (T:64)'
##for ((i = 0; i < 10; i++))
##do
##    ./bin/par_tasks 64 $1 $2
##done
##
###par tasks
##echo 'Parallel Times (Tasks) (T:128)'
##for ((i = 0; i < 10; i++))
##do
##    ./bin/par_tasks 128 $1 $2
##done
##
###par tasks
##echo 'Parallel Times (Tasks) (T:256)'
##for ((i = 0; i < 10; i++))
##do
##    ./bin/par_tasks 256 $1 $2
##done