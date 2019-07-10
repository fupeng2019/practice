/*
Navicat MySQL Data Transfer

Source Server         : 192.168.8.209
Source Server Version : 50634
Source Host           : 192.168.8.209:3306
Source Database       : psp

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2019-07-10 16:08:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `data_source`
-- ----------------------------
DROP TABLE IF EXISTS `data_source`;
CREATE TABLE `data_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `url` varchar(128) NOT NULL COMMENT '链接',
  `driver` varchar(128) NOT NULL COMMENT '驱动',
  `name` varchar(128) NOT NULL COMMENT '数据源名称',
  `data_source_type` varchar(128) NOT NULL COMMENT '数据源类型',
  `database_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '数据库类型:0:sql,1:nosql',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='数据库链接表';

-- ----------------------------
-- Records of data_source
-- ----------------------------
INSERT INTO `data_source` VALUES ('1', 'root', 'root', 'jdbc:mysql://192.168.8.209:3306/psp1?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true', 'com.mysql.jdbc.Driver', 'psp1', 'com.alibaba.druid.pool.DruidDataSource', '0');
INSERT INTO `data_source` VALUES ('2', 'root', 'root', 'jdbc:mysql://192.168.8.209:3306/psp2?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true', 'com.mysql.jdbc.Driver', 'psp2', 'com.alibaba.druid.pool.DruidDataSource', '0');

-- ----------------------------
-- Table structure for `execute_sql`
-- ----------------------------
DROP TABLE IF EXISTS `execute_sql`;
CREATE TABLE `execute_sql` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_source_id` int(11) NOT NULL COMMENT '数据源id',
  `execute_type` varchar(24) NOT NULL COMMENT '执行类型：select,insert,updata,delete',
  `table_name` varchar(50) NOT NULL COMMENT '需要执行的表',
  `type` tinyint(1) DEFAULT NULL COMMENT 'sql属于类型: 0:消息查询',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='执行sql主表';

-- ----------------------------
-- Records of execute_sql
-- ----------------------------
INSERT INTO `execute_sql` VALUES ('1', '1', 'select', 't_work1', '0');
INSERT INTO `execute_sql` VALUES ('2', '2', 'select', 't_work2', '0');

-- ----------------------------
-- Table structure for `execute_sql_detailed`
-- ----------------------------
DROP TABLE IF EXISTS `execute_sql_detailed`;
CREATE TABLE `execute_sql_detailed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field` varchar(50) NOT NULL COMMENT '字段',
  `as_field` varchar(50) NOT NULL COMMENT '映射字段',
  `execute_sql_id` int(11) NOT NULL COMMENT '执行表id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='执行sql详细';

-- ----------------------------
-- Records of execute_sql_detailed
-- ----------------------------
INSERT INTO `execute_sql_detailed` VALUES ('1', 'work_name1', 'workName', '1');
INSERT INTO `execute_sql_detailed` VALUES ('2', 'receive_date1', 'receiveDate', '1');
INSERT INTO `execute_sql_detailed` VALUES ('3', 'work_name2', 'workName', '2');
INSERT INTO `execute_sql_detailed` VALUES ('4', 'receive_date2', 'receiveDate', '2');

-- ----------------------------
-- Table structure for `t_work`
-- ----------------------------
DROP TABLE IF EXISTS `t_work`;
CREATE TABLE `t_work` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `work_name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `receive_date` datetime DEFAULT NULL COMMENT '接收时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_work
-- ----------------------------
INSERT INTO `t_work` VALUES ('44', '1', '2019-07-10 15:33:47');
INSERT INTO `t_work` VALUES ('45', '2', '2019-07-10 15:33:57');
