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

 Date: 04/08/2024 16:12:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for carousel
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键，自动递增',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片的 URL 地址',
  `alt_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片的替代文本或描述',
  `link_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片的链接地址',
  `position` int(0) NULL DEFAULT NULL COMMENT '显示顺序',
  `is_active` tinyint(1) NULL DEFAULT 1 COMMENT '标记图片是否显示',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of carousel
-- ----------------------------
INSERT INTO `carousel` VALUES (1, 'https://www.dpm.org.cn/Uploads/Picture/2022/06/08/s62a06be9e26f1.jpg', NULL, NULL, NULL, 1, '2024-07-31 20:44:34', '2024-07-31 20:44:34');
INSERT INTO `carousel` VALUES (2, 'https://www.dpm.org.cn/Uploads/Picture/2022/05/26/s628f5119637c1.jpg', NULL, NULL, NULL, 1, '2024-07-31 20:44:34', '2024-07-31 20:44:34');
INSERT INTO `carousel` VALUES (3, 'https://www.dpm.org.cn/Uploads/Picture/2022/05/31/s6295eda55e369.jpg', NULL, NULL, NULL, 1, '2024-07-31 20:44:34', '2024-07-31 20:44:34');
INSERT INTO `carousel` VALUES (4, 'https://www.dpm.org.cn/Uploads/Picture/2022/05/31/s6295d1401ffb4.JPG', NULL, NULL, NULL, 1, '2024-07-31 20:44:34', '2024-07-31 20:44:34');
INSERT INTO `carousel` VALUES (5, 'https://www.dpm.org.cn/Uploads/Picture/2022/05/31/s629606c22fefc.jpg', NULL, NULL, NULL, 1, '2024-07-31 20:44:34', '2024-07-31 20:44:34');
INSERT INTO `carousel` VALUES (6, 'https://www.dpm.org.cn/Uploads/Picture/2022/04/29/s626b3655db080.jpg', NULL, NULL, NULL, 1, '2024-07-31 20:44:34', '2024-07-31 20:44:34');

SET FOREIGN_KEY_CHECKS = 1;
