% Trabalho Individual

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SICStus PROLOG: Declaracoes iniciais

:- set_prolog_flag(discontiguous_warnings, off).
:- set_prolog_flag(single_var_warnings, off).
:- set_prolog_flag(unknown, fail).

%---------------------------------- - - - - - - - - - -  -  -  -  -  -
% SICStus PROLOG: definicoes iniciais
:- op(900, xfy, '::').
:- dynamic '-'/1.
:- dynamic paragem/12.

%------------------- IMPORTAR DADOS PARA A BASE DE CONHECIMENTO - - - - - - - - - -  -  -  -  -  -

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Lê o ficheiro processado com os dados das paragens e insere-os numa lista
% Extensão do predicado readData -> {V,F}
readData :-
    open('C:/Users/ruinu/Desktop/processado_lista.txt', read, Str),
    read_file(Str,Lines),
    close(Str),
    insere_paragens(Lines).

read_file(Stream,[]) :-
    at_end_of_stream(Stream).

read_file(Stream,[X|L]) :-
    \+ at_end_of_stream(Stream),
    read(Stream,X),
    read_file(Stream,L). 

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Insere as paragens na base de conhecimento
% Extensão do predicado insere_paragens: Lista -> {V,F}
insere_paragens([]).
insere_paragens([X|T]) :- insercao(X), insere_paragens(T).

%------------------- PREDICADOS DE INTERACAO COM A BASE DE CONHECIMENTO - - - - - - - - - -  -  -  -  -  -

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Distância entre dois pontos
% Extensão do predicado distancia_pontos: Ponto1, Ponto2, Resultado -> {V,F}
distancia_pontos(PontoA, PontoB, RR) :-
    paragem(Id, PontoA, X, Y, E, T, A, O, C, CR, N, F),
    paragem(Id1, PontoB, X1, Y1, E1, T1, A1, O1, C1, CR1, N1, F1),   
    R is sqrt((X1-X)^2 + (Y1-Y)^2),
    RR is R/1000.    

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Lista das paragens
% Extensão do predicado lista_paragens: Lista -> {V,F}
lista_paragens(L) :-
    solucoes((paragem(Id, P, X, Y, E, T, A, O, C, CR, N, F)),
            paragem(Id, P, X, Y, E, T, A, O, C, CR, N, F),L).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Lista das carreiras de um ponto
% Extensão do predicado lista_carreiras: Gid, Lista -> {V,F}
lista_carreiras(R,L) :-
    solucoesSemRep(C,
            Id^P^X^Y^E^T^A^O^C^CR^N^F^paragem(Id, R, X, Y, E, T, A, O, C, CR, N, F),L).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Lista das quantidades de carreiras de determinados pontos
% Extensão do predicado quant_carreiras_pontos: Lista, Resultado -> {V,F}
quant_carreiras_pontos([R|R1], L) :-
    lista_carreiras(R, T),
    comprimento(T, X),
    quant_carreiras_pontos(R1, L1),
    append([X],L1,L).    

quant_carreiras_pontos(_,[]).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Verificar se dois pontos têm carreiras iguais, caso tenham devolve a carreira
% Extensão do predicado carreira_igual: Id, Id, Carreira -> {V,F}
carreira_igual(Id1, Id2, I) :-
    paragem(Id1, P1, X1, Y1, E1, T1, A1, O1, C1, CR1, N1, F1),
    paragem(Id2, P2, X2, Y2, E2, T2, A2, O2, C2, CR2, N2, F2),
    C1 == C2, I is C1;
    I is 0.

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Dado um id, devolver o id geográfico/ponto
% Extensão do predicado id_geo: Id, Gid -> {V,F}
id_geo(Id, IdG) :-
    paragem(Id, P1, X1, Y1, E1, T1, A1, O1, C1, CR1, N1, F1),
    IdG is P1.  

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Dado um id, devolver a paragem respetiva
% Extensão do predicado id_paragem: Gid, Paragem -> {V,F}
id_paragem(Id, PA) :-
    findall((paragem(Id, Gid, X1, Y1, E1, T1, A1, O1, C1, CR1, N1, F1)),
            paragem(Id, Gid, X1, Y1, E1, T1, A1, O1, C1, CR1, N1, F1),PA).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Verificar se um paragem tem abrigo com publicidade
% Extensão do predicado tem_publicidade: Paragem -> {V,F}
tem_publicidade(P) :-
    paragem(Id, P, X, Y, E, T, A, O, C, CR, N, F),
    A == yes.    

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Verificar se um paragem tem paragem abrigada
% Extensão do predicado tem_abrigo: Ponto -> {V,F}
tem_abrigo(P) :-
    paragem(Id, P, X, Y, E, T, A, O, C, CR, N, F),
    nao(T==sem_abrigo).        


%------------------- QUERIES - - - - - - - - - -  -  -  -  -  -

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Calcular trajeto entre dois pontos
% Extensao do predicado caminho: Grafo, Inicio, Fim, Caminho -> {V, F, D}
caminho_normal(_,A,[A|C1],[A|C1]).
caminho_normal(G,A,[Y|C1],C) :- adjacente(X,Y,CA,D,O,G),
                                \+ memberchk(X,[Y|C1]), 
                                caminho_normal(G,A,[X,Y|C1],C).

caminho(G,A,B,C) :- 
        algoritmo_dfs(G,A,B,[A],C).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Seleciona(1)/Exclui(0) algumas das operadoras de transporte para um determinado percurso
% Extensão do predicado operadoras: Grafo, Inicio, Fim, Operadoras, Caminho -> {V,F}
caminho_op(_, _, A, [A|C1], _, [A|C1]).
caminho_op(T, G, A, [Y|C1], OS, C) :- 
        T == 1,
        adjacente(X,Y,CA,D,O,G),
        member(O, OS),
        \+ memberchk(X,[Y|C1]), 
        caminho_op(T, G,A,[X,Y|C1],OS,C);
        T == 0,
        adjacente(X,Y,CA,D,O,G),
        \+ memberchk(O, OS),
        \+ memberchk(X,[Y|C1]), 
        caminho_op(T, G,A,[X,Y|C1],OS,C).

operadoras(T, G, A, B, O, C) :- caminho_op(T, G, A, [B], O, C).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Identificar quais as paragens com o maior número de carreiras num determinado percurso
% Extensão do predicado paragens_mais_carreiras: Grafo, Inicio, Fim, Caminho , Paragens, Maior-> {V,F}

par_mais_carreiras([],_,[]).
par_mais_carreiras([H|T],X,M) :-
        lista_carreiras(H, L),
        comprimento(L,R),
        R == X,
        par_mais_carreiras(T, X, M1),
        append([H],M1,M);
        par_mais_carreiras(T, X, M1),
        append([],M1,M). 

paragens_mais_carreiras(G, A, B, C, M, X) :-
        caminho(G, A, B, C),
        quant_carreiras_pontos(C, L),
        maiorlista(L, X),
        par_mais_carreiras(C, X, M).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Escolher o menor percurso (usando critério menor número de paragens)
% Extensão do predicado caminho_menos_paragens: Grafo, Inicio, Fim, Caminho -> {V,F}

caminho_menos_paragens(G, A, B, C) :- 
        algoritmo_bfs(G, A, B, [[A]],L),
        inverso(L, C).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Escolher o percurso mais rápido (usando critério da distância)
% Extensão do predicado caminho_mais_rapido: Grafo, Inicio, Fim, Caminho -> {V,F}

caminho_mais_rapido(G, A, B, Caminho/Custo) :-
        distancia_pontos(A, B, R),
        algoritmo_a_estrela(G, A, B, [[A]/0/R], Inv_Caminho/Custo/_),
        inverso(Inv_Caminho, Caminho).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Escolher o percurso que passe apenas por abrigos com publicidade
% Extensão do predicado caminho_com_publicidade: Grafo, Inicio, Fim, Caminho -> {V,F}

com_publicidade(_,A,[A|C1],[A|C1]).
com_publicidade(G,A,[Y|C1],C) :- adjacente(X,Y,CA,D,O,G),
                                tem_publicidade(X),
                                \+ memberchk(X,[Y|C1]), 
                                com_publicidade(G,A,[X,Y|C1],C).

caminho_com_publicidade(G, A, B, C) :- com_publicidade(G, A, [B], C).

caminho_com_publicidade_bf(G, A, B, CC) :- 
        tem_publicidade(A),
        algoritmo_bfs_publicidade(G, A, B,[[A]], C),
        inverso(C, CC).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Escolher o percurso que passe apenas por  por paragens abrigadas
% Extensão do predicado caminho_com_abrigo: Grafo, Inicio, Fim, Caminho -> {V,F}

com_abrigo(_,A,[A|C1],[A|C1]).
com_abrigo(G,A,[Y|C1],C) :- adjacente(X,Y,CA,D,O,G),
                            tem_abrigo(X),
                            \+ memberchk(X,[Y|C1]), 
                            com_abrigo(G,A,[X,Y|C1],C).

caminho_com_abrigo(G, A, B, C) :- com_abrigo(G, A, [B], C).

caminho_com_abrigo_bf(G, A, B, CC) :- 
        tem_abrigo(A),
        algoritmo_bfs_abrigo(G, A, B,[[A]], C),
        inverso(C, CC).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Escolher um ou mais pontos intermédios por onde o percurso deverá passar.
% Extensão do predicado caminho_com_intermedios: Grafo, Inicio, Fim, Lista, Caminho -> {V,F}

com_intermedios(_,A,[A|C1],[],[A|C1]).
com_intermedios(G,A,[Y|C1],C) :- adjacente(X,Y,CA,D,O,G),
                            member(X, C),
                            \+ memberchk(X,[Y|C1]), 
                            com_intermedios(G,A,[X,Y|C1],C).

caminho_com_intermedios(G, A, B, L,  LL) :- 
        algoritmo_dfs(G, A, B, [A],LL),
        tem_elementos(L, LL);
        algoritmo_bfs(G, A, B, [[A]],LL),
        tem_elementos(L, LL).

%------------------- GRAFO E ALGORITMOS DE PESQUISA - - - - - - - - - -  -  -  -  -  -

%---------------------------------- - - - - - - - - - -  -  -  -  -   - 
% Lista dos pontos das paragens/vértices do grafo
% Extensão do predicado pontos: Lista -> {V,F}
pontos(L) :-
    solucoes((Ponto),
            paragem(Id, Ponto, X, Y, E, T, A, O, C, CR, N, F),L).

%---------------------------------- - - - - - - - - - -  -  -  -  -   - 
% Lista de ids dos pontos
% Extensão do predicado ids_pontos: Lista -> {V,F}

ids_pontos(L) :-
    solucoes((Id),
            paragem(Id, Ponto, X, Y, E, T, A, O, C, CR, N, F),L).                 

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Lista das arestas do grafo
% Extensão do predicado lista_arestas: Lista de ids dos pontos, Lista de carreiras, Resultado -> {V,F}

lista_arestas([],[]).
lista_arestas([X|T], L) :-
    cabeca_lista(T, Y),
    Y > 0,
    id_geo(Y, YGEO),
    id_geo(X, XGEO),
    carreira_igual(X, Y, I),
    I > 0,
    distancia_pontos(XGEO, YGEO, D),
    lista_arestas(T, L1),
    append([aresta(XGEO, YGEO, I, D, O)],L1,L);
    lista_arestas(T, L1),
    append([],L1,L).

%--------------------------------- - - - - - - - - - -  -  -  -  -  - 
% Extensao do predicado adjacente : Gid, Gid, Carreira, Distância, Operadora, Grafo -> {V, F, D}

adjacente(X,Y,C,D,O,grafo(N, L)) :- member(aresta(X,Y,C,D,O), L). 
adjacente(X,Y,C,D,O,grafo(N, L)) :- member(aresta(Y,X,C,D,O), L). 

adjacente_Aux(G, B,[Paragem|Caminho]/Distancia/_, [ProxParagem,Paragem|Caminho]/NovaDistancia/Estimativa) :-
    adjacente(Paragem, ProxParagem,C,ProxDistancia,O,G),
    \+ member(ProxParagem, Caminho),
    NovaDistancia is Distancia + ProxDistancia,
    distancia_pontos(ProxParagem, B, Estimativa).  

%---------------------------------- - - - - - - - - - -  -  -  -  -   -                
% Passar as paragens dos autocarros para um grafo

g(grafo(L, A)) :- pontos(L), ids_pontos(I), lista_arestas(I,A).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Algoritmo Breadth First search
% Extensão do predicado algoritmo_bfs: Grafo, Inicio, Fim, Extendidos, Caminho -> {V,F}

algoritmo_bfs(G, A, B, [[Paragem|Caminho]|_], [Paragem|Caminho]) :-
    cabeca_lista([Paragem|Caminho], B).

algoritmo_bfs(G, A, B, [Caminho|Caminhos], Solucao) :-
    extende(G, Caminho, NovosCaminhos),
    append(Caminhos, NovosCaminhos, Aux),
    algoritmo_bfs(G, A, B, Aux, Solucao).

extende(G, [Paragem|Caminho], NovosCaminhos) :-
    findall([NovaParagem, Paragem|Caminho],
            (adjacente(Paragem,NovaParagem,CA,D,O,G),
             nao(member(NovaParagem, [Paragem|Caminho]))), 
             NovosCaminhos),!.    

extende(G, Caminho,[]).

algoritmo_bfs_publicidade(G, A, B, [[Paragem|Caminho]|_], [Paragem|Caminho]) :-
    cabeca_lista([Paragem|Caminho], B).

algoritmo_bfs_publicidade(G, A, B, [Caminho|Caminhos], Solucao) :-
    extende_publicidade(G, Caminho, NovosCaminhos),
    append(Caminhos, NovosCaminhos, Aux),
    algoritmo_bfs_publicidade(G, A, B, Aux, Solucao).

extende_publicidade(G, [Paragem|Caminho], NovosCaminhos) :-
    findall([NovaParagem, Paragem|Caminho],
            (adjacente(Paragem,NovaParagem,CA,D,O,G),
             nao(member(NovaParagem, [Paragem|Caminho])),
             tem_publicidade(NovaParagem)), 
             NovosCaminhos),!.    

extende_publicidade(G, Caminho,[]).

algoritmo_bfs_abrigo(G, A, B, [[Paragem|Caminho]|_], [Paragem|Caminho]) :-
    cabeca_lista([Paragem|Caminho], B).

algoritmo_bfs_abrigo(G, A, B, [Caminho|Caminhos], Solucao) :-
    extende_abrigo(G, Caminho, NovosCaminhos),
    append(Caminhos, NovosCaminhos, Aux),
    algoritmo_bfs_abrigo(G, A, B, Aux, Solucao).

extende_abrigo(G, [Paragem|Caminho], NovosCaminhos) :-
    findall([NovaParagem, Paragem|Caminho],
            (adjacente(Paragem,NovaParagem,CA,D,O,G),
             nao(member(NovaParagem, [Paragem|Caminho])),
             tem_abrigo(NovaParagem)), 
             NovosCaminhos),!.    

extende_abrigo(G, Caminho,[]).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Algoritmo Depth First search
% Extensão do predicado algoritmo_dfs: Grafo, Inicio, Fim, Visitados, Caminho -> {V,F}

algoritmo_dfs(G, A, B, T, [B]) :-
    cabeca_lista(T, B).

algoritmo_dfs(G, A, B, Historico, [A|Caminho]) :-
    adjacente(A,Y,CA,D,O,G),
    C is A,
    nao(member(Y, Historico)),
    algoritmo_dfs(G, Y, B, [Y|Historico], Caminho).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Algoritmo A*
% Extensão do predicado algoritmo_a_estrela: Grafo, Inicio, Fim, Caminhados, Caminho -> {V,F}

algoritmo_a_estrela(G, A, B, Caminhos, Caminho) :-
        melhor(Caminhos, Caminho),
        Caminho = [Paragem|_]/_/_,
        Paragem == B.

algoritmo_a_estrela(G, A, B, Caminhos, Solucao) :-
        melhor(Caminhos, Melhor),
        extende_estrela(G, B, Melhor, CaminhosAux),
        algoritmo_a_estrela(G, A, B, CaminhosAux, Solucao).

melhor([Caminho], Caminho) :- !.

melhor([Caminho1/Distancia1/Estimativa1,_/Distancia2/Estimativa2|Caminhos], MelhorCaminho) :-
    Distancia1 + Estimativa1 =< Distancia2 + Estimativa2, !,     %-->
    melhor([Caminho1/Distancia1/Estimativa1|Caminhos], MelhorCaminho). 

melhor([_|Caminhos], MelhorCaminho) :- 
    melhor(Caminhos, MelhorCaminho).

extende_estrela(G, B, Caminho, CaminhosAux) :-
    findall(NovoCaminho, adjacente_Aux(G, B,Caminho,NovoCaminho),
            CaminhosAux). 
  



%------------------- PREDICADOS AUXILIARES - - - - - - - - - -  -  -  -  -  -

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta predicado nao: Questao -> {V, F}

nao(Questao) :- Questao, !, fail.
nao(Questao).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado solucoes: Termo, Questão, Resultado -> {V,F}

solucoes(X, Y, Z) :- findall(X, Y, Z).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado solucoesSemRep: Termo, Questão, Resultado -> {V,F}

solucoesSemRep(X, Y, Z) :- setof(X, Y, Z).

%---------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado insercao: Termo -> {V,F}

insercao(Termo) :- assert(Termo).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado cabeca_lista: Lista, Resultado -> {V,F}

cabeca_lista([], 0).
cabeca_lista([X], X).
cabeca_lista([H|T], H).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado comprimento: Lista, Resultado -> {V,F}

comprimento(S,N) :- length(S,N).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado maiorlista: Lista, Resultado -> {V,F}

maiorlista([X], X).
maiorlista([H|T], X) :- maiorlista(T,X1), maior(H, X1,X).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado  maior: Real, Real, Resultado -> {V,F}

maior(X,Y,X) :- X > Y. 
maior(X,Y,Y) :- X =< Y.

%->-------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado  ultimo: Lista, Real -> {V,F}

ultimo([X], X).
ultimo([H|T], R) :- ultimo(T, R).   

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado inverso: Lista, Resultado -> {V,F}

inverso(Lista, Nova):-
    inverso_Aux(Lista, [], Nova).

inverso_Aux([], Lista, Lista).
inverso_Aux([X|Lista], Aux, Nova):-
    inverso_Aux(Lista, [X|Aux], Nova).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado escolhe: Elemento, Lista, Resultado -> {V,F}

escolhe(Elem, [Elem|Xs], Xs).
escolhe(Elem, [X|Xs], [X|Ys]) :- escolhe(Elem, Xs, Ys).       

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado tem_elementos: Lista, Lista -> {V,F}

tem_elementos([H], LL) :-  member(H, LL).
tem_elementos([H|T], LL) :- member(H, LL), tem_elementos(T, LL).   

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado write_aux: Lista -> {V,F}

write_aux([H]) :- write(H), nl.
write_aux([H|T]) :- write(H), nl,write_aux(T).
