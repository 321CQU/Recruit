/*
 Navicat Premium Data Transfer

 Source Server         : sqlite
 Source Server Type    : SQLite
 Source Server Version : 3035005 (3.35.5)
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3035005 (3.35.5)
 File Encoding         : 65001

 Date: 15/03/2023 11:22:01
*/

PRAGMA foreign_keys = false;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS "course";
CREATE TABLE "course" (
  "code" integer NOT NULL,
  "instructor" TEXT,
  "name" TEXT,
  "credit" real,
  "crouse_num" text,
  "session_id" INTEGER,
  PRIMARY KEY ("code"),
  CONSTRAINT "sessionkey" FOREIGN KEY ("session_id") REFERENCES "session" ("id") ON DELETE SET NULL ON UPDATE NO ACTION
);

-- ----------------------------
-- Records of course
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS "score";
CREATE TABLE "score" (
  "id" integer NOT NULL,
  "score" integer,
  "study_nature" TEXT,
  "course_nature" TEXT,
  "course_code" integer,
  PRIMARY KEY ("id"),
  CONSTRAINT "cousekey" FOREIGN KEY ("course_code") REFERENCES "course" ("code") ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- ----------------------------
-- Records of score
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for session
-- ----------------------------
DROP TABLE IF EXISTS "session";
CREATE TABLE "session" (
  "id" INTEGER NOT NULL,
  "year" integer NOT NULL,
  "is_autumn" integer NOT NULL,
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of session
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
  "id" integer NOT NULL,
  "name" text,
  "age" integer,
  "sex" integer,
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO "user" ("id", "name", "age", "sex") VALUES (1, 'dxy', 12, 1);
INSERT INTO "user" ("id", "name", "age", "sex") VALUES (2, 'hzx', 23, 0);
INSERT INTO "user" ("id", "name", "age", "sex") VALUES (3, 'ljj', 23, 1);
INSERT INTO "user" ("id", "name", "age", "sex") VALUES (4, 'wyc', 23, 1);
INSERT INTO "user" ("id", "name", "age", "sex") VALUES (5, 'qm', 22, 0);
COMMIT;

PRAGMA foreign_keys = true;
