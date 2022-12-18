CREATE VIEW Medico_Atleta AS
	SELECT A.idAtleta, A.nome AS nome_Atleta, M.nome AS nome_Medico
    FROM Atleta AS A
		INNER JOIN TCRealizado as T
        ON A.idAtleta = T.Atleta_idAtleta
			INNER JOIN Medico as M
            ON T.Medico_idMedico = M.idMedico
	ORDER BY A.idAtleta ASC;