# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 123.57.166.240 (MySQL 5.5.5-10.3.17-MariaDB)
# Database: cobra
# Generation Time: 2020-11-12 01:51:48 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `config`;

CREATE TABLE `config` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `key` varchar(100) DEFAULT NULL COMMENT 'key',
  `value` text DEFAULT NULL COMMENT '值',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;

INSERT INTO `config` (`id`, `key`, `value`, `remark`)
VALUES
	('1','websocket-single','0','同一个用户是否只允许一个websock接收，1是，0可多个'),
	('2','system-login-single','0','同一个系统用户登录是否只允许一个，1是，0可多个');

/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table dept
# ------------------------------------------------------------

DROP TABLE IF EXISTS `dept`;

CREATE TABLE `dept` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级',
  `name` varchar(100) DEFAULT NULL COMMENT '部门名',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `seq` int(11) DEFAULT NULL COMMENT '顺序',
  `status` int(11) DEFAULT NULL COMMENT '状态,1启用，0禁用',
  `leader` varchar(100) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `del` int(11) DEFAULT NULL COMMENT '删除，0未删除，1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门';

LOCK TABLES `dept` WRITE;
/*!40000 ALTER TABLE `dept` DISABLE KEYS */;

INSERT INTO `dept` (`id`, `parent_id`, `name`, `remark`, `seq`, `status`, `leader`, `phone`, `email`, `del`, `create_time`, `modify_time`)
VALUES
	('4c23c700c7ae4599a37bc6e3af8249e4','d7d58db2d447447489df2e529de49622','深圳总公司','',2,1,NULL,NULL,NULL,0,'2019-12-13 23:38:19','2019-12-14 16:44:26'),
	('844f142ff2674baea7524e7b8b6352e2','d7d58db2d447447489df2e529de49622','上海总公司','',3,0,'','','',0,'2020-03-24 16:18:51','2020-10-19 14:24:08'),
	('d7d58db2d447447489df2e529de49622',NULL,'眼镜蛇','',1,1,NULL,NULL,NULL,0,'2019-12-13 23:34:32','2019-12-13 23:34:32'),
	('d851fa6a0c9440deb1dc8ef86db39729','d7d58db2d447447489df2e529de49622','北京总公司','',1,1,'张三','','',0,'2019-12-14 15:51:57','2019-12-19 23:39:32');

/*!40000 ALTER TABLE `dept` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table doc_directory
# ------------------------------------------------------------

DROP TABLE IF EXISTS `doc_directory`;

CREATE TABLE `doc_directory` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(200) DEFAULT NULL COMMENT '目录名',
  `parent_id` varchar(20) DEFAULT NULL COMMENT '上级id',
  `del` tinyint(1) DEFAULT NULL COMMENT '是否删除，0未删除，1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档目录';



# Dump of table doc_file
# ------------------------------------------------------------

DROP TABLE IF EXISTS `doc_file`;

CREATE TABLE `doc_file` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `directory_id` varchar(32) DEFAULT NULL COMMENT '目录',
  `filename` varchar(100) DEFAULT NULL COMMENT '文件名',
  `content` text DEFAULT NULL COMMENT '文件内容',
  `file_type` tinyint(1) DEFAULT NULL COMMENT '文件内容类型，1:markdown',
  `del` tinyint(1) DEFAULT NULL COMMENT '是否删除，0未删除，1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档';



# Dump of table log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `log`;

CREATE TABLE `log` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `start_time` datetime DEFAULT NULL COMMENT '请求时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `headers` text DEFAULT NULL COMMENT '头信息',
  `ip` varchar(100) DEFAULT NULL COMMENT 'ip',
  `duration` bigint(20) DEFAULT NULL COMMENT '时长',
  `params` text DEFAULT NULL COMMENT '参数',
  `params_body` text DEFAULT NULL COMMENT '参数body',
  `result_headers` text DEFAULT NULL COMMENT '结果头信息',
  `result_body` text DEFAULT NULL COMMENT '结果数据体',
  `method` varchar(100) DEFAULT NULL COMMENT '请求参数',
  `user_agent` varchar(500) DEFAULT NULL COMMENT 'http头ua(200不够用，从QQ里点进来的话会很长)',
  `ip_forward` varchar(300) DEFAULT NULL COMMENT 'IP列表',
  `ip_address` varchar(300) DEFAULT NULL COMMENT 'ip地址',
  `url` varchar(300) DEFAULT NULL COMMENT '请求的url',
  `terminal_type` varchar(100) DEFAULT NULL COMMENT '终端类型',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级',
  `name` varchar(100) DEFAULT NULL COMMENT '菜单名',
  `code` varchar(100) DEFAULT NULL COMMENT '编码',
  `type` int(11) DEFAULT NULL COMMENT '类型，1目录，2菜单，3功能,4子页面',
  `url` varchar(200) DEFAULT NULL COMMENT 'url',
  `file_path` varchar(200) DEFAULT NULL COMMENT '文件地址',
  `show` int(11) DEFAULT NULL COMMENT '是否显示,0未显示，1显示',
  `seq` int(11) DEFAULT NULL COMMENT '顺序',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `cache` tinyint(1) DEFAULT NULL COMMENT '是否缓存，0不缓存，1缓存',
  `del` int(11) DEFAULT NULL COMMENT '删除，0未删除，1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单';

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;

INSERT INTO `menu` (`id`, `parent_id`, `name`, `code`, `type`, `url`, `file_path`, `show`, `seq`, `icon`, `cache`, `del`, `create_time`, `modify_time`, `remark`)
VALUES
	('047815769a5b4fa2bb942386ffda4c74','bdbeb37ad7cd4ddb9ae12e6acf97dba1','文件管理','systemUploadFileIndex',2,'/system/uploadFile/index','/system/upload_file/index',1,9,'iconfont iaiai-file-fill',1,0,'2020-01-22 14:28:05','2020-01-22 14:49:32',NULL),
	('06386b357af54e9682cd61968ec4dc48','fe3a8de081a84a9e9514f728178772fd','用户管理','systemUserIndex',2,'/system/user/index','/system/user/index',1,1,'iconfont iaiai-ios-people',1,0,'2019-12-08 19:00:57','2019-12-20 20:14:54',NULL),
	('0a371a05962e444bab67a43953d189be','e8f4d13cf7ad4d85bf4ea379e67dd304','测试发送消息','websocketSend',4,'/system/websocket/send','/system/websocket/send',1,1,'iconfont iaiai-communityfill',0,0,'2020-10-26 11:17:20','2020-10-29 13:58:38',''),
	('119693d238614f7bb7f5ddfecf5f2679','06386b357af54e9682cd61968ec4dc48','修改密码','system:user:edit:pwd',3,'','',1,4,'',1,0,'2019-12-21 19:33:36','2019-12-21 19:33:36',NULL),
	('1612c2ca0cb44ca5bf496b32e7325256','06386b357af54e9682cd61968ec4dc48','添加用户','system:user:add',3,'','',1,1,'',1,0,'2019-12-21 16:58:11','2019-12-21 20:00:12',NULL),
	('1d74dea832df4c6087d541d68a44f14c','d7905ff62e4b46be83d1cfcf9d1b1282','neditor编辑器','codingNeditorIndex',2,'/coding/neditor/index','/coding/neditor/index',1,2,'iconfont iaiai-buffer',0,0,'2019-12-07 20:18:16','2020-11-07 11:39:12',NULL),
	('20ad8a7b2e4c4e1f83171d001334e4d8','5e56a2ddaa3f4bfba0d47b133210b9bc','部门列表','system:dept:query',3,'','',1,5,'',1,0,'2020-03-24 16:57:17','2020-03-24 16:58:04',NULL),
	('2311eb99227047dc9ccd9fd422b25bb0','5e56a2ddaa3f4bfba0d47b133210b9bc','删除部门','system:dept:del',3,'','',1,3,'',1,0,'2019-12-22 12:06:04','2019-12-22 12:07:32',NULL),
	('25896035da354c508f3a274c85e0c463','5e56a2ddaa3f4bfba0d47b133210b9bc','添加部门','system:dept:add',3,'','',1,1,'',1,0,'2019-12-22 12:05:28','2019-12-22 12:06:54',NULL),
	('333d2f5039ff4cbeb775f84e5c08c4a2','de5a7fd7916046be8c4cb80e305b7306','编辑新闻','newsEditHtml',4,'/news/edit/html','/news/edit_html',1,4,'iconfont iaiai-file-fill',0,0,'2020-10-24 16:26:38','2020-10-24 16:27:00',''),
	('3f16dab9a47c44f7b71474cc7270268b','06386b357af54e9682cd61968ec4dc48','编辑用户','system:user:edit',3,'','',1,2,'',1,0,'2019-12-21 17:31:09','2019-12-21 17:31:09',NULL),
	('3f7d8ed2e1474194a33fe9d787c47917','f746493b014f4707a52c8732fff92300','角色列表','system:role:query',3,'','',1,6,'',1,0,'2020-03-24 16:51:50','2020-03-24 16:57:42',NULL),
	('486ae2315fbf4501916571b0410c3811','de5a7fd7916046be8c4cb80e305b7306','添加新闻-markdown','newsAddMarkdown',4,'/news/add/markdown','/news/add.markdown',1,1,'iconfont iaiai-file-fill',0,0,'2020-02-08 01:13:13','2020-10-24 13:51:04',NULL),
	('49fe3a9bae434f709cd740c9feba1dbb','65c9fca27e054d6089ffa5e3a069b8bf','菜单管理','systemMenuIndex',2,'/system/menu/index','/system/menu/index',1,3,'iconfont iaiai-menu',1,0,'2019-12-08 19:02:22','2019-12-20 20:15:37',NULL),
	('4d45d45909744e20aed3c48815dd2d89','731202099c004ca8975c84b6cf2ab27d','踢下线','system:online:exit',3,'','',1,1,'',1,0,'2020-11-06 11:30:49','2020-11-06 11:30:49',''),
	('4dcde000465b4e29b054bbf682806ca7','06386b357af54e9682cd61968ec4dc48','导出用户','system:user:export',3,'','',1,6,'',1,0,'2020-03-24 16:09:42','2020-03-24 16:09:42',NULL),
	('519f88287839448ab0197459dec3d158','d1ff9e4178bd43bcb3633c843d070648','文档','docManager',1,'','',1,3,'iconfont iaiai-file-copy-fill',1,0,'2020-05-25 21:57:33','2020-10-24 17:01:16',NULL),
	('562eef50750f450baf6e8a53867ed2ed','f746493b014f4707a52c8732fff92300','修改状态','system:role:edit:status',3,'','',1,5,'',1,0,'2019-12-21 20:43:22','2019-12-21 20:43:22',NULL),
	('57a2c2608a6c40ffa8b3131f7b868ad3','49fe3a9bae434f709cd740c9feba1dbb','编辑菜单','system:menu:edit',3,'','',1,2,'',1,0,'2019-12-22 11:32:53','2019-12-22 11:32:53',NULL),
	('59a912f8556545cb88e76c2cb8e84fc9',NULL,'开发专用','coding',1,'','',1,3,'iconfont iaiai-buffer',1,0,'2019-12-06 17:26:21','2020-11-07 11:27:12','此目录下菜单全部是测试用，不允许给正式人配置'),
	('5aeebb8f2fb34356b56195c36ffdc465','d7905ff62e4b46be83d1cfcf9d1b1282','wangeditor编辑器','codingWangeditorIndex',2,'/coding/wangeditor/index','/coding/wangeditor/index',1,4,'iconfont iaiai-buffer',1,0,'2019-12-08 18:52:49','2020-11-07 11:38:58',NULL),
	('5e56a2ddaa3f4bfba0d47b133210b9bc','fe3a8de081a84a9e9514f728178772fd','部门管理','systemDeptIndex',2,'/system/dept/index','/system/dept/index',1,4,'iconfont iaiai-ios-people',1,0,'2019-12-18 17:29:18','2019-12-20 20:16:00',NULL),
	('5ef90637bcb248ecaa69248462acb1f2','de5a7fd7916046be8c4cb80e305b7306','删除新闻','news:del',3,'','',1,5,'',1,0,'2020-02-08 01:13:43','2020-10-24 16:26:17',NULL),
	('65c9fca27e054d6089ffa5e3a069b8bf','bdbeb37ad7cd4ddb9ae12e6acf97dba1','权限','systemPower',1,'','',1,2,'iconfont iaiai-boss-fill',1,0,'2020-11-07 10:54:00','2020-11-07 10:54:00',''),
	('6e5fd31c1433461083676bf9cfe798ba','d7905ff62e4b46be83d1cfcf9d1b1282','froala编辑器','codingFroalaIndex',2,'/coding/froala/index','/coding/froala/index',1,1,'iconfont iaiai-buffer',1,0,'2019-12-07 09:02:47','2020-11-07 11:39:29',NULL),
	('6ff664f30229463aa20f36f681990534','ea8205f1d54d46a3aeb8ee837a1bca36','图表展示','codingChartsOne',2,'/coding/charts/charts','/coding/charts/charts',1,1,'iconfont iaiai-md-analytics',1,0,'2020-11-11 10:48:22','2020-11-11 11:05:01',''),
	('7139ddb6eb0d4af0922e733eb175626c','d7905ff62e4b46be83d1cfcf9d1b1282','tinymce','codingTinymceIndex',2,'/coding/tinymce/index','/coding/tinymce/index',1,6,'iconfont iaiai-buffer',0,0,'2020-02-16 22:56:35','2020-11-07 11:38:42',NULL),
	('731202099c004ca8975c84b6cf2ab27d','bdbeb37ad7cd4ddb9ae12e6acf97dba1','在线用户','systemOnlineIndex',2,'/system/online/index','/system/online/index',1,8,'iconfont iaiai-account',1,0,'2019-12-26 20:19:04','2019-12-26 20:19:42',NULL),
	('800f5a3c48c54a92a975babb6e8495d4','49fe3a9bae434f709cd740c9feba1dbb','删除菜单','system:menu:del',3,'','',1,3,'',1,0,'2019-12-22 11:33:15','2019-12-22 11:33:15',NULL),
	('83076d6193b04a7e9ee4c94d9efbc47a','f746493b014f4707a52c8732fff92300','角色导出','system:role:export',3,'','',1,7,'',1,0,'2020-03-24 17:21:01','2020-03-24 17:21:01',NULL),
	('8553f19392144646a149f3381791eacd','5e56a2ddaa3f4bfba0d47b133210b9bc','编辑部门','system:dept:edit',3,'','',1,2,'',1,0,'2019-12-22 12:05:44','2019-12-22 12:07:01',NULL),
	('879b74428ca540bfba8835d6eab147d3','d7905ff62e4b46be83d1cfcf9d1b1282','ueditor编辑器','codingUeditorIndex',2,'/coding/ueditor/index','/coding/ueditor/index',1,3,'iconfont iaiai-buffer',0,0,'2020-04-28 17:04:13','2020-11-07 11:39:05',NULL),
	('88859bffcba34daa910222da353e2ef4','e8148999f552459fa1b5f0ad53894039','岗位列表','system:post:query',3,'','',1,5,'',1,0,'2020-03-24 17:08:05','2020-03-24 17:08:05',NULL),
	('897d47d901464adbb17fe867af5f3413','e8148999f552459fa1b5f0ad53894039','添加岗位','system:post:add',3,'','',1,1,'',1,0,'2019-12-22 12:21:48','2019-12-22 12:21:48',NULL),
	('8c03e43acef949ff93f31bca60fb06f2','bdbeb37ad7cd4ddb9ae12e6acf97dba1','关于','about',2,'/system/about','/system/about',1,100,'iconfont iaiai-person',1,0,'2020-11-07 17:24:46','2020-11-07 17:24:46',''),
	('8ea82b3ecbf747eeadf14b074d68f2c0','9d44fc2537854143830c8c2d119abc33','编辑新闻状态','news:type:edit:show',3,'','',1,4,'',1,0,'2020-01-24 13:33:32','2020-01-24 13:33:32',NULL),
	('8f894499171a4d0b9b820be042d95f0c','f746493b014f4707a52c8732fff92300','删除角色','system:role:del',3,'','',1,3,'',1,0,'2019-12-21 20:41:33','2019-12-21 20:41:33',NULL),
	('90305e1669404bac85592cf4a984e2ff','e8148999f552459fa1b5f0ad53894039','删除岗位','system:post:del',3,'','',1,3,'',1,0,'2019-12-22 12:22:48','2019-12-22 12:22:48',NULL),
	('91cb1f658afc48d4bdb13cdfbe929cb0','bdbeb37ad7cd4ddb9ae12e6acf97dba1','日志','systemLogIndex',2,'/system/log/index','/system/log',1,7,'iconfont iaiai-md-log',1,0,'2019-12-18 19:06:59','2019-12-26 20:19:52',NULL),
	('9870df4d69b54683ad99283955cb919e','e8148999f552459fa1b5f0ad53894039','编辑岗位','system:post:edit',3,'','',1,2,'',1,0,'2019-12-22 12:22:36','2019-12-22 12:22:36',NULL),
	('98dfc52244e3476aa2e9cfbc28f7e05e','de5a7fd7916046be8c4cb80e305b7306','显示','news:edit:show',3,'','',1,6,'',1,0,'2020-10-24 17:12:07','2020-10-24 17:12:07',''),
	('9d44fc2537854143830c8c2d119abc33','d1ff9e4178bd43bcb3633c843d070648','新闻类型','newsTypeIndex',2,'/news/type/index','/news/type/index',1,1,'iconfont iaiai-sort',1,0,'2020-01-23 15:35:02','2020-01-23 15:35:02',NULL),
	('a2478deed9a9403482aeedff3057ed5a','f746493b014f4707a52c8732fff92300','设置权限','system:role:permission',3,'','',1,4,'',1,0,'2019-12-21 20:42:44','2019-12-21 20:42:44',NULL),
	('a43590564b274df9887993c346b0c8dd','49fe3a9bae434f709cd740c9feba1dbb','添加菜单','system:menu:add',3,'','',1,1,'',1,0,'2019-12-22 11:32:38','2019-12-22 11:32:38',NULL),
	('aa9c08dc32ed4b639f9ad1088c6ea4ca','06386b357af54e9682cd61968ec4dc48','编辑状态','system:user:edit:status',3,'','',1,5,'',1,0,'2019-12-21 20:17:15','2019-12-21 20:17:15',NULL),
	('ac8ed412143c4df78904e0411b52a8c4','f746493b014f4707a52c8732fff92300','编辑角色','system:role:edit',3,'','',1,2,'',1,0,'2019-12-21 20:34:10','2019-12-21 20:34:10',NULL),
	('ae718b41b9f44d969d145401a9476c31','d7905ff62e4b46be83d1cfcf9d1b1282','CKEditor','codingCkeditor',2,'/coding/ckeditor/index','/coding/ckeditor/index',1,7,'iconfont iaiai-buffer',0,0,'2020-10-23 16:48:41','2020-11-07 11:38:24',''),
	('bdbeb37ad7cd4ddb9ae12e6acf97dba1',NULL,'系统管理','system',1,'','',1,5,'iconfont iaiai-windows-fill',1,0,'2019-12-08 18:59:59','2020-11-01 17:36:36',NULL),
	('bf4572aa49954051a108f7a081db1ff0','5e56a2ddaa3f4bfba0d47b133210b9bc','编辑状态','system:dept:edit:status',3,'','',1,4,'',1,0,'2019-12-22 12:06:30','2019-12-22 12:07:41',NULL),
	('bfa5c5dc5b6e443186d6a16a8a90f9a7','49fe3a9bae434f709cd740c9feba1dbb','编辑状态','system:menu:edit:status',3,'','',1,4,'',1,0,'2019-12-22 11:55:56','2019-12-22 11:55:56',NULL),
	('c70f4d30508e4f398d68387c3d6a5cbb','bdbeb37ad7cd4ddb9ae12e6acf97dba1','配置参数','systemConfig',2,'/system/config/index','/system/config/index',1,11,'iconfont iaiai-repairfill',0,0,'2020-11-01 15:47:40','2020-11-01 15:47:40',''),
	('d1ff9e4178bd43bcb3633c843d070648',NULL,'文档','documents',1,'','',1,4,'iconfont iaiai-file-copy-fill',1,0,'2020-01-23 13:32:32','2020-11-01 17:36:40',NULL),
	('d25907607ea341858b59e7ab892954f1','9d44fc2537854143830c8c2d119abc33','编辑新闻','news:type:edit',3,'','',1,2,'',1,0,'2020-01-24 13:30:58','2020-01-24 13:30:58',NULL),
	('d610383f336a4208afc2477d1f2551ec','59a912f8556545cb88e76c2cb8e84fc9','二维码','codingQrcode',2,'/coding/qrcode/index','/coding/qrcode/index',1,3,'iconfont iaiai-qrcode',1,0,'2020-11-11 14:47:24','2020-11-11 14:47:24',''),
	('d6c3f305e82c40338b04491392918369','59a912f8556545cb88e76c2cb8e84fc9','上传图片','codingUploadIndex',2,'/coding/upload/index','/coding/upload/index',1,8,'iconfont iaiai-buffer',1,0,'2020-01-21 20:56:34','2020-11-07 11:38:15',NULL),
	('d7905ff62e4b46be83d1cfcf9d1b1282','59a912f8556545cb88e76c2cb8e84fc9','大文本编辑器','codingHtml',1,'','',1,1,'iconfont iaiai-edit-file',1,0,'2020-11-11 09:26:33','2020-11-11 09:26:33',''),
	('dc2aa81d781d45c6857cc712280e9dea','de5a7fd7916046be8c4cb80e305b7306','编辑新闻','newsEditMarkdown',4,'/news/edit/markdown','/news/edit_markdown',1,3,'iconfont iaiai-file-fill',0,0,'2020-02-08 01:13:30','2020-10-24 15:33:01',NULL),
	('de5a7fd7916046be8c4cb80e305b7306','d1ff9e4178bd43bcb3633c843d070648','新闻列表','newsIndex',2,'/news/index','/news/index',1,2,'iconfont iaiai-news',1,0,'2020-01-24 13:40:09','2020-01-24 13:40:19',NULL),
	('de979a8d6fed4d57aaa84c1a1c3157a6',NULL,'首页','home',2,'/home','/home',1,2,'iconfont iaiai-home-fill',1,0,'2019-12-05 15:45:07','2020-11-01 17:36:48',NULL),
	('df99cf7248044211a405a87887bc1fdb','d7905ff62e4b46be83d1cfcf9d1b1282','mavonEditor编辑器','codingMavonEditor',2,'/coding/mavoneditor/index','/coding/mavoneditor/index',1,5,'iconfont iaiai-buffer',1,0,'2019-12-28 19:49:04','2020-11-07 11:38:51',NULL),
	('e4d526707d004d51954bd8ef5486ea8b','e8148999f552459fa1b5f0ad53894039','编辑状态','system:post:edit:status',3,'','',1,4,'',1,0,'2019-12-22 12:23:12','2019-12-22 12:23:12',NULL),
	('e8148999f552459fa1b5f0ad53894039','fe3a8de081a84a9e9514f728178772fd','岗位管理','systemPostIndex',2,'/system/post/index','/system/post/index',1,5,'iconfont iaiai-account-box',1,0,'2019-12-18 19:05:36','2019-12-20 20:16:21',NULL),
	('e838dfd4983e459da555f190600d77d2','de5a7fd7916046be8c4cb80e305b7306','添加新闻-html','newsAddHtml',4,'/news/add/html','/news/add_html',1,2,'iconfont iaiai-file-fill',0,0,'2020-10-24 14:57:42','2020-10-24 14:58:30',''),
	('e8f4d13cf7ad4d85bf4ea379e67dd304','bdbeb37ad7cd4ddb9ae12e6acf97dba1','websocket','websocket',2,'/system/websocket/index','/system/websocket/index',1,10,'iconfont iaiai-comment-fill-light',0,0,'2020-10-29 13:43:55','2020-10-29 13:58:15',''),
	('ea52098d03a342a0b6601b0cc4a25d59','519f88287839448ab0197459dec3d158','子菜单','docManagerChild',2,'/doc/manager/child','/home',1,1,'iconfont iaiai-adjust',1,0,'2020-10-24 17:02:06','2020-10-24 17:02:06',''),
	('ea611f185155418ab1e6cc3e991c1bbf','06386b357af54e9682cd61968ec4dc48','删除用户','system:user:del',3,'','',1,3,'',1,0,'2019-12-21 19:30:01','2019-12-21 19:30:06',NULL),
	('ea8205f1d54d46a3aeb8ee837a1bca36','59a912f8556545cb88e76c2cb8e84fc9','图表','codingCharts',1,'','',1,2,'iconfont iaiai-ios-analytics',1,0,'2020-11-11 10:41:01','2020-11-11 11:04:45',''),
	('ed9590c7691e47409869bc658923b1ee','f746493b014f4707a52c8732fff92300','添加角色','system:role:add',3,'','',1,1,'',1,0,'2019-12-21 20:33:50','2019-12-21 20:33:50',NULL),
	('eda6c4bc19c847e9a6fc5261f81f6fef','9d44fc2537854143830c8c2d119abc33','删除新闻','news:type:del',3,'','',1,3,'',1,0,'2020-01-24 13:31:18','2020-01-24 13:31:18',NULL),
	('f30146b188e04c8a819651beaea8e888','9d44fc2537854143830c8c2d119abc33','添加新闻','news:type:add',3,'','',1,1,'',1,0,'2020-01-24 13:29:40','2020-01-24 13:30:38',NULL),
	('f54215a224644d7abccd4dcd42417b51','bdbeb37ad7cd4ddb9ae12e6acf97dba1','更新日志','upgrade',2,'/system/upgrade','/system/upgrade',1,99,'iconfont iaiai-apps',1,0,'2020-11-07 17:39:32','2020-11-07 17:39:50',''),
	('f746493b014f4707a52c8732fff92300','65c9fca27e054d6089ffa5e3a069b8bf','角色管理','systemRoleIndex',2,'/system/role/index','/system/role/index',1,2,'iconfont iaiai-ios-people',1,0,'2019-12-08 19:01:25','2019-12-21 19:59:56',NULL),
	('fe3a8de081a84a9e9514f728178772fd','bdbeb37ad7cd4ddb9ae12e6acf97dba1','系统用户','systemUser',1,'','',1,1,'iconfont iaiai-meeting-fill',1,0,'2020-11-07 10:37:14','2020-11-07 10:37:14','');

/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table msg
# ------------------------------------------------------------

DROP TABLE IF EXISTS `msg`;

CREATE TABLE `msg` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `content` text DEFAULT NULL COMMENT '内容',
  `read` tinyint(1) DEFAULT NULL COMMENT '是否已读',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `read_time` datetime DEFAULT NULL COMMENT '读取时间',
  `del` tinyint(1) DEFAULT NULL COMMENT '删除，0未删除，1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



# Dump of table news
# ------------------------------------------------------------

DROP TABLE IF EXISTS `news`;

CREATE TABLE `news` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `type_id` varchar(32) DEFAULT NULL COMMENT '分类id',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `cover` varchar(300) DEFAULT NULL COMMENT '封面',
  `content` text DEFAULT NULL COMMENT '内容',
  `content_type` int(11) DEFAULT NULL COMMENT '内容类型,1:html，2: Markdown',
  `show` int(11) DEFAULT NULL COMMENT '是否显示，1显示，0不显示',
  `release_start_time` datetime DEFAULT NULL COMMENT '发布开始时间',
  `release_end_time` datetime DEFAULT NULL COMMENT '发布结束时间',
  `del` int(11) DEFAULT NULL COMMENT '删除，0未删除，1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='新闻';



# Dump of table news_type
# ------------------------------------------------------------

DROP TABLE IF EXISTS `news_type`;

CREATE TABLE `news_type` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(100) DEFAULT NULL COMMENT '分类名',
  `icon` varchar(200) DEFAULT NULL COMMENT '图片',
  `show` int(11) DEFAULT NULL COMMENT '是否显示，1显示，0不显示，默认为1',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父id',
  `seq` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `del` int(11) DEFAULT NULL COMMENT '删除，1已删除，0未删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='新闻分类';

LOCK TABLES `news_type` WRITE;
/*!40000 ALTER TABLE `news_type` DISABLE KEYS */;

INSERT INTO `news_type` (`id`, `name`, `icon`, `show`, `parent_id`, `seq`, `remark`, `del`, `create_time`, `modify_time`)
VALUES
	('1b763d27751a4358a06060d444acc07d','娱乐新闻','',1,NULL,3,'',0,'2020-01-24 01:01:54','2020-01-24 13:23:06'),
	('72da1f2ed2d84124ae667c7eebf883b0','国际新闻','',1,NULL,2,'',0,'2020-01-23 23:29:13','2020-01-24 13:23:06'),
	('c3009069baff4f8b8f9b9ab1f7edc27a','国内新闻','',1,NULL,1,'',0,'2020-01-23 23:28:58','2020-10-24 12:01:00'),
	('ebb79176bedc4312a809589d272fd196','实时新闻','',1,'c3009069baff4f8b8f9b9ab1f7edc27a',1,'',0,'2020-01-24 01:02:17','2020-01-24 12:58:59'),
	('fe01a65bd37c4cf4b6217a9ecfc91b6e','科技新闻','',1,NULL,4,'',0,'2020-01-24 01:02:37','2020-01-24 13:23:06');

/*!40000 ALTER TABLE `news_type` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `menu_id` varchar(32) DEFAULT NULL COMMENT '菜单id',
  `del` int(11) DEFAULT NULL COMMENT '删除，1已删除，0未删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限';

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;

INSERT INTO `permission` (`id`, `role_id`, `menu_id`, `del`, `create_time`, `modify_time`)
VALUES
	('006da4bc3d794170804736b76dd40cce','62185e8cd80c4cdf93ca4ff46012928c','562eef50750f450baf6e8a53867ed2ed',0,'2019-12-21 21:10:22','2019-12-21 21:10:22'),
	('02aba21561c14f0a858a77ee212c88a8','62185e8cd80c4cdf93ca4ff46012928c','bf4572aa49954051a108f7a081db1ff0',0,'2019-12-22 12:47:28','2019-12-22 12:47:28'),
	('030deee58b334b8b88ebe2de5608b0ad','aaa293b3033a4480bab65693e09de0a3','5e56a2ddaa3f4bfba0d47b133210b9bc',0,'2019-12-20 09:08:27','2019-12-20 09:08:27'),
	('0836f6d1782c4e6097e0984e248d105e','62185e8cd80c4cdf93ca4ff46012928c','fe3a8de081a84a9e9514f728178772fd',0,'2020-11-07 10:39:33','2020-11-07 10:39:33'),
	('0b51165a8d1e4b83a6c22514bae26fd8','62185e8cd80c4cdf93ca4ff46012928c','ed9590c7691e47409869bc658923b1ee',0,'2019-12-21 21:10:22','2019-12-21 21:10:22'),
	('0d2ae5b2ee874cf886dcd956dea9cb1e','62185e8cd80c4cdf93ca4ff46012928c','57a2c2608a6c40ffa8b3131f7b868ad3',0,'2019-12-22 12:03:15','2019-12-22 12:03:15'),
	('0e2231bcdafd4f6faa839d921d0fd0e9','62185e8cd80c4cdf93ca4ff46012928c','88859bffcba34daa910222da353e2ef4',0,'2020-03-24 17:09:49','2020-03-24 17:09:49'),
	('0fcab7fb3d7441159bbb2db8cc0de719','62185e8cd80c4cdf93ca4ff46012928c','4d45d45909744e20aed3c48815dd2d89',0,'2020-11-06 11:31:07','2020-11-06 11:31:07'),
	('10297b9eb58e491ba7995f5774b8ccb4','62185e8cd80c4cdf93ca4ff46012928c','dc2aa81d781d45c6857cc712280e9dea',0,'2020-02-08 01:17:14','2020-02-08 01:17:14'),
	('107a8f8aa25c41459b4566b1e19f513b','62185e8cd80c4cdf93ca4ff46012928c','5e56a2ddaa3f4bfba0d47b133210b9bc',0,'2019-12-18 22:24:16','2019-12-18 22:24:16'),
	('13ad4b6ee3614de08cb1236549695eba','62185e8cd80c4cdf93ca4ff46012928c','4dcde000465b4e29b054bbf682806ca7',0,'2020-03-24 16:10:04','2020-03-24 16:10:04'),
	('189362db31b54bd3b2195c8ab4a4285d','62185e8cd80c4cdf93ca4ff46012928c','333d2f5039ff4cbeb775f84e5c08c4a2',0,'2020-10-24 16:27:12','2020-10-24 16:27:12'),
	('1a69ec550307440e98f460034b33cc15','aaa293b3033a4480bab65693e09de0a3','e8148999f552459fa1b5f0ad53894039',0,'2019-12-20 09:08:27','2019-12-20 09:08:27'),
	('1cb50ad6c43f48529920ae7ab1578d49','62185e8cd80c4cdf93ca4ff46012928c','de979a8d6fed4d57aaa84c1a1c3157a6',0,'2019-12-18 22:24:16','2019-12-18 22:24:16'),
	('1d6989e77de74e3198d55898f87aaede','62185e8cd80c4cdf93ca4ff46012928c','91cb1f658afc48d4bdb13cdfbe929cb0',0,'2019-12-20 20:36:35','2019-12-20 20:36:35'),
	('2262476705ab44fbaf817b979f19be75','62185e8cd80c4cdf93ca4ff46012928c','59a912f8556545cb88e76c2cb8e84fc9',0,'2019-12-18 22:24:16','2019-12-18 22:24:16'),
	('22d9e32b7f5e4af49d63ea2136e82ddc','62185e8cd80c4cdf93ca4ff46012928c','d610383f336a4208afc2477d1f2551ec',0,'2020-11-11 14:47:35','2020-11-11 14:47:35'),
	('245328faf611412683d62024ff9f83d3','62185e8cd80c4cdf93ca4ff46012928c','119693d238614f7bb7f5ddfecf5f2679',0,'2019-12-21 19:48:43','2019-12-21 19:48:43'),
	('2b81bc7480aa45499cb28c9e83867170','62185e8cd80c4cdf93ca4ff46012928c','83076d6193b04a7e9ee4c94d9efbc47a',0,'2020-03-24 17:23:58','2020-03-24 17:23:58'),
	('2d732e0a57b2436ca7df7df0a2256fea','62185e8cd80c4cdf93ca4ff46012928c','bfa5c5dc5b6e443186d6a16a8a90f9a7',0,'2019-12-22 12:03:15','2019-12-22 12:03:15'),
	('2fcc6ea7329c4dc1a1031c746524da0e','62185e8cd80c4cdf93ca4ff46012928c','d1ff9e4178bd43bcb3633c843d070648',0,'2020-01-23 15:33:18','2020-01-23 15:33:18'),
	('327409e80d8d4a5b8e2a42ef41fc29cd','62185e8cd80c4cdf93ca4ff46012928c','731202099c004ca8975c84b6cf2ab27d',0,'2019-12-26 20:20:06','2019-12-26 20:20:06'),
	('35043f9e04954d4d97d5bcec98ab72f0','62185e8cd80c4cdf93ca4ff46012928c','65c9fca27e054d6089ffa5e3a069b8bf',0,'2020-11-07 10:54:52','2020-11-07 10:54:52'),
	('35a1ee2120e344989f8c78b1435bc08c','62185e8cd80c4cdf93ca4ff46012928c','49fe3a9bae434f709cd740c9feba1dbb',0,'2019-12-18 22:24:16','2019-12-18 22:24:16'),
	('35cf25a889f54eccba2cbeb75a608b72','62185e8cd80c4cdf93ca4ff46012928c','e4d526707d004d51954bd8ef5486ea8b',0,'2019-12-22 12:31:20','2019-12-22 12:31:20'),
	('36b9141a35ed404e8b99d0f40be0aa98','62185e8cd80c4cdf93ca4ff46012928c','9870df4d69b54683ad99283955cb919e',0,'2019-12-22 12:31:20','2019-12-22 12:31:20'),
	('3b20e30797b44e78a765c6752e87d531','62185e8cd80c4cdf93ca4ff46012928c','519f88287839448ab0197459dec3d158',0,'2020-05-25 21:57:48','2020-05-25 21:57:48'),
	('3d84a43c895b4c34acb4e29d694b0d84','62185e8cd80c4cdf93ca4ff46012928c','d25907607ea341858b59e7ab892954f1',0,'2020-01-24 13:33:56','2020-01-24 13:33:56'),
	('402c68e437c84d158a6ef09f36ce5480','62185e8cd80c4cdf93ca4ff46012928c','98dfc52244e3476aa2e9cfbc28f7e05e',0,'2020-10-24 17:12:21','2020-10-24 17:12:21'),
	('41dfd21d6a6c4f8fbfb91e07373d187a','62185e8cd80c4cdf93ca4ff46012928c','1612c2ca0cb44ca5bf496b32e7325256',0,'2019-12-21 19:48:43','2019-12-21 19:48:43'),
	('458048d79217478082930f9b71769dc1','62185e8cd80c4cdf93ca4ff46012928c','ae718b41b9f44d969d145401a9476c31',0,'2020-10-23 16:50:14','2020-10-23 16:50:14'),
	('45a2ebc3002b4224acfa4abd59a16691','62185e8cd80c4cdf93ca4ff46012928c','6e5fd31c1433461083676bf9cfe798ba',0,'2019-12-18 22:24:16','2019-12-18 22:24:16'),
	('465ce03663084045ad0f31c9664a03fe','aaa293b3033a4480bab65693e09de0a3','f746493b014f4707a52c8732fff92300',0,'2019-12-20 09:08:27','2019-12-20 09:08:27'),
	('491c76df4760407fa03d957e9a2859e7','62185e8cd80c4cdf93ca4ff46012928c','20ad8a7b2e4c4e1f83171d001334e4d8',0,'2020-03-24 17:04:07','2020-03-24 17:04:07'),
	('52242dd30db2438c9eccbbf0b8a52099','62185e8cd80c4cdf93ca4ff46012928c','8553f19392144646a149f3381791eacd',0,'2019-12-22 12:47:28','2019-12-22 12:47:28'),
	('54e771ffdda94dc4abddf6aaef5e62c1','aaa293b3033a4480bab65693e09de0a3','6e5fd31c1433461083676bf9cfe798ba',0,'2019-12-20 09:08:27','2019-12-20 09:08:27'),
	('56837050077e44fd931dd4eb8c24894d','62185e8cd80c4cdf93ca4ff46012928c','bdbeb37ad7cd4ddb9ae12e6acf97dba1',0,'2019-12-18 22:24:16','2019-12-18 22:24:16'),
	('5a9a07cd48b44975989af4b35941e9b5','aaa293b3033a4480bab65693e09de0a3','59a912f8556545cb88e76c2cb8e84fc9',0,'2019-12-20 09:08:27','2019-12-20 09:08:27'),
	('5b234c1ee41748dbaedf6725a74885f1','62185e8cd80c4cdf93ca4ff46012928c','3f7d8ed2e1474194a33fe9d787c47917',0,'2020-03-24 16:54:45','2020-03-24 16:54:45'),
	('5c215148031643a2b7268224da189eea','62185e8cd80c4cdf93ca4ff46012928c','7139ddb6eb0d4af0922e733eb175626c',0,'2020-02-16 22:58:25','2020-02-16 22:58:25'),
	('6010a01494b94b8bb02e2205d475933f','62185e8cd80c4cdf93ca4ff46012928c','e838dfd4983e459da555f190600d77d2',0,'2020-10-24 14:58:46','2020-10-24 14:58:46'),
	('64abdcc937384cf191d127ab0abbbe37','62185e8cd80c4cdf93ca4ff46012928c','e8f4d13cf7ad4d85bf4ea379e67dd304',0,'2020-10-29 13:47:16','2020-10-29 13:47:16'),
	('6677a1870ec74bf18923ff8880847751','62185e8cd80c4cdf93ca4ff46012928c','879b74428ca540bfba8835d6eab147d3',0,'2020-04-28 17:04:22','2020-04-28 17:04:22'),
	('671ee49ede2b4dd9a52d9c9871ae5131','aaa293b3033a4480bab65693e09de0a3','bdbeb37ad7cd4ddb9ae12e6acf97dba1',0,'2019-12-20 09:08:27','2019-12-20 09:08:27'),
	('68d9307da32447839b844f61df905d8c','62185e8cd80c4cdf93ca4ff46012928c','a2478deed9a9403482aeedff3057ed5a',0,'2019-12-21 20:57:20','2019-12-21 20:57:20'),
	('69d0d71b8a7a483787c1dfd332ef5283','62185e8cd80c4cdf93ca4ff46012928c','eda6c4bc19c847e9a6fc5261f81f6fef',0,'2020-01-24 13:33:56','2020-01-24 13:33:56'),
	('6bc07418a9e44f7ba3d4641457ece756','62185e8cd80c4cdf93ca4ff46012928c','f30146b188e04c8a819651beaea8e888',0,'2020-01-24 13:38:23','2020-01-24 13:38:23'),
	('6ca42458bec74ba19913d0d3d48c7f58','62185e8cd80c4cdf93ca4ff46012928c','ea8205f1d54d46a3aeb8ee837a1bca36',0,'2020-11-11 10:41:24','2020-11-11 10:41:24'),
	('702e82c252a047d68a40e235de2b2d54','62185e8cd80c4cdf93ca4ff46012928c','800f5a3c48c54a92a975babb6e8495d4',0,'2019-12-22 12:03:15','2019-12-22 12:03:15'),
	('708b65bd92064121ab9619b2b6b9a9ec','62185e8cd80c4cdf93ca4ff46012928c','047815769a5b4fa2bb942386ffda4c74',0,'2020-01-22 14:28:27','2020-01-22 14:28:27'),
	('72f12c86de864cdea94d540f9bf6e901','62185e8cd80c4cdf93ca4ff46012928c','25896035da354c508f3a274c85e0c463',0,'2019-12-22 12:47:28','2019-12-22 12:47:28'),
	('734abcc600894be5a9b1452d8a65bb90','62185e8cd80c4cdf93ca4ff46012928c','ea52098d03a342a0b6601b0cc4a25d59',0,'2020-10-24 17:02:15','2020-10-24 17:02:15'),
	('73f7d9d5863c48cbb2bafc5bc8b81613','62185e8cd80c4cdf93ca4ff46012928c','e8148999f552459fa1b5f0ad53894039',0,'2019-12-18 22:24:16','2019-12-18 22:24:16'),
	('781dc8e056db4719a424966e90a357e2','62185e8cd80c4cdf93ca4ff46012928c','897d47d901464adbb17fe867af5f3413',0,'2019-12-22 12:31:20','2019-12-22 12:31:20'),
	('782f32fd5b6a40d4993f6a079f927c8b','62185e8cd80c4cdf93ca4ff46012928c','a43590564b274df9887993c346b0c8dd',0,'2019-12-22 12:03:15','2019-12-22 12:03:15'),
	('83dd0823bed84ab489a15ad16626bbfb','62185e8cd80c4cdf93ca4ff46012928c','9d44fc2537854143830c8c2d119abc33',0,'2020-01-23 15:35:45','2020-01-23 15:35:45'),
	('8590899c19154af48f333abbe17ed8db','62185e8cd80c4cdf93ca4ff46012928c','6ff664f30229463aa20f36f681990534',0,'2020-11-11 10:48:37','2020-11-11 10:48:37'),
	('90106e8b2914406891772729d49464f4','62185e8cd80c4cdf93ca4ff46012928c','3f16dab9a47c44f7b71474cc7270268b',0,'2019-12-21 19:28:02','2019-12-21 19:28:02'),
	('918202db1262421b99ba3106669d3410','62185e8cd80c4cdf93ca4ff46012928c','8ea82b3ecbf747eeadf14b074d68f2c0',0,'2020-01-24 13:33:56','2020-01-24 13:33:56'),
	('929731b40bfd4b009d65b3b95f4b54d4','aaa293b3033a4480bab65693e09de0a3','de979a8d6fed4d57aaa84c1a1c3157a6',0,'2019-12-20 09:08:27','2019-12-20 09:08:27'),
	('93de7ed7189246a1ae2356977ce2e5ad','aaa293b3033a4480bab65693e09de0a3','06386b357af54e9682cd61968ec4dc48',0,'2019-12-20 09:08:27','2019-12-20 09:08:27'),
	('97374c11bc844ea1a70db0c9937bf457','62185e8cd80c4cdf93ca4ff46012928c','d6c3f305e82c40338b04491392918369',0,'2020-01-21 20:57:04','2020-01-21 20:57:04'),
	('9753cfc7f3fa4aab87cd44aee35f4d75','62185e8cd80c4cdf93ca4ff46012928c','5ef90637bcb248ecaa69248462acb1f2',0,'2020-02-08 01:17:58','2020-02-08 01:17:58'),
	('9bd28ec2a47b482caa8087f092d825a4','aaa293b3033a4480bab65693e09de0a3','1d74dea832df4c6087d541d68a44f14c',0,'2019-12-20 09:08:27','2019-12-20 09:08:27'),
	('9eac563843924ba8970a1dd7917ccebd','aaa293b3033a4480bab65693e09de0a3','5aeebb8f2fb34356b56195c36ffdc465',0,'2019-12-20 09:08:27','2019-12-20 09:08:27'),
	('9f7e781eae82421cbcfd83e4fe8fa929','aaa293b3033a4480bab65693e09de0a3','49fe3a9bae434f709cd740c9feba1dbb',0,'2019-12-20 09:08:27','2019-12-20 09:08:27'),
	('a089bdf80c3f4d439ecca2572f340949','62185e8cd80c4cdf93ca4ff46012928c','de5a7fd7916046be8c4cb80e305b7306',0,'2020-01-24 13:40:30','2020-01-24 13:40:30'),
	('a1240648d7684138b1bdef482c4d302d','62185e8cd80c4cdf93ca4ff46012928c','c70f4d30508e4f398d68387c3d6a5cbb',0,'2020-11-01 15:47:57','2020-11-01 15:47:57'),
	('a82890419d7c4a0db6cf98ffce040d70','62185e8cd80c4cdf93ca4ff46012928c','8c03e43acef949ff93f31bca60fb06f2',0,'2020-11-07 17:25:04','2020-11-07 17:25:04'),
	('ad0b696eecbd45e885662b052fefd6eb','62185e8cd80c4cdf93ca4ff46012928c','8f894499171a4d0b9b820be042d95f0c',0,'2019-12-21 21:10:22','2019-12-21 21:10:22'),
	('ae2302f0448e46b0a577d009633bb2c1','62185e8cd80c4cdf93ca4ff46012928c','ac8ed412143c4df78904e0411b52a8c4',0,'2019-12-21 20:58:53','2019-12-21 20:58:53'),
	('b248cbc848304a878ba7e37b72b26ecc','62185e8cd80c4cdf93ca4ff46012928c','2311eb99227047dc9ccd9fd422b25bb0',0,'2019-12-22 12:47:28','2019-12-22 12:47:28'),
	('b3a9280abfae45508f78c6f601b4f2af','62185e8cd80c4cdf93ca4ff46012928c','06386b357af54e9682cd61968ec4dc48',0,'2019-12-18 22:24:16','2019-12-18 22:24:16'),
	('bab0bd2d68144ad79be298d6f47598ce','62185e8cd80c4cdf93ca4ff46012928c','f54215a224644d7abccd4dcd42417b51',0,'2020-11-07 17:40:00','2020-11-07 17:40:00'),
	('bb2c341f008743c2b307e4193ac062f0','62185e8cd80c4cdf93ca4ff46012928c','1d74dea832df4c6087d541d68a44f14c',0,'2019-12-18 22:24:16','2019-12-18 22:24:16'),
	('da66f1da3c0345b5b66e683663505746','62185e8cd80c4cdf93ca4ff46012928c','f746493b014f4707a52c8732fff92300',0,'2019-12-18 22:24:16','2019-12-18 22:24:16'),
	('da7f7b21caeb4a92bda3ede45c0e68a1','62185e8cd80c4cdf93ca4ff46012928c','90305e1669404bac85592cf4a984e2ff',0,'2019-12-22 12:31:20','2019-12-22 12:31:20'),
	('eacc599d58b440718f2d1f334e37615a','62185e8cd80c4cdf93ca4ff46012928c','0a371a05962e444bab67a43953d189be',0,'2020-10-26 11:17:58','2020-10-26 11:17:58'),
	('ebff0dc3f8684f8eb22f6c289f4f97b5','62185e8cd80c4cdf93ca4ff46012928c','aa9c08dc32ed4b639f9ad1088c6ea4ca',0,'2019-12-21 20:21:18','2019-12-21 20:21:18'),
	('ee156f8ef51a4231b94be91b41971ccb','62185e8cd80c4cdf93ca4ff46012928c','486ae2315fbf4501916571b0410c3811',0,'2020-02-08 01:14:34','2020-02-08 01:14:34'),
	('f370b14989e44ab3916883dea21b5711','62185e8cd80c4cdf93ca4ff46012928c','d7905ff62e4b46be83d1cfcf9d1b1282',0,'2020-11-11 09:29:40','2020-11-11 09:29:40'),
	('f4c124ae196d4c8bb482a0dec72ff352','62185e8cd80c4cdf93ca4ff46012928c','df99cf7248044211a405a87887bc1fdb',0,'2019-12-28 19:50:48','2019-12-28 19:50:48'),
	('f61b8d23c980498ca199447dd8ab9b99','aaa293b3033a4480bab65693e09de0a3','91cb1f658afc48d4bdb13cdfbe929cb0',0,'2019-12-20 09:08:27','2019-12-20 09:08:27'),
	('fc68abf285b04d5192f5a5b18eea18cc','62185e8cd80c4cdf93ca4ff46012928c','5aeebb8f2fb34356b56195c36ffdc465',0,'2019-12-18 22:24:16','2019-12-18 22:24:16'),
	('ffcca6d0381c496cadf2a1de57ff970d','62185e8cd80c4cdf93ca4ff46012928c','ea611f185155418ab1e6cc3e991c1bbf',0,'2019-12-21 19:46:10','2019-12-21 19:46:10');

/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table post
# ------------------------------------------------------------

DROP TABLE IF EXISTS `post`;

CREATE TABLE `post` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(32) DEFAULT NULL COMMENT '岗位名',
  `code` varchar(50) DEFAULT NULL COMMENT '编码',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `seq` int(11) DEFAULT NULL COMMENT '顺序',
  `status` int(11) DEFAULT NULL COMMENT '状态,1启用，0禁用',
  `del` int(11) DEFAULT NULL COMMENT '删除，1已删除，0未删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位';

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;

INSERT INTO `post` (`id`, `name`, `code`, `remark`, `seq`, `status`, `del`, `create_time`, `modify_time`)
VALUES
	('21b11c10913b4b50ab60d6698e266dfa','董事长','ceo','',1,1,0,'2019-12-12 23:47:38','2019-12-13 20:35:22'),
	('4b91c9e706234237a983422dad6cca64','项目经理','se','',2,1,0,'2019-12-12 23:45:50','2019-12-13 20:35:49'),
	('92a6e3e0aeb4457ea9ef270ab2e4b328','人力资源','hr','',3,0,0,'2019-12-13 20:37:07','2020-03-24 17:06:48'),
	('d649d174d8e5495ea6fff3cd6a847751','普通员工','user','',4,1,0,'2019-12-13 20:37:56','2019-12-13 20:37:56');

/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(100) DEFAULT NULL COMMENT '角色名',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `seq` int(11) DEFAULT NULL COMMENT '顺序',
  `status` int(11) DEFAULT NULL COMMENT '状态,1启用，0禁用',
  `del` int(11) DEFAULT NULL COMMENT '删除，0未删除，1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` (`id`, `name`, `remark`, `seq`, `status`, `del`, `create_time`, `modify_time`)
VALUES
	('62185e8cd80c4cdf93ca4ff46012928c','管理员','不允许删除和禁用',1,1,0,'2019-12-10 21:15:28','2019-12-18 21:06:39'),
	('aaa293b3033a4480bab65693e09de0a3','运营','',2,1,0,'2019-12-20 09:08:11','2020-04-28 14:21:38');

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table upload_file
# ------------------------------------------------------------

DROP TABLE IF EXISTS `upload_file`;

CREATE TABLE `upload_file` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `filename` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件名',
  `suffix` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '后缀',
  `path` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '存放地址',
  `web_url` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '访问地址',
  `size` bigint(11) DEFAULT NULL COMMENT '文件大小',
  `del` int(11) DEFAULT NULL COMMENT '删除，0未删除，1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='上传的文件';



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `username` varchar(100) DEFAULT NULL COMMENT '登录名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `realname` varchar(100) DEFAULT NULL COMMENT '真实姓名(暂时无用)',
  `sex` int(11) DEFAULT NULL COMMENT '性别,0未知，1男，2女',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `status` int(11) DEFAULT NULL COMMENT '状态,1正常，0禁用',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `face` varchar(200) DEFAULT NULL COMMENT '头像地址',
  `del` int(11) DEFAULT NULL COMMENT '删除，0未删除，1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `username`, `password`, `nickname`, `realname`, `sex`, `email`, `status`, `phone`, `remark`, `face`, `del`, `create_time`, `modify_time`)
VALUES
	('1','admin','0914fa00ceae36cd13b4ba3478d5490d','管理员',NULL,0,NULL,1,NULL,NULL,'https://qjyp-oss.oss-cn-beijing.aliyuncs.com/test/images/e4788cd536234580ad61ac701c2d51f2.png',0,'2019-09-24 16:16:02','2020-11-11 16:50:57'),
	('2403caae4d5d430faa44a6cb93f53f51','iaiai','9db06bcff9248837f86d1a6bcf41c9e7','丸子',NULL,0,'176291935@qq.com',1,'15081199130','',NULL,0,'2019-12-19 23:54:11','2020-03-24 16:12:48'),
	('36b1b76363e64ca7bc15bd4595bab817','lisi','9db06bcff9248837f86d1a6bcf41c9e7','李四',NULL,0,'',1,'','',NULL,0,'2019-12-20 10:16:08','2019-12-23 10:48:20'),
	('45d06761e7df40b981889d24df17a7a6','zhaoliu','9db06bcff9248837f86d1a6bcf41c9e7','赵六',NULL,0,'',1,'13111111111','',NULL,0,'2019-12-20 10:18:29','2019-12-23 10:48:18'),
	('4c5230de59084308a7faedac55b757af','zhangsan','9db06bcff9248837f86d1a6bcf41c9e7','张三',NULL,0,'',1,'','',NULL,0,'2019-12-20 10:13:59','2019-12-20 10:13:59'),
	('a33e6eb44c8641ba80dd497a15c6a881','wangwu','9db06bcff9248837f86d1a6bcf41c9e7','王五',NULL,0,'',1,'','',NULL,0,'2019-12-20 10:17:49','2019-12-23 10:48:20'),
	('bf6404e5a52646698ab1139a85a98b41','mayun','9db06bcff9248837f86d1a6bcf41c9e7','马云',NULL,0,'',1,'13333333333','','http://localhost:5050/img/dd8c0a0a0b7d48fc84928f2ff2a1a08b.png',0,'2019-12-20 10:28:14','2020-02-10 15:51:30'),
	('f874d643dc1c4f96b08baa2775b5ef11','mahuateng','9db06bcff9248837f86d1a6bcf41c9e7','马化腾',NULL,0,'',1,'13222222222','',NULL,0,'2019-12-20 10:24:10','2019-12-23 10:47:44');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_dept
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_dept`;

CREATE TABLE `user_dept` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `del` int(11) DEFAULT NULL COMMENT '删除，0未删除，1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `user_dept` WRITE;
/*!40000 ALTER TABLE `user_dept` DISABLE KEYS */;

INSERT INTO `user_dept` (`id`, `user_id`, `dept_id`, `del`, `create_time`, `modify_time`)
VALUES
	('1','1','d7d58db2d447447489df2e529de49622',0,'2019-09-24 16:16:02','2019-12-19 23:33:25'),
	('78b580a23b094328aeabf1e1fb53575b','a33e6eb44c8641ba80dd497a15c6a881','d851fa6a0c9440deb1dc8ef86db39729',0,'2019-12-20 10:17:49','2019-12-20 10:17:49'),
	('96074d5e5154437d846f888d4955a4aa','bf6404e5a52646698ab1139a85a98b41','d851fa6a0c9440deb1dc8ef86db39729',0,'2019-12-20 10:28:14','2019-12-20 10:28:14'),
	('9fa00ff41d794655aadf527bbcf33e2d','f874d643dc1c4f96b08baa2775b5ef11','d851fa6a0c9440deb1dc8ef86db39729',0,'2019-12-20 10:24:10','2019-12-20 10:24:10'),
	('d20956c2376b4b7ca3a4a7adfa669979','2403caae4d5d430faa44a6cb93f53f51','d7d58db2d447447489df2e529de49622',0,'2019-12-20 09:48:56','2019-12-20 09:48:56'),
	('e4429b0003af4b13ab6eca6d784545e5','45d06761e7df40b981889d24df17a7a6','4c23c700c7ae4599a37bc6e3af8249e4',0,'2019-12-20 10:18:29','2019-12-20 10:18:29'),
	('e90c24786bc94ca78f14a592111e9ed7','36b1b76363e64ca7bc15bd4595bab817','4c23c700c7ae4599a37bc6e3af8249e4',0,'2019-12-20 10:16:08','2019-12-20 10:16:08'),
	('fbcc95dfd5ae41288701408b3cd51b05','4c5230de59084308a7faedac55b757af','d851fa6a0c9440deb1dc8ef86db39729',0,'2019-12-20 10:13:59','2019-12-20 10:13:59');

/*!40000 ALTER TABLE `user_dept` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_post
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_post`;

CREATE TABLE `user_post` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `post_id` varchar(32) DEFAULT NULL COMMENT '岗位id',
  `del` int(11) DEFAULT NULL COMMENT '删除，0未删除，1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `user_post` WRITE;
/*!40000 ALTER TABLE `user_post` DISABLE KEYS */;

INSERT INTO `user_post` (`id`, `user_id`, `post_id`, `del`, `create_time`, `modify_time`)
VALUES
	('03680a138f0440049237640a0a2e421a','f874d643dc1c4f96b08baa2775b5ef11','d649d174d8e5495ea6fff3cd6a847751',0,'2019-12-20 10:24:10','2019-12-20 10:24:10'),
	('04b249f40b5342e897e261b1c2ddceb5','a33e6eb44c8641ba80dd497a15c6a881','d649d174d8e5495ea6fff3cd6a847751',0,'2019-12-20 10:17:49','2019-12-20 10:17:49'),
	('36da44014ec247f095822847dc6a2163','4c5230de59084308a7faedac55b757af','4b91c9e706234237a983422dad6cca64',0,'2019-12-20 10:13:59','2019-12-20 10:13:59'),
	('37d7e27bd0b344778255331f91764cfa','bf6404e5a52646698ab1139a85a98b41','d649d174d8e5495ea6fff3cd6a847751',0,'2019-12-20 10:28:14','2019-12-20 10:28:14'),
	('399bae98124440a187bc5947a5ae38c5','2403caae4d5d430faa44a6cb93f53f51','21b11c10913b4b50ab60d6698e266dfa',0,'2019-12-19 23:54:31','2019-12-19 23:54:31'),
	('59a09aaa87204e93b5f4ed40857ca40c','1','21b11c10913b4b50ab60d6698e266dfa',0,'2019-12-19 23:29:24','2019-12-19 23:32:44'),
	('76ef9f22728b430f8845be299d7c41cb','36b1b76363e64ca7bc15bd4595bab817','4b91c9e706234237a983422dad6cca64',0,'2019-12-20 10:16:08','2019-12-20 10:16:08'),
	('d6f6467e212f43189dd1134e98df3135','45d06761e7df40b981889d24df17a7a6','d649d174d8e5495ea6fff3cd6a847751',0,'2019-12-20 10:18:29','2019-12-20 10:18:29'),
	('f67ba2183ef9409f804f3016fabf9d40','2403caae4d5d430faa44a6cb93f53f51','4b91c9e706234237a983422dad6cca64',0,'2019-12-20 09:09:23','2019-12-20 09:09:23');

/*!40000 ALTER TABLE `user_post` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `del` int(11) DEFAULT NULL COMMENT '删除，0未删除，1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;

INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `del`, `create_time`, `modify_time`)
VALUES
	('09261afb19314e07be557c9807baedda','45d06761e7df40b981889d24df17a7a6','aaa293b3033a4480bab65693e09de0a3',0,'2019-12-20 10:18:29','2019-12-20 10:18:29'),
	('0ddc8c2298484bdc9a5d9f98c184aae7','bf6404e5a52646698ab1139a85a98b41','aaa293b3033a4480bab65693e09de0a3',0,'2019-12-20 10:28:14','2019-12-20 10:28:14'),
	('1','1','62185e8cd80c4cdf93ca4ff46012928c',0,'2019-09-24 16:16:02','2019-09-24 16:16:02'),
	('978ae26a87784cecb7afe7cb418161fa','f874d643dc1c4f96b08baa2775b5ef11','aaa293b3033a4480bab65693e09de0a3',0,'2019-12-20 10:24:10','2019-12-20 10:24:10'),
	('a3188721018c4c0596e84df658910f97','a33e6eb44c8641ba80dd497a15c6a881','aaa293b3033a4480bab65693e09de0a3',0,'2019-12-20 10:17:49','2019-12-20 10:17:49'),
	('b2a6aa17891d488a97fc04b1d862e5ee','36b1b76363e64ca7bc15bd4595bab817','aaa293b3033a4480bab65693e09de0a3',0,'2019-12-20 10:16:08','2019-12-20 10:16:08'),
	('d9a6057b236d4b2aa47697b48d190fd1','4c5230de59084308a7faedac55b757af','aaa293b3033a4480bab65693e09de0a3',0,'2019-12-20 10:13:59','2019-12-20 10:13:59'),
	('f14dc1c3ebe94c059f81c41fc3cbdf54','2403caae4d5d430faa44a6cb93f53f51','aaa293b3033a4480bab65693e09de0a3',0,'2019-12-20 09:08:49','2019-12-20 09:08:49');

/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
