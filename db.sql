-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.26 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 prd-main 的数据库结构
CREATE DATABASE IF NOT EXISTS `prd-main` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `prd-main`;


-- 导出  表 prd-main.sys_menu 结构
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` varchar(42) NOT NULL,
  `pid` varchar(42) DEFAULT NULL,
  `text` varchar(64) DEFAULT NULL,
  `level` smallint(6) DEFAULT NULL,
  `state` varchar(8) DEFAULT NULL,
  `iconCls` varchar(64) DEFAULT NULL,
  `url` varchar(64) DEFAULT NULL,
  `instime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  prd-main.sys_menu 的数据：~6 rows (大约)
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `pid`, `text`, `level`, `state`, `iconCls`, `url`, `instime`) VALUES
	('1', '0', '系统', 1, 'open', 'fa fa-user', NULL, '2018-09-13 17:03:39'),
	('2', '0', '测试', 1, 'open', 'fa fa-file-text-o', NULL, '2018-09-13 17:16:13'),
	('3', '1', '系统管理', 2, 'open', 'fa fa-bullhorn', NULL, '2018-09-13 17:24:28'),
	('4', '1', '工作流管理', 2, 'open', 'fa fa-bullhorn', NULL, '2018-09-13 17:24:29'),
	('5', '3', '用户管理', 3, 'open', 'fa fa-bar-chart', 'view/sys/user/user.html', '2018-09-13 17:24:31'),
	('6', '3', '角色管理', 3, 'open', 'fa fa-list', NULL, '2018-09-13 17:24:32'),
	('7', '3', '组织管理', 3, 'open', 'fa fa-list', NULL, '2018-09-13 17:24:32'),
	('8', '3', '权限管理', 3, 'open', 'fa fa-list', NULL, '2018-09-13 17:24:32'),
	('9', '3', '菜单管理', 3, 'open', 'fa fa-list', NULL, '2018-09-13 17:24:32');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;


-- 导出  表 prd-main.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLENAME` varchar(64) NOT NULL DEFAULT '0',
  `ROLECODE` varchar(64) NOT NULL DEFAULT '0',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  prd-main.sys_role 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;


-- 导出  表 prd-main.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(64) NOT NULL,
  `PASSWORD` varchar(64) NOT NULL,
  `FULLNAME` varchar(64) DEFAULT NULL,
  `SEX` varchar(16) DEFAULT NULL,
  `EMAIL` varchar(64) DEFAULT NULL,
  `TELPHONE` varchar(32) DEFAULT NULL,
  `CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `LAST_LOGIN_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `STATUS` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- 正在导出表  prd-main.sys_user 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`ID`, `USERNAME`, `PASSWORD`, `FULLNAME`, `SEX`, `EMAIL`, `TELPHONE`, `CREATE_TIME`, `LAST_LOGIN_TIME`, `STATUS`) VALUES
	(11, 'zongwt', '123', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;


-- 导出  表 prd-main.sys_user_role 结构
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USER_ID` (`USER_ID`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  prd-main.sys_user_role 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
