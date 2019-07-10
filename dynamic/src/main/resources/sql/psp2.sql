/*
Navicat MySQL Data Transfer

Source Server         : 192.168.8.209
Source Server Version : 50634
Source Host           : 192.168.8.209:3306
Source Database       : psp2

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2019-07-10 16:08:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_work2`
-- ----------------------------
DROP TABLE IF EXISTS `t_work2`;
CREATE TABLE `t_work2` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `work_name2` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `receive_date2` datetime DEFAULT NULL COMMENT '接收时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_work2
-- ----------------------------
INSERT INTO `t_work2` VALUES ('2', '2', '2019-07-10 15:33:57');
