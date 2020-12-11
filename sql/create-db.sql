DROP SCHEMA IF EXISTS `customer-to-album`;

CREATE SCHEMA `customer-to-album`;

use `customer-to-album`;
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
stu_id INT AUTO_INCREMENT,
NAME VARCHAR(30),
age INT ,
class VARCHAR(50),
address VARCHAR(100),
PRIMARY KEY(stu_id)
);
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`(
cour_id INT AUTO_INCREMENT,
NAME VARCHAR(50),
CODE VARCHAR(30),
PRIMARY KEY(cour_id)
);
/**学生课程关联表*/
CREATE TABLE `customer_course`(
sc_id INT AUTO_INCREMENT,
stu_id INT ,
cour_id INT,
PRIMARY KEY(sc_id)
);


ALTER TABLE `customer_course` ADD CONSTRAINT stu_FK1 FOREIGN KEY(stu_id) REFERENCES student(stu_id);
ALTER TABLE Stu_Cour ADD CONSTRAINT cour_FK2 FOREIGN KEY(cour_id) REFERENCES Course(cour_id)
