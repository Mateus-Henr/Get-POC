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
-- Table `Get_POC`.`TB_Area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`TB_Area` (
  `ID` INT NOT NULL,
  `Name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`TB_PDF`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`TB_PDF` (
  `ID` INT NOT NULL,
  `Creation_Date` DATE NOT NULL,
  `Content` LONGTEXT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`TB_User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`TB_User` (
  `ID` VARCHAR(100) NOT NULL,
  `Password` VARCHAR(100) NOT NULL,
  `Name` VARCHAR(100) NOT NULL,
  `Type` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`TB_Teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`TB_Teacher` (
  `Email` VARCHAR(100) NOT NULL,
  `TB_User_ID` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`TB_User_ID`),
  CONSTRAINT `fk_teacher_user1`
    FOREIGN KEY (`TB_User_ID`)
    REFERENCES `Get_POC`.`TB_User` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`TB_POC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`TB_POC` (
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
    REFERENCES `Get_POC`.`TB_Area` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_POC_PDF1`
    FOREIGN KEY (`TB_PDF_ID`)
    REFERENCES `Get_POC`.`TB_PDF` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_POC_teacher1`
    FOREIGN KEY (`Teacher_Registrant`)
    REFERENCES `Get_POC`.`TB_Teacher` (`TB_User_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_POC_teacher2`
    FOREIGN KEY (`Teacher_Advisor`)
    REFERENCES `Get_POC`.`TB_Teacher` (`TB_User_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`TB_Student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`TB_Student` (
  `Email` VARCHAR(100) NOT NULL,
  `Registration` VARCHAR(100) NOT NULL,
  `POC` INT NULL,
  `TB_User_ID` VARCHAR(100) NOT NULL,
  INDEX `fk_TB_Student_POC1_idx` (`POC` ASC) VISIBLE,
  PRIMARY KEY (`TB_User_ID`),
  UNIQUE INDEX `Registration_UNIQUE` (`Registration` ASC) VISIBLE,
  CONSTRAINT `fk_TB_Student_POC1`
    FOREIGN KEY (`POC`)
    REFERENCES `Get_POC`.`TB_POC` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_Student_user1`
    FOREIGN KEY (`TB_User_ID`)
    REFERENCES `Get_POC`.`TB_User` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`TB_Discipline`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`TB_Discipline` (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(100) NOT NULL,
  `Description` TINYTEXT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`TB_Keyword`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`TB_Keyword` (
  `ID` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`POC_has_Keyword
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `Get_POC`.`TB_POC_has_Keyword` (
  `TB_POC_ID` INT NOT NULL,
  `TB_Keyword_ID` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`TB_POC_ID`, `TB_Keyword_ID`),
  INDEX `fk_TB_POC_has_Keyword_keyword1_idx` (`TB_Keyword_ID` ASC) VISIBLE,
  INDEX `fk_TB_POC_has_Keyword_POC1_idx` (`TB_POC_ID` ASC) VISIBLE,
  CONSTRAINT `TB_fk_POC_has_Keyword_POC1`
    FOREIGN KEY (`TB_POC_ID`)
    REFERENCES `Get_POC`.`TB_POC` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_POC_has_Keyword_keyeword1`
    FOREIGN KEY (`TB_Keyword_ID`)
    REFERENCES `Get_POC`.`TB_Keyword` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Get_POC`.`TB_Teacher_has_Discipline`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`TB_Teacher_has_Discipline` (
  `TB_Discipline_ID` INT UNSIGNED NOT NULL,
  `TB_Teacher_User_ID` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`TB_Discipline_ID`, `TB_Teacher_User_ID`),
  INDEX `fk_TB_Teacher_has_Discipline_teacher1_idx` (`TB_Teacher_User_ID` ASC) VISIBLE,
  INDEX `fk_TB_Teacher_has_Discipline_discipline1_idx` (`TB_Discipline_ID` ASC) VISIBLE,
  CONSTRAINT `fk_TB_Teacher_has_Discipline_discipline1`
    FOREIGN KEY (`TB_Discipline_ID`)
    REFERENCES `Get_POC`.`TB_Discipline` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_Teacher_has_Discipline_teacher1`
    FOREIGN KEY (`TB_Teacher_User_ID`)
    REFERENCES `Get_POC`.`TB_Teacher` (`TB_User_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`TB_Teacher_Co-advises_POC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`TB_Teacher_Co-advises_POC` (
  `TB_Teacher_User_ID` VARCHAR(100) NOT NULL,
  `TB_POC_ID` INT NOT NULL,
  PRIMARY KEY (`TB_Teacher_User_ID`, `TB_POC_ID`),
  INDEX `fk_TB_Teacher_Co-advises_POC_POC1_idx` (`TB_POC_ID` ASC) VISIBLE,
  INDEX `fk_TB_Teacher_Co-advises_POC_teacher1_idx` (`TB_Teacher_User_ID` ASC) VISIBLE,
  CONSTRAINT `fk_TB_Teacher_Co-advises_POC_teacher1`
    FOREIGN KEY (`TB_Teacher_User_ID`)
    REFERENCES `Get_POC`.`TB_Teacher_ID` (`TB_User_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_Teacher_Co-advises_POC_POC1`
    FOREIGN KEY (`TB_POC_ID`)
    REFERENCES `Get_POC`.`TB_POC` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Get_POC`.`TB_User_Manages_User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Get_POC`.`TB_User_Manages_User` (
  `TB_User_Administrator_ID` VARCHAR(100),
  `TB_User_Administered_ID` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`TB_User_Administrator_ID`, `TB_User_Administered_ID`),
  INDEX `fk_TB_User_Manages_User_user2_idx` (`TB_User_Administered_ID` ASC) VISIBLE,
  INDEX `fk_TB_User_Manages_User_user1_idx` (`TB_User_Administrator_ID` ASC) VISIBLE,
  CONSTRAINT `fk_TB_User_Manages_User_user1`
    FOREIGN KEY (`TB_User_Administrator_ID`)
    REFERENCES `Get_POC`.`TB_User` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_User_Manages_User_user2`
    FOREIGN KEY (`TB_User_Administered_ID`)
    REFERENCES `Get_POC`.`TB_User` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
