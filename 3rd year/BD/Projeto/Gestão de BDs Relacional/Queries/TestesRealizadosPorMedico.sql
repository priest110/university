SELECT M.nome, COUNT(*) AS TestesRealizados 
FROM TCRealizado AS TCR
	INNER JOIN Medico AS M
    ON TCR.Medico_idMedico = M.idMedico
GROUP BY Medico_idMedico
ORDER BY TestesRealizados DESC