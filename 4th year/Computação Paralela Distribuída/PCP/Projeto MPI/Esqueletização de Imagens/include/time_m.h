#include <sys/time.h>
#include <stdio.h>
#include <mpi.h>

#define TIME_RESOLUTION 1000000

void startTime (void);
void lap (void);
void stopTime (int iter);
void stopTimeW(void);
void beginComm();
void endComm();
void showCommTime();