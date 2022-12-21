-- Transação que regista um teste como realizado
DELIMITER $$
CREATE PROCEDURE teste_realizado (IN idTCA INT, IN idTCR INT, IN idT INT, IN d DATETIME, IN idA INT, IN idC INT, IN idM INT, IN eu INT)

BEGIN 
	DECLARE Erro BOOL DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET Erro = 1;
    START TRANSACTION;
    
-- insercao do teste como realizado
INSERT INTO TCRealizado (idTCRealizado, TesteClinico_idTesteClinico, data, Atleta_idAtleta, Clinica_idClinica, Medico_idMedico) VALUES (idTCR, idT, d, idA, idC, idM);

-- apagar o teste como agendado
DELETE FROM TCAgendado WHERE idTCAgendado = idTCA;

-- adicionar o equipamento utilizado, caso algum tenha sido usado
IF (eu != NULL)
THEN INSERT INTO EquipamentoUtilizado (EquipamentoDisponivel_idEquipamentoDisponivel, TCRealizado_idTCRealizado) VALUES (eu, idTCR);
END IF; 

IF Erro
THEN ROLLBACK;
ELSE COMMIT;
END IF;
END $$