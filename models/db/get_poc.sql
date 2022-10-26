-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Get_POC
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Get_POC
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Get_POC` DEFAULT CHARACTER SET utf8 ;
USE `Get_POC` ;

-- -----------------------------------------------------
-- Table `Get_POC`.`area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`area` (
  `idarea` INT NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idarea`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`PDF`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`PDF` (
  `idPDF` INT NOT NULL,
  `data_criacao` DATE NOT NULL,
  `conteudo` LONGTEXT NOT NULL,
  PRIMARY KEY (`idPDF`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`user` (
  `username` VARCHAR(100) NOT NULL,
  `senha` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`professor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`professor` (
  `email` VARCHAR(100) NOT NULL,
  `user_username` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`user_username`),
  CONSTRAINT `fk_professor_user1`
    FOREIGN KEY (`user_username`)
    REFERENCES `Get_POC`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`POC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`POC` (
  `idPOC` INT NOT NULL,
  `titulo` VARCHAR(100) NOT NULL,
  `data_defesa` DATE NOT NULL,
  `resumo` TINYTEXT NOT NULL,
  `area_idarea` INT NOT NULL,
  `PDF_idPDF` INT NOT NULL,
  `prof_cadastrante` VARCHAR(100) NOT NULL,
  `prof_orientador` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idPOC`),
  INDEX `fk_POC_area1_idx` (`area_idarea` ASC) VISIBLE,
  INDEX `fk_POC_PDF1_idx` (`PDF_idPDF` ASC) VISIBLE,
  INDEX `fk_POC_professor1_idx` (`prof_cadastrante` ASC) VISIBLE,
  INDEX `fk_POC_professor2_idx` (`prof_orientador` ASC) VISIBLE,
  CONSTRAINT `fk_POC_area1`
    FOREIGN KEY (`area_idarea`)
    REFERENCES `Get_POC`.`area` (`idarea`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_POC_PDF1`
    FOREIGN KEY (`PDF_idPDF`)
    REFERENCES `Get_POC`.`PDF` (`idPDF`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_POC_professor1`
    FOREIGN KEY (`prof_cadastrante`)
    REFERENCES `Get_POC`.`professor` (`user_username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_POC_professor2`
    FOREIGN KEY (`prof_orientador`)
    REFERENCES `Get_POC`.`professor` (`user_username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`aluno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`aluno` (
  `email` VARCHAR(100) NOT NULL,
  `matricula` VARCHAR(45) NOT NULL,
  `POC_feito` INT NULL,
  `user_username` VARCHAR(100) NOT NULL,
  INDEX `fk_aluno_POC1_idx` (`POC_feito` ASC) VISIBLE,
  PRIMARY KEY (`user_username`),
  UNIQUE INDEX `matricula_UNIQUE` (`matricula` ASC) VISIBLE,
  CONSTRAINT `fk_aluno_POC1`
    FOREIGN KEY (`POC_feito`)
    REFERENCES `Get_POC`.`POC` (`idPOC`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_aluno_user1`
    FOREIGN KEY (`user_username`)
    REFERENCES `Get_POC`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`disciplina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`disciplina` (
  `iddisciplina` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `descricao` TINYTEXT NOT NULL,
  PRIMARY KEY (`iddisciplina`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`palavra_chave`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`palavra_chave` (
  `idpalavra_chave` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idpalavra_chave`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`POC_has_palavra_chave`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`POC_has_palavra_chave` (
  `POC_idPOC` INT NOT NULL,
  `palavra_chave_idpalavra_chave` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`POC_idPOC`, `palavra_chave_idpalavra_chave`),
  INDEX `fk_POC_has_palavra_chave_palavra_chave1_idx` (`palavra_chave_idpalavra_chave` ASC) VISIBLE,
  INDEX `fk_POC_has_palavra_chave_POC1_idx` (`POC_idPOC` ASC) VISIBLE,
  CONSTRAINT `fk_POC_has_palavra_chave_POC1`
    FOREIGN KEY (`POC_idPOC`)
    REFERENCES `Get_POC`.`POC` (`idPOC`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_POC_has_palavra_chave_palavra_chave1`
    FOREIGN KEY (`palavra_chave_idpalavra_chave`)
    REFERENCES `Get_POC`.`palavra_chave` (`idpalavra_chave`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`table1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`table1` (
  `idtable1` INT NOT NULL,
  PRIMARY KEY (`idtable1`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`leciona_lecionada`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`leciona_lecionada` (
  `disciplina_iddisciplina` INT UNSIGNED NOT NULL,
  `professor_user_username` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`disciplina_iddisciplina`, `professor_user_username`),
  INDEX `fk_disciplina_has_professor_professor1_idx` (`professor_user_username` ASC) VISIBLE,
  INDEX `fk_disciplina_has_professor_disciplina1_idx` (`disciplina_iddisciplina` ASC) VISIBLE,
  CONSTRAINT `fk_disciplina_has_professor_disciplina1`
    FOREIGN KEY (`disciplina_iddisciplina`)
    REFERENCES `Get_POC`.`disciplina` (`iddisciplina`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_disciplina_has_professor_professor1`
    FOREIGN KEY (`professor_user_username`)
    REFERENCES `Get_POC`.`professor` (`user_username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`coorientacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`coorientacao` (
  `prof_co_orienta` VARCHAR(100) NOT NULL,
  `POC_coorientado` INT NOT NULL,
  PRIMARY KEY (`prof_co_orienta`, `POC_coorientado`),
  INDEX `fk_professor_has_POC_POC1_idx` (`POC_coorientado` ASC) VISIBLE,
  INDEX `fk_professor_has_POC_professor1_idx` (`prof_co_orienta` ASC) VISIBLE,
  CONSTRAINT `fk_professor_has_POC_professor1`
    FOREIGN KEY (`prof_co_orienta`)
    REFERENCES `Get_POC`.`professor` (`user_username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_professor_has_POC_POC1`
    FOREIGN KEY (`POC_coorientado`)
    REFERENCES `Get_POC`.`POC` (`idPOC`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`administra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`administra` (
  `administrador_username` VARCHAR(100) NOT NULL,
  `administrado_username` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`administrador_username`, `administrado_username`),
  INDEX `fk_user_has_user_user2_idx` (`administrado_username` ASC) VISIBLE,
  INDEX `fk_user_has_user_user1_idx` (`administrador_username` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_user_user1`
    FOREIGN KEY (`administrador_username`)
    REFERENCES `Get_POC`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_user_user2`
    FOREIGN KEY (`administrado_username`)
    REFERENCES `Get_POC`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
