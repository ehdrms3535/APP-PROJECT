-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: account_db
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `checkpoint`
--

DROP TABLE IF EXISTS `checkpoint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkpoint` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `name` varchar(100) NOT NULL,
  `number` int NOT NULL,
  `deleted` bit(1) NOT NULL,
  `map_config_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2ynlnmsxb34klxr633uj1f29i` (`number`),
  KEY `FKihmif3jc9sn6mpwx6ltjcy91i` (`map_config_id`),
  CONSTRAINT `FKihmif3jc9sn6mpwx6ltjcy91i` FOREIGN KEY (`map_config_id`) REFERENCES `map_config` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkpoint`
--

LOCK TABLES `checkpoint` WRITE;
/*!40000 ALTER TABLE `checkpoint` DISABLE KEYS */;
INSERT INTO `checkpoint` VALUES (21,35.870878578461465,128.60222100059266,'1',1,_binary '',NULL),(22,35.86957446767286,128.601877885187,'2',2,_binary '',NULL),(23,35.8690702057472,128.60415102474946,'3',3,_binary '',NULL),(24,35.870409101050136,128.60425824831373,'4',4,_binary '',NULL),(25,35.872531983485565,128.60632130066284,'7',7,_binary '',NULL),(26,35.87264500305143,128.6075222045826,'8',8,_binary '',NULL),(27,35.87146637221314,128.6063951094507,'11',11,_binary '',1),(28,35.86993079090791,128.60642282892317,'12',12,_binary '',1),(29,35.86947869619083,128.60874022506644,'13',13,_binary '',1),(30,35.871408774866964,128.60872949638056,'14',14,_binary '',1),(31,35.86890857037621,128.60646352734793,'16',16,_binary '',1),(32,35.8691433138748,128.6040710304037,'17',17,_binary '\0',1),(33,35.8673874979792,128.60367628895108,'18',18,_binary '\0',1),(34,35.86677888880307,128.60619753012543,'19',19,_binary '\0',1),(35,35.83497091578008,128.74552792217835,'20',20,_binary '\0',NULL),(39,35.86953656663211,128.6021338109369,'112',112,_binary '\0',NULL),(40,35.870341110818295,128.60426035331668,'55',55,_binary '\0',NULL);
/*!40000 ALTER TABLE `checkpoint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `name` varchar(255) NOT NULL,
  `owner_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKhyl13wj2rpoe8p7d2v8cg3y4e` (`name`),
  KEY `FK79j5wnqiu1y2a3csjvmwv20hj` (`owner_id`),
  CONSTRAINT `FK79j5wnqiu1y2a3csjvmwv20hj` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `map_config`
--

DROP TABLE IF EXISTS `map_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `map_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `geo_json` text,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `map_config`
--

LOCK TABLES `map_config` WRITE;
/*!40000 ALTER TABLE `map_config` DISABLE KEYS */;
INSERT INTO `map_config` VALUES (1,'2025-05-27 20:42:04.275062','{\"type\":\"FeatureCollection\",\"features\":[]}','11'),(6,'2025-05-29 23:54:23.578248','{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[128.624496,35.877539],[128.674277,35.864048],[128.665694,35.843879],[128.703288,35.846661],[128.643207,35.856398]]}}]}','22'),(7,'2025-06-13 00:46:19.078080','{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[128.617102,35.870699],[128.64594,35.855677],[128.648515,35.8764],[128.618132,35.87348],[128.761982,35.835784]]}}]}','7');
/*!40000 ALTER TABLE `map_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` bigint DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,1,'1','12'),(2,1,'1','123'),(3,12,'1','123123123121'),(4,12,'1','313'),(5,123,'1','123131'),(6,12,'1','13213213');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reference`
--

DROP TABLE IF EXISTS `reference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reference` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `description` text NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `title` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reference`
--

LOCK TABLES `reference` WRITE;
/*!40000 ALTER TABLE `reference` DISABLE KEYS */;
/*!40000 ALTER TABLE `reference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `road_info`
--

DROP TABLE IF EXISTS `road_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `road_info` (
  `id` bigint NOT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `osm_id` varchar(255) DEFAULT NULL,
  `preference` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `road_info`
--

LOCK TABLES `road_info` WRITE;
/*!40000 ALTER TABLE `road_info` DISABLE KEYS */;
INSERT INTO `road_info` VALUES (1,'12','way/902569335',5),(2,'1','way/53452870',5),(52,'bad','way/1087527712',1),(53,'good','way/37814274',5),(54,'bad','way/32895814',1),(56,'good','way/1087527712',5),(57,'good','way/826105696',5),(58,'good','way/32895899',5),(102,'bad','way/384173075',1),(103,'good','way/384173078',5),(104,'5','way/53452870',5),(105,'5','way/384173078',5),(152,'12','way/1087527712',5),(202,'2342342','way/1219921972',2);
/*!40000 ALTER TABLE `road_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `road_info_seq`
--

DROP TABLE IF EXISTS `road_info_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `road_info_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `road_info_seq`
--

LOCK TABLES `road_info_seq` WRITE;
/*!40000 ALTER TABLE `road_info_seq` DISABLE KEYS */;
INSERT INTO `road_info_seq` VALUES (301);
/*!40000 ALTER TABLE `road_info_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `groupid` int NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,0,'1','1'),(2,0,'2','2');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_group` (
  `user_id` bigint NOT NULL,
  `group_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`),
  KEY `FK71ue7uuqo0neoodkx7smf7e3o` (`group_id`),
  CONSTRAINT `FK1vm7hjlu1n79o7y4ga4091dj6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK71ue7uuqo0neoodkx7smf7e3o` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group`
--

LOCK TABLES `user_group` WRITE;
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

UNLOCK TABLES;

-- 테스트용 계정 추가
INSERT INTO `user` (id, groupid, password, username) VALUES (3, 0, '1234', 'testuser');

-- Dump completed on 2025-06-14 22:33:00


-- Dump completed on 2025-06-14 22:33:00
