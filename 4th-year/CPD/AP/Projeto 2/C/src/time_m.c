
/* Para medir o tempo */
#include "../include/time_m.h"
#include <stdio.h>

struct timeval t;
long long unsigned cpu_time;
long long unsigned final_time;

void startTime (void) 
{
        gettimeofday(&t, NULL);
        cpu_time = t.tv_sec * TIME_RESOLUTION + t.tv_usec;
}

long long unsigned stopTime (void) 
{
        gettimeofday(&t, NULL);
        final_time = t.tv_sec * TIME_RESOLUTION + t.tv_usec;

        final_time -= cpu_time;

        return final_time;
}

void execTime(void)
{
        printf("%llu,", final_time);
}

long long unsigned getTime()
{
        gettimeofday(&t, NULL);
        return t.tv_sec * TIME_RESOLUTION + t.tv_usec;
}