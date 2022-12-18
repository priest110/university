CREATE VIEW TCA_Detalhado AS
	SELECT T.designacao, TCA.data, A.nome AS nomeAtleta, C.nome AS nomeClinica, M.nome as nomeMedico
    FROM TesteClinico AS T
		INNER JOIN TCAgendado as TCA
        ON T.idTesteClinico = TCA.TesteClinico_idTesteClinico
			INNER JOIN Atleta as A
            ON TCA.Atleta_idAtleta = A.idAtleta
			INNER JOIN Clinica as C
			ON TCA.Clinica_idClinica = C.idClinica
            INNER JOIN Medico as M
            ON TCA.Medico_idMedico = M.idMedico
	ORDER BY TCA.data
