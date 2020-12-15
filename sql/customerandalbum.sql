DROP SCHEMA IF EXISTS `customer-to-album`;

CREATE SCHEMA `customer-to-album`;

use `customer-to-album`;

SET FOREIGN_KEY_CHECKS = 0;





DROP TABLE IF EXISTS `album`;

CREATE TABLE `album` (
   `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `image` varchar(2000) DEFAULT NULL,
  `time` integer NOT NULL,
  PRIMARY KEY (`id`)
  
);


DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `image` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `customer_album`;

CREATE TABLE `customer_album` (
  `customer_id` varchar(255) NOT NULL,
  `album_id` varchar(255) NOT NULL,
  
  PRIMARY KEY (`customer_id`,`album_id`),
  
  
  CONSTRAINT `FK_ALBUM` FOREIGN KEY (`album_id`) 
  REFERENCES `album` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_CUSTOMER` FOREIGN KEY (`customer_id`) 
  REFERENCES `customer` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
);

SET FOREIGN_KEY_CHECKS = 1;
