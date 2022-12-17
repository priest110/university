CC=gcc
FLAG = -Wall

all: cv ma sv ag
cv: ClienteDeVendas.o
	$(CC) -g $(FLAG) ClienteDeVendas.o -o cv
ma: ManutencaoDeArtigos.o
	$(CC) -g $(FLAG) ManutencaoDeArtigos.o -o ma
sv: ServidorDeVendas.o
	$(CC) -g $(FLAG) ServidorDeVendas.o -o sv
ag: Agregador.o
	$(CC) -g $(FLAG) Agregador.o -o ag
ManutencaoDeArtigos.o: ManutencaoDeArtigos.c
	$(CC) -c $(FLAG) ManutencaoDeArtigos.c
ClienteDeVendas.o: ClienteDeVendas.c
	$(CC) -c $(FLAG) ClienteDeVendas.c
ServidorDeVendas.o: ServidorDeVendas.c
	$(CC) -c $(FLAG) ServidorDeVendas.c
Agregador.o: Agregador.c
	$(CC) -c $(FLAG) Agregador.c

.PHONY: clean

clean:
	rm -f *.o cv ma sv ag clienteMostrarAux artigosAux agregadorAux clienteAlterarAux agregadorAux
