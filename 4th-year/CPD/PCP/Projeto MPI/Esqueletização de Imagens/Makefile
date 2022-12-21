#Can only be used in SeARCH
SMCC=mpicc
GCC=gcc
SRC=src/
BIN=bin/
INC=include/
CFLAGS=-O3 -std=c99

#Versão MPI com comunicação extra
mpi_a: time_m.o $(SRC)tp2_par_A.c
		$(SMCC) $(CFLAGS) -o $(BIN)mpi_a $(SRC)tp2_par_A.c $(BIN)time_m.o

#Versão MPI sem comunicação extra
mpi_b: time_m.o $(SRC)tp2_par_B.c
		$(SMCC) $(CFLAGS) -o $(BIN)mpi_b $(SRC)tp2_par_B.c $(BIN)time_m.o

#Versão OpenMP
omp: time_m.o $(SRC)tp1_par.c
		$(GCC) $(CFLAGS) -o $(BIN)par_omp $(SRC)tp1_par.c $(BIN)time_m.o

#Versão Sequencial
seq: $(BIN)time_m.o $(SRC)tp2_seq.c
		$(GCC) $(CFLAGS) -o $(BIN)seq $(SRC)tp2_seq.c $(BIN)time_m.o

time_m.o: $(SRC)time_m.c $(INC)time_m.h
		$(SMCC) -c $(SRC)time_m.c -o $(BIN)time_m.o

clean:
		rm -rf $(BIN)*