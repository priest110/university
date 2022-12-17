#ifndef QUERIES_H
#define QUERIES_H


/*
Funcao principal da 1ª query - Produtos
*/
CatProd queryUmProd(char *ficheiro);

/*
Funcao principal da 1ª query - Clientes
*/
CatCli queryUmCli(char *ficheiro);

/*
Funcao principal da 1ª query - Vendas
*/
void queryUmVendas(char *ficheiro, CatProd catp, CatCli catc, FatGlobal *fat, VendasTotal *vendas);

/*
Funcao principal da 2ª query
*/
void queryDois(CatProd catp);

/*
Funcao principal da 3ª query
*/
void queryTres(FatGlobal fat);

/*
Funcao principal da 4ª query
*/
void queryQuatro(CatProd catp, FatGlobal fat);

/*
Funcao principal da 5ª query
*/
void queryCinco(CatCli cat, VendasTotal vendas);

/*
Funcao principal da 6ª query
*/
void querySeis(CatProd catp, CatCli catc, FatGlobal fat, VendasTotal vendas);

/*
Funcao principal da 7ª query
*/
void querySete(VendasTotal vendas);

/*
Funcao principal da 8ª query
*/
void queryOito(FatGlobal fat);

/*
Funcao principal da 9ª query
*/
void queryNove(CatCli cat, VendasTotal vendas);

/*
Funcao principal da 10ª query
*/
void queryDez(VendasTotal vendas);

/*
Funcao principal da 11ª query
*/
void queryOnze(VendasTotal vendas, CatProd cat);

/*
Funcao principal da 12ª query
*/
void queryDoze(VendasTotal vendas);

#endif