/*
Navicat MySQL Data Transfer

Source Server         : 192.168.60.87
Source Server Version : 50173
Source Host           : 192.168.60.87:3306
Source Database       : udpserver

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-08-22 10:21:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for can_property
-- ----------------------------
DROP TABLE IF EXISTS `can_property`;
CREATE TABLE `can_property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `can_id` float NOT NULL,
  `property` tinyblob,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of can_property
-- ----------------------------

-- ----------------------------
-- Table structure for client_info
-- ----------------------------
DROP TABLE IF EXISTS `client_info`;
CREATE TABLE `client_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(20) NOT NULL,
  `connected` smallint(6) DEFAULT NULL,
  `lastconnecteddate` datetime DEFAULT NULL,
  `leastsignbits` bigint(20) DEFAULT NULL,
  `mostsignbits` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_c3my8mh7emvddja5g7x7jjhpc` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of client_info
-- ----------------------------
INSERT INTO `client_info` VALUES ('1', 'main', '1', '2017-08-21 18:01:27', '-7715885601806607623', '1028412119858693671');
INSERT INTO `client_info` VALUES ('2', 'cooling', '0', '2017-07-04 14:06:04', null, null);
INSERT INTO `client_info` VALUES ('3', 'dive', '1', '2017-08-21 18:01:27', '-8744809353326972668', '2677247774546478995');
INSERT INTO `client_info` VALUES ('4', 'fuel', '0', '2017-06-16 07:27:47', null, null);
INSERT INTO `client_info` VALUES ('5', 'environment', '0', '2017-06-16 07:27:47', null, null);
INSERT INTO `client_info` VALUES ('6', 'trimming', '0', '2017-08-16 09:24:49', null, null);
INSERT INTO `client_info` VALUES ('7', 'fire', '0', '2017-06-16 07:27:48', null, null);
INSERT INTO `client_info` VALUES ('8', 'flow', '0', '2017-06-09 23:05:46', null, null);

-- ----------------------------
-- Table structure for device_property
-- ----------------------------
DROP TABLE IF EXISTS `device_property`;
CREATE TABLE `device_property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(30) DEFAULT NULL,
  `device_key` varchar(10) DEFAULT NULL,
  `property_key` varchar(10) DEFAULT NULL,
  `client_id` varchar(30) DEFAULT NULL,
  `udp_server_index` int(11) DEFAULT NULL,
  `read_sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=434 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device_property
-- ----------------------------
INSERT INTO `device_property` VALUES ('1', '深度传感器', 'K1-06', null, 'main', null, null);
INSERT INTO `device_property` VALUES ('2', '深度传感器', 'K1-08', 'real', 'main', '3', '68');
INSERT INTO `device_property` VALUES ('3', 'H2、O2、CO2分析仪', 'K9-09', null, 'main', null, null);
INSERT INTO `device_property` VALUES ('4', 'H2、O2、CO2分析仪', 'K9-08', null, 'main', null, null);
INSERT INTO `device_property` VALUES ('5', 'H2、O2、CO2分析仪', 'K9-07', null, 'main', null, null);
INSERT INTO `device_property` VALUES ('6', '油箱液位计：2#日用燃油箱', 'K12-016', 'real', 'main', '3', '95');
INSERT INTO `device_property` VALUES ('7', '冷却流量计：Ⅰ舱1#数据机柜', 'K6-011', 'real', 'cooling', '1', '76');
INSERT INTO `device_property` VALUES ('8', '冷却流量计：Ⅰ舱2#数据机柜', 'K6-012', 'real', 'cooling', '1', '77');
INSERT INTO `device_property` VALUES ('9', '冷却流量计：Ⅱ舱数据机柜', 'K6-013', 'real', 'cooling', '2', '65');
INSERT INTO `device_property` VALUES ('10', '冷却流量计：1#逆变电源', 'K6-014', 'real', 'cooling', '2', '66');
INSERT INTO `device_property` VALUES ('11', '冷却流量计：2#逆变电源', 'K6-015', 'real', 'cooling', '2', '67');
INSERT INTO `device_property` VALUES ('12', '冷却流量计：推进电机', 'K6-016', 'real', 'cooling', '3', '83');
INSERT INTO `device_property` VALUES ('13', '冷却流量计：均衡泵电机', 'K6-017', 'real', 'cooling', '3', '84');
INSERT INTO `device_property` VALUES ('14', '冷却流量计：均衡泵电机', 'K6-018', 'real', 'cooling', '3', '85');
INSERT INTO `device_property` VALUES ('15', '冷却流量计：溴化锂机组', 'K6-019', 'real', 'cooling', '3', '86');
INSERT INTO `device_property` VALUES ('16', '冷却流量计：1#动力装置排烟管', 'K6-020', 'real', 'cooling', '3', '87');
INSERT INTO `device_property` VALUES ('17', '冷却流量计：2#动力装置排烟管', 'K6-021', 'real', 'cooling', '3', '88');
INSERT INTO `device_property` VALUES ('18', '冷却流量计：1#动力装置', 'K6-022', 'real', 'cooling', '3', '89');
INSERT INTO `device_property` VALUES ('19', '冷却流量计：2#动力装置', 'K6-023', 'real', 'cooling', '3', '90');
INSERT INTO `device_property` VALUES ('20', '冷却流量计：3#动力装置', 'K6-024', 'real', 'cooling', '3', '91');
INSERT INTO `device_property` VALUES ('21', '冷却流量计：4#动力装置', 'K6-025', 'real', 'cooling', '3', '92');
INSERT INTO `device_property` VALUES ('22', '冷却流量调节阀：1#淡水泵', 'K6-06', 'real', 'cooling', '3', '66');
INSERT INTO `device_property` VALUES ('23', '冷却流量调节阀：2#淡水泵', 'K6-07', 'real', 'cooling', '3', '67');
INSERT INTO `device_property` VALUES ('24', '冷却温度传感器：淡水压力总管', 'K6-046', 'real', 'cooling', '3', '117');
INSERT INTO `device_property` VALUES ('25', '淡水泵压力总管压力传感器', 'K6-03', 'real', 'cooling', '3', '81');
INSERT INTO `device_property` VALUES ('26', '冷却流量计：压力总管与回水总管间', 'K6-026', 'real', 'cooling', '3', '93');
INSERT INTO `device_property` VALUES ('27', '冷却温度传感器：淡水回水总管', 'K6-047', 'real', 'cooling', '3', '118');
INSERT INTO `device_property` VALUES ('28', '淡水泵回水总管压力传感器', 'K6-04', 'real', 'cooling', '3', '82');
INSERT INTO `device_property` VALUES ('29', '冷却温度传感器：Ⅰ舱1#数据机柜', 'K6-031', 'real', 'cooling', '1', '86');
INSERT INTO `device_property` VALUES ('30', '冷却温度传感器：Ⅰ舱2#数据机柜', 'K6-032', 'real', 'cooling', '1', '87');
INSERT INTO `device_property` VALUES ('31', '冷却温度传感器：Ⅱ舱数据机柜', 'K6-033', 'real', 'cooling', '2', '96');
INSERT INTO `device_property` VALUES ('32', '冷却温度传感器：1#逆变电源', 'K6-034', 'real', 'cooling', '2', '97');
INSERT INTO `device_property` VALUES ('35', '冷却温度传感器：2#逆变电源', 'K6-035', 'real', 'cooling', '2', '98');
INSERT INTO `device_property` VALUES ('36', '冷却温度传感器：推进电机', 'K6-036', 'real', 'cooling', '3', '107');
INSERT INTO `device_property` VALUES ('37', '冷却温度传感器：均衡泵电机', 'K6-037', 'real', 'cooling', '3', '108');
INSERT INTO `device_property` VALUES ('38', '冷却温度传感器：Ⅲ舱数据机柜', 'K6-038', 'real', 'cooling', '3', '109');
INSERT INTO `device_property` VALUES ('39', '冷却温度传感器：溴化锂机组', 'K6-039', 'real', 'cooling', '3', '110');
INSERT INTO `device_property` VALUES ('40', '冷却温度传感器：1#动力装置排烟管', 'K6-040', 'real', 'cooling', '3', '111');
INSERT INTO `device_property` VALUES ('41', '冷却温度传感器：2#动力装置排烟管', 'K6-041', 'real', 'cooling', '3', '112');
INSERT INTO `device_property` VALUES ('42', '冷却温度传感器：1#动力装置', 'K6-042', 'real', 'cooling', '3', '113');
INSERT INTO `device_property` VALUES ('43', '冷却温度传感器：2#动力装置', 'K6-043', 'real', 'cooling', '3', '114');
INSERT INTO `device_property` VALUES ('44', '冷却温度传感器：3#动力装置', 'K6-044', 'real', 'cooling', '3', '115');
INSERT INTO `device_property` VALUES ('45', '冷却温度传感器：4#动力装置', 'K6-045', 'real', 'cooling', '3', '116');
INSERT INTO `device_property` VALUES ('46', '通气阀电动作动器：1#压载水舱左', 'K4-08', 'open', 'main,dive', '1', '5');
INSERT INTO `device_property` VALUES ('47', '通气阀电动作动器：1#压载水舱右', 'K4-09', 'open', 'main,dive', '1', '7');
INSERT INTO `device_property` VALUES ('48', '高压空气压力传感器：1#水舱阀后', 'K4-043', 'real', 'dive', '1', '73');
INSERT INTO `device_property` VALUES ('49', '快速吹除电磁阀：1#压载水舱', 'K4-015', 'feedback', 'dive', '1', '13');
INSERT INTO `device_property` VALUES ('50', '正常吹除电磁阀：1#压载水舱', 'K4-020', 'feedback', 'dive', '1', '15');
INSERT INTO `device_property` VALUES ('51', '高压空气压力传感器：1#气瓶', 'K4-042', 'real', 'main,dive', '1', '72');
INSERT INTO `device_property` VALUES ('52', '高压空气压力传感器：总管', 'K4-041', 'real', 'dive', '1', '71');
INSERT INTO `device_property` VALUES ('53', '高压空气压力传感器：3#气瓶', 'K4-046', 'real', 'main,dive', '3', '77');
INSERT INTO `device_property` VALUES ('54', '快速吹除电磁阀：3#压载水舱', 'K4-017', 'feedback', 'dive', '3', '23');
INSERT INTO `device_property` VALUES ('55', '正常吹除电磁阀：3#压载水舱', 'K4-022', 'feedback', 'dive', '3', '25');
INSERT INTO `device_property` VALUES ('56', '高压空气压力传感器：3#水舱阀后', 'K4-047', 'real', 'dive', '3', '78');
INSERT INTO `device_property` VALUES ('57', '通气阀电动作动器：3#压载水舱', 'K4-012', 'open', 'main,dive', '3', '19');
INSERT INTO `device_property` VALUES ('58', '应急通气阀电动作动器：3#压载水舱', 'K4-014', 'open', 'main,dive', '3', '48');
INSERT INTO `device_property` VALUES ('59', '通气阀电动作动器：2#压载水舱右', 'K4-011', 'open', 'main,dive', '1', '11');
INSERT INTO `device_property` VALUES ('60', '通气阀电动作动器：2#压载水舱左', 'K4-010', 'open', 'main,dive', '1', '9');
INSERT INTO `device_property` VALUES ('61', '高压空气压力传感器：2#水舱阀后', 'K4-045', 'real', 'dive', '1', '75');
INSERT INTO `device_property` VALUES ('62', '快速吹除电磁阀：2#压载水舱', 'K4-016', 'feedback', 'dive', '1', '14');
INSERT INTO `device_property` VALUES ('63', '正常吹除电磁阀：2#压载水舱', 'K4-021', 'feedback', 'dive', '1', '16');
INSERT INTO `device_property` VALUES ('64', '1号淡水泵', 'K6-01', 'start', 'main', '3', '33');
INSERT INTO `device_property` VALUES ('65', '2号淡水泵', 'K6-02', 'start', 'main', '3', '34');
INSERT INTO `device_property` VALUES ('66', '氧气瓶压力传感器：1#2#集装格', 'K12-021', 'real', 'main,fuel,environment', '2', '68');
INSERT INTO `device_property` VALUES ('67', '氧气瓶压力传感器：3#4#集装格', 'K12-022', 'real', 'main,fuel,environment', '2', '69');
INSERT INTO `device_property` VALUES ('68', '氧气瓶压力传感器：5#6#集装格', 'K12-023', 'real', 'main,fuel,environment', '2', '70');
INSERT INTO `device_property` VALUES ('69', '氧气瓶压力传感器：7#8#集装格', 'K12-024', 'real', 'main,fuel,environment', '2', '71');
INSERT INTO `device_property` VALUES ('71', '高压空气压力传感器：2#气瓶', 'K4-044', 'real', 'main,dive', '1', '74');
INSERT INTO `device_property` VALUES ('75', '高压空气压力传感器：4#气瓶', 'K4-048', 'real', 'main,dive', '3', '79');
INSERT INTO `device_property` VALUES ('76', '双阀座电液通海阀', 'K3-04', 'open', 'main,trimming', '3', '1');
INSERT INTO `device_property` VALUES ('77', '溴化锂制冷机组', 'K10-040', null, 'main,environment', null, null);
INSERT INTO `device_property` VALUES ('78', '氟利昂机组：压缩机低压报警', 'K10-010', 'call01', 'main,environment', '0', '1');
INSERT INTO `device_property` VALUES ('79', '快速吹除电磁阀：4#压载水舱', 'K4-018', 'feedback', 'dive', '3', '24');
INSERT INTO `device_property` VALUES ('80', '正常吹除电磁阀：4#压载水舱', 'K4-023', 'feedback', 'dive', '3', '26');
INSERT INTO `device_property` VALUES ('81', '高压空气压力传感器：4#水舱阀后', 'K4-049', 'real', 'dive', '3', '80');
INSERT INTO `device_property` VALUES ('82', '通气阀电动作动器：4#压载水舱', 'K4-013', 'open', 'main,dive', '3', '21');
INSERT INTO `device_property` VALUES ('83', '排气压力传感器：直排管路', 'K12-045', 'real', 'fuel', '3', '101');
INSERT INTO `device_property` VALUES ('84', '排气压力传感器：泵排管路', 'K12-046', 'real', 'fuel', '3', '102');
INSERT INTO `device_property` VALUES ('85', '排气滤器：1#发电机组', 'K12-051', 'call', 'fuel', '3', '40');
INSERT INTO `device_property` VALUES ('86', '排气滤器：2#发电机组', 'K12-052', 'call', 'fuel', '3', '41');
INSERT INTO `device_property` VALUES ('87', '排气滤器：3#发电机组', 'K12-053', 'call', 'fuel', '3', '42');
INSERT INTO `device_property` VALUES ('88', '排气滤器：4#发电机组', 'K12-054', 'call', 'fuel', '3', '43');
INSERT INTO `device_property` VALUES ('89', '冷凝水滤器：1#发电机组', 'K12-055', 'call', 'fuel', '3', '44');
INSERT INTO `device_property` VALUES ('90', '冷凝水滤器：2#发电机组', 'K12-056', 'call', 'fuel', '3', '45');
INSERT INTO `device_property` VALUES ('91', '冷凝水滤器：3#发电机组', 'K12-057', 'call', 'fuel', '3', '46');
INSERT INTO `device_property` VALUES ('92', '冷凝水滤器：4#发电机组', 'K12-058', 'call', 'fuel', '3', '47');
INSERT INTO `device_property` VALUES ('93', '排气压力传感器：1#发电机组', 'K12-041', 'real', 'fuel', '3', '97');
INSERT INTO `device_property` VALUES ('95', '排气压力传感器：2#发电机组', 'K12-042', 'real', 'fuel', '3', '98');
INSERT INTO `device_property` VALUES ('96', '排气压力传感器：3#发电机组', 'K12-043', 'real', 'fuel', '3', '99');
INSERT INTO `device_property` VALUES ('97', '排气压力传感器：4#发电机组', 'K12-044', 'real', 'fuel', '3', '100');
INSERT INTO `device_property` VALUES ('98', '燃油总管压力变送器', 'K12-031', 'real', 'fuel', '3', '96');
INSERT INTO `device_property` VALUES ('99', '1#燃油预供泵', 'K12-05', 'start', 'fuel', '3', '35');
INSERT INTO `device_property` VALUES ('100', '2#燃油预供泵', 'K12-06', 'start', 'fuel', '3', '36');
INSERT INTO `device_property` VALUES ('101', '油箱液位计：1#日用燃油箱', 'K12-015', 'real', 'fuel', '3', '94');
INSERT INTO `device_property` VALUES ('102', '燃油流量计', 'K12-011', 'real', 'fuel', '1', '78');
INSERT INTO `device_property` VALUES ('103', '燃油滤器', 'K12-050', 'call', 'fuel', '1', '39');
INSERT INTO `device_property` VALUES ('104', '1#燃油输送泵', 'K12-02', null, 'fuel', null, null);
INSERT INTO `device_property` VALUES ('105', '2#燃油输送泵', 'K12-03', null, 'fuel', null, null);
INSERT INTO `device_property` VALUES ('106', '油舱液位计：燃油舱', 'K12-014', 'real', 'fuel', '1', '79');
INSERT INTO `device_property` VALUES ('108', '氮气压力变送器：氮气瓶', 'K12-032', 'real', 'fuel', '2', '72');
INSERT INTO `device_property` VALUES ('109', '氮气压力变送器：中压吹扫', 'K12-033', 'real', 'fuel', '2', '73');
INSERT INTO `device_property` VALUES ('110', '氮气压力变送器：低压吹扫', 'K12-034', 'real', 'fuel', '2', '74');
INSERT INTO `device_property` VALUES ('111', '氧浓度仪：1#', 'K9-021', 'real', 'environment', '2', '88');
INSERT INTO `device_property` VALUES ('112', '氧浓度仪：2#', 'K9-022', 'real', 'environment', '2', '89');
INSERT INTO `device_property` VALUES ('113', '氧浓度仪：3#', 'K9-023', 'real', 'environment', '2', '90');
INSERT INTO `device_property` VALUES ('114', '氧浓度仪：4#', 'K9-024', 'real', 'environment', '2', '91');
INSERT INTO `device_property` VALUES ('115', '氧浓度仪：5#', 'K9-025', 'real', 'environment', '2', '92');
INSERT INTO `device_property` VALUES ('116', '氧浓度仪：6#', 'K9-026', 'real', 'environment', '2', '93');
INSERT INTO `device_property` VALUES ('117', '氧浓度仪：7#', 'K9-027', 'real', 'environment', '2', '94');
INSERT INTO `device_property` VALUES ('118', '氧浓度仪：8#', 'K9-028', 'real', 'environment', '2', '95');
INSERT INTO `device_property` VALUES ('122', '空调淡水泵', 'K10-01', 'start', 'environment', '3', '37');
INSERT INTO `device_property` VALUES ('123', '排风机（艏部空调风机）', 'K8-03', null, 'environment', null, null);
INSERT INTO `device_property` VALUES ('124', '送风机（艉部空调风机）', 'K8-02', 'low', 'environment', '3', '38');
INSERT INTO `device_property` VALUES ('125', '电动浮筒液位计：艏右纵倾平衡水舱', 'K3-08', 'real', 'trimming', '1', '68');
INSERT INTO `device_property` VALUES ('126', '电动浮筒液位计：艏左纵倾平衡水舱', 'K3-07', 'real', 'trimming', '1', '67');
INSERT INTO `device_property` VALUES ('127', '均衡电动球阀：右舷纵倾', 'K3-035', 'open', 'trimming', '3', '17');
INSERT INTO `device_property` VALUES ('128', '均衡遥测流量计：右舷纵倾平衡', 'K3-019', null, 'trimming', null, null);
INSERT INTO `device_property` VALUES ('129', '电动浮筒液位计：艉右纵倾平衡水舱', 'K3-010', 'real', 'trimming', '3', '70');
INSERT INTO `device_property` VALUES ('130', '低压空气压力传感器：右舷', 'K3-050', 'real', 'trimming', '3', '76');
INSERT INTO `device_property` VALUES ('131', '均衡遥测流量计：左舷纵倾平衡', 'K3-018', null, 'trimming', null, null);
INSERT INTO `device_property` VALUES ('132', '电动浮筒液位计：艉左纵倾平衡水舱', 'K3-09', 'real', 'trimming', '3', '69');
INSERT INTO `device_property` VALUES ('133', '均衡电动球阀：左舷纵倾', 'K3-034', 'open', 'trimming', '3', '15');
INSERT INTO `device_property` VALUES ('134', '低压空气压力传感器：左舷', 'K3-049', 'real', 'trimming', '3', '75');
INSERT INTO `device_property` VALUES ('136', '均衡电动球阀：泵排', 'K3-030', 'open', 'trimming', '1', '1');
INSERT INTO `device_property` VALUES ('137', '均衡泵', 'K3-01', 'feedback-0', 'trimming', '2', '11');
INSERT INTO `device_property` VALUES ('138', '均衡电动球阀：自注', 'K3-031', 'close', 'main,trimming', '1', '4');
INSERT INTO `device_property` VALUES ('139', '自流注水流量调节阀', 'K3-022', 'real', 'trimming', '3', '65');
INSERT INTO `device_property` VALUES ('140', '均衡遥测流量计：浮力调整', 'K3-017', null, 'trimming', null, null);
INSERT INTO `device_property` VALUES ('141', '均衡电动球阀：左舷浮调', 'K3-032', 'open', 'trimming', '3', '11');
INSERT INTO `device_property` VALUES ('142', '电动浮筒液位计：左舷浮力调整水舱', 'K3-05', 'real', 'trimming', '1', '65');
INSERT INTO `device_property` VALUES ('143', '均衡电动球阀：右舷浮调', 'K3-033', 'open', 'trimming', '3', '13');
INSERT INTO `device_property` VALUES ('144', '电动浮筒液位计：右舷浮力调整水舱', 'K3-06', 'real', 'trimming', '1', '66');
INSERT INTO `device_property` VALUES ('145', '气灭火电磁施放阀：通信室', 'K5-018', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('146', '火灾探测器：通信室1', 'K5-050', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('147', '手动报警按钮：通信室', 'K5-041', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('148', '火灾探测器：通信室2', 'K5-051', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('149', '火灾探测器：1人住室', 'K5-057', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('150', '火灾探测器：4人住室', 'K5-058', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('151', '手动报警按钮：健身房', 'K5-042', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('152', '气灭火电磁施放阀：健身房', 'K5-020', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('153', '灾探测器：健身房1', 'K5-052', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('154', '灾探测器：健身房2', 'K5-053', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('155', '灾探测器：健身房3', 'K5-054', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('156', '火灾探测器：Ⅱ舱上层1', 'K5-062', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('157', '火灾探测器：Ⅱ舱上层2', 'K5-063', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('158', '气灭火电磁施放阀：Ⅱ舱上层', 'K5-021', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('159', '火灾探测器：Ⅱ舱上层3', 'K5-064', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('160', '火灾探测器：Ⅱ舱上层4', 'K5-065', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('161', '气灭火电磁施放阀：Ⅱ舱下层', 'K5-022', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('162', '手动报警按钮：Ⅱ舱下层', 'K5-045', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('163', '火灾探测器：Ⅱ舱下层1', 'K5-066', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('164', '火灾探测器：Ⅱ舱下层2', 'K5-067', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('165', '气灭火电磁施放阀：Ⅲ舱', 'K5-023', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('166', '火灾探测器：Ⅲ舱1', 'K5-068', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('167', '手动报警按钮：Ⅲ舱', 'K5-046', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('168', '火灾探测器：Ⅲ舱2', 'K5-069', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('169', '火灾探测器：Ⅲ舱3', 'K5-070', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('170', '火灾探测器：Ⅲ舱4', 'K5-071', null, 'fire', null, null);
INSERT INTO `device_property` VALUES ('171', '通风空调温湿度变送器：Ⅲ舱舱室', 'K8-055', 'real-0', null, '3', '103');
INSERT INTO `device_property` VALUES ('172', '通风空调温湿度变送器：Ⅲ舱舱室', 'K8-055', 'real-1', null, '3', '104');
INSERT INTO `device_property` VALUES ('173', '通风空调温湿度变送器：Ⅲ舱空调器出口', 'K8-056', 'real-0', null, '3', '105');
INSERT INTO `device_property` VALUES ('174', '通风空调温湿度变送器：Ⅲ舱空调器出口', 'K8-056', 'real-1', null, '3', '106');
INSERT INTO `device_property` VALUES ('175', '均衡电动球阀：泵排', 'K3-030', 'close', 'trimming', '1', '2');
INSERT INTO `device_property` VALUES ('176', '均衡电动球阀：自注', 'K3-031', 'open', 'main,trimming', '1', '3');
INSERT INTO `device_property` VALUES ('177', '通气阀电动作动器：1#压载水舱左', 'K4-08', 'close', 'dive', '1', '6');
INSERT INTO `device_property` VALUES ('178', '通气阀电动作动器：1#压载水舱右', 'K4-09', 'close', 'dive', '1', '8');
INSERT INTO `device_property` VALUES ('179', '通气阀电动作动器：2#压载水舱左', 'K4-010', 'close', 'dive', '1', '10');
INSERT INTO `device_property` VALUES ('180', '通气阀电动作动器：2#压载水舱右', 'K4-011', 'close', 'dive', '1', '12');
INSERT INTO `device_property` VALUES ('181', '蓄电池室电动风阀：VX1', 'K8-020', 'open', null, '1', '17');
INSERT INTO `device_property` VALUES ('182', '蓄电池室电动风阀：VX1', 'K8-020', 'close', null, '1', '18');
INSERT INTO `device_property` VALUES ('183', '蓄电池室电动风阀：VX2', 'K8-021', 'open', null, '1', '19');
INSERT INTO `device_property` VALUES ('184', '蓄电池室电动风阀：VX2', 'K8-021', 'close', null, '1', '20');
INSERT INTO `device_property` VALUES ('185', '蓄电池室电动风阀：VX3', 'K8-022', 'open', null, '1', '21');
INSERT INTO `device_property` VALUES ('186', '蓄电池室电动风阀：VX3', 'K8-022', 'close', null, '1', '22');
INSERT INTO `device_property` VALUES ('187', '蓄电池室电动风阀：VX4', 'K8-023', 'open', null, '1', '23');
INSERT INTO `device_property` VALUES ('188', '蓄电池室电动风阀：VX4', 'K8-023', 'close', null, '1', '24');
INSERT INTO `device_property` VALUES ('189', '蓄电池室电动风阀：VX5', 'K8-024', 'open', null, '1', '25');
INSERT INTO `device_property` VALUES ('190', '蓄电池室电动风阀：VX5', 'K8-024', 'close', null, '1', '26');
INSERT INTO `device_property` VALUES ('191', '蓄电池室电动风阀：VX6', 'K8-025', 'open', null, '1', '27');
INSERT INTO `device_property` VALUES ('192', '蓄电池室电动风阀：VX6', 'K8-025', 'close', null, '1', '28');
INSERT INTO `device_property` VALUES ('193', '蓄电池室电动风阀：VX7', 'K8-026', 'open', null, '1', '29');
INSERT INTO `device_property` VALUES ('194', '蓄电池室电动风阀：VX7', 'K8-026', 'close', null, '1', '30');
INSERT INTO `device_property` VALUES ('195', '蓄电池室电动风阀：VX8', 'K8-027', 'open', null, '1', '31');
INSERT INTO `device_property` VALUES ('196', '蓄电池室电动风阀：VX8', 'K8-027', 'close', null, '1', '32');
INSERT INTO `device_property` VALUES ('197', '舱室通风空调电动风阀：V1-1', 'K8-028', 'open', null, '1', '33');
INSERT INTO `device_property` VALUES ('198', '舱室通风空调电动风阀：V1-1', 'K8-028', 'close', null, '1', '34');
INSERT INTO `device_property` VALUES ('199', '舱室通风空调电动风阀：V1-2', 'K8-029', 'open', null, '1', '35');
INSERT INTO `device_property` VALUES ('200', '舱室通风空调电动风阀：V1-2', 'K8-029', 'close', null, '1', '36');
INSERT INTO `device_property` VALUES ('201', '舱室通风空调电动风阀：V1-3', 'K8-030', 'open', null, '1', '37');
INSERT INTO `device_property` VALUES ('202', '舱室通风空调电动风阀：V1-3', 'K8-030', 'close', null, '1', '38');
INSERT INTO `device_property` VALUES ('203', '1#燃油输送泵启动信号', 'K12-02', 'start', null, '1', '40');
INSERT INTO `device_property` VALUES ('204', '2#燃油输送泵启动信号', 'K12-03', 'start', null, '1', '41');
INSERT INTO `device_property` VALUES ('205', '蓄电池风机启动信号', 'K8-01', 'start', null, '1', '42');
INSERT INTO `device_property` VALUES ('206', '排风机低速运行信号', 'K8-03', 'low', null, '1', '43');
INSERT INTO `device_property` VALUES ('207', '排风机高速运行信号', 'K8-03', 'high', null, '1', '44');
INSERT INTO `device_property` VALUES ('208', '驾控台', 'K4-00', '下潜', null, '1', '45');
INSERT INTO `device_property` VALUES ('209', '驾控台', 'K4-00', '吹出', null, '1', '46');
INSERT INTO `device_property` VALUES ('210', '备用', 'K0', 'reserve', null, '1', '47');
INSERT INTO `device_property` VALUES ('211', '备用', 'K0', 'reserve', null, '1', '48');
INSERT INTO `device_property` VALUES ('212', '备用', 'K0', 'reserve', null, '1', '49');
INSERT INTO `device_property` VALUES ('213', '备用', 'K0', 'reserve', null, '1', '50');
INSERT INTO `device_property` VALUES ('214', '备用', 'K0', 'reserve', null, '1', '51');
INSERT INTO `device_property` VALUES ('215', '备用', 'K0', 'reserve', null, '1', '52');
INSERT INTO `device_property` VALUES ('216', '备用', 'K0', 'reserve', null, '1', '53');
INSERT INTO `device_property` VALUES ('217', '备用', 'K0', 'reserve', null, '1', '54');
INSERT INTO `device_property` VALUES ('218', '备用', 'K0', 'reserve', null, '1', '55');
INSERT INTO `device_property` VALUES ('219', '备用', 'K0', 'reserve', null, '1', '56');
INSERT INTO `device_property` VALUES ('220', '备用', 'K0', 'reserve', null, '1', '57');
INSERT INTO `device_property` VALUES ('221', '备用', 'K0', 'reserve', null, '1', '58');
INSERT INTO `device_property` VALUES ('222', '备用', 'K0', 'reserve', null, '1', '59');
INSERT INTO `device_property` VALUES ('223', '备用', 'K0', 'reserve', null, '1', '60');
INSERT INTO `device_property` VALUES ('224', '备用', 'K0', 'reserve', null, '1', '61');
INSERT INTO `device_property` VALUES ('225', '备用', 'K0', 'reserve', null, '1', '62');
INSERT INTO `device_property` VALUES ('226', '备用', 'K0', 'reserve', null, '1', '63');
INSERT INTO `device_property` VALUES ('227', '备用', 'K0', 'reserve', null, '1', '64');
INSERT INTO `device_property` VALUES ('228', '水舱压力传感器：艏左纵倾平衡', 'K3-045', 'real', 'trimming', '1', '69');
INSERT INTO `device_property` VALUES ('229', '水舱压力传感器：艏右纵倾平衡', 'K3-046', 'real', 'trimming', '1', '70');
INSERT INTO `device_property` VALUES ('230', '通风空调温湿度变送器：蓄电池空调器出口', 'K8-051', 'real-0', null, '1', '80');
INSERT INTO `device_property` VALUES ('231', '通风空调温湿度变送器：蓄电池空调器出口', 'K8-051', 'real-1', null, '1', '81');
INSERT INTO `device_property` VALUES ('232', '通风空调温湿度变送器：Ⅰ舱上层', 'K8-052', 'real-0', null, '1', '82');
INSERT INTO `device_property` VALUES ('233', '通风空调温湿度变送器：Ⅰ舱上层', 'K8-052', 'real-1', null, '1', '83');
INSERT INTO `device_property` VALUES ('234', '通风空调温度传感器：蓄电池室内', 'K8-041', 'real', null, '1', '84');
INSERT INTO `device_property` VALUES ('235', '通风空调温度传感器：蓄电池空调器进口', 'K8-042', 'real', null, '1', '85');
INSERT INTO `device_property` VALUES ('236', '备用', 'K0', 'reserve', null, '1', '88');
INSERT INTO `device_property` VALUES ('237', '备用', 'k0', 'reserve', null, '1', '89');
INSERT INTO `device_property` VALUES ('238', '备用', 'k0', 'reserve', null, '1', '90');
INSERT INTO `device_property` VALUES ('239', '备用', 'k0', 'reserve', null, '1', '91');
INSERT INTO `device_property` VALUES ('240', '备用', 'k0', 'reserve', null, '1', '92');
INSERT INTO `device_property` VALUES ('241', '备用', 'k0', 'reserve', null, '1', '93');
INSERT INTO `device_property` VALUES ('242', '备用', 'k0', 'reserve', null, '1', '94');
INSERT INTO `device_property` VALUES ('243', '备用', 'k0', 'reserve', null, '1', '95');
INSERT INTO `device_property` VALUES ('244', '备用', 'k0', 'reserve', null, '1', '96');
INSERT INTO `device_property` VALUES ('245', '备用', 'k0', 'reserve', null, '1', '97');
INSERT INTO `device_property` VALUES ('246', '备用', 'k0', 'reserve', null, '1', '98');
INSERT INTO `device_property` VALUES ('247', '备用', 'k0', 'reserve', null, '1', '99');
INSERT INTO `device_property` VALUES ('248', '备用', 'k0', 'reserve', null, '1', '100');
INSERT INTO `device_property` VALUES ('249', '备用', 'k0', 'reserve', null, '1', '101');
INSERT INTO `device_property` VALUES ('250', '备用', 'k0', 'reserve', null, '1', '102');
INSERT INTO `device_property` VALUES ('251', '舱室通风空调电动风阀：V2-1', 'K8-031', 'open', null, '2', '1');
INSERT INTO `device_property` VALUES ('252', '舱室通风空调电动风阀：V2-1', 'K8-031', 'close', null, '2', '2');
INSERT INTO `device_property` VALUES ('253', '舱室通风空调电动风阀：V2-2', 'K8-032', 'open', null, '2', '3');
INSERT INTO `device_property` VALUES ('254', '舱室通风空调电动风阀：V2-2', 'K8-032', 'close', null, '2', '4');
INSERT INTO `device_property` VALUES ('255', '舱室通风空调电动风阀：V2-3', 'K8-033', 'open', null, '2', '5');
INSERT INTO `device_property` VALUES ('256', '舱室通风空调电动风阀：V2-3', 'K8-033', 'close', null, '2', '6');
INSERT INTO `device_property` VALUES ('257', '舱室通风空调电动风阀：V2-4', 'K8-034', 'open', null, '2', '7');
INSERT INTO `device_property` VALUES ('258', '舱室通风空调电动风阀：V2-4', 'K8-034', 'close', null, '2', '8');
INSERT INTO `device_property` VALUES ('259', '舱室通风空调电动风阀：V2-5', 'K8-035', 'open', null, '2', '9');
INSERT INTO `device_property` VALUES ('260', '舱室通风空调电动风阀：V2-5', 'K8-035', 'close', null, '2', '10');
INSERT INTO `device_property` VALUES ('261', '均衡泵', 'K3-01', 'feedback-1', null, '2', '12');
INSERT INTO `device_property` VALUES ('262', '备用', 'K0', 'reserve', null, '2', '13');
INSERT INTO `device_property` VALUES ('263', '备用', 'K0', 'reserve', null, '2', '14');
INSERT INTO `device_property` VALUES ('264', '备用', 'K0', 'reserve', null, '2', '15');
INSERT INTO `device_property` VALUES ('265', '备用', 'K0', 'reserve', null, '2', '16');
INSERT INTO `device_property` VALUES ('266', '备用', 'K0', 'reserve', null, '2', '17');
INSERT INTO `device_property` VALUES ('267', '备用', 'K0', 'reserve', null, '2', '18');
INSERT INTO `device_property` VALUES ('268', '备用', 'K0', 'reserve', null, '2', '19');
INSERT INTO `device_property` VALUES ('269', '备用', 'K0', 'reserve', null, '2', '20');
INSERT INTO `device_property` VALUES ('270', '备用', 'K0', 'reserve', null, '2', '21');
INSERT INTO `device_property` VALUES ('271', '备用', 'K0', 'reserve', null, '2', '22');
INSERT INTO `device_property` VALUES ('272', '备用', 'K0', 'reserve', null, '2', '23');
INSERT INTO `device_property` VALUES ('273', '备用', 'K0', 'reserve', null, '2', '24');
INSERT INTO `device_property` VALUES ('274', '备用', 'K0', 'reserve', null, '2', '25');
INSERT INTO `device_property` VALUES ('275', '备用', 'K0', 'reserve', null, '2', '26');
INSERT INTO `device_property` VALUES ('276', '备用', 'K0', 'reserve', null, '2', '27');
INSERT INTO `device_property` VALUES ('277', '备用', 'K0', 'reserve', null, '2', '28');
INSERT INTO `device_property` VALUES ('278', '备用', 'K0', 'reserve', null, '2', '29');
INSERT INTO `device_property` VALUES ('279', '备用', 'K0', 'reserve', null, '2', '30');
INSERT INTO `device_property` VALUES ('280', '备用', 'K0', 'reserve', null, '2', '31');
INSERT INTO `device_property` VALUES ('281', '备用', 'K0', 'reserve', null, '2', '32');
INSERT INTO `device_property` VALUES ('282', '备用', 'K0', 'reserve', null, '2', '33');
INSERT INTO `device_property` VALUES ('283', '备用', 'K0', 'reserve', null, '2', '34');
INSERT INTO `device_property` VALUES ('284', '备用', 'K0', 'reserve', null, '2', '35');
INSERT INTO `device_property` VALUES ('285', '备用', 'K0', 'reserve', null, '2', '36');
INSERT INTO `device_property` VALUES ('286', '备用', 'K0', 'reserve', null, '2', '37');
INSERT INTO `device_property` VALUES ('287', '备用', 'K0', 'reserve', null, '2', '38');
INSERT INTO `device_property` VALUES ('288', '备用', 'K0', 'reserve', null, '2', '39');
INSERT INTO `device_property` VALUES ('289', '备用', 'K0', 'reserve', null, '2', '40');
INSERT INTO `device_property` VALUES ('290', '备用', 'K0', 'reserve', null, '2', '41');
INSERT INTO `device_property` VALUES ('291', '备用', 'K0', 'reserve', null, '2', '42');
INSERT INTO `device_property` VALUES ('292', '备用', 'K0', 'reserve', null, '2', '43');
INSERT INTO `device_property` VALUES ('293', '备用', 'K0', 'reserve', null, '2', '44');
INSERT INTO `device_property` VALUES ('294', '备用', 'K0', 'reserve', null, '2', '45');
INSERT INTO `device_property` VALUES ('295', '备用', 'K0', 'reserve', null, '2', '46');
INSERT INTO `device_property` VALUES ('296', '备用', 'K0', 'reserve', null, '2', '47');
INSERT INTO `device_property` VALUES ('297', '备用', 'K0', 'reserve', null, '2', '48');
INSERT INTO `device_property` VALUES ('298', '备用', 'K0', 'reserve', null, '2', '49');
INSERT INTO `device_property` VALUES ('299', '备用', 'K0', 'reserve', null, '2', '50');
INSERT INTO `device_property` VALUES ('300', '备用', 'K0', 'reserve', null, '2', '51');
INSERT INTO `device_property` VALUES ('301', '备用', 'K0', 'reserve', null, '2', '52');
INSERT INTO `device_property` VALUES ('302', '备用', 'K0', 'reserve', null, '2', '53');
INSERT INTO `device_property` VALUES ('303', '备用', 'K0', 'reserve', null, '2', '54');
INSERT INTO `device_property` VALUES ('304', '备用', 'K0', 'reserve', null, '2', '55');
INSERT INTO `device_property` VALUES ('305', '备用', 'K0', 'reserve', null, '2', '56');
INSERT INTO `device_property` VALUES ('306', '备用', 'K0', 'reserve', null, '2', '57');
INSERT INTO `device_property` VALUES ('307', '备用', 'K0', 'reserve', null, '2', '58');
INSERT INTO `device_property` VALUES ('308', '备用', 'K0', 'reserve', null, '2', '59');
INSERT INTO `device_property` VALUES ('309', '备用', 'K0', 'reserve', null, '2', '60');
INSERT INTO `device_property` VALUES ('310', '备用', 'K0', 'reserve', null, '2', '61');
INSERT INTO `device_property` VALUES ('311', '备用', 'K0', 'reserve', null, '2', '62');
INSERT INTO `device_property` VALUES ('312', '备用', 'K0', 'reserve', null, '2', '63');
INSERT INTO `device_property` VALUES ('313', '备用', 'K0', 'reserve', null, '2', '64');
INSERT INTO `device_property` VALUES ('314', '电动通风舷侧阀：左舷1', 'K8-06', 'real', null, '2', '75');
INSERT INTO `device_property` VALUES ('315', '电动通风舷侧阀：左舷2', 'K8-07', 'real', null, '2', '76');
INSERT INTO `device_property` VALUES ('316', '电动通风舷侧阀：右舷1', 'K8-08', 'real', null, '2', '77');
INSERT INTO `device_property` VALUES ('317', '电动通风舷侧阀：右舷2', 'K8-09', 'real', null, '2', '78');
INSERT INTO `device_property` VALUES ('318', '电动隔舱蝶阀：27#舱壁左舷', 'K8-011', 'real', null, '2', '79');
INSERT INTO `device_property` VALUES ('319', '电动隔舱蝶阀：27#舱壁右舷', 'K8-012', 'real', null, '2', '80');
INSERT INTO `device_property` VALUES ('320', '电动隔舱蝶阀：48#舱壁左舷', 'K8-013', 'real', null, '2', '81');
INSERT INTO `device_property` VALUES ('321', '电动隔舱蝶阀：48#舱壁右舷1', 'K8-014', 'real', null, '2', '82');
INSERT INTO `device_property` VALUES ('322', '电动隔舱蝶阀：48#舱壁右舷2', 'K8-015', 'real', null, '2', '83');
INSERT INTO `device_property` VALUES ('323', '通风空调温湿度变送器：Ⅱ舱空调器出口', 'K8-053', 'real-0', null, '2', '84');
INSERT INTO `device_property` VALUES ('324', '通风空调温湿度变送器：Ⅱ舱空调器出口', 'K8-053', 'real-1', null, '2', '85');
INSERT INTO `device_property` VALUES ('325', '通风空调温湿度变送器：Ⅱ舱上层', 'K8-054', 'real-0', null, '2', '86');
INSERT INTO `device_property` VALUES ('326', '通风空调温湿度变送器：Ⅱ舱上层', 'K8-054', 'real-1', null, '2', '87');
INSERT INTO `device_property` VALUES ('327', '通风空调温度传感器：Ⅱ舱空调器进口', 'K8-043', 'real', null, '2', '99');
INSERT INTO `device_property` VALUES ('328', '通风空调温度传感器：Ⅱ舱下层', 'K8-044', 'real', null, '2', '100');
INSERT INTO `device_property` VALUES ('329', '备用', 'K0', 'reserve', null, '2', '101');
INSERT INTO `device_property` VALUES ('330', '备用', 'K0', 'reserve', null, '2', '102');
INSERT INTO `device_property` VALUES ('331', '备用', 'K0', 'reserve', null, '2', '103');
INSERT INTO `device_property` VALUES ('332', '备用', 'K0', 'reserve', null, '2', '104');
INSERT INTO `device_property` VALUES ('333', '备用', 'K0', 'reserve', null, '2', '105');
INSERT INTO `device_property` VALUES ('334', '备用', 'K0', 'reserve', null, '2', '106');
INSERT INTO `device_property` VALUES ('335', '备用', 'K0', 'reserve', null, '2', '107');
INSERT INTO `device_property` VALUES ('336', '备用', 'K0', 'reserve', null, '2', '108');
INSERT INTO `device_property` VALUES ('337', '备用', 'K0', 'reserve', null, '2', '109');
INSERT INTO `device_property` VALUES ('338', '双阀座电液通海阀', 'K3-04', 'close', 'main,trimming', '3', '2');
INSERT INTO `device_property` VALUES ('339', '电动四通球阀：左舷', 'K3-011', 'open', null, '3', '3');
INSERT INTO `device_property` VALUES ('340', '电动四通球阀：左舷', 'K3-011', 'close', null, '3', '4');
INSERT INTO `device_property` VALUES ('341', '电动四通球阀：右舷', 'K3-012', 'open', null, '3', '5');
INSERT INTO `device_property` VALUES ('342', '电动四通球阀：右舷', 'K3-012', 'close', null, '3', '6');
INSERT INTO `device_property` VALUES ('343', '低压空气电动球阀：左舷', 'K3-028', 'open', null, '3', '7');
INSERT INTO `device_property` VALUES ('344', '低压空气电动球阀：左舷', 'K3-028', 'close', null, '3', '8');
INSERT INTO `device_property` VALUES ('345', '低压空气电动球阀：右舷', 'K3-029', 'open', null, '3', '9');
INSERT INTO `device_property` VALUES ('346', '低压空气电动球阀：右舷', 'K3-029', 'close', null, '3', '10');
INSERT INTO `device_property` VALUES ('347', '均衡电动球阀：左舷浮调', 'K3-032', 'close', 'trimming', '3', '12');
INSERT INTO `device_property` VALUES ('348', '均衡电动球阀：右舷浮调', 'K3-033', 'close', 'trimming', '3', '14');
INSERT INTO `device_property` VALUES ('349', '均衡电动球阀：左舷纵倾', 'K3-034', 'close', 'trimming', '3', '16');
INSERT INTO `device_property` VALUES ('350', '均衡电动球阀：右舷纵倾', 'K3-035', 'close', 'trimming', '3', '18');
INSERT INTO `device_property` VALUES ('351', '通气阀电动作动器：3#压载水舱', 'K4-012', 'close', 'dive', '3', '20');
INSERT INTO `device_property` VALUES ('352', '通气阀电动作动器：4#压载水舱', 'K4-013', 'close', 'dive', '3', '22');
INSERT INTO `device_property` VALUES ('353', '舱室通风空调电动风阀：V3-1', 'K8-036', 'open', null, '3', '27');
INSERT INTO `device_property` VALUES ('354', '舱室通风空调电动风阀：V3-1', 'K8-036', 'close', null, '3', '28');
INSERT INTO `device_property` VALUES ('355', '舱室通风空调电动风阀：V3-2', 'K8-037', 'open', null, '3', '29');
INSERT INTO `device_property` VALUES ('356', '舱室通风空调电动风阀：V3-2', 'K8-037', 'close', null, '3', '30');
INSERT INTO `device_property` VALUES ('357', '舱室通风空调电动风阀：V3-3', 'K8-038', 'open', null, '3', '31');
INSERT INTO `device_property` VALUES ('358', '舱室通风空调电动风阀：V3-3', 'K8-038', 'close', null, '3', '32');
INSERT INTO `device_property` VALUES ('359', '送风机（艉部空调风机）', 'K8-02', 'high', 'environment', '3', '39');
INSERT INTO `device_property` VALUES ('360', '应急通气阀电动作动器：3#压载水舱', 'K4-014', 'close', 'dive', '3', '49');
INSERT INTO `device_property` VALUES ('361', '备用', 'K0', 'reserve', null, '3', '50');
INSERT INTO `device_property` VALUES ('362', '备用', 'K0', 'reserve', null, '3', '51');
INSERT INTO `device_property` VALUES ('363', '备用', 'K0', 'reserve', null, '3', '52');
INSERT INTO `device_property` VALUES ('364', '备用', 'K0', 'reserve', null, '3', '53');
INSERT INTO `device_property` VALUES ('365', '备用', 'K0', 'reserve', null, '3', '54');
INSERT INTO `device_property` VALUES ('366', '备用', 'K0', 'reserve', null, '3', '55');
INSERT INTO `device_property` VALUES ('367', '备用', 'K0', 'reserve', null, '3', '56');
INSERT INTO `device_property` VALUES ('368', '备用', 'K0', 'reserve', null, '3', '57');
INSERT INTO `device_property` VALUES ('369', '备用', 'K0', 'reserve', null, '3', '58');
INSERT INTO `device_property` VALUES ('370', '备用', 'K0', 'reserve', null, '3', '59');
INSERT INTO `device_property` VALUES ('371', '备用', 'K0', 'reserve', null, '3', '60');
INSERT INTO `device_property` VALUES ('372', '备用', 'K0', 'reserve', null, '3', '61');
INSERT INTO `device_property` VALUES ('373', '备用', 'K0', 'reserve', null, '3', '62');
INSERT INTO `device_property` VALUES ('374', '备用', 'K0', 'reserve', null, '3', '63');
INSERT INTO `device_property` VALUES ('375', '备用', 'K0', 'reserve', null, '3', '64');
INSERT INTO `device_property` VALUES ('376', '均衡泵出口压力传感器', 'K3-043', 'real', null, '3', '71');
INSERT INTO `device_property` VALUES ('377', '均衡泵入口压力传感器', 'K3-044', 'real', null, '3', '72');
INSERT INTO `device_property` VALUES ('378', '水舱压力传感器：艉左纵倾平衡', 'K3-047', 'real', 'trimming', '3', '73');
INSERT INTO `device_property` VALUES ('379', '水舱压力传感器：艉右纵倾平衡', 'K3-048', 'real', 'trimming', '3', '74');
INSERT INTO `device_property` VALUES ('380', '通风空调温度传感器：Ⅲ舱动力装置区域', 'K8-045', 'real', null, '3', '122');
INSERT INTO `device_property` VALUES ('381', '通风空调温度传感器：Ⅲ舱空调器进口', 'K8-046', 'real', null, '3', '123');
INSERT INTO `device_property` VALUES ('382', '备用', 'K0', 'reserve', null, '3', '124');
INSERT INTO `device_property` VALUES ('383', '备用', 'K0', 'reserve', null, '3', '125');
INSERT INTO `device_property` VALUES ('384', '备用', 'K0', 'reserve', null, '3', '126');
INSERT INTO `device_property` VALUES ('385', '备用', 'K0', 'reserve', null, '3', '127');
INSERT INTO `device_property` VALUES ('386', '备用', 'K0', 'reserve', null, '3', '128');
INSERT INTO `device_property` VALUES ('387', '备用', 'K0', 'reserve', null, '3', '129');
INSERT INTO `device_property` VALUES ('388', '备用', 'K0', 'reserve', null, '3', '130');
INSERT INTO `device_property` VALUES ('389', '备用', 'K0', 'reserve', null, '3', '131');
INSERT INTO `device_property` VALUES ('390', '备用', 'K0', 'reserve', null, '3', '132');
INSERT INTO `device_property` VALUES ('391', '备用', 'K0', 'reserve', null, '3', '133');
INSERT INTO `device_property` VALUES ('392', '备用', 'K0', 'reserve', null, '3', '134');
INSERT INTO `device_property` VALUES ('395', '压缩机高压报警', 'K10-010', 'call02', 'main,environment', '0', '2');
INSERT INTO `device_property` VALUES ('396', '防冻报警', 'K10-010', 'call03', 'main,environment', '0', '3');
INSERT INTO `device_property` VALUES ('397', '压缩机过载报警', 'K10-010', 'call04', 'main,environment', '0', '4');
INSERT INTO `device_property` VALUES ('398', '冷媒水流量报警', 'K10-010', 'call05', 'main,environment', '0', '5');
INSERT INTO `device_property` VALUES ('399', '冷却水流量报警', 'K10-010', 'call06', 'main,environment', '0', '6');
INSERT INTO `device_property` VALUES ('400', '冷媒水泵联锁报警', 'K10-010', 'call07', 'main,environment', '0', '7');
INSERT INTO `device_property` VALUES ('401', '冷却水泵联锁报警', 'K10-010', 'call08', 'main,environment', '0', '8');
INSERT INTO `device_property` VALUES ('402', '综合报警', 'K10-010', 'call09', 'main,environment', '0', '9');
INSERT INTO `device_property` VALUES ('403', '冷媒水泵运行指示', 'K10-010', 'run01', 'main,environment', '0', '10');
INSERT INTO `device_property` VALUES ('404', '冷却水泵运行指示', 'K10-010', 'run02', 'main,environment', '0', '11');
INSERT INTO `device_property` VALUES ('405', '压缩机运行指示', 'K10-010', 'run03', 'main,environment', '0', '12');
INSERT INTO `device_property` VALUES ('406', '电子膨胀阀运行指示', 'K10-010', 'run04', 'main,environment', '0', '13');
INSERT INTO `device_property` VALUES ('407', '机组启动状态', 'K10-010', 'status01', 'main,environment', '0', '14');
INSERT INTO `device_property` VALUES ('408', '压缩机延时启动状态', 'K10-010', 'status02', 'main,environment', '0', '15');
INSERT INTO `device_property` VALUES ('409', '通讯故障', 'K10-010', 'failure', 'main,environment', '0', '16');
INSERT INTO `device_property` VALUES ('410', '压缩机吸气压力', 'K10-010', 'MPa01', 'main,environment', '0', '65');
INSERT INTO `device_property` VALUES ('411', '压缩机排气压力', 'K10-010', 'MPa02', 'main,environment', '0', '66');
INSERT INTO `device_property` VALUES ('412', '冷媒水进水温度', 'K10-010', 'temp01', 'main,environment', '0', '67');
INSERT INTO `device_property` VALUES ('413', '冷媒水出水温度', 'K10-010', 'temp02', 'main,environment', '0', '68');
INSERT INTO `device_property` VALUES ('414', '压缩机排气温度', 'K10-010', 'temp03', 'main,environment', '0', '69');
INSERT INTO `device_property` VALUES ('415', '压缩机吸气温度', 'K10-010', 'temp04', 'main,environment', '0', '70');
INSERT INTO `device_property` VALUES ('416', '自流循环系统泵阀状态001', 'R-01', 'status', 'flow', '4', '1');
INSERT INTO `device_property` VALUES ('417', '自流循环系统泵阀状态002', 'R-02', 'status', 'flow', '4', '2');
INSERT INTO `device_property` VALUES ('418', '自流循环系统泵阀状态003', 'R-03', 'status', 'flow', '4', '3');
INSERT INTO `device_property` VALUES ('419', '自流循环系统泵阀状态004', 'R-04', 'status', 'flow', '4', '4');
INSERT INTO `device_property` VALUES ('420', '自流循环系统泵阀状态005', 'R-05', 'status', 'flow', '4', '5');
INSERT INTO `device_property` VALUES ('421', '自流循环系统泵阀状态006', 'R-06', 'status', 'flow', '4', '6');
INSERT INTO `device_property` VALUES ('422', '自流循环系统泵阀状态007', 'R-07', 'status', 'flow', '4', '7');
INSERT INTO `device_property` VALUES ('423', '自流循环系统泵阀状态008', 'R-08', 'status', 'flow', '4', '8');
INSERT INTO `device_property` VALUES ('424', '自流循环系统泵阀状态009', 'R-09', 'status', 'flow', '4', '9');
INSERT INTO `device_property` VALUES ('425', '轴系调节阀开度', 'R-11', 'real', 'flow', '4', '65');
INSERT INTO `device_property` VALUES ('426', '自流循环系统海水进口温度', 'R-12', 'real', 'flow', '4', '66');
INSERT INTO `device_property` VALUES ('427', '自流循环系统左旋海水出口温度', 'R-13', 'real', 'flow', '4', '67');
INSERT INTO `device_property` VALUES ('428', '自流循环系统右旋海水出口温度', 'R-14', 'real', 'flow', '4', '68');
INSERT INTO `device_property` VALUES ('429', '左旋换热器进出口差', 'R-15', 'real', 'flow', '4', '69');
INSERT INTO `device_property` VALUES ('430', '右旋换热器进出口差', 'R-16', 'real', 'flow', '4', '70');
INSERT INTO `device_property` VALUES ('431', '一体化海水泵进出口压差', 'R-17', 'real', 'flow', '4', '71');
INSERT INTO `device_property` VALUES ('433', '泵状态', 'R-10', 'status', 'flow', '4', '10');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `developers` varchar(10) NOT NULL,
  `sort` smallint(6) NOT NULL,
  `bo` bit(1) NOT NULL,
  `num` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1', 'HW', '1', '\0', '1');
INSERT INTO `orders` VALUES ('2', 'HW', '2', '', '1');
INSERT INTO `orders` VALUES ('3', 'HW', '3', '\0', '1');
INSERT INTO `orders` VALUES ('4', 'HW', '4', '\0', '1');
INSERT INTO `orders` VALUES ('5', 'HW', '5', '', '1');
INSERT INTO `orders` VALUES ('6', 'HW', '6', '', '1');
INSERT INTO `orders` VALUES ('7', 'HW', '7', '', '1');
INSERT INTO `orders` VALUES ('8', 'HW', '8', '\0', '1');
INSERT INTO `orders` VALUES ('9', 'HW', '9', '', '2');
INSERT INTO `orders` VALUES ('10', 'HW', '10', '\0', '2');
INSERT INTO `orders` VALUES ('11', 'HW', '11', '\0', '2');
INSERT INTO `orders` VALUES ('12', 'HW', '12', '\0', '2');
INSERT INTO `orders` VALUES ('13', 'HW', '13', '\0', '2');
INSERT INTO `orders` VALUES ('14', 'HW', '14', '\0', '2');
INSERT INTO `orders` VALUES ('15', 'HW', '15', '\0', '2');
INSERT INTO `orders` VALUES ('16', 'HW', '16', '', '2');
INSERT INTO `orders` VALUES ('17', 'HW', '17', '\0', '3');
INSERT INTO `orders` VALUES ('18', 'HW', '18', '\0', '3');
INSERT INTO `orders` VALUES ('19', 'HW', '19', '\0', '3');
INSERT INTO `orders` VALUES ('20', 'HW', '20', '\0', '3');
INSERT INTO `orders` VALUES ('21', 'HW', '21', '\0', '3');
INSERT INTO `orders` VALUES ('22', 'HW', '22', '\0', '3');
INSERT INTO `orders` VALUES ('23', 'HW', '23', '\0', '3');
INSERT INTO `orders` VALUES ('24', 'HW', '24', '\0', '3');
INSERT INTO `orders` VALUES ('25', 'HW', '25', '\0', '4');
INSERT INTO `orders` VALUES ('26', 'HW', '26', '\0', '4');
INSERT INTO `orders` VALUES ('27', 'HW', '27', '\0', '4');
INSERT INTO `orders` VALUES ('28', 'HW', '28', '\0', '4');
INSERT INTO `orders` VALUES ('29', 'HW', '29', '\0', '4');
INSERT INTO `orders` VALUES ('30', 'HW', '30', '\0', '4');
INSERT INTO `orders` VALUES ('31', 'HW', '31', '\0', '4');
INSERT INTO `orders` VALUES ('32', 'HW', '32', '\0', '4');
INSERT INTO `orders` VALUES ('33', 'HW', '33', '', '5');
INSERT INTO `orders` VALUES ('34', 'HW', '34', '\0', '5');
INSERT INTO `orders` VALUES ('35', 'HW', '35', '\0', '5');
INSERT INTO `orders` VALUES ('36', 'HW', '36', '\0', '5');
INSERT INTO `orders` VALUES ('37', 'HW', '37', '\0', '5');
INSERT INTO `orders` VALUES ('38', 'HW', '38', '\0', '5');
INSERT INTO `orders` VALUES ('39', 'HW', '39', '\0', '5');
INSERT INTO `orders` VALUES ('40', 'HW', '40', '\0', '5');
INSERT INTO `orders` VALUES ('41', 'HW', '41', '\0', '6');
INSERT INTO `orders` VALUES ('42', 'HW', '42', '\0', '6');
INSERT INTO `orders` VALUES ('43', 'HW', '43', '\0', '6');
INSERT INTO `orders` VALUES ('44', 'HW', '44', '\0', '6');
INSERT INTO `orders` VALUES ('45', 'HW', '45', '\0', '6');
INSERT INTO `orders` VALUES ('46', 'HW', '46', '\0', '6');
INSERT INTO `orders` VALUES ('47', 'HW', '47', '\0', '6');
INSERT INTO `orders` VALUES ('48', 'HW', '48', '\0', '6');
INSERT INTO `orders` VALUES ('49', 'HW', '49', '\0', '7');
INSERT INTO `orders` VALUES ('50', 'HW', '50', '\0', '7');
INSERT INTO `orders` VALUES ('51', 'HW', '51', '\0', '7');
INSERT INTO `orders` VALUES ('52', 'HW', '52', '\0', '7');
INSERT INTO `orders` VALUES ('53', 'HW', '53', '\0', '7');
INSERT INTO `orders` VALUES ('54', 'HW', '54', '\0', '7');
INSERT INTO `orders` VALUES ('55', 'HW', '55', '\0', '7');
INSERT INTO `orders` VALUES ('56', 'HW', '56', '\0', '7');
INSERT INTO `orders` VALUES ('57', 'HW', '57', '\0', '8');
INSERT INTO `orders` VALUES ('58', 'HW', '58', '\0', '8');
INSERT INTO `orders` VALUES ('59', 'HW', '59', '\0', '8');
INSERT INTO `orders` VALUES ('60', 'HW', '60', '\0', '8');
INSERT INTO `orders` VALUES ('61', 'HW', '61', '\0', '8');
INSERT INTO `orders` VALUES ('62', 'HW', '62', '\0', '8');
INSERT INTO `orders` VALUES ('63', 'HW', '63', '\0', '8');
INSERT INTO `orders` VALUES ('64', 'HW', '64', '\0', '8');
INSERT INTO `orders` VALUES ('65', 'HW', '65', '\0', '5.03');
INSERT INTO `orders` VALUES ('66', 'HW', '66', '\0', '11.5');
INSERT INTO `orders` VALUES ('67', 'HW', '67', '\0', '22.6');
INSERT INTO `orders` VALUES ('68', 'HW', '68', '\0', '0');
INSERT INTO `orders` VALUES ('69', 'HW', '69', '\0', '0');
INSERT INTO `orders` VALUES ('70', 'HW', '70', '\0', '0');
INSERT INTO `orders` VALUES ('71', 'HW', '71', '\0', '0');
INSERT INTO `orders` VALUES ('72', 'HW', '72', '\0', '0');
INSERT INTO `orders` VALUES ('73', 'HW', '73', '\0', '0');
INSERT INTO `orders` VALUES ('74', 'TH', '1', '\0', '1');
INSERT INTO `orders` VALUES ('75', 'TH', '2', '\0', '1');
INSERT INTO `orders` VALUES ('76', 'TH', '3', '\0', '1');
INSERT INTO `orders` VALUES ('77', 'TH', '4', '\0', '1');
INSERT INTO `orders` VALUES ('78', 'TH', '5', '\0', '1');
INSERT INTO `orders` VALUES ('79', 'TH', '6', '\0', '1');
INSERT INTO `orders` VALUES ('80', 'TH', '7', '\0', '1');
INSERT INTO `orders` VALUES ('81', 'TH', '8', '\0', '1');
INSERT INTO `orders` VALUES ('82', 'TH', '9', '\0', '2');
INSERT INTO `orders` VALUES ('83', 'TH', '10', '\0', '2');
INSERT INTO `orders` VALUES ('84', 'TH', '11', '\0', '2');
INSERT INTO `orders` VALUES ('85', 'TH', '12', '\0', '2');
INSERT INTO `orders` VALUES ('86', 'TH', '13', '\0', '2');
INSERT INTO `orders` VALUES ('87', 'TH', '14', '\0', '2');
INSERT INTO `orders` VALUES ('88', 'TH', '15', '\0', '2');
INSERT INTO `orders` VALUES ('89', 'TH', '16', '\0', '2');
INSERT INTO `orders` VALUES ('90', 'TH', '17', '\0', '3');
INSERT INTO `orders` VALUES ('91', 'TH', '18', '\0', '3');
INSERT INTO `orders` VALUES ('92', 'TH', '19', '\0', '3');
INSERT INTO `orders` VALUES ('93', 'TH', '20', '\0', '3');
INSERT INTO `orders` VALUES ('94', 'TH', '21', '\0', '3');
INSERT INTO `orders` VALUES ('95', 'TH', '22', '\0', '3');
INSERT INTO `orders` VALUES ('96', 'TH', '23', '\0', '3');
INSERT INTO `orders` VALUES ('97', 'TH', '24', '\0', '3');
INSERT INTO `orders` VALUES ('98', 'TH', '25', '\0', '4');
INSERT INTO `orders` VALUES ('99', 'TH', '26', '\0', '4');
INSERT INTO `orders` VALUES ('100', 'TH', '27', '\0', '4');
INSERT INTO `orders` VALUES ('101', 'TH', '28', '\0', '4');
INSERT INTO `orders` VALUES ('102', 'TH', '29', '\0', '4');
INSERT INTO `orders` VALUES ('103', 'TH', '30', '\0', '4');
INSERT INTO `orders` VALUES ('104', 'TH', '31', '\0', '4');
INSERT INTO `orders` VALUES ('105', 'TH', '32', '\0', '4');
INSERT INTO `orders` VALUES ('106', 'TH', '33', '\0', '5');
INSERT INTO `orders` VALUES ('107', 'TH', '34', '\0', '5');
INSERT INTO `orders` VALUES ('108', 'TH', '35', '\0', '5');
INSERT INTO `orders` VALUES ('109', 'TH', '36', '\0', '5');
INSERT INTO `orders` VALUES ('110', 'TH', '37', '\0', '5');
INSERT INTO `orders` VALUES ('111', 'TH', '38', '\0', '5');
INSERT INTO `orders` VALUES ('112', 'TH', '39', '\0', '5');
INSERT INTO `orders` VALUES ('113', 'TH', '40', '\0', '5');
INSERT INTO `orders` VALUES ('114', 'TH', '41', '\0', '6');
INSERT INTO `orders` VALUES ('115', 'TH', '42', '\0', '6');
INSERT INTO `orders` VALUES ('116', 'TH', '43', '\0', '6');
INSERT INTO `orders` VALUES ('117', 'TH', '44', '\0', '6');
INSERT INTO `orders` VALUES ('118', 'TH', '45', '\0', '6');
INSERT INTO `orders` VALUES ('119', 'TH', '46', '\0', '6');
INSERT INTO `orders` VALUES ('120', 'TH', '47', '\0', '6');
INSERT INTO `orders` VALUES ('121', 'TH', '48', '\0', '6');
INSERT INTO `orders` VALUES ('122', 'TH', '49', '\0', '7');
INSERT INTO `orders` VALUES ('123', 'TH', '50', '\0', '7');
INSERT INTO `orders` VALUES ('124', 'TH', '51', '\0', '7');
INSERT INTO `orders` VALUES ('125', 'TH', '52', '\0', '7');
INSERT INTO `orders` VALUES ('126', 'TH', '53', '\0', '7');
INSERT INTO `orders` VALUES ('127', 'TH', '54', '\0', '7');
INSERT INTO `orders` VALUES ('128', 'TH', '55', '\0', '7');
INSERT INTO `orders` VALUES ('129', 'TH', '56', '\0', '7');
INSERT INTO `orders` VALUES ('130', 'TH', '57', '\0', '8');
INSERT INTO `orders` VALUES ('131', 'TH', '58', '\0', '8');
INSERT INTO `orders` VALUES ('132', 'TH', '59', '\0', '8');
INSERT INTO `orders` VALUES ('133', 'TH', '60', '\0', '8');
INSERT INTO `orders` VALUES ('134', 'TH', '61', '\0', '8');
INSERT INTO `orders` VALUES ('135', 'TH', '62', '\0', '8');
INSERT INTO `orders` VALUES ('136', 'TH', '63', '\0', '8');
INSERT INTO `orders` VALUES ('137', 'TH', '64', '\0', '8');
INSERT INTO `orders` VALUES ('138', 'TH', '65', '\0', '0');
INSERT INTO `orders` VALUES ('139', 'TH', '66', '\0', '0');
INSERT INTO `orders` VALUES ('140', 'TH', '67', '\0', '0');
INSERT INTO `orders` VALUES ('141', 'TH', '68', '\0', '0');
INSERT INTO `orders` VALUES ('142', 'TH', '69', '\0', '0');
INSERT INTO `orders` VALUES ('143', 'TH', '70', '\0', '0');
INSERT INTO `orders` VALUES ('144', 'TH', '71', '\0', '0');
INSERT INTO `orders` VALUES ('145', 'TH', '72', '\0', '0');
INSERT INTO `orders` VALUES ('146', 'TH', '73', '\0', '0');

-- ----------------------------
-- Table structure for original_message
-- ----------------------------
DROP TABLE IF EXISTS `original_message`;
CREATE TABLE `original_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `original_code` longblob NOT NULL,
  `resource_ip` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33715 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of original_message
-- ----------------------------

-- ----------------------------
-- Event structure for event_time_clear_data
-- ----------------------------
DROP EVENT IF EXISTS `event_time_clear_data`;
DELIMITER ;;
CREATE EVENT `event_time_clear_data` ON SCHEDULE EVERY 3 MONTH STARTS '2017-06-20 08:41:15' ENDS '2020-06-20 09:41:13' ON COMPLETION PRESERVE ENABLE DO BEGIN
DELETE FROM `original_message`;  
END
;;
DELIMITER ;
