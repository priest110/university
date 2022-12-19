#!/bin/sh

# Fazer para:
#
# MPI:
#  --map-by ppr:1:core
#  --map-by node
#  --map-by ppr:1:socket
# OpenMP
# Sequencial

# TYPE A

#Map by node
for thread in 2 4 8 16 32
do
    echo "(A) Map by node "
    echo $thread
    for ((i=0;i<10;i++))
    do
        echo "------------------------"
        mpirun --map-by node -np $thread -mca btl self,sm,tcp bin/mpi_a $1 $2
        echo "------------------------"
    done
done

#Map by ppr:1:core
for thread in 2 4 8 16 32
do
    echo "(A) Map by ppr:1:core: "
    echo $thread
    for ((i=0;i<10;i++))
    do
        echo "------------------------"
        mpirun --map-by ppr:1:core -np $thread -mca btl self,sm,tcp bin/mpi_a $1 $2
        echo "------------------------"
    done
done

# TYPE B

#Map by ppr:1:core
for thread in 2 4 8 16 32
do
    echo "(B) Map by ppr:1:core: "
    echo $thread
    for ((i=0;i<10;i++))
    do
        echo "------------------------"
        mpirun --map-by ppr:1:core -np $thread -mca btl self,sm,tcp bin/mpi_b $1 $2
        echo "------------------------"
    done
done

#Map by node
for thread in 2 4 8 16 32
do
    echo "(B) Map by node "
    echo $thread
    for ((i=0;i<10;i++))
    do
        echo "------------------------"
        mpirun --map-by node -np $thread -mca btl self,sm,tcp bin/mpi_b $1 $2
        echo "------------------------"
    done
done