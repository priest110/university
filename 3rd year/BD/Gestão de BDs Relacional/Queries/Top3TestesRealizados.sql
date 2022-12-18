SELECT T.designacao, COUNT(idTCRealizado) AS NumTestes 
FROM TCRealizado AS TCR
	INNER JOIN TesteClinico AS T
	ON TCR.TesteClinico_idTesteClinico = T.idTesteClinico
WHERE TCR.data BETWEEN '2016-10-01 00:00:00' AND '2016-10-31 23:59:59'
GROUP BY TesteClinico_idTesteClinico
ORDER BY NumTestes DESC
LIMIT 3