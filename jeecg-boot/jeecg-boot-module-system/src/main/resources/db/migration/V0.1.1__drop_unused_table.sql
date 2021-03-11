-- 在线表单
delete from onl_cgform_head where not id in ('50ada03485b341378d8e94d01781be8c', '56efb74326e74064b60933f6f8af30ea', '86bf17839a904636b7ed96201b2fa6ea', '09fd28e4b7184c1a9668496a5c496450');
delete from onl_cgform_field where cgform_head_id not in ('50ada03485b341378d8e94d01781be8c', '56efb74326e74064b60933f6f8af30ea', '86bf17839a904636b7ed96201b2fa6ea', '09fd28e4b7184c1a9668496a5c496450');

-- DROP TABLE IF EXISTS `mvp_api_call_sms`;
-- DROP TABLE IF EXISTS `ces_order_main`;
-- DROP TABLE IF EXISTS `ces_order_customer`;
-- DROP TABLE IF EXISTS `ces_order_goods`;

-- 数据表
DROP TABLE IF EXISTS `ces_field_kongj`;
DROP TABLE IF EXISTS `ces_order_goods$1`;
DROP TABLE IF EXISTS `ces_shop_goods`;
DROP TABLE IF EXISTS `ces_shop_type`;
DROP TABLE IF EXISTS `demo_field_def_val_main`;
DROP TABLE IF EXISTS `demo_field_def_val_sub`;
DROP TABLE IF EXISTS `demo_field_def_val_sub$1`;
DROP TABLE IF EXISTS `test_demo`;
DROP TABLE IF EXISTS `test_enhance_select`;
DROP TABLE IF EXISTS `test_note`;
DROP TABLE IF EXISTS `test_order_main`;
DROP TABLE IF EXISTS `test_order_product`;
DROP TABLE IF EXISTS `test_order_product$1`;
DROP TABLE IF EXISTS `test_shoptype_tree`;
