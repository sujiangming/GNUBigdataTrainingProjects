/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : university

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-04-23 10:01:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_university_user
-- ----------------------------
DROP TABLE IF EXISTS `t_university_user`;
CREATE TABLE `t_university_user` (
  `id` varchar(255) NOT NULL,
  `account` varchar(255) NOT NULL,
  `cname` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
