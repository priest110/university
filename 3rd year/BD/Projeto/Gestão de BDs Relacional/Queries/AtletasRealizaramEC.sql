SELECT A.nome, COUNT(*) AS Num_Testes from TCRealizado AS TCR
	INNER JOIN Atleta AS A
		ON TCR.Atleta_idAtleta = A.idAtleta
	INNER JOIN TesteClinico AS T
		ON TCR.TesteClinico_idTesteClinico = T.idTesteClinico
	WHERE T.designacao = 'Eletrocardiograma'
    GROUP BY TCR.Atleta_idAtleta
	ORDER BY A.nome ASC