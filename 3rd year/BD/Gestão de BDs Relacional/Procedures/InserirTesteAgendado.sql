-- Transação que regista o agendamento de um teste
DELIMITER $$
CREATE PROCEDURE agenda_teste (IN idTCA INT, IN idT INT, IN d DATETIME, IN idA INT, IN idC INT, IN idM INT)

BEGIN 
	DECLARE Erro BOOL DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET Erro = 1;
    START TRANSACTION;
    
-- insercao do teste
INSERT INTO TCAgendado (idTCAgendado, TesteClinico_idTesteClinico, data, Atleta_idAtleta, Clinica_idClinica, Medico_idMedico) VALUES (idTCA, idT, d, idA, idC, idM);

IF Erro
THEN ROLLBACK;
ELSE COMMIT;
END IF;
END $$