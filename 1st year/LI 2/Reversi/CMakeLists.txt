cmake_minimum_required(VERSION 3.13)
project(li2reversi C)

set(CMAKE_C_STANDARD 99)

add_executable(li2reversi main.c estado.h estado.c bot.c bot.h listas.c listas.h)