#include <sys/time.h>
#include <stdio.h>

#define TIME_RESOLUTION 1000000

void startTime (void);
long long unsigned stopTime(void);
void execTime  (void);
long long unsigned getTime (void);