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
INSERT INTO route_stations VALUES
(114, 125, 2, '2022-04-27 15:00:00', '2022-04-27 15:10:00'),
(114, 125, 3, '2022-04-27 17:00:00', '2022-04-27 17:20:00'),
(114, 125, 4, '2022-04-27 19:00:00', '2022-04-27 19:20:00'),
(114, 125, 5, '2022-04-29 21:00:00', '2022-04-29 21:20:00'),
(114, 125, 6, '2022-04-29 23:30:00', '2022-04-29 23:50:00'),
(411, 125, 6, '2022-04-29 23:50:00', '2022-04-30 00:05:00'),
(411, 125, 5, '2022-04-30 02:00:00', '2022-04-30 02:20:00'),
(411, 125, 4, '2022-04-30 04:00:00', '2022-04-30 04:20:00'),
(411, 125, 3, '2022-04-30 06:00:00', '2022-04-30 06:20:00'),
(411, 125, 2, '2022-04-30 08:30:00', '2022-04-30 08:40:00');
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
-- Table structure for table `train_coach`
--
DROP TABLE IF EXISTS `train_coaches`;
CREATE TABLE train_coaches(
coach_id INT NOT NULL,
route_id INT NOT NULL,
comfort_class ENUM ('FIRST', 'SECOND'),
seats INT NOT NULL,
price INT NOT NULL,
PRIMARY KEY(coach_id));
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
--
-- Table structure for table `tickets`
--
create table tickets(
id INT NOT NULL,
user_id INT NOT NULL,
train_id INT NOT NULL,
first_name varchar(255) NOT NULL,
last_name varchar(255) NOT NULL,
departure_station varchar(255) NOT NULL,
departure_time varchar(255) NOT NULL,
arrival_station varchar(255) NOT NULL,
arrival_time varchar(255) NOT NULL,
place INT NOT NULL,
wagon INT NOT NULL,
comfort_class varchar(255) NOT NULL,
price INT NOT NULL,
isPaid bit,
PRIMARY KEY(id),
 foreign key (user_id) references users(id),
 foreign key (wagon) references train_coaches(coach_id))
