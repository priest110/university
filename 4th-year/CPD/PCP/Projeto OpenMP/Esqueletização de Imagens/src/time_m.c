
/* Para medir o tempo */
#include "../include/time_m.h"
#include <stdio.h>

struct timeval t;
long long unsigned cpu_time;

void startTime (void) {
        gettimeofday(&t, NULL);
        cpu_time = t.tv_sec * TIME_RESOLUTION + t.tv_usec;
}

void stopTime (int iter) {
        gettimeofday(&t, NULL);
        long long unsigned final_time = t.tv_sec * TIME_RESOLUTION + t.tv_usec;

        final_time -= cpu_time;

        printf("After %d iterations, ", iter);
        printf("%llu us have passed...\n", final_time);
}