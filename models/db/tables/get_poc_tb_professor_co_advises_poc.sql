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
-- Table structure for table `tb_professor_co_advises_poc`
--

DROP TABLE IF EXISTS `tb_professor_co_advises_poc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_professor_co_advises_poc` (
  `TB_POC_ID` int NOT NULL AUTO_INCREMENT,
  `tb_teacher_TB_User_ID` varchar(100) NOT NULL,
  PRIMARY KEY (`TB_POC_ID`,`tb_teacher_TB_User_ID`),
  KEY `fk_TB_Teacher_Co-advises_POC_POC1_idx` (`TB_POC_ID`),
  KEY `fk_tb_teacher_co-advises_poc_tb_teacher1_idx` (`tb_teacher_TB_User_ID`),
  CONSTRAINT `fk_TB_Teacher_Co-advises_POC_POC1` FOREIGN KEY (`TB_POC_ID`) REFERENCES `tb_poc` (`ID`),
  CONSTRAINT `fk_tb_teacher_co-advises_poc_teacher1` FOREIGN KEY (`tb_teacher_TB_User_ID`) REFERENCES `tb_professor` (`TB_User_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_professor_co_advises_poc`
--

LOCK TABLES `tb_professor_co_advises_poc` WRITE;
/*!40000 ALTER TABLE `tb_professor_co_advises_poc` DISABLE KEYS */;
INSERT INTO `tb_professor_co_advises_poc` VALUES (2,'Fabricio'),(2,'nacif');
/*!40000 ALTER TABLE `tb_professor_co_advises_poc` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-14 13:59:47
