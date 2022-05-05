CREATE DATABASE  IF NOT EXISTS `traindb`;
USE `traindb`;
--
-- Table structure for table `route_stations`
--
DROP TABLE IF EXISTS `route_stations`;
CREATE TABLE `route_stations` (
  `id` int NOT NULL,
  `train_id` int NOT NULL,
  `station_id` int NOT NULL,
  `arrival` datetime NOT NULL,
  `departure` datetime NOT NULL,
  KEY `train_id` (`train_id`),
  KEY `station_id` (`station_id`),
  CONSTRAINT `route_stations_ibfk_1` FOREIGN KEY (`train_id`) REFERENCES `trains` (`id`),
  CONSTRAINT `route_stations_ibfk_2` FOREIGN KEY (`station_id`) REFERENCES `stations` (`id`)
)
--
-- Table structure for table `stations`
--
DROP TABLE IF EXISTS `stations`;
CREATE TABLE `stations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(55) NOT NULL,
  `code` varchar(55) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
)
--
-- Table structure for table `train_models`
--
DROP TABLE IF EXISTS `train_models`;
CREATE TABLE `train_models` (
  `id` int NOT NULL AUTO_INCREMENT,
  `model` varchar(55) NOT NULL,
  PRIMARY KEY (`id`)
)
--
-- Table structure for table `trains`
--
DROP TABLE IF EXISTS `trains`;
CREATE TABLE `trains` (
  `id` int NOT NULL AUTO_INCREMENT,
  `model_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `model_id` (`model_id`),
  CONSTRAINT `trains_ibfk_1` FOREIGN KEY (`model_id`) REFERENCES `train_models` (`id`)
)
--
-- Table structure for table `users`
--
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(55) NOT NULL,
  `last_name` varchar(55) NOT NULL,
  `pass_encoded` varchar(255)  NOT NULL,
  `email` varchar(55) NOT NULL,
  `role` varchar(50)  NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
)

