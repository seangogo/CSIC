/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50620
 Source Host           : localhost
 Source Database       : servicedb

 Target Server Type    : MySQL
 Target Server Version : 50620
 File Encoding         : utf-8

 Date: 04/21/2016 12:39:06 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_group`
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `group_name` varchar(100) DEFAULT NULL COMMENT '分组名称',
  `parent_id` int(10) DEFAULT NULL COMMENT '父级id',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_sys_organization_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `t_resource`
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `parent_id` int(10) DEFAULT NULL COMMENT '父id',
  `type` varchar(10) DEFAULT NULL COMMENT '类型 1、2、3、4',
  `res_url` varchar(200) DEFAULT NULL COMMENT '菜单链接',
  `level` int(11) DEFAULT '0' COMMENT '几级菜单 0为根目录 1为1级目录 以此类推',
  `icon_cls` varchar(50) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `order_by` int(11) DEFAULT '0' COMMENT '排序',
  `permission` varchar(50) DEFAULT NULL COMMENT '权限字符串',
  `has_child` varchar(11) DEFAULT NULL COMMENT '是否有子目录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(32) NOT NULL COMMENT '角色',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `resource_ids` varchar(250) DEFAULT NULL COMMENT 'resource_id按逗号隔开',
  `description` varchar(32) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- -------------------------------------
--  Table structure for `t_role_resource`
-- -------------------------------------
DROP TABLE IF EXISTS `t_role_resource`;
CREATE TABLE `t_role_resource` (
  `role_id` int(10) UNSIGNED NOT NULL,
  `resource_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`),
  CONSTRAINT `FK_roleid` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_resourceid` FOREIGN KEY (`resource_id`) REFERENCES `t_resource` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_task`
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `user_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6934pq1ra1kbi62rgwsg5c8e6` (`user_id`),
  CONSTRAINT `FK_6934pq1ra1kbi62rgwsg5c8e6` FOREIGN KEY (`user_id`) REFERENCES `test_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_name` varchar(64) NOT NULL COMMENT '登录账户：手机号码',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '盐',
  `user_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户姓名',
  `user_ico` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户头像',
  `user_sex` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `user_address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `user_company` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '公司',
  `user_mail` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `last_location` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '最后上传的经纬度',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后次登录时间',
  `register_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `login_count` int(11) DEFAULT NULL COMMENT '登录次数',
  `device_os` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '设备标示 ： ios  、 android',
  `device_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '设备id',
  `is_login` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否登录  0 未登录  1已登录',
  `user_state` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户状态  0未认证 1已认证',
  `user_type` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户类型  0普通用户  1工程师',
  `repair_type` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '工程师类型  0报修 1实施',
  `role_id` int(10) DEFAULT NULL COMMENT '角色id',
  `group_id` int(10) DEFAULT NULL COMMENT '部门id',
  `is_locked` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '锁定用户 0正常  1锁定',
  `last_location_time` timestamp NULL DEFAULT NULL COMMENT '最后一次上传经纬度时间'
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`) USING BTREE,
  KEY `FK_user_role_role_id` (`role_id`),
  CONSTRAINT `FK_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- -------------------------------------
--  Table structure for `t_user_feedback`
-- -------------------------------------
DROP TABLE IF EXISTS `t_user_feedback`;
CREATE TABLE `t_user_feedback` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户反馈 主键id',
  `device_version` varchar(32) DEFAULT NULL COMMENT '设备版本号',
  `device_system` varchar(32) DEFAULT NULL COMMENT '设备系统版本',
  `device_size` varchar(32) DEFAULT NULL COMMENT '设备分辨率',
  `device_network` varchar(32) DEFAULT NULL COMMENT '设备网络类型',
  `app_version` varchar(32) DEFAULT NULL COMMENT 'app的版本号',
  `content` varchar(512) DEFAULT NULL COMMENT '反馈内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_id` int(10) DEFAULT NULL COMMENT '发表用户id',
  PRIMARY KEY (`id`),
  KEY `PK_t_user_feedback_user_id` (`user_id`),
  CONSTRAINT `PK_t_user_feedback_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- -------------------------------------
--  Table structure for `t_user_position`
-- -------------------------------------
DROP TABLE IF EXISTS `t_user_position`;
CREATE TABLE `t_user_position` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `last_location` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '最后上传的经纬度',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ---------------------------------
--  Table structure for `t_word_book`
-- ---------------------------------
DROP TABLE IF EXISTS `t_word_book`;
CREATE TABLE `t_word_book` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '字典id',
  `word_index` int(11) NOT NULL COMMENT '字典key',
  `type` varchar(32) NOT NULL COMMENT '字典类型',
  `word_value` varchar(32) NOT NULL COMMENT '字典 对应 key的值',
  `order_by` int(11) DEFAULT NULL COMMENT '排序 越大越靠前 ',
  `parent_id` int(10) DEFAULT NULL COMMENT '父级id',
  `description` varchar(64) DEFAULT NULL COMMENT '描述',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '最后次修改时间',
  `updater` int(10) DEFAULT NULL COMMENT '修改人 user_id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_type` (`word_index`,`type`),
  KEY `updater` (`updater`),
  CONSTRAINT `updaterId` FOREIGN KEY (`updater`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- -------------------------------------
--  Table structure for `t_operation_log`
-- -------------------------------------
DROP TABLE IF EXISTS `t_operation_log`;
CREATE TABLE `t_operation_log` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '操作记录id',
  `type` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '类型 0普通操作记录  1异常操作记录',
  `description` varchar(64) DEFAULT NULL COMMENT '操作描述',
  `method` varchar(256) DEFAULT NULL COMMENT '方法描述',
  `request_ip` varchar(64) DEFAULT NULL COMMENT '请求ip',
  `creater` int(10) DEFAULT NULL COMMENT '创建人',
  `exception_code` varchar(128) DEFAULT NULL COMMENT '异常类型',
  `exception_detail` text COMMENT '异常详情',
  `params` text COMMENT '请求参数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `creater` (`creater`),
  CONSTRAINT `operationCreater` FOREIGN KEY (`creater`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ------------------------------
--  Table structure for `t_banner`
-- ------------------------------
DROP TABLE IF EXISTS `t_banner`;
CREATE TABLE `t_banner` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `banner_img` varchar(1024) DEFAULT NULL COMMENT '广告图片url',
  `banner_target_url` varchar(255) DEFAULT NULL COMMENT '跳转目标URL',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `order_by` int(1) DEFAULT NULL COMMENT '排序',
  `is_show` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否展示',
  `news_id` int(10) DEFAULT NULL COMMENT '新闻id',
  `banner_type` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'banner类型 0新闻  1外链',
  `detail_content` varchar(255) DEFAULT NULL,
  `banner_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='banner表';

-- --------------------------------
--  Table structure for `t_code_num`
-- --------------------------------
DROP TABLE IF EXISTS `t_code_num`;
CREATE TABLE `t_code_num` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `code_time` varchar(11) NOT NULL COMMENT '编号年月 如：201605',
  `code_num` int(20) NOT NULL DEFAULT '0' COMMENT '编号流水码 每月重置 默认为0',
  `code_param` varchar(32) NOT NULL COMMENT '编号参数  如： SERVICEORDER:服务订单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- -----------------------------------
--  Table structure for `t_invite_code`
-- -----------------------------------
DROP TABLE IF EXISTS `t_invite_code`;
CREATE TABLE `t_invite_code` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code_num` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '邀请码编号',
  `from_id` int(11) NOT NULL COMMENT '生成邀请码的用户',
  `to_id` int(11) DEFAULT NULL COMMENT '使用邀请码的用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `use_time` timestamp NULL DEFAULT NULL COMMENT '认证时间',
  `is_use` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否使用 0 未使用  1已使用',
  PRIMARY KEY (`id`),
  KEY `from_id` (`from_id`),
  KEY `to_id` (`to_id`),
  CONSTRAINT `fromUser` FOREIGN KEY (`from_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `toUser` FOREIGN KEY (`to_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `t_news`
-- ----------------------------
DROP TABLE IF EXISTS `t_news`;
CREATE TABLE `t_news` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `news_title` varchar(64) DEFAULT NULL COMMENT '新闻标题',
  `news_desc` varchar(256) DEFAULT NULL COMMENT '新闻描述',
  `news_content` text COMMENT '新闻内容（富文本）',
  `news_img` varchar(128) DEFAULT NULL COMMENT '新闻图片',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `order_by` int(1) DEFAULT '0' COMMENT '排序',
  `update_time` timestamp NOT NULL DEFAULT NULL COMMENT '修改时间',
  `is_show` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否展示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------------
--  Table structure for `t_order_cost`
-- ----------------------------------
DROP TABLE IF EXISTS `t_order_cost`;
CREATE TABLE `t_order_cost` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` int(10) NOT NULL COMMENT '订单id',
  `cost_type` int(10) NOT NULL COMMENT '费用类型id',
  `cost` float(11,2) DEFAULT NULL COMMENT '费用',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `cost_type` (`cost_type`) USING BTREE,
  CONSTRAINT `t_order_cost_ibfk_1` FOREIGN KEY (`cost_type`) REFERENCES `t_order_cost_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------
--  Table structure for `t_order_cost_type`
-- ---------------------------------------
DROP TABLE IF EXISTS `t_order_cost_type`;
CREATE TABLE `t_order_cost_type` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cost_name` varchar(32) NOT NULL COMMENT '费用名称',
  `has_reward` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否有提成 0没有  1有',
  `reward_ratio` float(11,2) DEFAULT '0.00' COMMENT '提成比例',
  `is_locked` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否锁定  0未锁定 1锁定',
  `order_by` int(11) DEFAULT '0' COMMENT '排序',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ------------------------------------
--  Table structure for `t_repair_order`
-- ------------------------------------
DROP TABLE IF EXISTS `t_repair_order`;
CREATE TABLE `t_repair_order` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单id 主键',
  `order_num` varchar(64) NOT NULL COMMENT '订单编号',
  `repair_type` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '报修类型 0设备报修 1安装实施 ',
  `order_state` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '订单状态 0待处理 1已接单 2已完成 3已取消 4已评价 ',
  `user_id` int(10) NOT NULL COMMENT '发布人   用户id',
  `repair_id` int(10) DEFAULT NULL COMMENT '维修人   工程师id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '订单发布时间',
  `appointment_time` timestamp NULL DEFAULT NULL COMMENT '预约时间',
  `completion_time` timestamp NULL DEFAULT NULL COMMENT '订单完成时间',
  `contact_user` varchar(32) NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(32) NOT NULL,
  `contact_address` varchar(255) NOT NULL,
  `contact_location` varchar(128) DEFAULT NULL COMMENT '联系地址  经纬度',
  `order_desc` varchar(1024) DEFAULT NULL COMMENT '订单描述',
  `order_imgs` varchar(1024) DEFAULT NULL COMMENT '订单图片  按逗号分隔',
  `order_imgs_thumb` varchar(1024) DEFAULT NULL COMMENT '订单图片缩略图 按 逗号分隔',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新订单时间',
  `comment_content` varchar(1024) DEFAULT NULL COMMENT '订单评价内容',
  `comment_star` char(1) DEFAULT NULL COMMENT '订单评星 1，2，3，4，5',
  `has_again` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否再次上门 0 不是  1是',
  `has_complain` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否有投诉     0没有  1有',
  `has_comment` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否有评论 0没有  1有',
  `is_read` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '工程师是否查看订单  0未读 1已读',
  `order_source` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '发布订单来源  0手机发布  1后台发布',
  `doc_path` varchar(255) DEFAULT NULL COMMENT '附件路径',
  `doc_name` varchar(255) DEFAULT NULL COMMENT '附件文件名',
  `cc_ids` varchar(1024) DEFAULT NULL COMMENT '相关人id',
  `order_explain` varchar(1024) DEFAULT NULL COMMENT '订单处理说明',
  `qty` int(10) DEFAULT NULL COMMENT '设备维修类型订单的台数',
  PRIMARY KEY (`id`),
  KEY `FK_bdl9ux6a5pt526dckp5dnd90d` (`repair_id`) USING BTREE,
  KEY `FK_iwl7ua5ivj3mp1ot41golh86v` (`user_id`) USING BTREE,
  CONSTRAINT `t_repair_order_ibfk_1` FOREIGN KEY (`repair_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `t_repair_order_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- -----------------------------------------
--  Table structure for `t_repair_order_cost`
-- -----------------------------------------
DROP TABLE IF EXISTS `t_repair_order_cost`;
CREATE TABLE `t_repair_order_cost` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` int(10) NOT NULL,
  `cost_type` int(10) NOT NULL,
  `repair_id` int(10) NOT NULL,
  `cost` float(11,2) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------------------
--  Table structure for `t_repair_order_log`
-- ----------------------------------------
DROP TABLE IF EXISTS `t_repair_order_log`;
CREATE TABLE `t_repair_order_log` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单状态id  主键',
  `order_id` int(10) DEFAULT NULL COMMENT '订单id',
  `creater` int(10) DEFAULT NULL COMMENT '创建这个状态的人   user_id',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `repair_id` int(10) DEFAULT NULL COMMENT '当前维修人 user_id',
  `msg_type` int(1) DEFAULT NULL COMMENT '消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉  6预约时间更改 7再次上门 8更换工程师 ',
  `msg_content` varchar(1024) DEFAULT NULL COMMENT '消息内容',
  `comment_star` char(1) DEFAULT NULL COMMENT '评星 1,2,3,4,5',
  `content_type` varchar(64) DEFAULT NULL COMMENT '消息内容类型',
  PRIMARY KEY (`id`),
  KEY `repair_id` (`repair_id`) USING BTREE,
  KEY `creater` (`creater`) USING BTREE,
  CONSTRAINT `t_repair_order_log_ibfk_1` FOREIGN KEY (`creater`) REFERENCES `t_user` (`id`),
  CONSTRAINT `t_repair_order_log_ibfk_2` FOREIGN KEY (`repair_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- --------------------------------------
--  Table structure for `t_order_engineer`
-- --------------------------------------
DROP TABLE IF EXISTS `t_order_engineer`;
CREATE TABLE `t_order_engineer` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` int(10) NOT NULL COMMENT '订单id',
  `user_id` int(10) NOT NULL COMMENT '相关人id',
  `ext1` varchar(20) DEFAULT NULL COMMENT '备用1',
  `ext2` varchar(20) DEFAULT NULL COMMENT '备用2',
  `ext3` varchar(20) DEFAULT NULL COMMENT '备用3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
