CC=gcc
FLAG = -Wall

Projeto: catProd.o catClientes.o faturacao.o gestaoDeFiliais.o navegador.o queries.o aplicacao.o
	$(CC) -g $(FLAG) catProd.o catClientes.o faturacao.o gestaoDeFiliais.o navegador.o queries.o aplicacao.o -o Projeto
aplicacao.o: aplicacao.c catProd.h catClientes.h faturacao.h gestaoDeFiliais.h navegador.h queries.h
	$(CC) -c $(FLAG) aplicacao.c
queries.o: catProd.h catClientes.h faturacao.h gestaoDeFiliais.h navegador.h queries.c queries.h
	$(CC) -c $(FLAG) queries.c
gestaoDeFiliais.o: catProd.h catClientes.h faturacao.h gestaoDeFiliais.c gestaoDeFiliais.h 
	$(CC) -c $(FLAG) gestaoDeFiliais.c
faturacao.o: catProd.h catClientes.h faturacao.c faturacao.h
	$(CC) -c $(FLAG) faturacao.c
navegador.o: navegador.c navegador.h
	$(CC) -c $(FLAG) navegador.c
catClientes.o: catClientes.c catClientes.h
	$(CC) -c $(FLAG) catClientes.c
catProd.o: catProd.c catProd.h
	$(CC) -c $(FLAG) catProd.c

.PHONY: clean

clean:
	rm -f *.o Projeto
