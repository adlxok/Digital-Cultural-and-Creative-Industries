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

 Date: 13/07/2024 21:59:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
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
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (3, 'adlx', 'hashed_password_1', 'John', 'john.doe@example.com', '1990-05-15', 1, '1234567890', '123 Main St', 'New York', 'USA', 'https://example.com/john_doe.jpg', 'Hello, I am John Doe.', 1, '2024-07-13 12:36:24', '2024-07-13 12:36:24');
INSERT INTO `user` VALUES (4, 'jane_smith', 'hashed_password_2', 'Jane', 'jane.smith@example.com', '1985-08-25', 2, '1987654321', '456 Elm St', 'Los Angeles', 'USA', 'https://example.com/jane_smith.jpg', 'Hi, I am Jane Smith.', 1, '2024-07-13 12:36:24', '2024-07-13 12:36:24');
INSERT INTO `user` VALUES (5, 'alice_wonderland', 'hashed_password_3', 'Alice', 'alice.wonderland@example.com', '1995-11-10', 2, '1765432109', '789 Oak St', 'San Francisco', 'USA', 'https://example.com/alice_wonderland.jpg', 'Hey, I am Alice Wonderland.', 1, '2024-07-13 12:36:24', '2024-07-13 12:36:24');

SET FOREIGN_KEY_CHECKS = 1;
