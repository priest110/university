-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mieicare
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mieicare
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mieicare` DEFAULT CHARACTER SET utf8 ;
USE `mieicare` ;

-- -----------------------------------------------------
-- Table `mieicare`.`CodigoPostal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mieicare`.`CodigoPostal` (
  `codigoPostal` VARCHAR(8) NOT NULL,
  `cidade` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`codigoPostal`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mieicare`.`Modalidade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mieicare`.`Modalidade` (
  `idModalidade` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `escalao` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`idModalidade`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mieicare`.`Atleta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mieicare`.`Atleta` (
  `idAtleta` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `nºCC` VARCHAR(15) NOT NULL,
  `nif` VARCHAR(15) NOT NULL,
  `ddn` DATETIME NOT NULL,
  `genero` CHAR BINARY NOT NULL,
  `rua` VARCHAR(45) NOT NULL,
  `CodigoPostal_codigoPostal` VARCHAR(8) NOT NULL,
  `equipa` VARCHAR(45) NULL,
  `contacto` VARCHAR(13) NOT NULL,
  `Modalidade_idModalidade` INT NOT NULL,
  PRIMARY KEY (`idAtleta`, `CodigoPostal_codigoPostal`, `Modalidade_idModalidade`),
  INDEX `fk_Atleta_CodigoPostal_idx` (`CodigoPostal_codigoPostal` ASC) VISIBLE,
  INDEX `fk_Atleta_Modalidade1_idx` (`Modalidade_idModalidade` ASC) VISIBLE,
  CONSTRAINT `fk_Atleta_CodigoPostal`
    FOREIGN KEY (`CodigoPostal_codigoPostal`)
    REFERENCES `mieicare`.`CodigoPostal` (`codigoPostal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Atleta_Modalidade1`
    FOREIGN KEY (`Modalidade_idModalidade`)
    REFERENCES `mieicare`.`Modalidade` (`idModalidade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mieicare`.`Clinica`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mieicare`.`Clinica` (
  `idClinica` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `rua` VARCHAR(45) NOT NULL,
  `CodigoPostal_codigoPostal` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`idClinica`, `CodigoPostal_codigoPostal`),
  INDEX `fk_Clinica_CodigoPostal1_idx` (`CodigoPostal_codigoPostal` ASC) VISIBLE,
  CONSTRAINT `fk_Clinica_CodigoPostal1`
    FOREIGN KEY (`CodigoPostal_codigoPostal`)
    REFERENCES `mieicare`.`CodigoPostal` (`codigoPostal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mieicare`.`Medico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mieicare`.`Medico` (
  `idMedico` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `nºCC` VARCHAR(15) NOT NULL,
  `cedula` VARCHAR(15) NOT NULL,
  `ddn` DATETIME NOT NULL,
  `genero` CHAR BINARY NOT NULL,
  `rua` VARCHAR(45) NOT NULL,
  `CodigoPostal_codigoPostal` VARCHAR(8) NOT NULL,
  `contacto` VARCHAR(13) NOT NULL,
  `Clinica_idClinica` INT NOT NULL,
  PRIMARY KEY (`idMedico`, `CodigoPostal_codigoPostal`, `Clinica_idClinica`),
  INDEX `fk_Medico_CodigoPostal1_idx` (`CodigoPostal_codigoPostal` ASC) VISIBLE,
  INDEX `fk_Medico_Clinica1_idx` (`Clinica_idClinica` ASC) VISIBLE,
  CONSTRAINT `fk_Medico_CodigoPostal1`
    FOREIGN KEY (`CodigoPostal_codigoPostal`)
    REFERENCES `mieicare`.`CodigoPostal` (`codigoPostal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Medico_Clinica1`
    FOREIGN KEY (`Clinica_idClinica`)
    REFERENCES `mieicare`.`Clinica` (`idClinica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mieicare`.`TesteClinico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mieicare`.`TesteClinico` (
  `idTesteClinico` INT NOT NULL AUTO_INCREMENT,
  `designacao` VARCHAR(45) NOT NULL,
  `preco` FLOAT NOT NULL,
  PRIMARY KEY (`idTesteClinico`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mieicare`.`TCAgendado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mieicare`.`TCAgendado` (
  `idTCAgendado` INT NOT NULL AUTO_INCREMENT,
  `TesteClinico_idTesteClinico` INT NOT NULL,
  `data` DATETIME NOT NULL,
  `Atleta_idAtleta` INT NOT NULL,
  `Clinica_idClinica` INT NOT NULL,
  `Medico_idMedico` INT NOT NULL,
  PRIMARY KEY (`idTCAgendado`, `TesteClinico_idTesteClinico`, `Atleta_idAtleta`, `Clinica_idClinica`, `Medico_idMedico`),
  INDEX `fk_TCAgendado_Clinica1_idx` (`Clinica_idClinica` ASC) VISIBLE,
  INDEX `fk_TCAgendado_Medico1_idx` (`Medico_idMedico` ASC) VISIBLE,
  INDEX `fk_TCAgendado_TesteClinico1_idx` (`TesteClinico_idTesteClinico` ASC) VISIBLE,
  CONSTRAINT `fk_TCAgendado_Atleta1`
    FOREIGN KEY (`Atleta_idAtleta`)
    REFERENCES `mieicare`.`Atleta` (`idAtleta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TCAgendado_Clinica1`
    FOREIGN KEY (`Clinica_idClinica`)
    REFERENCES `mieicare`.`Clinica` (`idClinica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TCAgendado_Medico1`
    FOREIGN KEY (`Medico_idMedico`)
    REFERENCES `mieicare`.`Medico` (`idMedico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TCAgendado_TesteClinico1`
    FOREIGN KEY (`TesteClinico_idTesteClinico`)
    REFERENCES `mieicare`.`TesteClinico` (`idTesteClinico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mieicare`.`TCRealizado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mieicare`.`TCRealizado` (
  `idTCRealizado` INT NOT NULL AUTO_INCREMENT,
  `TesteClinico_idTesteClinico` INT NOT NULL,
  `data` DATETIME NOT NULL,
  `Atleta_idAtleta` INT NOT NULL,
  `Clinica_idClinica` INT NOT NULL,
  `Medico_idMedico` INT NOT NULL,
  PRIMARY KEY (`idTCRealizado`, `TesteClinico_idTesteClinico`, `Atleta_idAtleta`, `Clinica_idClinica`, `Medico_idMedico`),
  INDEX `fk_TCRealizado_Clinica1_idx` (`Clinica_idClinica` ASC) VISIBLE,
  INDEX `fk_TCRealizado_Medico1_idx` (`Medico_idMedico` ASC) VISIBLE,
  INDEX `fk_TCRealizado_TesteClinico1_idx` (`TesteClinico_idTesteClinico` ASC) VISIBLE,
  CONSTRAINT `fk_TCRealizado_Atleta1`
    FOREIGN KEY (`Atleta_idAtleta`)
    REFERENCES `mieicare`.`Atleta` (`idAtleta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TCRealizado_Clinica1`
    FOREIGN KEY (`Clinica_idClinica`)
    REFERENCES `mieicare`.`Clinica` (`idClinica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TCRealizado_Medico1`
    FOREIGN KEY (`Medico_idMedico`)
    REFERENCES `mieicare`.`Medico` (`idMedico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TCRealizado_TesteClinico1`
    FOREIGN KEY (`TesteClinico_idTesteClinico`)
    REFERENCES `mieicare`.`TesteClinico` (`idTesteClinico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mieicare`.`Contacto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mieicare`.`Contacto` (
  `numero` VARCHAR(13) NOT NULL,
  `Clinica_idClinica` INT NOT NULL,
  PRIMARY KEY (`numero`, `Clinica_idClinica`),
  INDEX `fk_Contacto_Clinica1_idx` (`Clinica_idClinica` ASC) VISIBLE,
  CONSTRAINT `fk_Contacto_Clinica1`
    FOREIGN KEY (`Clinica_idClinica`)
    REFERENCES `mieicare`.`Clinica` (`idClinica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mieicare`.`Equipamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mieicare`.`Equipamento` (
  `idEquipamento` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idEquipamento`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mieicare`.`EquipamentoDisponivel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mieicare`.`EquipamentoDisponivel` (
  `idEquipamentoDisponivel` INT NOT NULL AUTO_INCREMENT,
  `Equipamento_idEquipamento` INT NOT NULL,
  `Clinica_idClinica` INT NOT NULL,
  PRIMARY KEY (`idEquipamentoDisponivel`, `Equipamento_idEquipamento`, `Clinica_idClinica`),
  INDEX `fk_EquipamentoDisponivel_Equipamento1_idx` (`Equipamento_idEquipamento` ASC) VISIBLE,
  INDEX `fk_EquipamentoDisponivel_Clinica1_idx` (`Clinica_idClinica` ASC) VISIBLE,
  CONSTRAINT `fk_EquipamentoDisponivel_Equipamento1`
    FOREIGN KEY (`Equipamento_idEquipamento`)
    REFERENCES `mieicare`.`Equipamento` (`idEquipamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_EquipamentoDisponivel_Clinica1`
    FOREIGN KEY (`Clinica_idClinica`)
    REFERENCES `mieicare`.`Clinica` (`idClinica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mieicare`.`EquipamentoUtilizado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mieicare`.`EquipamentoUtilizado` (
  `EquipamentoDisponivel_idEquipamentoDisponivel` INT NOT NULL,
  `TCRealizado_idTCRealizado` INT NOT NULL,
  PRIMARY KEY (`EquipamentoDisponivel_idEquipamentoDisponivel`, `TCRealizado_idTCRealizado`),
  INDEX `fk_Equipamento_has_TCRealizado_TCRealizado1_idx` (`TCRealizado_idTCRealizado` ASC) VISIBLE,
  INDEX `fk_Equipamento_has_TCRealizado_Equipamento1_idx` (`EquipamentoDisponivel_idEquipamentoDisponivel` ASC) VISIBLE,
  CONSTRAINT `fk_Equipamento_has_TCRealizado_Equipamento1`
    FOREIGN KEY (`EquipamentoDisponivel_idEquipamentoDisponivel`)
    REFERENCES `mieicare`.`EquipamentoDisponivel` (`idEquipamentoDisponivel`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Equipamento_has_TCRealizado_TCRealizado1`
    FOREIGN KEY (`TCRealizado_idTCRealizado`)
    REFERENCES `mieicare`.`TCRealizado` (`idTCRealizado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mieicare`.`EquipamentoNecessario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mieicare`.`EquipamentoNecessario` (
  `TesteClinico_idTesteClinico` INT NOT NULL,
  `Equipamento_idEquipamento` INT NOT NULL,
  PRIMARY KEY (`TesteClinico_idTesteClinico`, `Equipamento_idEquipamento`),
  INDEX `fk_TesteClinico_has_Equipamento_Equipamento1_idx` (`Equipamento_idEquipamento` ASC) VISIBLE,
  INDEX `fk_TesteClinico_has_Equipamento_TesteClinico1_idx` (`TesteClinico_idTesteClinico` ASC) VISIBLE,
  CONSTRAINT `fk_TesteClinico_has_Equipamento_TesteClinico1`
    FOREIGN KEY (`TesteClinico_idTesteClinico`)
    REFERENCES `mieicare`.`TesteClinico` (`idTesteClinico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TesteClinico_has_Equipamento_Equipamento1`
    FOREIGN KEY (`Equipamento_idEquipamento`)
    REFERENCES `mieicare`.`Equipamento` (`idEquipamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
