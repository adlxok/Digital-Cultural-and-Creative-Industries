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

 Date: 11/07/2024 21:16:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户唯一标识，自增',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名，不能为空且唯一',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码，不能为空，通常存储哈希后的密码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱，唯一',
  `full_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户全名',
  `birth_date` date NULL DEFAULT NULL COMMENT '出生日期',
  `gender` tinyint(0) NULL DEFAULT NULL COMMENT '性别，1: 男, 2: 女, 3: 其他',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '城市',
  `country` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国家',
  `postal_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮政编码',
  `profile_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像图片地址',
  `bio` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '用户简介或个人介绍',
  `is_active` tinyint(1) NULL DEFAULT 1 COMMENT '用户账号的激活状态，默认为 true',
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '用户创建时间',
  `updated_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '用户信息最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'john_doe', 'password123', 'john.doe@example.com', 'John Doe', '1980-01-15', 1, '123-456-7890', '123 Main St', 'Anytown', 'USA', '12345', 'https://example.com/profile_images/john_doe.jpg', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', 1, '2024-07-11 21:15:21', '2024-07-11 21:15:21');
INSERT INTO `users` VALUES (2, 'jane_smith', 'secret456', 'jane.smith@example.com', 'Jane Smith', '1990-05-20', 2, '987-654-3210', '456 Elm St', 'Sometown', 'Canada', '67890', 'https://example.com/profile_images/jane_smith.jpg', 'Nulla nec purus feugiat, volutpat urna id, ultricies nunc.', 1, '2024-07-11 21:15:21', '2024-07-11 21:15:21');
INSERT INTO `users` VALUES (3, 'sam_jones', 'pass321word', 'sam.jones@example.com', 'Sam Jones', '1985-11-03', 3, '555-123-4567', '789 Oak Ave', 'Othertown', 'Australia', '54321', 'https://example.com/profile_images/sam_jones.jpg', 'Fusce eu libero vitae erat suscipit sodales.', 1, '2024-07-11 21:15:21', '2024-07-11 21:15:21');

SET FOREIGN_KEY_CHECKS = 1;
