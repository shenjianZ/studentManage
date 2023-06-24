/*
Navicat MySQL Data Transfer

Source Server         : mydemo
Source Server Version : 50738
Source Host           : localhost:3306
Source Database       : db_student

Target Server Type    : MYSQL
Target Server Version : 50738
File Encoding         : 65001

Date: 2023-06-11 16:45:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for schoolclass
-- ----------------------------
DROP TABLE IF EXISTS `schoolclass`;
CREATE TABLE `schoolclass` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `className` varchar(20) DEFAULT NULL,
  `classDesc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schoolclass
-- ----------------------------
INSERT INTO `schoolclass` VALUES ('12', '软件工程', '软件班级');
INSERT INTO `schoolclass` VALUES ('14', '软件工程2班', '软件工程是一门研究用工程化方法构建和维护有效的、实用的和高质量的软件的学科。它涉及程序设计语言、数据库、软件开发工具、系统平台、标准、设计模式等方面。在现代社会中，软件应用于多个方面。典型的软件有电子邮件、嵌入式系统、人机...');
INSERT INTO `schoolclass` VALUES ('15', '数科11班', 'Aaaa');
INSERT INTO `schoolclass` VALUES ('16', '', '');

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(20) NOT NULL,
  `student_name` varchar(50) NOT NULL,
  `chinese` float(5,2) DEFAULT NULL,
  `math` float(5,2) DEFAULT NULL,
  `english` float(5,2) DEFAULT NULL,
  `total` float(5,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES ('1', '001', '张三', '90.50', '85.20', '92.00', '267.70');
INSERT INTO `score` VALUES ('2', '002', '李四', '85.00', '90.50', '78.00', '253.50');
INSERT INTO `score` VALUES ('3', '003', '王五', '92.50', '88.50', '94.00', '275.00');
INSERT INTO `score` VALUES ('4', '004', '赵六', '78.50', '82.00', '83.50', '244.00');
INSERT INTO `score` VALUES ('5', '005', '钱七', '87.00', '91.50', '89.00', '267.50');
INSERT INTO `score` VALUES ('6', '006', '孙八', '93.50', '95.00', '96.50', '285.00');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `sn` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `dept` varchar(128) DEFAULT NULL,
  `classId` int(11) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `classId` (`classId`) USING BTREE,
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`classId`) REFERENCES `schoolclass` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('5', '张三', '11162656', '男', '计算机学院', '12', '上海市徐汇区肇嘉浜路101号');
INSERT INTO `student` VALUES ('6', '李四', '111324354', '男', '计算机学院', '14', '世纪大道11号');
INSERT INTO `student` VALUES ('7', '王五', '3243232', '女', '计算机', '14', '上海陆家嘴1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'louse', '123456', null);
INSERT INTO `user` VALUES ('2', 'Bob', 'abcdef', 'bob@example.com');
INSERT INTO `user` VALUES ('3', 'Carol', 'qwerty', 'carol@example.com');
INSERT INTO `user` VALUES ('4', '张甜', 'asd', null);
