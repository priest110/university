#####################

# just have this here cause makefile will think 
# my compiler is Clang and it won't recognize
# OpenMP flags or the library
ifeq ($(USER), tarly127)
	CC = gcc-10
else
	CC = gcc
endif

BIN = bin/
SRC = src/
INCLUDES = include/

CFLAGS = -O3 -fopenmp


#####################

.DEFAULT_GOAL = par


par: $(BIN)rooms.o $(SRC)tp1_par.c $(BIN)time_m.o
	$(CC) $(CFLAGS) $(SRC)tp1_par.c $(BIN)rooms.o $(BIN)time_m.o -o $(BIN)par -lm

seq: $(BIN)rooms.o $(SRC)tp1_seq.c $(BIN)time_m.o
	$(CC) $(CFLAGS) $(SRC)tp1_seq.c $(BIN)rooms.o $(BIN)time_m.o -o $(BIN)seq -lm

$(BIN)rooms.o: $(SRC)rooms.c $(INCLUDES)rooms.h
	$(CC) -O3 -c $(SRC)rooms.c -o $(BIN)rooms.o

$(BIN)time_m.o: $(SRC)time_m.c $(INCLUDES)time_m.h
	$(CC) -O3 -c $(SRC)time_m.c -o $(BIN)time_m.o


clean:
	rm -r bin/*

clean_output:
	rm -r output/*