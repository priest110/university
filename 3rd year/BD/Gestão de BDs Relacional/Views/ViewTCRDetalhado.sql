CREATE VIEW TCR_Detalhado AS
	SELECT T.designacao, TCR.data, A.nome AS nomeAtleta, C.nome AS nomeClinica, M.nome as nomeMedico
    FROM TesteClinico AS T
		INNER JOIN TCRealizado as TCR
        ON T.idTesteClinico = TCR.TesteClinico_idTesteClinico
			INNER JOIN Atleta as A
            ON TCR.Atleta_idAtleta = A.idAtleta
			INNER JOIN Clinica as C
			ON TCR.Clinica_idClinica = C.idClinica
            INNER JOIN Medico as M
            ON TCR.Medico_idMedico = M.idMedico
	ORDER BY TCR.data DESC
