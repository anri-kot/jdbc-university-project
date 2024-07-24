-- MariaDB dump 10.19  Distrib 10.4.32-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: university
-- ------------------------------------------------------
-- Server version	10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auditlog_professors`
--

DROP TABLE IF EXISTS `auditlog_professors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditlog_professors` (
  `idlog_pr` int(11) NOT NULL AUTO_INCREMENT,
  `_user` int(11) DEFAULT NULL,
  `_pr_matr` int(11) DEFAULT NULL,
  `action` varchar(130) DEFAULT NULL,
  `action_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`idlog_pr`),
  KEY `fk_logpr_professors_idx` (`_pr_matr`),
  KEY `fk_logpr_users_idx` (`_user`),
  CONSTRAINT `fk_logpr_professors` FOREIGN KEY (`_pr_matr`) REFERENCES `professors` (`pr_matr`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_logpr_users` FOREIGN KEY (`_user`) REFERENCES `users` (`idUser`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditlog_professors`
--

LOCK TABLES `auditlog_professors` WRITE;
/*!40000 ALTER TABLE `auditlog_professors` DISABLE KEYS */;
INSERT INTO `auditlog_professors` VALUES (1,1,10,'Updated Professor','2024-07-23 21:30:33'),(2,1,3,'Updated Professor','2024-07-23 21:40:10'),(3,1,11,'Added new Professor','2024-07-23 21:40:58'),(4,1,10,'Updated Professor','2024-07-23 22:06:13');
/*!40000 ALTER TABLE `auditlog_professors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditlog_students`
--

DROP TABLE IF EXISTS `auditlog_students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditlog_students` (
  `idlog_st` int(11) NOT NULL AUTO_INCREMENT,
  `_user` int(11) DEFAULT NULL,
  `_st_matr` int(11) DEFAULT NULL,
  `action` varchar(130) DEFAULT NULL,
  `action_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`idlog_st`),
  KEY `fk_logst_users_idx` (`_user`),
  KEY `fk_logst_students_idx` (`_st_matr`),
  CONSTRAINT `fk_logst_students` FOREIGN KEY (`_st_matr`) REFERENCES `students` (`st_matr`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_logst_users` FOREIGN KEY (`_user`) REFERENCES `users` (`idUser`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditlog_students`
--

LOCK TABLES `auditlog_students` WRITE;
/*!40000 ALTER TABLE `auditlog_students` DISABLE KEYS */;
INSERT INTO `auditlog_students` VALUES (1,3,3,'testing','2024-07-17 22:27:38'),(2,1,2,'Updated Student','2024-07-20 21:53:57'),(4,1,NULL,'Updated Student','2024-07-20 22:27:33'),(5,3,23,'Updated Student','2024-07-20 22:36:15'),(6,3,24,'Added new Student','2024-07-22 01:54:27'),(7,1,8,'Updated Student','2024-07-22 03:09:25'),(8,1,24,'Updated Student','2024-07-23 02:14:22');
/*!40000 ALTER TABLE `auditlog_students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classes`
--

DROP TABLE IF EXISTS `classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `classes` (
  `idClass` varchar(3) NOT NULL,
  `semester` int(1) DEFAULT NULL,
  `Courses_idCourse` int(11) NOT NULL,
  PRIMARY KEY (`idClass`),
  KEY `fk_Classes_Courses_idx` (`Courses_idCourse`),
  CONSTRAINT `fk_Classes_Courses` FOREIGN KEY (`Courses_idCourse`) REFERENCES `courses` (`idCourse`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classes`
--

LOCK TABLES `classes` WRITE;
/*!40000 ALTER TABLE `classes` DISABLE KEYS */;
INSERT INTO `classes` VALUES ('1A',1,1),('1B',1,2),('1C',1,3),('2A',2,1),('2B',2,2),('2C',2,3);
/*!40000 ALTER TABLE `classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses` (
  `idCourse` int(11) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(50) NOT NULL,
  `c_duration` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCourse`),
  UNIQUE KEY `c_name_UNIQUE` (`c_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Data Science',200),(2,'Computer Engineering',250),(3,'Software Development',150);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrollments`
--

DROP TABLE IF EXISTS `enrollments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enrollments` (
  `idEnrollment` int(11) NOT NULL AUTO_INCREMENT,
  `en_year` int(4) DEFAULT NULL,
  `Students_st_matr` int(11) NOT NULL,
  `Classes_idClass` varchar(3) NOT NULL,
  PRIMARY KEY (`idEnrollment`),
  KEY `fk_Enrollments_Students1_idx` (`Students_st_matr`),
  KEY `fk_Enrollments_Classes1_idx` (`Classes_idClass`),
  CONSTRAINT `fk_Enrollments_Classes1` FOREIGN KEY (`Classes_idClass`) REFERENCES `classes` (`idClass`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Enrollments_Students1` FOREIGN KEY (`Students_st_matr`) REFERENCES `students` (`st_matr`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollments`
--

LOCK TABLES `enrollments` WRITE;
/*!40000 ALTER TABLE `enrollments` DISABLE KEYS */;
INSERT INTO `enrollments` VALUES (3,2020,1,'1A'),(4,2020,2,'1B'),(5,2021,3,'2B'),(6,2022,4,'2B'),(8,2020,6,'2C'),(9,2020,7,'1C'),(10,2021,8,'2C'),(11,2011,5,'2B'),(12,2021,5,'2A'),(13,2023,5,'1C'),(14,2021,1,'1C'),(15,2021,9,'1C'),(16,2023,10,'1A'),(17,2020,4,'1C'),(18,2022,1,'2B'),(19,2012,11,'1A'),(20,2024,13,'1C'),(23,2023,18,'1C'),(24,2023,15,'2A');
/*!40000 ALTER TABLE `enrollments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grades`
--

DROP TABLE IF EXISTS `grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grades` (
  `idGrade` int(11) NOT NULL AUTO_INCREMENT,
  `Students_st_matr` int(11) NOT NULL,
  `grade` double DEFAULT NULL,
  `_idClass` varchar(3) NOT NULL,
  PRIMARY KEY (`idGrade`),
  KEY `fk_Grades_Students1_idx` (`Students_st_matr`),
  KEY `_idClass` (`_idClass`),
  CONSTRAINT `fk_Grades_Students1` FOREIGN KEY (`Students_st_matr`) REFERENCES `students` (`st_matr`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `grades_ibfk_1` FOREIGN KEY (`_idClass`) REFERENCES `classes` (`idClass`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grades`
--

LOCK TABLES `grades` WRITE;
/*!40000 ALTER TABLE `grades` DISABLE KEYS */;
INSERT INTO `grades` VALUES (1,1,10,'1A'),(2,2,7,'2A'),(3,3,4,'1B'),(4,4,5.5,'2B'),(5,5,6.8,'1C'),(6,6,9.5,'2C'),(7,7,8.5,'1A');
/*!40000 ALTER TABLE `grades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professors`
--

DROP TABLE IF EXISTS `professors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professors` (
  `pr_matr` int(11) NOT NULL AUTO_INCREMENT,
  `pr_name` varchar(75) NOT NULL,
  `pr_address` varchar(100) DEFAULT NULL,
  `pr_phone` varchar(11) DEFAULT NULL,
  `pr_ssn` varchar(11) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `Courses_idCourse` int(11) NOT NULL,
  PRIMARY KEY (`pr_matr`),
  UNIQUE KEY `pr_ssn_UNIQUE` (`pr_ssn`),
  KEY `fk_Professors_Courses1_idx` (`Courses_idCourse`),
  CONSTRAINT `fk_Professors_Courses1` FOREIGN KEY (`Courses_idCourse`) REFERENCES `courses` (`idCourse`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professors`
--

LOCK TABLES `professors` WRITE;
/*!40000 ALTER TABLE `professors` DISABLE KEYS */;
INSERT INTO `professors` VALUES (1,'Nakabachi Makise','Japan','12312312312','11111222222',3500,1),(3,'Machado de Assis','Brazil','31231231231','31231231231',5000,3),(4,'Ed Sheeran','Canada','77885599441','77445522115',5150.5,3),(6,'Mr. Beast','Australia','11554847852','99565622315',10000,2),(9,'Maasaki Yuasa','Nagasaki, Japan','12452515152','19854213254',12999.99,1),(10,'Test3','Test3','95195195195','15487845126',12452,2),(11,'Jean Paul','Texas','55212145121','32654812456',2599.99,3);
/*!40000 ALTER TABLE `professors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `students` (
  `st_matr` int(11) NOT NULL AUTO_INCREMENT,
  `st_name` varchar(75) NOT NULL,
  `st_address` varchar(100) DEFAULT NULL,
  `st_phone` varchar(11) DEFAULT NULL,
  `st_ssn` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`st_matr`),
  UNIQUE KEY `new_ssn_UNIQUE` (`st_ssn`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'Robert William','Texas','11111111111','11111111111'),(2,'Marcos Augustus','Brazil','22222222222','22222222222'),(3,'Bianca Menezes','Mexico','33333333333','33333333333'),(4,'Lucia Takagawa','Japan','41414141414','44444444444'),(5,'Luke Stenford','Britain','55555555555','55115511551'),(6,'Eirin Loren','Australia','55554444545','55555555555'),(7,'Yuki Minase','Nagasaki, Japan','66666666666','66666666666'),(8,'Lucas Miguel','Texas','71717171717','71717171717'),(9,'Mariana Galvao Santos','Brazil','6185525512','81818181881'),(10,'Michael Reeves','LA, California','36521445212','12654385214'),(11,'John Titor','Peru','45612378945','95195195195'),(13,'Max Webber','Germany','85649421657','98564321598'),(14,'Satoru Gojo','Osaka, Japan','78546123584','95195195555'),(15,'Natasha Poklonskaya','Berlin, Russia','85462358421','98756485321'),(17,'Natasha Lynch','Argentina','85456215648','95845621546'),(18,'Pablo Picasso','Australia','88554124615','88745213059'),(19,'Camila Williams','Rome','21546523145','21547856951'),(23,'Joao Guilherme','Rio de Janeiro','11545254658','95326512458'),(24,'Caio Diaz','Rio de Janeiro','14556985321','14568523212');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `u_name` varchar(75) DEFAULT NULL,
  `admin` tinyint(4) NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','123','admin@email.com','Administrator',1),(2,'nonadmin','123','nonadmin@email.com','Not Administrator',0),(3,'admin2','123','admin2@email.com','Administrator 2',1),(5,'satoru','coxinha','satoru@gmail.com','Satoru.',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-24 16:35:18
