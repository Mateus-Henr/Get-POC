-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema get_poc
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema get_poc
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema getpoc
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `get_poc` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `get_poc` ;

-- -----------------------------------------------------
-- Table `get_poc`.`tb_area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `get_poc`.`tb_area` (
  `ID` INT NOT NULL,
  `Name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `get_poc`.`tb_discipline`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `get_poc`.`tb_discipline` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(100) NOT NULL,
  `Description` TINYTEXT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `get_poc`.`tb_keyword`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `get_poc`.`tb_keyword` (
  `ID` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `get_poc`.`tb_pdf`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `get_poc`.`tb_pdf` (
  `ID` INT NOT NULL,
  `Creation_Date` DATE NOT NULL,
  `Content` LONGTEXT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `get_poc`.`tb_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `get_poc`.`tb_user` (
  `ID` VARCHAR(100) NOT NULL,
  `Password` VARCHAR(100) NOT NULL,
  `Name` VARCHAR(100) NOT NULL,
  `Type` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `get_poc`.`tb_teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `get_poc`.`tb_teacher` (
  `Email` VARCHAR(100) NOT NULL,
  `TB_User_ID` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`TB_User_ID`),
  CONSTRAINT `fk_teacher_user1`
    FOREIGN KEY (`TB_User_ID`)
    REFERENCES `get_poc`.`tb_user` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `get_poc`.`tb_poc`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `get_poc`.`tb_poc` (
  `ID` INT NOT NULL,
  `Title` VARCHAR(100) NOT NULL,
  `Defense_Date` DATE NOT NULL,
  `Summary` TINYTEXT NOT NULL,
  `TB_Area_ID` INT NOT NULL,
  `TB_PDF_ID` INT NOT NULL,
  `Teacher_Registrant` VARCHAR(100) NOT NULL,
  `Teacher_Advisor` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_TB_POC_area1_idx` (`TB_Area_ID` ASC) VISIBLE,
  INDEX `fk_TB_POC_PDF1_idx` (`TB_PDF_ID` ASC) VISIBLE,
  INDEX `fk_TB_POC_teacher1_idx` (`Teacher_Registrant` ASC) VISIBLE,
  INDEX `fk_TB_POC_teacher2_idx` (`Teacher_Advisor` ASC) VISIBLE,
  CONSTRAINT `fk_TB_POC_area1`
    FOREIGN KEY (`TB_Area_ID`)
    REFERENCES `get_poc`.`tb_area` (`ID`),
  CONSTRAINT `fk_TB_POC_PDF1`
    FOREIGN KEY (`TB_PDF_ID`)
    REFERENCES `get_poc`.`tb_pdf` (`ID`),
  CONSTRAINT `fk_TB_POC_teacher1`
    FOREIGN KEY (`Teacher_Registrant`)
    REFERENCES `get_poc`.`tb_teacher` (`TB_User_ID`),
  CONSTRAINT `fk_TB_POC_teacher2`
    FOREIGN KEY (`Teacher_Advisor`)
    REFERENCES `get_poc`.`tb_teacher` (`TB_User_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `get_poc`.`tb_poc_has_keyword`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `get_poc`.`tb_poc_has_keyword` (
  `TB_POC_ID` INT NOT NULL,
  `TB_Keyword_ID` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`TB_POC_ID`, `TB_Keyword_ID`),
  INDEX `fk_TB_POC_has_Keyword_keyword1_idx` (`TB_Keyword_ID` ASC) VISIBLE,
  INDEX `fk_TB_POC_has_Keyword_POC1_idx` (`TB_POC_ID` ASC) VISIBLE,
  CONSTRAINT `fk_TB_POC_has_Keyword_keyeword1`
    FOREIGN KEY (`TB_Keyword_ID`)
    REFERENCES `get_poc`.`tb_keyword` (`ID`),
  CONSTRAINT `TB_fk_POC_has_Keyword_POC1`
    FOREIGN KEY (`TB_POC_ID`)
    REFERENCES `get_poc`.`tb_poc` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `get_poc`.`tb_student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `get_poc`.`tb_student` (
  `Email` VARCHAR(100) NOT NULL,
  `Registration` VARCHAR(100) NOT NULL,
  `POC` INT NULL DEFAULT NULL,
  `TB_User_ID` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`TB_User_ID`),
  UNIQUE INDEX `Registration_UNIQUE` (`Registration` ASC) VISIBLE,
  INDEX `fk_TB_Student_POC1_idx` (`POC` ASC) VISIBLE,
  CONSTRAINT `fk_TB_Student_POC1`
    FOREIGN KEY (`POC`)
    REFERENCES `get_poc`.`tb_poc` (`ID`),
  CONSTRAINT `fk_TB_Student_user1`
    FOREIGN KEY (`TB_User_ID`)
    REFERENCES `get_poc`.`tb_user` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `get_poc`.`tb_teacher_co-advises_poc`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `get_poc`.`tb_teacher_co-advises_poc` (
  `TB_POC_ID` INT NOT NULL,
  `tb_teacher_TB_User_ID` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`TB_POC_ID`, `tb_teacher_TB_User_ID`),
  INDEX `fk_TB_Teacher_Co-advises_POC_POC1_idx` (`TB_POC_ID` ASC) VISIBLE,
  INDEX `fk_tb_teacher_co-advises_poc_tb_teacher1_idx` (`tb_teacher_TB_User_ID` ASC) VISIBLE,
  CONSTRAINT `fk_TB_Teacher_Co-advises_POC_POC1`
    FOREIGN KEY (`TB_POC_ID`)
    REFERENCES `get_poc`.`tb_poc` (`ID`),
  CONSTRAINT `fk_tb_teacher_co-advises_poc_teacher1`
    FOREIGN KEY (`tb_teacher_TB_User_ID`)
    REFERENCES `get_poc`.`tb_teacher` (`TB_User_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `get_poc`.`tb_teacher_has_discipline`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `get_poc`.`tb_teacher_has_discipline` (
  `TB_Discipline_ID` INT UNSIGNED NOT NULL,
  `TB_Teacher_User_ID` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`TB_Discipline_ID`, `TB_Teacher_User_ID`),
  INDEX `fk_TB_Teacher_has_Discipline_teacher1_idx` (`TB_Teacher_User_ID` ASC) VISIBLE,
  INDEX `fk_TB_Teacher_has_Discipline_discipline1_idx` (`TB_Discipline_ID` ASC) VISIBLE,
  CONSTRAINT `fk_TB_Teacher_has_Discipline_discipline1`
    FOREIGN KEY (`TB_Discipline_ID`)
    REFERENCES `get_poc`.`tb_discipline` (`ID`),
  CONSTRAINT `fk_TB_Teacher_has_Discipline_teacher1`
    FOREIGN KEY (`TB_Teacher_User_ID`)
    REFERENCES `get_poc`.`tb_teacher` (`TB_User_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `get_poc`.`tb_user_manages_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `get_poc`.`tb_user_manages_user` (
  `TB_User_Administrator_ID` VARCHAR(100) NOT NULL,
  `TB_User_Administered_ID` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`TB_User_Administrator_ID`, `TB_User_Administered_ID`),
  INDEX `fk_TB_User_Manages_User_user2_idx` (`TB_User_Administered_ID` ASC) VISIBLE,
  INDEX `fk_TB_User_Manages_User_user1_idx` (`TB_User_Administrator_ID` ASC) VISIBLE,
  CONSTRAINT `fk_TB_User_Manages_User_user1`
    FOREIGN KEY (`TB_User_Administrator_ID`)
    REFERENCES `get_poc`.`tb_user` (`ID`),
  CONSTRAINT `fk_TB_User_Manages_User_user2`
    FOREIGN KEY (`TB_User_Administered_ID`)
    REFERENCES `get_poc`.`tb_user` (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
