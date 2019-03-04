/*
 Navicat Premium Data Transfer

 Source Server         : myConnection
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : trip_book

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 21/01/2019 22:49:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cars
-- ----------------------------
DROP TABLE IF EXISTS `cars`;
CREATE TABLE `cars`  (
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` int(10) NULL DEFAULT NULL,
  `numCars` int(255) NULL DEFAULT NULL,
  `numAvail` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cars
-- ----------------------------
INSERT INTO `cars` VALUES ('C1', 'NewYork', 30, 500, 200);
INSERT INTO `cars` VALUES ('C2', 'London', 40, 200, 100);
INSERT INTO `cars` VALUES ('C3', 'Beijing', 50, 300, 20);
INSERT INTO `cars` VALUES ('C4', 'Chengdu', 25, 300, 45);
INSERT INTO `cars` VALUES ('C5', 'Yunnan', 78, 340, 23);
INSERT INTO `cars` VALUES ('C6', 'Hefei', 56, 234, 98);
INSERT INTO `cars` VALUES ('C7', 'Hangzhou', 23, 584, 77);
INSERT INTO `cars` VALUES ('C8', 'Suzhou', 66, 578, 49);
INSERT INTO `cars` VALUES ('C9', 'Guangzhou', 450, 340, 23);

-- ----------------------------
-- Table structure for carsorder
-- ----------------------------
DROP TABLE IF EXISTS `carsorder`;
CREATE TABLE `carsorder`  (
  `cOrderId` int(11) NOT NULL,
  `custName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `carType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`cOrderId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of carsorder
-- ----------------------------
INSERT INTO `carsorder` VALUES (1, 'Tom', 'C1');
INSERT INTO `carsorder` VALUES (2, 'Rose', 'C2');
INSERT INTO `carsorder` VALUES (3, 'Lucy', 'C3');
INSERT INTO `carsorder` VALUES (4, 'Jack', 'C4');
INSERT INTO `carsorder` VALUES (5, 'Kim', 'C5');
INSERT INTO `carsorder` VALUES (6, 'Alice', 'C6');
INSERT INTO `carsorder` VALUES (7, 'Jay', 'C7');
INSERT INTO `carsorder` VALUES (8, 'Zhou', 'C8');
INSERT INTO `carsorder` VALUES (9, 'Tom', 'C4');

-- ----------------------------
-- Table structure for customers
-- ----------------------------
DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers`  (
  `custName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `passWord` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`custName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customers
-- ----------------------------
INSERT INTO `customers` VALUES ('alice', '888');
INSERT INTO `customers` VALUES ('cly', '123456');
INSERT INTO `customers` VALUES ('jack', '000');
INSERT INTO `customers` VALUES ('jay', '111');
INSERT INTO `customers` VALUES ('June', '678');
INSERT INTO `customers` VALUES ('lucy', '123');
INSERT INTO `customers` VALUES ('lyx', '23333');
INSERT INTO `customers` VALUES ('rose', '456');
INSERT INTO `customers` VALUES ('tom', '666');

-- ----------------------------
-- Table structure for flightorder
-- ----------------------------
DROP TABLE IF EXISTS `flightorder`;
CREATE TABLE `flightorder`  (
  `fOrderId` int(11) NOT NULL,
  `flightType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `custName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`fOrderId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flightorder
-- ----------------------------
INSERT INTO `flightorder` VALUES (1, 'F1', 'Rose');
INSERT INTO `flightorder` VALUES (2, 'F2', 'Tom');
INSERT INTO `flightorder` VALUES (3, 'F3', 'Lucy');
INSERT INTO `flightorder` VALUES (4, 'F3', 'Tom');
INSERT INTO `flightorder` VALUES (5, 'F2', 'Jack');
INSERT INTO `flightorder` VALUES (6, 'F4', 'Rose');
INSERT INTO `flightorder` VALUES (7, 'F5', 'Alice');
INSERT INTO `flightorder` VALUES (8, 'F4', 'Jay');
INSERT INTO `flightorder` VALUES (9, 'F3', 'Alice');

-- ----------------------------
-- Table structure for flights
-- ----------------------------
DROP TABLE IF EXISTS `flights`;
CREATE TABLE `flights`  (
  `flightNum` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` int(50) NULL DEFAULT NULL,
  `numSeats` int(255) NULL DEFAULT NULL,
  `numAvail` int(255) NULL DEFAULT NULL,
  `FromCity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ArivCity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`flightNum`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flights
-- ----------------------------
INSERT INTO `flights` VALUES ('1', 300, 100, 20, 'Xian', 'France');
INSERT INTO `flights` VALUES ('2', 600, 200, 38, 'Beijing', 'Chengdu');
INSERT INTO `flights` VALUES ('3', 500, 30, 12, 'NewYork', 'London');
INSERT INTO `flights` VALUES ('4', 450, 242, 89, 'Chengdu', 'Yunnan');
INSERT INTO `flights` VALUES ('5', 238, 87, 23, 'Hangzhou', 'Beijing');
INSERT INTO `flights` VALUES ('6', 890, 350, 120, 'Guangzhou', 'Beijing');

-- ----------------------------
-- Table structure for hotels
-- ----------------------------
DROP TABLE IF EXISTS `hotels`;
CREATE TABLE `hotels`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` int(10) NULL DEFAULT NULL,
  `numRooms` int(255) NULL DEFAULT NULL,
  `numAvail` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hotels
-- ----------------------------
INSERT INTO `hotels` VALUES ('Bye', 'Yunnan', 789, 322, 89);
INSERT INTO `hotels` VALUES ('Happy', 'Shenzhen', 890, 400, 200);
INSERT INTO `hotels` VALUES ('kaixin', 'Hangzhou', 345, 123, 45);
INSERT INTO `hotels` VALUES ('qitian', 'NewYork', 500, 300, 100);
INSERT INTO `hotels` VALUES ('qq', 'Fuzhou', 200, 500, 10);
INSERT INTO `hotels` VALUES ('rujia', 'London', 300, 400, 20);
INSERT INTO `hotels` VALUES ('Tian', 'Chengdu', 678, 456, 244);

-- ----------------------------
-- Table structure for hotelsorder
-- ----------------------------
DROP TABLE IF EXISTS `hotelsorder`;
CREATE TABLE `hotelsorder`  (
  `hOrderId` int(11) NOT NULL,
  `hotelType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `custName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hotelRoom` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`hOrderId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hotelsorder
-- ----------------------------
INSERT INTO `hotelsorder` VALUES (1, 'H1', 'tom', '101');
INSERT INTO `hotelsorder` VALUES (2, 'H2', 'lucy', '305');
INSERT INTO `hotelsorder` VALUES (3, 'H3', 'alice', '478');
INSERT INTO `hotelsorder` VALUES (4, 'H4', 'jack', '899');
INSERT INTO `hotelsorder` VALUES (5, 'H5', 'jay', '236');
INSERT INTO `hotelsorder` VALUES (6, 'H6', 'rose', '678');
INSERT INTO `hotelsorder` VALUES (7, 'H7', 'zhou', '456');
INSERT INTO `hotelsorder` VALUES (8, 'H8', 'jone`', '189');
INSERT INTO `hotelsorder` VALUES (9, 'H5', 'Jay', '505');

SET FOREIGN_KEY_CHECKS = 1;
