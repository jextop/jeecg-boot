-- 新增数据表
DROP TABLE IF EXISTS `mvp_api_call_sms`;
CREATE TABLE `mvp_api_call_sms` (
  `id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL,
  `create_by` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所属部门',
  `phone_number` varchar(16) COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `sms_code` varchar(16) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '短信码',
  `received` int DEFAULT NULL COMMENT 'received',
  `err_code` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'err_code',
  `err_msg` longtext COLLATE utf8mb4_general_ci COMMENT 'err_msg',
  `request_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'request_id',
  `request_body` longtext COLLATE utf8mb4_general_ci COMMENT 'request_body',
  `biz_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'biz_id',
  `out_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'out_id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 在线表单开发
delete from onl_cgform_head where id in ('50ada03485b341378d8e94d01781be8c');
delete from onl_cgform_field where cgform_head_id in ('50ada03485b341378d8e94d01781be8c');

INSERT INTO `onl_cgform_head` (`id`, `table_name`, `table_type`, `table_version`, `table_txt`, `is_checkbox`, `is_db_synch`, `is_page`, `is_tree`, `id_sequence`, `id_type`, `query_mode`, `relation_type`, `sub_table_str`, `tab_order_num`, `tree_parent_id_field`, `tree_id_field`, `tree_fieldname`, `form_category`, `form_template`, `form_template_mobile`, `scroll`, `copy_version`, `copy_type`, `physic_id`, `update_by`, `update_time`, `create_by`, `create_time`, `theme_template`) VALUES
('50ada03485b341378d8e94d01781be8c',	'mvp_api_call_sms',	1,	9,	'SMS发送记录',	'Y',	'Y',	'Y',	'N',	NULL,	'UUID',	'single',	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	'bdfl_include',	'2',	NULL,	1,	NULL,	0,	NULL,	'admin',	'2021-03-10 16:35:54',	'admin',	'2021-03-10 16:26:39',	'normal');

INSERT INTO `onl_cgform_field` (`id`, `cgform_head_id`, `db_field_name`, `db_field_txt`, `db_field_name_old`, `db_is_key`, `db_is_null`, `db_type`, `db_length`, `db_point_length`, `db_default_val`, `dict_field`, `dict_table`, `dict_text`, `field_show_type`, `field_href`, `field_length`, `field_valid_type`, `field_must_input`, `field_extend_json`, `field_default_value`, `is_query`, `is_show_form`, `is_show_list`, `is_read_only`, `query_mode`, `main_table`, `main_field`, `order_num`, `update_by`, `update_time`, `create_time`, `create_by`, `converter`, `query_def_val`, `query_dict_text`, `query_dict_field`, `query_dict_table`, `query_show_type`, `query_config_flag`, `query_valid_type`, `query_must_input`, `sort_flag`) VALUES
('090e29b1d0ca0d73107c4a629bb6bd2d',	'50ada03485b341378d8e94d01781be8c',	'sms_code',	'短信码',	NULL,	0,	1,	'string',	16,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	1,	1,	0,	'single',	'',	'',	8,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:26:39',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0'),
('2869d7453b0931301f5be0ee47088a13',	'50ada03485b341378d8e94d01781be8c',	'err_msg',	'err_msg',	NULL,	0,	1,	'Text',	0,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	1,	0,	0,	'single',	'',	'',	11,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:26:40',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0'),
('3e049c8cb7a232c6bf42e189d7cafc71',	'50ada03485b341378d8e94d01781be8c',	'update_time',	'更新日期',	NULL,	0,	1,	'Date',	20,	0,	'',	'',	'',	'',	'datetime',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	0,	'single',	'',	'',	5,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:26:39',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('45f8409dafd3713c96f934da61a8357f',	'50ada03485b341378d8e94d01781be8c',	'update_by',	'更新人',	NULL,	0,	1,	'string',	50,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	0,	'single',	'',	'',	4,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:26:39',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('4f7702b5621b2b2a89c31e23373e277e',	'50ada03485b341378d8e94d01781be8c',	'create_by',	'创建人',	NULL,	0,	1,	'string',	50,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	0,	'single',	'',	'',	2,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:26:39',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('58e583eb5352ef108a9667c181fc0e1a',	'50ada03485b341378d8e94d01781be8c',	'received',	'received',	NULL,	0,	1,	'int',	1,	0,	'',	'yn',	'',	'',	'list',	'',	120,	NULL,	'0',	'',	'',	1,	1,	1,	0,	'single',	'',	'',	9,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:26:39',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0'),
('6d6f055a295a2e2943adbcfb5b80006d',	'50ada03485b341378d8e94d01781be8c',	'out_id',	'out_id',	NULL,	0,	1,	'string',	64,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	1,	0,	0,	'single',	'',	'',	15,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:26:40',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0'),
('755528a1525a9d13b718a7fcffa7a41e',	'50ada03485b341378d8e94d01781be8c',	'request_id',	'request_id',	NULL,	0,	1,	'string',	64,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	1,	1,	0,	'single',	'',	'',	12,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:26:40',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0'),
('91f2d80113cf504723bf5efa8ac7afb6',	'50ada03485b341378d8e94d01781be8c',	'sys_org_code',	'所属部门',	NULL,	0,	1,	'string',	64,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	0,	'single',	'',	'',	6,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:26:39',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('92991e006e183638881e0433b7f2219c',	'50ada03485b341378d8e94d01781be8c',	'phone_number',	'手机号',	NULL,	0,	0,	'string',	16,	0,	'',	'',	'',	'',	'text',	'',	120,	'm',	'1',	'',	'',	1,	1,	1,	0,	'single',	'',	'',	7,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:26:39',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0'),
('939f154d4f3bafc21a6149cdcc2050fe',	'50ada03485b341378d8e94d01781be8c',	'create_time',	'创建日期',	NULL,	0,	1,	'Date',	20,	0,	'',	'',	'',	'',	'datetime',	'',	120,	NULL,	'0',	'',	'',	1,	0,	1,	0,	'group',	'',	'',	3,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:26:39',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('c8ed6bdf56d16122fd58845ff750b936',	'50ada03485b341378d8e94d01781be8c',	'biz_id',	'biz_id',	NULL,	0,	1,	'string',	64,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	1,	0,	0,	'single',	'',	'',	14,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:26:40',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0'),
('cfe193c1d56a3e733363b0441e18477f',	'50ada03485b341378d8e94d01781be8c',	'err_code',	'err_code',	NULL,	0,	1,	'string',	64,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	1,	1,	0,	'single',	'',	'',	10,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:26:40',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0'),
('d7bc95bbd4cceb5bcc9a77b3de3c1aa9',	'50ada03485b341378d8e94d01781be8c',	'id',	'主键',	NULL,	1,	0,	'string',	36,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	0,	0,	1,	'single',	'',	'',	1,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:26:39',	'admin',	'',	'',	'',	'',	'',	NULL,	'0',	NULL,	NULL,	'0'),
('da71a1877b7d95b7968c5d9f4a32425c',	'50ada03485b341378d8e94d01781be8c',	'request_body',	'request_body',	NULL,	0,	1,	'Text',	0,	0,	'',	'',	'',	'',	'text',	'',	120,	NULL,	'0',	'',	'',	0,	1,	0,	0,	'single',	'',	'',	13,	'admin',	'2021-03-10 16:35:54',	'2021-03-10 16:30:02',	'admin',	'',	'',	'',	'',	'',	'text',	'0',	NULL,	NULL,	'0');

-- 菜单和权限配置
delete from sys_permission where id in ('1369569329649557506', '1369570113380425729');
delete from sys_role_permission where permission_id in ('1369569329649557506', '1369570113380425729');

INSERT INTO `sys_permission` (`id`, `parent_id`, `name`, `url`, `component`, `component_name`, `redirect`, `menu_type`, `perms`, `perms_type`, `sort_no`, `always_show`, `icon`, `is_route`, `is_leaf`, `keep_alive`, `hidden`, `description`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`, `rule_flag`, `status`, `internal_or_external`) VALUES
('1369569329649557506',	NULL,	'运营数据',	'/operation',	'layouts/RouteView',	NULL,	NULL,	0,	NULL,	'1',	0.10,	0,	'stock',	1,	0,	0,	0,	NULL,	'admin',	'2021-03-10 16:42:28',	NULL,	NULL,	0,	0,	'1',	0),
('1369570113380425729',	'1369569329649557506',	'SMS短信',	'/online/cgformList/50ada03485b341378d8e94d01781be8c',	'50ada03485b341378d8e94d01781be8c',	NULL,	NULL,	1,	NULL,	'1',	1.00,	0,	NULL,	0,	1,	0,	0,	NULL,	'admin',	'2021-03-10 16:45:35',	NULL,	NULL,	0,	0,	'1',	0);

INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `data_rule_ids`, `operate_date`, `operate_ip`) VALUES
('1369570265545580546',	'f6817f48af4fb3af11b9e8bf182f618b',	'1369569329649557506',	NULL,	'2021-03-10 16:46:11',	'127.0.0.1'),
('1369570265562357762',	'f6817f48af4fb3af11b9e8bf182f618b',	'1369570113380425729',	NULL,	'2021-03-10 16:46:11',	'127.0.0.1');
