/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : localhost:3306
 Source Schema         : dcc

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 23/07/2024 11:30:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for udc_menu
-- ----------------------------
DROP TABLE IF EXISTS `udc_menu`;
CREATE TABLE `udc_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `pid` bigint(0) NULL DEFAULT NULL COMMENT '父级权限id',
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '名称',
  `perm_key` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `value` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '权限值',
  `icon` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图标',
  `type` int(0) NULL DEFAULT NULL COMMENT '权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）',
  `uri` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '前端资源路径',
  `status` int(0) NULL DEFAULT NULL COMMENT '启用状态；0->禁用；1->启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of udc_menu
-- ----------------------------
INSERT INTO `udc_menu` VALUES (1, NULL, '浏览', 'dcc:look', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `udc_menu` VALUES (2, NULL, '使用', 'dcc:use', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `udc_menu` VALUES (3, NULL, '测试', 'dcc:test', NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `udc_menu` VALUES (4, NULL, 'utest', 'dcc:utest', NULL, NULL, NULL, NULL, 1, NULL, NULL);

-- ----------------------------
-- Table structure for udc_role
-- ----------------------------
DROP TABLE IF EXISTS `udc_role`;
CREATE TABLE `udc_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '名称',
  `role_key` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `description` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '描述',
  `count` int(0) NULL DEFAULT NULL COMMENT '用户数量',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `status` int(0) NULL DEFAULT 1 COMMENT '启用状态：0->禁用；1->启用',
  `sort` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of udc_role
-- ----------------------------
INSERT INTO `udc_role` VALUES (1, '访问者', 'vistor', '访问者', 1, '2024-07-18 15:17:30', 1, 0);
INSERT INTO `udc_role` VALUES (2, '用户', 'user', '用户', 1, '2024-07-18 15:20:32', 1, 0);

-- ----------------------------
-- Table structure for udc_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `udc_role_menu`;
CREATE TABLE `udc_role_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(0) NULL DEFAULT NULL,
  `menu_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '后台用户角色和权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of udc_role_menu
-- ----------------------------
INSERT INTO `udc_role_menu` VALUES (1, 1, 1);
INSERT INTO `udc_role_menu` VALUES (2, 2, 2);
INSERT INTO `udc_role_menu` VALUES (3, 1, 3);
INSERT INTO `udc_role_menu` VALUES (4, 2, 4);

-- ----------------------------
-- Table structure for udc_user
-- ----------------------------
DROP TABLE IF EXISTS `udc_user`;
CREATE TABLE `udc_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户唯一标识，自增',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名，不能为空且唯一',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码，不能为空，通常存储哈希后的密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱，唯一',
  `birth_date` date NULL DEFAULT NULL COMMENT '出生日期',
  `gender` tinyint(0) NULL DEFAULT NULL COMMENT '性别：0->未知；1->男；2->女',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '城市',
  `country` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国家',
  `profile_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像图片地址',
  `bio` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '用户简介或个人介绍',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '帐号启用状态:0->禁用；1->启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of udc_user
-- ----------------------------
INSERT INTO `udc_user` VALUES (3, 'adlx', '$2a$10$FO4A4kWvSDECXSiCGuDEt.rVA3wm0CoOUQ9UR59V3UA3dRjNi7nuu', 'John', 'john.doe@example.com', '1990-05-15', 1, '1234567890', '123 Main St', 'New York', 'USA', 'https://example.com/john_doe.jpg', 'Hello, I am John Doe.', 1, '2024-07-13 12:36:24', '2024-07-13 12:36:24');
INSERT INTO `udc_user` VALUES (4, 'jane_smith', '123456', 'Jane', 'jane.smith@example.com', '1985-08-25', 2, '1987654321', '456 Elm St', 'Los Angeles', 'USA', 'https://example.com/jane_smith.jpg', 'Hi, I am Jane Smith.', 1, '2024-07-13 12:36:24', '2024-07-13 12:36:24');
INSERT INTO `udc_user` VALUES (5, 'alice_wonderland', '123456', 'Alice', 'alice.wonderland@example.com', '1995-11-10', 2, '1765432109', '789 Oak St', 'San Francisco', 'USA', 'https://example.com/alice_wonderland.jpg', 'Hey, I am Alice Wonderland.', 1, '2024-07-13 12:36:24', '2024-07-13 12:36:24');

-- ----------------------------
-- Table structure for udc_user_role
-- ----------------------------
DROP TABLE IF EXISTS `udc_user_role`;
CREATE TABLE `udc_user_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NULL DEFAULT NULL,
  `role_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户和角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of udc_user_role
-- ----------------------------
INSERT INTO `udc_user_role` VALUES (1, 3, 2);
INSERT INTO `udc_user_role` VALUES (2, 4, 1);

SET FOREIGN_KEY_CHECKS = 1;
