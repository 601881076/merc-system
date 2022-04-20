/*
Navicat MySQL Data Transfer

Source Server         : tanyi
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : chef

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2021-09-05 11:30:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for merchant_order
-- ----------------------------
DROP TABLE IF EXISTS `merchant_order`;
CREATE TABLE `merchant_order` (
  `position` varchar(255) DEFAULT NULL COMMENT '招聘职位',
  `salary` varchar(255) DEFAULT NULL COMMENT '薪资类型',
  `work_type` varchar(255) DEFAULT NULL COMMENT '工作类型(全职,兼职)',
  `work_area` varchar(255) DEFAULT NULL COMMENT '工作区域',
  `work_age` varchar(255) DEFAULT NULL COMMENT '工作年限',
  `education` varchar(255) DEFAULT NULL COMMENT '文化程度',
  `work_num` int(11) DEFAULT NULL COMMENT '招聘人数',
  `work_welfare` varchar(255) DEFAULT NULL COMMENT '工作福利',
  `admin_id` bigint(11) DEFAULT NULL COMMENT '关联用户ID',
  `status` tinyint(255) DEFAULT NULL COMMENT '(0->等待接单状态;1->已接单;2->已完成)',
  `id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL COMMENT '登录名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `icon` varchar(500) DEFAULT NULL COMMENT '头像',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `note` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `status` int(1) DEFAULT '1' COMMENT '帐号启用状态(0->禁用；1->启用)',
  `user_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户类型(0->普通用户；1->厨师; 2->管理员)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(64) DEFAULT NULL COMMENT '最近一次登录IP地址',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Table structure for t_chef
-- ----------------------------
DROP TABLE IF EXISTS `t_chef`;
CREATE TABLE `t_chef` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) NOT NULL COMMENT '用户id',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(32) DEFAULT '0' COMMENT '性别',
  `phone` varchar(32) DEFAULT NULL COMMENT '厨师手机号',
  `birth_date` varchar(32) DEFAULT NULL COMMENT '出生年月',
  `title` varchar(32) DEFAULT NULL COMMENT '厨师称号  (暂时不要)',
  `job_date` varchar(32) DEFAULT NULL COMMENT '从业时间',
  `credential_url` varchar(64) DEFAULT NULL COMMENT '资质图片 (暂时不要)',
  `specialty` varchar(64) DEFAULT NULL COMMENT '拿手菜',
  `cuisine` varchar(64) DEFAULT NULL COMMENT '擅长菜系',
  `status` tinyint(1) DEFAULT '0' COMMENT '认证状态(0->认证中；1->已认证)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `qq` varchar(32) DEFAULT NULL COMMENT '厨师qq',
  `weixin` varchar(32) DEFAULT NULL COMMENT '微信',
  `area` varchar(64) DEFAULT NULL COMMENT '服务地区',
  `is_food` varchar(32) DEFAULT NULL COMMENT '是否自备食材',
  `is_ware` varchar(32) DEFAULT NULL COMMENT '是否自备厨具',
  `job_week` varchar(64) DEFAULT NULL COMMENT '工作时间 周',
  `job_start_time` varchar(32) DEFAULT NULL COMMENT '工作开始时间',
  `job_end_time` varchar(32) DEFAULT NULL COMMENT '工作结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='厨师表';

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(32) NOT NULL COMMENT '订单id',
  `admin_id` bigint(20) NOT NULL COMMENT '用户id',
  `title` varchar(32) DEFAULT NULL COMMENT '标题',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `amount` bigint(20) NOT NULL COMMENT '订单金额(分为单位)',
  `status` tinyint(1) DEFAULT '0' COMMENT '(0->等待接单状态;1->已接单;2->已完成)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `receive_time` datetime DEFAULT NULL COMMENT '订单接收时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for user_order
-- ----------------------------
DROP TABLE IF EXISTS `user_order`;
CREATE TABLE `user_order` (
  `dinner_name` varchar(255) DEFAULT NULL COMMENT '用餐类型',
  `dinner_date` datetime DEFAULT NULL COMMENT '用餐时间',
  `dinner_num` varchar(255) DEFAULT NULL COMMENT '用餐桌上',
  `dinner_area` varchar(255) DEFAULT NULL COMMENT '用餐区域',
  `dinner_address` varchar(255) DEFAULT NULL COMMENT '用餐地址',
  `dinner_demand` varchar(255) DEFAULT NULL COMMENT '用餐需求',
  `dinne_deatil` varchar(255) DEFAULT NULL COMMENT '其他需求',
  `admin_id` bigint(11) DEFAULT NULL COMMENT '关联用户',
  `status` tinyint(255) DEFAULT NULL COMMENT '(0->等待接单状态;1->已接单;2->已完成)',
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
