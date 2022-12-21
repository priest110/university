ifeq ($(USER), tarly127)
		CC=gcc-10
else
		CC=gcc
endif
OBIN=bin
IBIN=src

all:
		make par2 par_col par_line par_line2 par_tasks seq seq2

par2: $(IBIN)/tp1_par_v2_w.c time_m.o
		$(CC) -O3 -fopenmp -std=c11 -o $(OBIN)/par_2w $(IBIN)/tp1_par_v2_w.c $(OBIN)/time_m.o

par_col: $(IBIN)/tp1_par_v1_col.c time_m.o
		$(CC) -O3 -fopenmp -std=c11 -o $(OBIN)/par_col $(IBIN)/tp1_par_v1_col.c $(OBIN)/time_m.o

par_line: $(IBIN)/tp1_par_v1_line.c time_m.o
		$(CC) -O3 -fopenmp -std=c11 -o $(OBIN)/par_line $(IBIN)/tp1_par_v1_line.c $(OBIN)/time_m.o

par_line2: $(IBIN)/tp1_par_v2_line.c time_m.o
		$(CC) -O3 -fopenmp -std=c11 -o $(OBIN)/par_2l $(IBIN)/tp1_par_v2_line.c $(OBIN)/time_m.o

par_tasks: $(IBIN)/tp1_par_v1_tasks.c time_m.o
		$(CC) -O3 -fopenmp -std=c11 -o $(OBIN)/par_tasks $(IBIN)/tp1_par_v1_tasks.c $(OBIN)/time_m.o

seq: $(IBIN)/tp1_seq_l.c time_m.o
		$(CC) -O3 -fopenmp -std=c11 -o $(OBIN)/seql $(IBIN)/tp1_seq_l.c $(OBIN)/time_m.o

seq2: $(IBIN)/tp1_seq_w.c time_m.o
		$(CC) -O3 -fopenmp -std=c11 -o $(OBIN)/seqw $(IBIN)/tp1_seq_w.c $(OBIN)/time_m.o

time_m.o: src/time_m.c include/time_m.h
		gcc -c src/time_m.c -o $(OBIN)/time_m.o

clean:
		rm -f $(OBIN)/*
