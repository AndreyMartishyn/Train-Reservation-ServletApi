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
  FOREIGN KEY (`train_id`) REFERENCES `trains` (`id`),
  FOREIGN KEY (`station_id`) REFERENCES `stations` (`id`)
)
INSERT INTO route_stations VALUES
(114, 125, 2, '2022-05-27 15:00:00', '2022-05-27 15:10:00'),
(114, 125, 3, '2022-05-27 17:00:00', '2022-05-27 17:20:00'),
(114, 125, 4, '2022-05-27 19:00:00', '2022-05-27 19:20:00'),
(114, 125, 5, '2022-05-29 21:00:00', '2022-05-29 21:20:00'),
(114, 125, 6, '2022-05-29 23:30:00', '2022-05-29 23:50:00'),
(411, 125, 6, '2022-05-29 23:50:00', '2022-05-30 00:05:00'),
(411, 125, 5, '2022-05-30 02:00:00', '2022-05-30 02:20:00'),
(411, 125, 4, '2022-05-30 04:00:00', '2022-05-30 04:20:00'),
(411, 125, 3, '2022-05-30 06:00:00', '2022-05-30 06:20:00'),
(411, 125, 2, '2022-05-30 08:30:00', '2022-05-30 08:40:00');
--
-- Table structure for table `stations`
--
DROP TABLE IF EXISTS `stations`;
CREATE TABLE `stations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(55) NOT NULL,
  `code` varchar(55) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
)
INSERT INTO stations (name, code) VALUES
("Ivano-Frankivsk", "IFR"),
("Lviv", "LVV"),
("Brody", "BDY"),
("Kryvyn", "KVN"),
("Shepetivka", "SPK"),
("Kyiv", "KVP");
--
-- Table structure for table `train_models`
--
DROP TABLE IF EXISTS `train_models`;
CREATE TABLE `train_models` (
  `id` int NOT NULL AUTO_INCREMENT
  `model` varchar(55) NOT NULL,
  PRIMARY KEY (`id`)
)
INSERT INTO stations (model) VALUES
("Tarpan"),
("Hyundai Rotem"),
("Skoda EJ-675");
--
-- Table structure for table `trains`
--
DROP TABLE IF EXISTS `trains`;
CREATE TABLE `trains` (
  `id` int NOT NULL ,
  `model_id` int NOT NULL,
  PRIMARY KEY (`id`),
   FOREIGN KEY (`model_id`) REFERENCES `train_models` (`id`)
)
INSERT INTO trains (id, model_id) VALUES
(200, 1),
(125, 2),
(300, 3),
--
-- Table structure for table `train_coach`
--
DROP TABLE IF EXISTS `train_wagons`;
CREATE TABLE train_wagons(
wagon_id INT NOT NULL,
route_id INT NOT NULL,
comfort_class ENUM ('FIRST', 'SECOND') NOT NULL,
seats INT NOT NULL UNSIGNED,
price INT NOT NULL UNSIGNED,
PRIMARY KEY(wagon_id),
FOREIGN KEY (route_id) REFERENCES route_stations(id),
INSERT INTO train_wagons (wagon_id, route_id, comfort_class, seats, price) VALUES
(2, 114, 'FIRST', 45, 80),
(5, 114, 'FIRST', 40, 80),
(7, 114, 'SECOND', 60, 50),
(4, 114, 'SECOND', 60, 50),

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
INSERT INTO users (id, first_name, last_name, pass_encoded, email, role) VALUES
--encoded password is 'password1' as decoded, for login purposes
(1, ADMIN, ADMIN, '846d29aa0bb20f3665d1b5b558b8b7bdf34537b8d98694881b1c42566ee2a7fd', admin@gmail.com, 'ADMIN'),
(1, First, Customer, '846d29aa0bb20f3665d1b5b558b8b7bdf34537b8d98694881b1c42566ee2a7fd', customer@gmail.com, 'CUSTOMER')
--
-- Table structure for table `tickets`
--
create table tickets(
id INT NOT NULL AUTO_INCREMENT,
user_id INT NOT NULL,
train_id INT NOT NULL,
first_name varchar(55) NOT NULL,
last_name varchar(55) NOT NULL,
departure_station INT NOT NULL,
departure_time varchar(55) NOT NULL,
arrival_station INT NOT NULL,
arrival_time varchar(55) NOT NULL,
place INT NOT NULL,
wagon_id INT NOT NULL,
price INT NOT NULL,
isPaid bit,
duration varchar(55),
PRIMARY KEY(id),
FOREIGN KEY(user_id) REFERENCES users(id),
FOREIGN KEY(wagon_id) REFERENCES train_wagons(wagon_id)),
FOREIGN KEY(train_id) REFERENCES trains(id),
FOREIGN KEY(departure_station) REFERENCES stations(id),
FOREIGN KEY(arrival_station) REFERENCES stations(id))

