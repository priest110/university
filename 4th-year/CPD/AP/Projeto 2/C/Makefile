#####################

# just have this here cause makefile will think 
# my compiler is Clang and it won't recognize
# OpenMP flags or the library
ifeq ($(USER), tarly127)
	CC = gcc-10
else
	CC = gcc
endif

BIN      = bin/
SRC      = src/
INCLUDES = include/

CFLAGS = -O3 -fopenmp


#####################

.DEFAULT_GOAL = main


main: $(BIN)poisson.o $(BIN)time_m.o $(SRC)main.c
	$(CC) $(CFLAGS) $(SRC)main.c $(BIN)poisson.o $(BIN)time_m.o -o $(BIN)poisson -lm

$(BIN)poisson.o: $(INCLUDES)poisson.h $(SRC)poisson.c
	$(CC) $(CFLAGS) -c $(SRC)poisson.c -o $(BIN)poisson.o -lm

$(BIN)time_m.o: $(SRC)time_m.c $(INCLUDES)time_m.h
	$(CC) -O3 -c $(SRC)time_m.c -o $(BIN)time_m.o


clean:
	rm -r bin/*

clean_output:
	rm -r output/*