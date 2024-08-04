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

 Date: 04/08/2024 16:12:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for navigation_items
-- ----------------------------
DROP TABLE IF EXISTS `navigation_items`;
CREATE TABLE `navigation_items`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '导航项唯一标识，自增',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '导航项名称，例如\"首页\"或\"论坛\"',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '导航项链接地址，指向该项的目标页面',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '父导航项ID，用于表示子导航项。NULL表示顶级导航项',
  `position` int(0) NULL DEFAULT 0 COMMENT '导航项显示顺序，数值越小越靠前',
  `is_active` tinyint(1) NULL DEFAULT 1 COMMENT '导航项是否激活，TRUE表示显示，FALSE表示隐藏',
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '导航项创建时间',
  `updated_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '导航项最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of navigation_items
-- ----------------------------
INSERT INTO `navigation_items` VALUES (1, '首页', '/home', NULL, 1, 1, '2024-07-30 15:40:11', '2024-07-30 15:40:11');
INSERT INTO `navigation_items` VALUES (2, '创作', '/create', NULL, 2, 1, '2024-07-30 15:40:11', '2024-07-30 15:40:11');
INSERT INTO `navigation_items` VALUES (3, '论坛', '/forum', NULL, 3, 1, '2024-07-30 15:40:11', '2024-07-30 15:40:11');
INSERT INTO `navigation_items` VALUES (4, '商城', '/shop', NULL, 4, 1, '2024-07-30 15:40:11', '2024-07-30 15:40:11');
INSERT INTO `navigation_items` VALUES (5, '关于', '/about', NULL, 5, 1, '2024-07-30 15:40:11', '2024-07-30 15:40:11');

SET FOREIGN_KEY_CHECKS = 1;
