/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80200 (8.2.0)
 Source Host           : localhost:3306
 Source Schema         : db_campus_trade_hub

 Target Server Type    : MySQL
 Target Server Version : 80200 (8.2.0)
 File Encoding         : 65001

 Date: 03/09/2024 23:50:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `consignee_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收货人电话',
  `consignee_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收货人姓名',
  `default_flag` tinyint NULL DEFAULT NULL COMMENT '是否默认地址',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户主键id',
  `school_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '学校名称',
  `campus_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所在校区',
  `dormitory_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '宿舍楼号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `add_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `add_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (2, '15949260233', '王玉', 1, 12, '虚拟智云大学', '创新校区', '1号宿舍楼');
INSERT INTO `address` VALUES (3, '12345236984', '王新', 1, 1, '虚拟智云大学', '创新校区', '3号宿舍楼');
INSERT INTO `address` VALUES (4, '12345203654', '吴新', 1, 15, '虚拟智云大学', '数字校区', '2号宿舍楼');
INSERT INTO `address` VALUES (5, '1398765678', '哦哦', 1, 5, '虚拟智云大学', '创新校区', '2号宿舍楼');

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `account_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '管理员账号',
  `admin_password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '账号密码',
  `admin_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '管理员名',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account_number`(`account_number` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'sa', 'sa123456', '超级管理员');
INSERT INTO `admin` VALUES (2, 'sa123', '123456', '徐六');
INSERT INTO `admin` VALUES (3, 'sa456', '123456', '宋琪');

-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `create_time` timestamp NOT NULL COMMENT '加入收藏的时间',
  `user_id` bigint NOT NULL COMMENT '用户主键id',
  `idle_id` bigint NOT NULL COMMENT '闲置商品主键id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `f_idle_id`(`idle_id` ASC) USING BTREE,
  INDEX `f_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `f_idle_id` FOREIGN KEY (`idle_id`) REFERENCES `idle_item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `f_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 92 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of favorite
-- ----------------------------
INSERT INTO `favorite` VALUES (8, '2024-04-05 04:38:58', 1, 77);
INSERT INTO `favorite` VALUES (10, '2024-04-05 04:39:25', 1, 48);
INSERT INTO `favorite` VALUES (13, '2024-04-05 04:41:08', 1, 40);
INSERT INTO `favorite` VALUES (25, '2024-04-05 12:55:47', 1, 1);
INSERT INTO `favorite` VALUES (27, '2024-04-05 12:56:10', 1, 35);
INSERT INTO `favorite` VALUES (28, '2024-04-05 13:10:11', 1, 49);
INSERT INTO `favorite` VALUES (29, '2024-04-05 13:10:22', 1, 58);
INSERT INTO `favorite` VALUES (31, '2024-04-05 13:11:43', 5, 75);
INSERT INTO `favorite` VALUES (32, '2024-04-05 13:11:56', 5, 68);
INSERT INTO `favorite` VALUES (33, '2024-04-05 13:12:15', 5, 13);
INSERT INTO `favorite` VALUES (34, '2024-04-05 13:12:43', 5, 43);
INSERT INTO `favorite` VALUES (35, '2024-04-05 13:13:08', 7, 84);
INSERT INTO `favorite` VALUES (36, '2024-04-05 13:13:21', 7, 36);
INSERT INTO `favorite` VALUES (37, '2024-04-05 13:13:41', 7, 49);
INSERT INTO `favorite` VALUES (38, '2024-04-05 13:14:20', 8, 9);
INSERT INTO `favorite` VALUES (39, '2024-04-05 13:14:36', 8, 1);
INSERT INTO `favorite` VALUES (40, '2024-04-05 13:14:49', 8, 71);
INSERT INTO `favorite` VALUES (41, '2024-04-05 13:15:36', 9, 82);
INSERT INTO `favorite` VALUES (42, '2024-04-05 13:15:48', 9, 58);
INSERT INTO `favorite` VALUES (43, '2024-04-05 13:15:55', 9, 6);
INSERT INTO `favorite` VALUES (44, '2024-04-05 13:16:04', 9, 19);
INSERT INTO `favorite` VALUES (45, '2024-04-05 13:16:17', 9, 70);
INSERT INTO `favorite` VALUES (46, '2024-04-05 13:16:28', 9, 74);
INSERT INTO `favorite` VALUES (47, '2024-04-05 13:16:52', 9, 57);
INSERT INTO `favorite` VALUES (48, '2024-04-05 13:17:04', 9, 49);
INSERT INTO `favorite` VALUES (49, '2024-04-05 13:17:35', 10, 67);
INSERT INTO `favorite` VALUES (50, '2024-04-05 13:18:12', 10, 5);
INSERT INTO `favorite` VALUES (51, '2024-04-05 13:18:17', 10, 74);
INSERT INTO `favorite` VALUES (52, '2024-04-05 13:19:18', 13, 74);
INSERT INTO `favorite` VALUES (53, '2024-04-05 13:19:35', 13, 31);
INSERT INTO `favorite` VALUES (54, '2024-04-05 13:19:52', 13, 33);
INSERT INTO `favorite` VALUES (55, '2024-04-05 13:21:01', 13, 82);
INSERT INTO `favorite` VALUES (59, '2024-04-05 13:27:29', 15, 76);
INSERT INTO `favorite` VALUES (60, '2024-04-05 13:27:46', 15, 44);
INSERT INTO `favorite` VALUES (61, '2024-04-05 13:27:58', 15, 62);
INSERT INTO `favorite` VALUES (62, '2024-04-05 13:30:22', 15, 54);
INSERT INTO `favorite` VALUES (63, '2024-04-05 13:30:39', 15, 60);
INSERT INTO `favorite` VALUES (64, '2024-04-05 13:31:47', 12, 75);
INSERT INTO `favorite` VALUES (65, '2024-04-05 13:31:57', 12, 54);
INSERT INTO `favorite` VALUES (66, '2024-04-05 13:32:40', 12, 70);
INSERT INTO `favorite` VALUES (67, '2024-04-05 13:33:19', 13, 70);
INSERT INTO `favorite` VALUES (68, '2024-04-05 13:33:40', 13, 54);
INSERT INTO `favorite` VALUES (69, '2024-04-05 13:34:14', 15, 70);
INSERT INTO `favorite` VALUES (70, '2024-04-05 13:35:29', 16, 75);
INSERT INTO `favorite` VALUES (71, '2024-04-05 13:35:46', 16, 44);
INSERT INTO `favorite` VALUES (76, '2024-04-23 07:15:05', 14, 87);
INSERT INTO `favorite` VALUES (78, '2024-04-23 07:15:14', 14, 84);
INSERT INTO `favorite` VALUES (79, '2024-04-23 07:15:31', 14, 48);
INSERT INTO `favorite` VALUES (80, '2024-04-23 07:24:24', 5, 14);
INSERT INTO `favorite` VALUES (81, '2024-04-23 07:27:57', 10, 92);
INSERT INTO `favorite` VALUES (82, '2024-04-23 07:41:26', 7, 87);
INSERT INTO `favorite` VALUES (83, '2024-04-23 07:41:40', 7, 86);
INSERT INTO `favorite` VALUES (84, '2024-04-23 07:48:18', 12, 92);
INSERT INTO `favorite` VALUES (85, '2024-04-23 07:51:43', 3, 87);
INSERT INTO `favorite` VALUES (86, '2024-04-23 07:51:55', 3, 97);
INSERT INTO `favorite` VALUES (88, '2024-04-23 14:27:28', 1, 97);
INSERT INTO `favorite` VALUES (90, '2024-04-24 03:20:17', 2, 87);
INSERT INTO `favorite` VALUES (91, '2024-04-24 03:20:27', 2, 56);

-- ----------------------------
-- Table structure for idle_item
-- ----------------------------
DROP TABLE IF EXISTS `idle_item`;
CREATE TABLE `idle_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `idle_details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '商品详情介绍',
  `idle_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '商品名称',
  `picture_list` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '商品图片',
  `idle_price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `idle_label` int NOT NULL COMMENT '商品类别',
  `release_time` timestamp NOT NULL COMMENT '发布时间',
  `idle_status` tinyint NULL DEFAULT NULL COMMENT '状态（1-上架、2-下架 、0-删除）',
  `user_id` bigint NOT NULL COMMENT '用户主键id',
  `attribute` tinyint NOT NULL COMMENT '商品属性（1-出售 、2-出租）',
  `lease_type` tinyint NULL DEFAULT NULL COMMENT '出租类型（按日出租1，按周出租2，按月出租3）',
  `origin_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '物品原价',
  `new_degree` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '物品新旧程度',
  `countNum` decimal(10, 2) NULL DEFAULT NULL COMMENT '物品点击次数及收藏次数总和',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idle_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `idle_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of idle_item
-- ----------------------------
INSERT INTO `idle_item` VALUES (1, '九成新，无较大磨损', '篮球', '[\"http://localhost:8088/image?imageName=file17118980368201003file17057726576181008篮球.jpg\"]', 10.00, 5, '2024-03-31 15:14:32', 1, 2, 1, 4, 20.00, '九成新', 31.00);
INSERT INTO `idle_item` VALUES (2, '配件齐全，很新无划痕，功能完好', 'CCD', '[\"http://localhost:8088/image?imageName=file17118982807331005file17057727490211009ccd.jpg\"]', 20.00, 9, '2024-03-31 15:18:06', 1, 1, 1, 4, 40.00, '八成新', 12.00);
INSERT INTO `idle_item` VALUES (3, '全新，未使用过', '置物架', '[\"http://localhost:8088/image?imageName=file17120535639131003file17057729225031010置物架.jpg\"]', 10.00, 9, '2024-04-02 10:26:11', 1, 6, 1, 4, 20.00, '全新', 15.00);
INSERT INTO `idle_item` VALUES (4, '下单点错了，懒得退，便宜出\n\n', '无糖面包', '[\"http://localhost:8088/image?imageName=file17120539162781007无糖面包.png\"]', 9.90, 1, '2024-04-02 10:31:59', 2, 5, 1, 4, 19.90, '全新', 31.00);
INSERT INTO `idle_item` VALUES (5, '太苦了，谁要。。\n', '冻干咖啡', '[\"http://localhost:8088/image?imageName=file17120540106831008冻干咖啡.png\"]', 10.00, 1, '2024-04-02 10:33:33', 1, 5, 1, 4, 20.00, '全新', 12.00);
INSERT INTO `idle_item` VALUES (6, '拍毕业照穿了一次，99新\n', '百褶裙', '[\"http://localhost:8088/image?imageName=file17120541002881009百褶裙.png\"]', 50.00, 2, '2024-04-02 10:35:03', 1, 5, 1, 4, 109.00, '九成新', 15.00);
INSERT INTO `idle_item` VALUES (7, '已经洗干净的，配帽子\n', '军训服', '[\"http://localhost:8088/image?imageName=file17120541995761010军训服.png\"]', 15.00, 2, '2024-04-02 10:36:41', 1, 5, 2, 2, 110.00, '八成新', 1.00);
INSERT INTO `idle_item` VALUES (8, '颜色偏正红\n', '唇釉', '[\"http://localhost:8088/image?imageName=file17120542638801011唇釉.png\"]', 20.00, 3, '2024-04-02 10:37:48', 1, 5, 1, 4, 60.00, '九成新', 19.00);
INSERT INTO `idle_item` VALUES (9, '5成新，很好打\n', '篮球', '[\"http://localhost:8088/image?imageName=file17120543461251012篮球（租）.png\"]', 1.00, 5, '2024-04-02 10:39:08', 1, 5, 2, 1, 40.00, '九成新', 13.00);
INSERT INTO `idle_item` VALUES (10, '要准备实习了，便宜出\n', '羽毛球拍子', '[\"http://localhost:8088/image?imageName=file17120545177711014羽毛球拍.png\"]', 10.00, 5, '2024-04-02 10:41:59', 1, 6, 1, 4, 40.00, '八成新', 20.00);
INSERT INTO `idle_item` VALUES (11, '要准备实习了，便宜出\n', '羽毛球拍子', '[\"http://localhost:8088/image?imageName=file17120545177711014羽毛球拍.png\"]', 10.00, 5, '2024-04-02 10:42:01', 1, 6, 1, 4, 40.00, '八成新', 12.00);
INSERT INTO `idle_item` VALUES (12, '全新版\n', '单片机原理与应用', '[\"http://localhost:8088/image?imageName=file17120545753761015计算机组成原理.png\"]', 10.00, 4, '2024-04-02 10:42:57', 1, 6, 1, 4, 20.00, '八成新', 31.00);
INSERT INTO `idle_item` VALUES (13, '清华大学出版社\n', '人机交互教程', '[\"http://localhost:8088/image?imageName=file17120546380111016人机交互.png\"]', 10.00, 4, '2024-04-02 10:44:00', 2, 6, 1, 4, 10.00, '全新', 14.00);
INSERT INTO `idle_item` VALUES (14, '全新，体验感很好\n', '水浒传', '[\"http://localhost:8088/image?imageName=file17120547050871017水浒（租）.png\"]', 10.00, 4, '2024-04-02 10:45:06', 1, 6, 2, 3, 20.00, '九成新', 25.00);
INSERT INTO `idle_item` VALUES (15, '专门按摩肩颈的，效果很好\n', '按摩仪', '[\"http://localhost:8088/image?imageName=file17120548139471018按摩.png\"]', 20.00, 9, '2024-04-02 10:46:56', 1, 6, 2, 3, 340.00, '九成新', 15.00);
INSERT INTO `idle_item` VALUES (16, '只戴过3天，99新', '纯银项链', '[\"http://localhost:8088/image?imageName=file17120549616201019纯银项链.png\"]', 100.00, 2, '2024-04-02 10:49:36', 1, 6, 1, 4, 280.00, '九成新', 25.00);
INSERT INTO `idle_item` VALUES (17, '喜欢就快来入手吧\n', '果冻', '[\"http://localhost:8088/image?imageName=file17120550942201020果冻.png\"]', 5.00, 1, '2024-04-02 10:51:36', 1, 2, 1, 4, 10.00, '全新', 11.00);
INSERT INTO `idle_item` VALUES (18, '穿着略微显胖，全新\n', '靴子', '[\"http://localhost:8088/image?imageName=file17120551424451021靴子.png\"]', 50.00, 2, '2024-04-02 10:52:25', 1, 2, 1, 4, 90.00, '全新', 20.00);
INSERT INTO `idle_item` VALUES (19, '很蓬的裙子，比较减龄\n', '礼服裙', '[\"http://localhost:8088/image?imageName=file17120552231701022蓬蓬晚礼服.png\"]', 20.00, 2, '2024-04-02 10:53:45', 1, 2, 2, 1, 1000.00, '九成新', 16.00);
INSERT INTO `idle_item` VALUES (20, '效果还不错，有人想要吗\n', '清洁泥膜', '[\"http://localhost:8088/image?imageName=file17120559005931023清洁泥膜.png\"]', 120.00, 3, '2024-04-02 11:05:03', 1, 2, 1, 4, 300.00, '九成新', 6.00);
INSERT INTO `idle_item` VALUES (21, '9成新\n', '电子表', '[\"http://localhost:8088/image?imageName=file17120561954401025电子表.png\"]', 100.00, 7, '2024-04-02 11:09:57', 1, 3, 1, 4, 170.00, '九成新', 22.00);
INSERT INTO `idle_item` VALUES (22, '胶卷送，拍照巨好看\n', '拍立得', '[\"http://localhost:8088/image?imageName=file17120565016731026拍立得（租.png\"]', 10.00, 7, '2024-04-02 11:15:04', 1, 3, 2, 1, 140.00, '九成新', 3.00);
INSERT INTO `idle_item` VALUES (23, '体验感很不错\n', '鼠标', '[\"http://localhost:8088/image?imageName=file17120565818121027鼠标.png\"]', 1.00, 7, '2024-04-02 11:16:24', 1, 3, 2, 1, 40.00, '全新', 5.00);
INSERT INTO `idle_item` VALUES (24, '双面的\n', '胶带', '[\"http://localhost:8088/image?imageName=file17120567630011028胶带.png\"]', 1.00, 8, '2024-04-02 11:19:25', 1, 3, 1, 4, 5.00, '全新', 8.00);
INSERT INTO `idle_item` VALUES (25, '包含电池\n', '计算器', '[\"http://localhost:8088/image?imageName=file17120569728571031计算器.png\"]', 5.00, 8, '2024-04-02 11:22:56', 1, 7, 1, 4, 10.00, '全新', 16.00);
INSERT INTO `idle_item` VALUES (26, '很厚实，耐用\n', '草稿纸', '[\"http://localhost:8088/image?imageName=file17120570947661032草稿纸.png\"]', 3.00, 8, '2024-04-02 11:24:56', 1, 7, 1, 4, 10.00, '全新', 10.00);
INSERT INTO `idle_item` VALUES (27, '任天堂NS出租\n\n', '游戏卡', '[\"http://localhost:8088image?imageName=file17120572125341033游戏卡.png\"]', 1.00, 9, '2024-04-02 11:27:16', 1, 7, 2, 1, 40.00, '全新', 1.00);
INSERT INTO `idle_item` VALUES (28, '未拆封', '医用口罩', '[\"http://localhost:8088/image?imageName=file17120574313771034医用口罩.png\"]', 1.00, 6, '2024-04-02 11:30:33', 1, 7, 1, 4, 10.00, '全新', 2.00);
INSERT INTO `idle_item` VALUES (29, '很出片，很好用\n', '双截棍', '[\"http://localhost:8088/image?imageName=file17120575123711035屏幕截图 2024-03-31 191436.png\"]', 10.00, 5, '2024-04-02 11:31:54', 1, 7, 1, 4, 20.00, '全新', 2.00);
INSERT INTO `idle_item` VALUES (30, '很好看，可以锻炼人的推理能力\n', '福尔摩斯探案集', '[\"http://localhost:8088/image?imageName=file17120577625201038福尔摩斯.png\"]', 1.00, 4, '2024-04-02 11:36:05', 1, 8, 2, 1, 20.00, '九成新', 12.00);
INSERT INTO `idle_item` VALUES (31, '毕业了出闲置\n', '退热贴', '[\"http://localhost:8088/image?imageName=file17120578397121039退热贴.png\"]', 10.00, 6, '2024-04-02 11:37:21', 1, 8, 1, 4, 20.00, '全新', 10.00);
INSERT INTO `idle_item` VALUES (32, '存储卡内存大\n', 'CCD', '[\"http://localhost:8088/image?imageName=file17120579293991040CCD.png\"]', 10.00, 7, '2024-04-02 11:38:51', 1, 8, 1, 4, 10.00, '九成新', 1.00);
INSERT INTO `idle_item` VALUES (33, '哪里痛贴哪里\n', '万通筋骨贴', '[\"http://localhost:8088/image?imageName=file17120580280161041万通筋骨贴.png\"]', 10.00, 6, '2024-04-02 11:40:30', 1, 8, 1, 4, 40.00, '九成新', 11.00);
INSERT INTO `idle_item` VALUES (34, '很好用，音质很好', '耳机', '[\"http://localhost:8088/image?imageName=file17120581024701042骨传导.png\"]', 30.00, 7, '2024-04-02 11:41:45', 1, 8, 1, 4, 80.00, '全新', 12.00);
INSERT INTO `idle_item` VALUES (35, '只戴过一次，9成新\n', '拳击手套', '[\"http://localhost:8088/image?imageName=file17120582426351045拳击手套.png\"]', 30.00, 5, '2024-04-02 11:44:05', 1, 9, 1, 4, 80.00, '全新', 2.00);
INSERT INTO `idle_item` VALUES (36, '安卓苹果都适配\n', '平板笔', '[\"http://localhost:8088/image?imageName=file17120586548811046平板笔.png\"]', 1.00, 8, '2024-04-02 11:50:57', 1, 9, 2, 1, 390.00, '九成新', 13.00);
INSERT INTO `idle_item` VALUES (37, '屑末很少，便于使用\n', '橡皮', '[\"http://localhost:8088/image?imageName=file17120587103031047橡皮.png\"]', 2.00, 8, '2024-04-02 11:51:52', 1, 9, 1, 4, 10.00, '全新', 12.00);
INSERT INTO `idle_item` VALUES (38, '涂答题卡必备\n', '2B铅笔', '[\"http://localhost:8088/image?imageName=file171205876552510482B铅笔.png\"]', 1.00, 8, '2024-04-02 11:52:47', 2, 9, 1, 4, 10.00, '九成新', 7.00);
INSERT INTO `idle_item` VALUES (39, '已清洗干净\n', '毛毯', '[\"http://localhost:8088/image?imageName=file17120588177101049毛毯.png\"]', 60.00, 9, '2024-04-02 11:53:40', 1, 9, 1, 4, 160.00, '九成新', 1.00);
INSERT INTO `idle_item` VALUES (40, '可以收纳不穿的衣服，很结实\n', '收纳箱', '[\"http://localhost:8088/image?imageName=file17120588602071050收纳箱.png\"]', 40.00, 9, '2024-04-02 11:54:21', 1, 9, 1, 4, 50.00, '全新', 2.00);
INSERT INTO `idle_item` VALUES (41, '公牛牌，值得信赖\n', '插排', '[\"http://localhost:8088/image?imageName=file17120589400951051插排租、.png\"]', 10.00, 9, '2024-04-02 11:55:42', 1, 9, 2, 2, 70.00, '全新', 13.00);
INSERT INTO `idle_item` VALUES (42, '红轴，全新\n', '键盘', '[\"http://localhost:8088/image?imageName=file17120589886221052键盘租.png\"]', 10.00, 7, '2024-04-02 11:56:30', 1, 9, 2, 2, 80.00, '九成新', 4.00);
INSERT INTO `idle_item` VALUES (43, '受益颇多\n', '前端开发入门与实战', '[\"http://localhost:8088/image?imageName=file17120592025671055前端开发.png\"]', 10.00, 4, '2024-04-02 12:00:07', 2, 10, 2, 2, 80.00, '全新', 16.00);
INSERT INTO `idle_item` VALUES (44, '受益颇多\n', '前端开发入门与实战', '[\"http://localhost:8088/image?imageName=file17120592025671055前端开发.png\"]', 10.00, 4, '2024-04-02 12:00:07', 2, 10, 2, 2, 80.00, '全新', 7.00);
INSERT INTO `idle_item` VALUES (45, '受益颇多\n', '前端开发入门与实战', '[\"http://localhost:8088/image?imageName=file17120592025671055前端开发.png\"]', 10.00, 4, '2024-04-02 12:00:06', 2, 10, 2, 2, 80.00, '全新', 26.00);
INSERT INTO `idle_item` VALUES (46, '受益颇多\n', '前端开发入门与实战', '[\"http://localhost:8088/image?imageName=file17120592025671055前端开发.png\"]', 10.00, 4, '2024-04-02 12:00:04', 2, 10, 2, 2, 80.00, '全新', 15.00);
INSERT INTO `idle_item` VALUES (47, '受益颇多\n', '前端开发入门与实战', '[\"http://localhost:8088/image?imageName=file17120592025671055前端开发.png\"]', 10.00, 4, '2024-04-02 12:00:07', 1, 10, 2, 2, 80.00, '全新', 5.00);
INSERT INTO `idle_item` VALUES (48, '170以上的姐妹们都可以穿哦\n', '旗袍', '[\"http://localhost:8088/image?imageName=file17120593235501056旗袍.png\"]', 10.00, 2, '2024-04-02 12:02:06', 1, 10, 2, 1, 210.00, '全新', 4.00);
INSERT INTO `idle_item` VALUES (49, '很流畅，保准不断墨\n', '中性笔', '[\"http://localhost:8088/image?imageName=file17120593865381057中性笔.png\"]', 5.00, 8, '2024-04-02 12:03:08', 1, 10, 1, 4, 10.00, '全新', 5.00);
INSERT INTO `idle_item` VALUES (50, '套装，很齐全\n', '指甲剪', '[\"http://localhost:8088/image?imageName=file17120594580051058指甲剪.png\"]', 3.00, 9, '2024-04-02 12:04:20', 1, 10, 2, 1, 20.00, '九成新', 12.00);
INSERT INTO `idle_item` VALUES (51, '日常，古风，舞台妆都可以\n', '妆造', '[\"http://localhost:8088/image?imageName=file17120595501061059化妆台.png\"]', 10.00, 3, '2024-04-02 12:05:52', 1, 10, 2, 1, 70.00, '全新', 2.00);
INSERT INTO `idle_item` VALUES (52, '具体造型私聊我\n', '美甲', '[\"http://localhost:8088/image?imageName=file17120597870751062美甲.png\"]', 50.00, 3, '2024-04-02 12:09:49', 1, 4, 2, 1, 130.00, '全新', 13.00);
INSERT INTO `idle_item` VALUES (53, '计算机组成原理，有笔记本版', '计算机组成原理', '[\"http://localhost:8088/image?imageName=file17120598901631063计算机组成原理.png\"]', 10.00, 4, '2024-04-02 12:11:33', 1, 4, 1, 4, 20.00, '全新', 17.00);
INSERT INTO `idle_item` VALUES (54, '古汉语版\n', '三国演义', '[\"http://localhost:8088/image?imageName=file17120599627771064三国演义.png\"]', 10.00, 4, '2024-04-02 12:12:45', 1, 4, 2, 2, 60.00, '全新', 9.00);
INSERT INTO `idle_item` VALUES (55, '可以治疗腰背疼痛\n', '红外线理疗灯', '[\"http://localhost:8088/image?imageName=file17120600633401065红外线理疗仪.png\"]', 20.00, 9, '2024-04-02 12:14:25', 1, 4, 2, 1, 190.00, '全新', 3.00);
INSERT INTO `idle_item` VALUES (56, '配置很齐全\n', '自行车', '[\"http://localhost:8088/image?imageName=file17120601286351066自行车.png\"]', 10.00, 5, '2024-04-02 12:15:30', 1, 4, 2, 2, 1100.00, '全新', 7.00);
INSERT INTO `idle_item` VALUES (57, '43码，只穿了一次，便宜出\n', '运动鞋', '[\"http://localhost:8088/image?imageName=file17120602268241067运动鞋.png\"]', 110.00, 2, '2024-04-02 12:17:08', 1, 4, 1, 4, 190.00, '全新', 4.00);
INSERT INTO `idle_item` VALUES (58, '配件齐全，功能完好，按天租用', '佳能照相机', '[\"http://localhost:8088/image?imageName=file17120603596571068file17057730699071011佳能照相机.jpg\"]', 50.00, 7, '2024-04-02 12:19:21', 1, 4, 1, 4, 100.00, '全新', 8.00);
INSERT INTO `idle_item` VALUES (59, '毕业了带不回去，便宜出\n', '男士控油洗面奶', '[\"http://localhost:8088/image?imageName=file17120604791141069男士控油洗面奶.png\"]', 40.00, 3, '2024-04-02 12:21:21', 1, 4, 1, 4, 80.00, '全新', 5.00);
INSERT INTO `idle_item` VALUES (60, '20包，可以当早饭吃\n', '葱香饼干', '[\"http://localhost:8088/image?imageName=file17120609612181072葱香饼干.png\"]', 10.00, 1, '2024-04-02 12:29:23', 1, 12, 1, 4, 10.00, '全新', 30.00);
INSERT INTO `idle_item` VALUES (61, '共两瓶，先到先得。\n', '黄桃罐头', '[\"http://localhost:8088/image?imageName=file17120610117901073黄桃罐头.png\"]', 10.90, 1, '2024-04-02 12:30:16', 1, 12, 1, 4, 30.00, '全新', 3.00);
INSERT INTO `idle_item` VALUES (62, '保正，好看', '汉服', '[\"http://localhost:8088/image?imageName=file17120614742101075汉服.png\"]', 10.00, 2, '2024-04-02 12:37:57', 1, 12, 2, 1, 410.00, '全新', 26.00);
INSERT INTO `idle_item` VALUES (63, '专用骑行手套，8成新\n', '手套', '[\"http://localhost:8088/image?imageName=file17120616338071076手套.png\"]', 40.00, 2, '2024-04-02 12:40:35', 1, 12, 1, 4, 60.00, '全新', 10.00);
INSERT INTO `idle_item` VALUES (64, '一个月后过期，需要的dd，四瓶哦\n', 'AD钙', '[\"http://localhost:8088/image?imageName=file17120620186191079AD钙.png\"]', 10.00, 1, '2024-04-02 12:47:01', 1, 13, 1, 4, 20.00, '全新', 1.00);
INSERT INTO `idle_item` VALUES (65, '九成新', '微喇裤', '[\"http://localhost:8088/image?imageName=file17120621056421080微喇裤.png\"]', 120.00, 2, '2024-04-02 12:48:34', 1, 13, 1, 4, 150.00, '全新', 4.00);
INSERT INTO `idle_item` VALUES (66, '卡通人形套\n', '敬礼蛙蛙', '[\"http://localhost:8088/image?imageName=file17120622234451081玩偶服.png\"]', 150.00, 9, '2024-04-02 12:50:26', 1, 13, 1, 4, 160.00, '全新', 1.00);
INSERT INTO `idle_item` VALUES (67, '好吃的！快来看看\n', '豆干', '[\"http://localhost:8088/image?imageName=file17120623614471082豆干.png\"]', 2.00, 1, '2024-04-02 12:52:43', 1, 13, 1, 4, 10.00, '全新', 3.00);
INSERT INTO `idle_item` VALUES (68, '西电第四版\n', '数字信号处理', '[\"http://localhost:8088/image?imageName=file17120643041831085数字信号处理.png\"]', 10.00, 4, '2024-04-02 13:25:06', 1, 14, 1, 4, 30.00, '九成新', 4.00);
INSERT INTO `idle_item` VALUES (69, '很省电，充电款\n', '闹钟', '[\"http://localhost:8088/image?imageName=file17120648730501087闹钟.png\"]', 10.00, 9, '2024-04-02 13:34:36', 1, 14, 1, 4, 20.00, '全新', 2.00);
INSERT INTO `idle_item` VALUES (70, '无缺页，有笔记，按本卖	', '计算机大二下所有专业书', '[\"http://localhost:8088/image?imageName=file17120649943031088file17057732831481014计卓专业书.jpg\"]', 10.00, 4, '2024-04-02 13:36:36', 1, 14, 1, 4, 40.00, '七成新', 7.00);
INSERT INTO `idle_item` VALUES (71, '无绳的，需要的dd\n', '跳绳', '[\"http://localhost:8088/image?imageName=file17120682663701089跳绳.png\"]', 10.00, 5, '2024-04-02 14:31:08', 2, 14, 1, 4, 20.00, '九成新', 20.00);
INSERT INTO `idle_item` VALUES (72, '无绳的，需要的dd\n', '跳绳', '[\"http://localhost:8088/image?imageName=file17120682663701089跳绳.png\"]', 10.00, 5, '2024-04-02 14:31:09', 1, 14, 1, 4, 20.00, '九成新', 13.00);
INSERT INTO `idle_item` VALUES (73, '无绳的，需要的dd\n', '跳绳', '[\"http://localhost:8088/image?imageName=file17120682663701089跳绳.png\"]', 10.00, 5, '2024-04-02 14:31:10', 0, 14, 1, 4, 20.00, '九成新', 15.00);
INSERT INTO `idle_item` VALUES (74, '300°的，有人需要吗，本人度数涨了，用不了了\n', '美瞳', '[\"http://localhost:8088/image?imageName=file17120684887051090美瞳.png\"]', 20.00, 3, '2024-04-02 14:34:50', 1, 14, 1, 4, 40.00, '全新', 6.00);
INSERT INTO `idle_item` VALUES (75, '第七版，谢希仁版\n', '计算机网络', '[\"http://localhost:8088/image?imageName=file17120693430001091计算机网络.png\"]', 5.00, 4, '2024-04-02 14:49:05', 1, 14, 1, 4, 20.00, '八成新', 9.00);
INSERT INTO `idle_item` VALUES (76, '第七版，谢希仁版\n', '计算机网络', '[\"http://localhost:8088/image?imageName=file17120693430001091计算机网络.png\"]', 5.00, 4, '2024-04-02 14:49:06', 0, 14, 1, 4, 20.00, '八成新', 17.00);
INSERT INTO `idle_item` VALUES (77, '共五个，配套卖', '发夹', '[\"http://localhost:8088/image?imageName=file17120695785641094file17057738083591022发夹.jpg\"]', 5.00, 9, '2024-04-02 14:53:00', 1, 15, 1, 4, 10.00, '八成新', 4.00);
INSERT INTO `idle_item` VALUES (78, '共五个，配套卖', '发夹', '[\"http://localhost:8088/image?imageName=file17120695785641094file17057738083591022发夹.jpg\"]', 5.00, 9, '2024-04-02 14:53:00', 2, 15, 1, 4, 10.00, '八成新', 15.00);
INSERT INTO `idle_item` VALUES (79, '毕业了带不走，想便宜卖了', '多肉', '[\"http://localhost:8088/image?imageName=file17120701756681095file17059923126151002OIP-C.jpg\"]', 10.00, 9, '2024-04-02 15:02:59', 2, 15, 1, 4, 20.00, '九成新', 16.00);
INSERT INTO `idle_item` VALUES (80, '九成新	ipad', 'ipad', '[\"http://localhost:8088/image?imageName=file17120704571791096file17061462668701003ipad.jpg\"]', 1999.00, 7, '2024-04-02 15:07:39', 2, 15, 1, 4, 2260.00, '九成新', 16.00);
INSERT INTO `idle_item` VALUES (81, '刚买，包装未拆，日期较新', '感冒灵颗粒', '[\"http://localhost:8088/image?imageName=file17120711104971098file17057734347311016感冒灵颗粒.jpg\"]', 10.00, 6, '2024-04-02 15:18:32', 1, 16, 1, 4, 30.00, '全新', 16.00);
INSERT INTO `idle_item` VALUES (82, '长6米，很有氛围感\n', '星星灯', '[\"http://localhost:8088/image?imageName=file17121436337801004星星等.png\"]', 10.00, 9, '2024-04-03 11:27:15', 1, 1, 1, 4, 20.00, '全新', 8.00);
INSERT INTO `idle_item` VALUES (83, '送墨汁\n', '钢笔', '[\"http://localhost:8088/image?imageName=file17121437772601005钢笔.png\"]', 20.00, 8, '2024-04-03 11:29:38', 1, 1, 1, 4, 30.00, '全新', 8.00);
INSERT INTO `idle_item` VALUES (84, '学姐严选\n', '公图板+尺', '[\"http://localhost:8088/image?imageName=file17121438941261006工图板.png\"]', 10.00, 8, '2024-04-03 11:31:36', 1, 1, 2, 2, 130.00, '九成新', 6.00);
INSERT INTO `idle_item` VALUES (85, '深蓝色的长裙，适合主持节目穿\n', '礼服长裙', '[\"http://localhost:8088/image?imageName=file17121443760951007神蓝色长裙.png\"]', 50.00, 2, '2024-04-03 11:39:38', 1, 1, 2, 1, 1000.00, '九成新', 2.00);
INSERT INTO `idle_item` VALUES (86, '175，男女都可以穿\n', '黑色西服', '[\"http://localhost:8088/image?imageName=file17121446566211008黑色西服.png\"]', 20.00, 2, '2024-04-03 11:44:18', 2, 1, 2, 1, 230.00, '全新', 9.00);
INSERT INTO `idle_item` VALUES (87, '退货期限过了，有人喜欢吗\n', '卫衣', '[\"http://localhost:8088/image?imageName=file17122136796911003卫衣.png\"]', 30.00, 2, '2024-04-04 06:54:41', 1, 1, 1, 4, 260.00, '全新', 38.00);
INSERT INTO `idle_item` VALUES (89, '第二版\n', '软件工程与开发技术', '[\"http://localhost:8088/image?imageName=file17138561645661003软件工程与开发技术.png\"]', 10.00, 4, '2024-04-23 07:09:27', 1, 9, 1, 4, 20.00, '全新', 0.00);
INSERT INTO `idle_item` VALUES (90, '涂答题卡必备', '2B铅笔', '[\"http://localhost:8088/image?imageName=file171385637069710042B铅笔.png\"]', 5.00, 8, '2024-04-23 07:12:52', 1, 15, 1, 4, 10.00, '全新', 0.00);
INSERT INTO `idle_item` VALUES (91, '五成新', '镜子', '[\"http://localhost:8088/image?imageName=file17138567147491005镜子.png\"]', 5.00, 9, '2024-04-23 07:18:36', 1, 13, 1, 4, 10.00, '全新', 2.00);
INSERT INTO `idle_item` VALUES (92, '十分详细，保过', '高数笔记', '[\"http://localhost:8088/image?imageName=file17138571900471007草稿纸.png\"]', 10.00, 8, '2024-04-23 07:26:31', 1, 5, 2, 3, 40.00, '九成新', 3.00);
INSERT INTO `idle_item` VALUES (93, '要准备实习了，便宜出', '乒乓球拍子', '[\"http://localhost:8088/image?imageName=file17138573850211008乒乓球拍（租）.png\"]', 10.00, 5, '2024-04-23 07:29:47', 2, 10, 1, 4, 40.00, '九成新', 0.00);
INSERT INTO `idle_item` VALUES (94, '颜色不太适合我，退不了了，便宜出呀', '羽绒服', '[\"http://localhost:8088/image?imageName=file17138574832871009羽绒服.png\"]', 130.00, 2, '2024-04-23 07:31:25', 1, 10, 1, 4, 230.00, '全新', 1.00);
INSERT INTO `idle_item` VALUES (95, '感觉平时不太用，需要的可以小刀', '充电宝', '[\"http://localhost:8088/image?imageName=file17138579845731010充电宝（租.png\"]', 60.00, 9, '2024-04-23 07:39:47', 1, 10, 1, 4, 80.00, '全新', 2.00);
INSERT INTO `idle_item` VALUES (96, '单只10公斤', '哑铃', '[\"http://localhost:8088/image?imageName=file17138582210411011哑铃（租）.png\"]', 10.00, 5, '2024-04-23 07:43:43', 1, 7, 2, 3, 50.00, '九成新', 0.00);
INSERT INTO `idle_item` VALUES (97, '175，男女都可以穿', '正装', '[\"http://localhost:8088/image?imageName=file17138583802971012黑色西服.png\"]', 10.00, 2, '2024-04-23 07:46:23', 1, 7, 2, 1, 120.00, '九成新', 6.00);
INSERT INTO `idle_item` VALUES (98, '碳纤维的，很不错', '羽毛球拍', '[\"http://localhost:8088/image?imageName=file17138585841051013羽毛球拍（租）.png\"]', 40.00, 5, '2024-04-23 07:49:45', 1, 12, 1, 4, 70.00, '全新', 0.00);
INSERT INTO `idle_item` VALUES (99, '西电第四版', '数字信号处理', '[\"http://localhost:8088/image?imageName=file17138588733801014数字信号处理.png\"]', 10.00, 4, '2024-04-23 07:54:35', 1, 3, 1, 4, 30.00, '全新', 0.00);
INSERT INTO `idle_item` VALUES (100, '纸张可拆卸，买就送替换装', '笔记本', '[\"http://localhost:8088/image?imageName=file17138626776861018笔记本.png\"]', 10.00, 8, '2024-04-23 08:58:47', 1, 1, 1, 4, 20.00, '全新', 0.00);
INSERT INTO `idle_item` VALUES (101, '纸张可拆卸，买就送替换装', '笔记本', '[\"http://localhost:8088/image?imageName=file17138627784611019笔记本.png\"]', 10.00, 8, '2024-04-23 08:59:43', 2, 1, 1, 4, 20.00, '全新', 0.00);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `user_id` bigint NOT NULL COMMENT '用户主键id',
  `idle_id` bigint NOT NULL COMMENT '闲置主键id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '留言内容',
  `create_time` datetime NOT NULL COMMENT '留言时间',
  `to_user` bigint NOT NULL COMMENT '所回复的用户',
  `to_message` bigint NULL DEFAULT NULL COMMENT '所回复的留言',
  `status` bigint NULL DEFAULT NULL COMMENT '留言是否被查看',
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图片评论',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id_index`(`user_id` ASC) USING BTREE,
  INDEX `idle_id_index`(`idle_id` ASC) USING BTREE,
  INDEX `to_user_index`(`to_user` ASC) USING BTREE,
  INDEX `me_to_message`(`to_message` ASC) USING BTREE,
  CONSTRAINT `me_idle_id` FOREIGN KEY (`idle_id`) REFERENCES `idle_item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `me_to_message` FOREIGN KEY (`to_message`) REFERENCES `message` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `me_to_user` FOREIGN KEY (`to_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `me_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (2, 12, 14, '能便宜一点吗', '2024-04-02 12:30:43', 6, NULL, 0, NULL);
INSERT INTO `message` VALUES (3, 12, 2, '在哪个校区', '2024-04-02 12:31:11', 1, NULL, 1, NULL);
INSERT INTO `message` VALUES (4, 14, 6, '好好看', '2024-04-02 13:37:04', 5, NULL, 0, NULL);
INSERT INTO `message` VALUES (5, 15, 87, '能不能便宜些', '2024-04-05 11:56:35', 1, NULL, 0, NULL);
INSERT INTO `message` VALUES (6, 1, 87, '价格好商量', '2024-04-05 11:57:09', 15, 5, 0, NULL);
INSERT INTO `message` VALUES (13, 1, 55, '可以便宜一点吗', '2024-04-06 08:33:11', 4, NULL, 0, NULL);
INSERT INTO `message` VALUES (14, 4, 55, '可以的亲亲', '2024-04-06 08:33:28', 1, 13, 0, NULL);
INSERT INTO `message` VALUES (15, 1, 58, '可以便宜点吗', '2024-04-06 12:14:17', 4, NULL, 0, NULL);
INSERT INTO `message` VALUES (16, 4, 58, '不可以的亲', '2024-04-06 12:14:39', 1, 15, 0, NULL);
INSERT INTO `message` VALUES (17, 1, 6, '你好啊', '2024-04-18 12:27:52', 5, NULL, 0, NULL);
INSERT INTO `message` VALUES (18, 1, 6, '哈哈哈哈', '2024-04-18 12:30:45', 5, NULL, 0, NULL);
INSERT INTO `message` VALUES (19, 1, 6, 'hello', '2024-04-18 12:31:35', 5, NULL, 0, NULL);
INSERT INTO `message` VALUES (20, 1, 6, '欧克', '2024-04-18 12:45:58', 5, NULL, 0, NULL);
INSERT INTO `message` VALUES (21, 5, 6, '欧克', '2024-04-18 12:49:36', 1, 20, 0, NULL);
INSERT INTO `message` VALUES (22, 7, 87, '能不能再多拍几张照片我看看', '2024-04-23 08:15:05', 1, NULL, 1, NULL);
INSERT INTO `message` VALUES (23, 1, 87, NULL, '2024-04-23 08:26:31', 7, 22, 1, 'http://localhost:8088/image?imageName=file17138607889131015卫衣.jpg');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `notice_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '公告类型',
  `key_sentence` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标题或关键句',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '公告内容',
  `announce_time` timestamp NULL DEFAULT NULL COMMENT '发布时间',
  `admin_id` bigint NULL DEFAULT NULL COMMENT '发布的管理员id',
  `admin_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '发布的管理员名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '公告', '校园闲置物品租售平台上线公告', '为了积极响应国家循环经济和资源节约型社会建设号召，同时优化校园资源配置、促进绿色低碳生活理念在校园内的普及与实践，我校特此推出“idlePlanet”——线上闲置物品租售平台。', '2024-02-22 20:16:02', 1, '超级管理员');
INSERT INTO `notice` VALUES (2, '通知', ' 校园闲置物品租售平台“idlePlanet”更新', '各位同学请注意！为庆祝新学期的到来，我校闲置物品交易平台将于即日起至月底开展“春季大清仓”活动，鼓励大家积极参与清理个人闲置物品，上传至平台进行交易。活动期间，优质卖家有机会获得平台提供的神秘大奖，快来参与吧！', '2024-04-02 16:41:28', 1, '超级管理员');

-- ----------------------------
-- Table structure for order_address
-- ----------------------------
DROP TABLE IF EXISTS `order_address`;
CREATE TABLE `order_address`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `consignee_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '收货人',
  `consignee_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '收货人手机号',
  `detail_address` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `orderId`(`order_id` ASC) USING BTREE,
  INDEX `order_id_index`(`order_id` ASC) USING BTREE,
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_address
-- ----------------------------
INSERT INTO `order_address` VALUES (10, 18, '王新', '12345236984', '虚拟智云大学创新校区3号宿舍楼');
INSERT INTO `order_address` VALUES (23, 31, '王新', '12345236984', '虚拟智云大学创新校区3号宿舍楼');
INSERT INTO `order_address` VALUES (24, 32, '哦哦', '1398765678', '虚拟智云大学创新校区2号宿舍楼');
INSERT INTO `order_address` VALUES (26, 34, '哦哦', '1398765678', '虚拟智云大学创新校区2号宿舍楼');
INSERT INTO `order_address` VALUES (27, 35, '哦哦', '1398765678', '虚拟智云大学创新校区2号宿舍楼');
INSERT INTO `order_address` VALUES (28, 36, '哦哦', '1398765678', '虚拟智云大学创新校区2号宿舍楼');
INSERT INTO `order_address` VALUES (29, 37, '哦哦', '1398765678', '虚拟智云大学创新校区2号宿舍楼');
INSERT INTO `order_address` VALUES (30, 38, '哦哦', '1398765678', '虚拟智云大学创新校区2号宿舍楼');
INSERT INTO `order_address` VALUES (31, 39, '哦哦', '1398765678', '虚拟智云大学创新校区2号宿舍楼');
INSERT INTO `order_address` VALUES (32, 40, '哦哦', '1398765678', '虚拟智云大学创新校区2号宿舍楼');
INSERT INTO `order_address` VALUES (33, 41, '哦哦', '1398765678', '虚拟智云大学创新校区2号宿舍楼');
INSERT INTO `order_address` VALUES (34, 42, '吴新', '12345203654', '虚拟智云大学数字校区2号宿舍楼');
INSERT INTO `order_address` VALUES (35, 43, '吴新', '12345203654', '虚拟智云大学数字校区2号宿舍楼');
INSERT INTO `order_address` VALUES (36, 44, '吴新', '12345203654', '虚拟智云大学数字校区2号宿舍楼');
INSERT INTO `order_address` VALUES (37, 45, '吴新', '12345203654', '虚拟智云大学数字校区2号宿舍楼');
INSERT INTO `order_address` VALUES (38, 46, '吴新', '12345203654', '虚拟智云大学数字校区2号宿舍楼');
INSERT INTO `order_address` VALUES (39, 47, '哦哦', '1398765678', '虚拟智云大学创新校区2号宿舍楼');
INSERT INTO `order_address` VALUES (40, 48, '哦哦', '1398765678', '虚拟智云大学创新校区2号宿舍楼');
INSERT INTO `order_address` VALUES (41, 49, '吴新', '12345203654', '虚拟智云大学数字校区2号宿舍楼');
INSERT INTO `order_address` VALUES (48, 56, '王新', '12345236984', '虚拟智云大学创新校区3号宿舍楼');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `order_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '用户主键id',
  `idle_id` bigint NOT NULL COMMENT '闲置物品主键id',
  `order_price` decimal(10, 2) NOT NULL COMMENT '订单总价',
  `payment_status` tinyint NULL DEFAULT NULL COMMENT '支付状态',
  `create_time` timestamp NOT NULL COMMENT '创建时间',
  `payment_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `order_status` tinyint NOT NULL COMMENT '订单状态（0-待付款、1-待发货、2-待收货、3-已完成、4-已取消）',
  `seller` bigint NOT NULL COMMENT '给谁通知新消息\r\n给谁通知新消息',
  `status` bigint NULL DEFAULT NULL COMMENT '订单是否为新状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `or_user_id`(`user_id` ASC) USING BTREE,
  INDEX `or_idle_id`(`idle_id` ASC) USING BTREE,
  CONSTRAINT `or_idle_id` FOREIGN KEY (`idle_id`) REFERENCES `idle_item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `or_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '171206125995110002', 12, 12, 10.00, 0, '2024-04-02 12:34:20', NULL, 4, 3, 1);
INSERT INTO `orders` VALUES (2, '171206441857410003', 14, 12, 10.00, 0, '2024-04-02 13:26:59', NULL, 4, 1, 0);
INSERT INTO `orders` VALUES (3, '171206450788210004', 14, 50, 3.00, 0, '2024-04-02 13:28:28', NULL, 4, 1, 0);
INSERT INTO `orders` VALUES (4, '171206618716810005', 14, 6, 50.00, 0, '2024-04-02 13:56:27', NULL, 4, 1, 0);
INSERT INTO `orders` VALUES (17, '171232307521310002', 10, 60, 10.00, 0, '2024-04-05 13:17:55', NULL, 4, 1, 0);
INSERT INTO `orders` VALUES (18, '171232449929010003', 1, 54, 10.00, 1, '2024-04-05 13:41:39', '2024-04-05 13:42:19', 6, 1, 1);
INSERT INTO `orders` VALUES (31, '171240638699110012', 1, 58, 50.00, 1, '2024-04-06 12:26:27', '2024-04-06 12:26:34', 4, 0, 0);
INSERT INTO `orders` VALUES (32, '171369989161010003', 5, 82, 10.00, 0, '2024-04-21 11:44:52', '2024-04-21 11:47:14', 4, 0, 1);
INSERT INTO `orders` VALUES (34, '171370035903910005', 5, 80, 1999.00, 0, '2024-04-21 11:52:39', '2024-04-21 11:52:44', 4, 15, 1);
INSERT INTO `orders` VALUES (35, '171370052894310006', 5, 79, 10.00, 0, '2024-04-21 11:55:29', '2024-04-21 11:55:35', 4, 15, 1);
INSERT INTO `orders` VALUES (36, '171370058034910007', 5, 78, 5.00, 0, '2024-04-21 11:56:20', '2024-04-21 11:56:24', 4, 15, 1);
INSERT INTO `orders` VALUES (37, '171371343925810002', 5, 80, 1999.00, 1, '2024-04-21 15:30:39', '2024-04-21 15:30:47', 4, 15, 1);
INSERT INTO `orders` VALUES (38, '171371363058310003', 5, 79, 10.00, 1, '2024-04-21 15:33:51', '2024-04-21 15:33:55', 4, 15, 1);
INSERT INTO `orders` VALUES (39, '171371379945610004', 5, 77, 5.00, 1, '2024-04-21 15:36:39', '2024-04-21 15:36:44', 2, 15, 1);
INSERT INTO `orders` VALUES (40, '171371382331710005', 5, 78, 5.00, 0, '2024-04-21 15:37:03', NULL, 5, 0, 1);
INSERT INTO `orders` VALUES (41, '171371435906310006', 5, 78, 5.00, 1, '2024-04-21 15:45:59', '2024-04-21 15:46:04', 4, 15, 1);
INSERT INTO `orders` VALUES (42, '171371457793810007', 15, 8, 20.00, 1, '2024-04-21 15:49:38', '2024-04-21 15:49:43', 4, 5, 1);
INSERT INTO `orders` VALUES (43, '171371553089010008', 15, 5, 10.00, 1, '2024-04-21 16:05:31', '2024-04-21 16:05:39', 4, 5, 1);
INSERT INTO `orders` VALUES (44, '171371579152310009', 15, 9, 1.00, 1, '2024-04-21 16:09:52', '2024-04-21 16:09:57', 6, 15, 1);
INSERT INTO `orders` VALUES (45, '171371650146710002', 15, 74, 20.00, 1, '2024-04-21 16:21:41', '2024-04-21 16:21:51', 2, 14, 1);
INSERT INTO `orders` VALUES (46, '171371651931310003', 15, 6, 50.00, 0, '2024-04-21 16:21:59', NULL, 5, 15, 1);
INSERT INTO `orders` VALUES (47, '171371698246610002', 5, 80, 1999.00, 1, '2024-04-21 16:29:42', '2024-04-21 16:29:48', 3, 5, 1);
INSERT INTO `orders` VALUES (48, '171371703928610003', 5, 79, 10.00, 1, '2024-04-21 16:30:39', '2024-04-21 16:30:46', 3, 5, 1);
INSERT INTO `orders` VALUES (49, '171371748492210004', 15, 4, 9.90, 1, '2024-04-21 16:38:05', '2024-04-21 16:38:13', 4, 5, 1);
INSERT INTO `orders` VALUES (56, '171388252236510003', 1, 50, 3.00, 0, '2024-04-23 14:28:42', NULL, 5, 1, 1);

-- ----------------------------
-- Table structure for report_item
-- ----------------------------
DROP TABLE IF EXISTS `report_item`;
CREATE TABLE `report_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idle_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '被举报的商品',
  `idle_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '该商品所属用户',
  `reporter_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '\r\n编辑该举报信息的用户',
  `report_details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `report_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '举报商品的类型',
  `picture_list` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `report_time` timestamp(6) NULL DEFAULT NULL,
  `handle_status` tinyint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of report_item
-- ----------------------------

-- ----------------------------
-- Table structure for report_order
-- ----------------------------
DROP TABLE IF EXISTS `report_order`;
CREATE TABLE `report_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '被举报的订单编号',
  `report_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '举报原因',
  `reporter_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '\r\n编辑该举报信息的用户',
  `tran_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '交易状态',
  `deal_way` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '期待的处理方式',
  `report_details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '具体原因',
  `picture_list` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `report_time` timestamp(6) NULL DEFAULT NULL,
  `handle_status` tinyint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of report_order
-- ----------------------------

-- ----------------------------
-- Table structure for report_user
-- ----------------------------
DROP TABLE IF EXISTS `report_user`;
CREATE TABLE `report_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '被举报的商品',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '被举报用户账号',
  `reporter_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '\r\n编辑该举报信息的用户',
  `report_details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '举报原因',
  `report_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '举报类型',
  `picture_list` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '截图证明',
  `report_time` timestamp(6) NULL DEFAULT NULL COMMENT '举报时间',
  `handle_status` tinyint NULL DEFAULT NULL COMMENT '处理状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of report_user
-- ----------------------------

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `create_time` timestamp NOT NULL COMMENT '创建时间',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `idle_id` bigint NOT NULL COMMENT '商品id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------
INSERT INTO `shopping_cart` VALUES (1, '2024-04-02 12:31:18', 12, 14);
INSERT INTO `shopping_cart` VALUES (3, '2024-04-02 12:44:40', 13, 8);
INSERT INTO `shopping_cart` VALUES (4, '2024-04-02 12:44:59', 13, 16);
INSERT INTO `shopping_cart` VALUES (5, '2024-04-02 13:26:57', 14, 12);
INSERT INTO `shopping_cart` VALUES (6, '2024-04-02 14:56:17', 15, 66);
INSERT INTO `shopping_cart` VALUES (7, '2024-04-23 07:16:30', 14, 52);
INSERT INTO `shopping_cart` VALUES (8, '2024-04-23 07:20:33', 1, 91);
INSERT INTO `shopping_cart` VALUES (9, '2024-04-23 07:24:23', 5, 14);
INSERT INTO `shopping_cart` VALUES (11, '2024-04-23 07:27:56', 10, 92);
INSERT INTO `shopping_cart` VALUES (12, '2024-04-23 07:41:31', 7, 87);
INSERT INTO `shopping_cart` VALUES (13, '2024-04-23 07:48:03', 12, 92);
INSERT INTO `shopping_cart` VALUES (14, '2024-04-23 07:51:56', 3, 97);
INSERT INTO `shopping_cart` VALUES (15, '2024-04-23 07:52:06', 3, 16);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `account_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '账号',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '昵称',
  `user_password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '账号密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
  `sex` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别',
  `age` int UNSIGNED NULL DEFAULT NULL COMMENT '年龄',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号',
  `campus` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '所在校区',
  `sign_in_time` timestamp NULL DEFAULT NULL COMMENT '注册时间',
  `user_status` tinyint NULL DEFAULT NULL COMMENT '用户状态（0-正常、1-封禁）',
  `message_status` tinyint NULL DEFAULT NULL COMMENT '消息状态（0-无新消息、1-有新消息）',
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '收款二维码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '6210321', '布偶', '123456', 'http://localhost:8088/image?imageName=file17120711944371099file170576665188910020cf309b9c90bbf7ae4cdead79537133.jpg', '男', 18, '13569842310', '创新校区', '2024-03-31 13:48:39', 0, 2, 'http://localhost:8088/image?imageName=file17138821049871002收款码.png');
INSERT INTO `user` VALUES (2, '6210322', '球球', '123456', 'http://localhost:8088/image?imageName=file17120694796691092file170577223716210048c93c2d6325224a4fde9770275d9515.jpg', '女', 19, '18362549872', '创新校区', '2024-03-31 13:49:31', 0, 0, 'http://localhost:8088/image?imageName=file17138821049871002收款码.png');
INSERT INTO `user` VALUES (3, '6210323', 'jdkhw', '123456', 'http://localhost:8088/image?imageName=file17120596995511060file170576665188910020cf309b9c90bbf7ae4cdead79537133.jpg', '女', 18, '00000000000', '科技校区', '2024-03-31 13:50:13', 0, 0, 'http://localhost:8080/image?imageName=file17120561299811024oRgjhoJbct.png');
INSERT INTO `user` VALUES (4, '6210324', 'saying', '147258', 'http://localhost:8088/image?imageName=file17120596995511060file170576665188910020cf309b9c90bbf7ae4cdead79537133.jpg', '女', 18, '00000000000', '创新校区', '2024-03-31 13:50:42', 0, 0, 'http://localhost:8088/image?imageName=file17138821049871002收款码.png');
INSERT INTO `user` VALUES (5, '6210325', '萝卜鸡蛋', '123456', 'http://localhost:8088/image?imageName=file17120536107761005file170577218991310039966522adea92462292091d3c9999b7.jpg', '男', 18, '00000000000', '创新校区', '2024-04-02 10:19:19', 0, 1, 'http://localhost:8088/image?imageName=file17138570994211006file17120532852931002oRgjhoJbct.png');
INSERT INTO `user` VALUES (6, '6210326', 'spider', '789456', 'http://localhost:8088/image?imageName=file17120535858271004file1705766695668100392f7db8473db7397934d8d7efb920c7.jpg', '女', 20, '15642365984', '科技校区', '2024-04-02 10:20:09', 0, 1, 'http://localhost:8080/image?imageName=file17120536312511006oRgjhoJbct.png');
INSERT INTO `user` VALUES (7, '6210327', '九七', '123456', 'http://localhost:8088/image?imageName=file17120568563291029file1705772356431100638746465a9141a401279526798aecf5.jpg', '男', 18, '00000000000', '数字校区', '2024-04-02 11:20:26', 0, 0, 'http://localhost:8088/image?imageName=file17138619370411016file17120532852931002oRgjhoJbct.png');
INSERT INTO `user` VALUES (8, '6210328', '嘻嘻', '123456', 'http://localhost:8088/image?imageName=file17120575886041036file17057721450931002807da1d2f6c8267ca3c0ab41be99b56.jpg', '女', 20, '15932460259', '创新校区', '2024-04-02 11:32:32', 0, 0, 'http://localhost:8080/image?imageName=file17120576123761037oRgjhoJbct.pnghttp://localhost:8088/image?imageName=file17138821049871002收款码.png');
INSERT INTO `user` VALUES (9, '6210329', '啦啦啦', '123456', 'http://localhost:8088/image?imageName=file17120581787951043file170577223716210048c93c2d6325224a4fde9770275d9515.jpg', '男', 19, '00000000000', '数字校区', '2024-04-02 11:42:31', 0, 0, 'http://localhost:8080/image?imageName=file17120581926901044oRgjhoJbct.png');
INSERT INTO `user` VALUES (10, '6210330', '五三', '123456', 'http://localhost:8088/image?imageName=file17120590922401053file170577248714610074f692abbc403f9614128a1a2c06734f.jpg', '女', 18, '00000000000', '创新校区', '2024-04-02 11:57:24', 0, 0, 'http://localhost:8080/image?imageName=file17120536312511006oRgjhoJbct.png');
INSERT INTO `user` VALUES (11, '6210331', '嘿嘿', '123456', 'http://localhost:8088/image?imageName=file17120590922401053file170577248714610074f692abbc403f9614128a1a2c06734f.jpg', '男', 18, '00000000000', '数字校区', '2024-04-02 12:22:31', 1, 0, 'http://localhost:8088/image?imageName=file17138821049871002收款码.png');
INSERT INTO `user` VALUES (12, '6210332', 'wji', '123456', 'http://localhost:8088/image?imageName=file17120608578981070file1705772356431100638746465a9141a401279526798aecf5.jpg', '男', 21, '00000000000', '创新校区', '2024-04-02 12:27:05', 0, 0, 'http://localhost:8080/image?imageName=file17120613933641074oRgjhoJbct.png');
INSERT INTO `user` VALUES (13, '6210333', '琪琪', '123456', 'http://localhost:8088/image?imageName=file17120616760701077file170576665188910020cf309b9c90bbf7ae4cdead79537133.jpg', '女', 18, '00000000000', '创新校区', '2024-04-02 12:33:55', 0, 0, 'http://localhost:8080/image?imageName=file17120616957231078oRgjhoJbct.png');
INSERT INTO `user` VALUES (14, '6210334', '物语', '123456', 'http://localhost:8088/image?imageName=file17120625284931083file170577229523210059966522adea92462292091d3c9999b7.jpg', '男', 18, '00000000000', '创新校区', '2024-04-02 12:54:31', 0, 0, 'http://localhost:8088/image?imageName=file17138821049871002收款码.png');
INSERT INTO `user` VALUES (15, '6210335', '吾心', '123456', 'http://localhost:8088/image?imageName=file17120694796691092file170577223716210048c93c2d6325224a4fde9770275d9515.jpg', '男', 18, '00000000000', '创新校区', '2024-04-02 14:50:33', 0, 0, 'http://localhost:8088/image?imageName=file17138821049871002收款码.png');
INSERT INTO `user` VALUES (16, '6210336', '爱新', '123456', 'http://localhost:8088/image?imageName=file17120694796691092file170577223716210048c93c2d6325224a4fde9770275d9515.jpg', '男', 18, '00000000000', '创新校区', '2024-04-02 15:10:15', 0, 6, 'http://localhost:8088/image?imageName=file17138821049871002收款码.png');
INSERT INTO `user` VALUES (17, '6210340', '期望', '123456', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '男', 18, '00000000000', '创新校区', '2024-04-23 09:05:30', 0, 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
