-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: get_poc
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_poc`
--

DROP TABLE IF EXISTS `tb_poc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_poc` (
  `ID` int NOT NULL,
  `Title` varchar(100) NOT NULL,
  `Defense_Date` date NOT NULL,
  `Summary` tinytext NOT NULL,
  `TB_Area_ID` int NOT NULL,
  `TB_PDF_ID` int NOT NULL,
  `Teacher_Registrant` varchar(100) NOT NULL,
  `Teacher_Advisor` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_TB_POC_area1_idx` (`TB_Area_ID`),
  KEY `fk_TB_POC_PDF1_idx` (`TB_PDF_ID`),
  KEY `fk_TB_POC_teacher1_idx` (`Teacher_Registrant`),
  KEY `fk_TB_POC_teacher2_idx` (`Teacher_Advisor`),
  CONSTRAINT `fk_TB_POC_area1` FOREIGN KEY (`TB_Area_ID`) REFERENCES `tb_area` (`ID`),
  CONSTRAINT `fk_TB_POC_PDF1` FOREIGN KEY (`TB_PDF_ID`) REFERENCES `tb_pdf` (`ID`),
  CONSTRAINT `fk_TB_POC_teacher1` FOREIGN KEY (`Teacher_Registrant`) REFERENCES `tb_teacher` (`TB_User_ID`),
  CONSTRAINT `fk_TB_POC_teacher2` FOREIGN KEY (`Teacher_Advisor`) REFERENCES `tb_teacher` (`TB_User_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_poc`
--

LOCK TABLES `tb_poc` WRITE;
/*!40000 ALTER TABLE `tb_poc` DISABLE KEYS */;
INSERT INTO `tb_poc` VALUES (2,'POC 2','1999-04-04','Lorem ipsum leo dictumst aptent hendrerit ipsum rutrum dolor tincidunt sodales quis, condimentum sem viverra per a condimentum volutpat justo magna justo. vestibulum ornare nam cubilia sem, quam suspendisse ultricies. ',2,1,'nacif','fabricio'),(3,'Algoritmos estruturados baseados em X','1999-04-04','Summary 3',3,1,'nacif','fabricio');
/*!40000 ALTER TABLE `tb_poc` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-09  8:06:56
