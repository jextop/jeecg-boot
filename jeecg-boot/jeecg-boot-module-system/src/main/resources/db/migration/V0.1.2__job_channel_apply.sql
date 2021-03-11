-- 新增数据表
DROP TABLE IF EXISTS `mvp_job_apply`;
CREATE TABLE `mvp_job_apply` (
  `id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `create_by` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所属部门',
  `company_nickame` varchar(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '公司简称',
  `company_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司名称',
  `job_title` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '应聘职位',
  `job_channel_id` varchar(36) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '应聘渠道',
  `job_owner` varchar(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '招聘人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `mvp_job_channel`;
CREATE TABLE `mvp_job_channel` (
  `id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `create_by` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所属部门',
  `name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 添加组合关键字和外键
alter table mvp_job_apply
add UNIQUE KEY `unique_key_name_title` (`job_title`,`company_name`) USING BTREE,
add CONSTRAINT `job_apply_channel` FOREIGN KEY (`job_channel_id`) REFERENCES `mvp_job_channel` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- 必要的初始化数据
INSERT INTO `mvp_job_channel` (`id`, `create_by`, `create_time`, `update_by`, `update_time`, `sys_org_code`, `name`) VALUES
('1369880635644669954',	'admin',	'2021-03-11 13:19:29',	NULL,	NULL,	'A01',	'BOSS直聘'),
('1369881429458644993',	'admin',	'2021-03-11 13:22:38',	NULL,	NULL,	'A01',	'51Job'),
('1369881474773905410',	'admin',	'2021-03-11 13:22:49',	NULL,	NULL,	'A01',	'智联招聘'),
('1369881509515325442',	'admin',	'2021-03-11 13:22:57',	NULL,	NULL,	'A01',	'猎聘'),
('1369881533913591809',	'admin',	'2021-03-11 13:23:03',	NULL,	NULL,	'A01',	'拉钩');

-- 在线表单开发
delete from onl_cgform_head where id in ('536c490c4494439398fc910d3d705805', 'edf0e6ad392744b99c10201d6e6bdfc9');
delete from onl_cgform_field where cgform_head_id in ('536c490c4494439398fc910d3d705805', 'edf0e6ad392744b99c10201d6e6bdfc9');

INSERT INTO `onl_cgform_head` (`id`, `table_name`, `table_type`, `table_version`, `table_txt`, `is_checkbox`, `is_db_synch`, `is_page`, `is_tree`, `id_sequence`, `id_type`, `query_mode`, `relation_type`, `sub_table_str`, `tab_order_num`, `tree_parent_id_field`, `tree_id_field`, `tree_fieldname`, `form_category`, `form_template`, `form_template_mobile`, `scroll`, `copy_version`, `copy_type`, `physic_id`, `update_by`, `update_time`, `create_by`, `create_time`, `theme_template`) VALUES
('536c490c4494439398fc910d3d705805',	'mvp_job_channel',	1,	1,	'求职渠道',	'Y',	'Y',	'Y',	'N',	NULL,	'UUID',	'single',	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	'bdfl_include',	'1',	NULL,	1,	NULL,	0,	NULL,	'admin',	'2021-03-11 13:02:59',	'admin',	'2021-03-11 13:02:52',	'normal'),
('edf0e6ad392744b99c10201d6e6bdfc9',	'mvp_job_apply',	1,	13,	'求职应聘',	'Y',	'Y',	'Y',	'N',	NULL,	'UUID',	'single',	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	'bdfl_include',	'2',	NULL,	1,	NULL,	0,	NULL,	'admin',	'2021-03-11 13:26:12',	'admin',	'2021-03-11 12:19:10',	'normal');

INSERT INTO `onl_cgform_field` (`id`, `cgform_head_id`, `db_field_name`, `db_field_txt`, `db_field_name_old`, `db_is_key`, `db_is_null`, `db_type`, `db_length`, `db_point_length`, `db_default_val`, `dict_field`, `dict_table`, `dict_text`, `field_show_type`, `field_href`, `field_length`, `field_valid_type`, `field_must_input`, `field_extend_json`, `field_default_value`, `is_query`, `is_show_form`, `is_show_list`, `is_read_only`, `query_mode`, `main_table`, `main_field`, `order_num`, `update_by`, `update_time`, `create_time`, `create_by`, `converter`, `query_def_val`, `query_dict_text`, `query_dict_field`, `query_dict_table`, `query_show_type`, `query_config_flag`, `query_valid_type`, `query_must_input`, `sort_flag`) VALUES
('024feb70e1f57bcab8d643ecb3bc714c',	'edf0e6ad392744b99c10201d6e6bdfc9',	'sys_org_code',	'所属部门',	NULL,	0,	1,	'string',	64,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	0,	'single',	'',	'',	6,	'admin',	'2021-03-11 13:26:08',	'2021-03-11 12:19:10',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('33bec8fa3a59338fb6657b302cc0162b',	'edf0e6ad392744b99c10201d6e6bdfc9',	'company_nickame',	'公司简称',	NULL,	0,	1,	'string',	16,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	1,	1,	1,	0,	'single',	'',	'',	7,	'admin',	'2021-03-11 13:26:08',	'2021-03-11 12:23:51',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0'),
('44b51e6dd5a4d7713100eaa83addd078',	'536c490c4494439398fc910d3d705805',	'name',	'名称',	NULL,	1,	0,	'string',	32,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'1',	'',	'',	1,	1,	1,	0,	'single',	'',	'',	7,	NULL,	NULL,	'2021-03-11 13:02:52',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0'),
('5f46d83484a205e7a559d488bcf3b4bb',	'edf0e6ad392744b99c10201d6e6bdfc9',	'create_time',	'创建日期',	NULL,	0,	1,	'Date',	20,	0,	'',	'',	'',	'',	'datetime',	'',	120,	NULL,	'0',	'',	'',	1,	0,	1,	0,	'group',	'',	'',	3,	'admin',	'2021-03-11 13:26:08',	'2021-03-11 12:19:10',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('636a8885ac1e7181d49dc25d2611079e',	'edf0e6ad392744b99c10201d6e6bdfc9',	'update_by',	'更新人',	NULL,	0,	1,	'string',	50,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	0,	'single',	'',	'',	4,	'admin',	'2021-03-11 13:26:08',	'2021-03-11 12:19:10',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('6b174a73553671f602dc20b15f48c98d',	'edf0e6ad392744b99c10201d6e6bdfc9',	'update_time',	'更新日期',	NULL,	0,	1,	'Date',	20,	0,	'',	'',	'',	'',	'datetime',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	0,	'single',	'',	'',	5,	'admin',	'2021-03-11 13:26:08',	'2021-03-11 12:19:10',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('72e095ce219e0bb21f55ad8827d7f149',	'edf0e6ad392744b99c10201d6e6bdfc9',	'company_name',	'公司名称',	NULL,	1,	0,	'string',	64,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'1',	'',	'',	0,	1,	1,	0,	'single',	'',	'',	8,	'admin',	'2021-03-11 13:26:08',	'2021-03-11 12:23:51',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0'),
('7cc5f61005ecc1f755fe3b525672f4df',	'edf0e6ad392744b99c10201d6e6bdfc9',	'job_channel_id',	'应聘渠道',	NULL,	0,	1,	'string',	36,	0,	'',	'id',	'mvp_job_channel',	'name',	'list',	'',	120,	NULL,	'0',	'',	'',	1,	1,	1,	0,	'single',	'mvp_job_channel',	'id',	10,	'admin',	'2021-03-11 13:26:08',	'2021-03-11 12:23:51',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0'),
('7f0e24fbbf1bee63f20cf335902b725f',	'edf0e6ad392744b99c10201d6e6bdfc9',	'id',	'主键',	NULL,	1,	0,	'string',	36,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	1,	'single',	'',	'',	1,	'admin',	'2021-03-11 13:26:08',	'2021-03-11 12:19:10',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('840d486dc64c98ff3fd9bfc1e87c907c',	'536c490c4494439398fc910d3d705805',	'id',	'主键',	NULL,	1,	0,	'string',	36,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	1,	'single',	'',	'',	1,	NULL,	NULL,	'2021-03-11 13:02:52',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('9b2b4ea0513dcc6f8f5abd6ed76a0166',	'edf0e6ad392744b99c10201d6e6bdfc9',	'job_title',	'应聘职位',	NULL,	1,	0,	'string',	32,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'1',	'',	'',	1,	1,	1,	0,	'single',	'',	'',	9,	'admin',	'2021-03-11 13:26:08',	'2021-03-11 12:23:51',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0'),
('a74c57f1696729a47992060c40643433',	'536c490c4494439398fc910d3d705805',	'create_time',	'创建日期',	NULL,	0,	1,	'Date',	20,	0,	'',	'',	'',	'',	'datetime',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	0,	'single',	'',	'',	3,	NULL,	NULL,	'2021-03-11 13:02:52',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('ad2183cb364750cc878b20c8ecda5ffb',	'edf0e6ad392744b99c10201d6e6bdfc9',	'job_owner',	'招聘人',	NULL,	0,	1,	'string',	16,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	1,	1,	1,	0,	'single',	'',	'',	11,	'admin',	'2021-03-11 13:26:08',	'2021-03-11 12:23:51',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0'),
('b29d238ce1042477ea9f43da30cb6453',	'536c490c4494439398fc910d3d705805',	'sys_org_code',	'所属部门',	NULL,	0,	1,	'string',	64,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	0,	'single',	'',	'',	6,	NULL,	NULL,	'2021-03-11 13:02:52',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('c19d50d9d9260173845e1ee883663489',	'536c490c4494439398fc910d3d705805',	'update_by',	'更新人',	NULL,	0,	1,	'string',	50,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	0,	'single',	'',	'',	4,	NULL,	NULL,	'2021-03-11 13:02:52',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('ca2b2f3e0d43f80fb3451742f8bbff3f',	'536c490c4494439398fc910d3d705805',	'update_time',	'更新日期',	NULL,	0,	1,	'Date',	20,	0,	'',	'',	'',	'',	'datetime',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	0,	'single',	'',	'',	5,	NULL,	NULL,	'2021-03-11 13:02:52',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('dee7eb4645d41fe33727afcad9022f7e',	'536c490c4494439398fc910d3d705805',	'create_by',	'创建人',	NULL,	0,	1,	'string',	50,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	0,	'single',	'',	'',	2,	NULL,	NULL,	'2021-03-11 13:02:52',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('fd50e850d0a0ffbc9b89b4ff3911ad86',	'edf0e6ad392744b99c10201d6e6bdfc9',	'create_by',	'创建人',	NULL,	0,	1,	'string',	50,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	0,	'single',	'',	'',	2,	'admin',	'2021-03-11 13:26:08',	'2021-03-11 12:19:10',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0');

-- 菜单和权限配置
delete from sys_permission where id in ('1369884182171971585', '1369884299511820289', '1369884448527052801');
delete from sys_role_permission where permission_id in ('1369884182171971585', '1369884299511820289', '1369884448527052801');

INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`, `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`, `is_route`, `is_leaf`, `keep_alive`, `hidden`, `description`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`, `internal_or_external`) VALUES
('1369884182171971585',	NULL,	'求职应聘',	'/job',	'layouts/RouteView',	NULL,	NULL,	0,	NULL,	'1',	0.05,	0,	'schedule',	1,	0,	0,	0,	NULL,	'admin',	'2021-03-11 13:33:35',	NULL,	NULL,	0,	0,	'1',	0),
('1369884299511820289',	'1369884182171971585',	'求职应聘',	'/online/cgformList/edf0e6ad392744b99c10201d6e6bdfc9',	'edf0e6ad392744b99c10201d6e6bdfc9',	NULL,	NULL,	1,	NULL,	'1',	1.00,	0,	NULL,	0,	1,	0,	0,	NULL,	'admin',	'2021-03-11 13:34:03',	NULL,	NULL,	0,	0,	'1',	0),
('1369884448527052801',	'1369884182171971585',	'求职渠道',	'/online/cgformList/536c490c4494439398fc910d3d705805',	'536c490c4494439398fc910d3d705805',	NULL,	NULL,	1,	NULL,	'1',	1.00,	0,	NULL,	0,	1,	0,	0,	NULL,	'admin',	'2021-03-11 13:34:38',	NULL,	NULL,	0,	0,	'1',	0);

INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`) VALUES
('1369884503413714945',	'f6817f48af4fb3af11b9e8bf182f618b',	'1369884182171971585',	NULL,	'2021-03-11 13:34:51',	'127.0.0.1'),
('1369884503417909249',	'f6817f48af4fb3af11b9e8bf182f618b',	'1369884299511820289',	NULL,	'2021-03-11 13:34:51',	'127.0.0.1'),
('1369884503417909250',	'f6817f48af4fb3af11b9e8bf182f618b',	'1369884448527052801',	NULL,	'2021-03-11 13:34:51',	'127.0.0.1');
