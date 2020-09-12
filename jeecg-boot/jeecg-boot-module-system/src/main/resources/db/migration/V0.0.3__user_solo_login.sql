ALTER TABLE `sys_user`
ADD COLUMN `solo_login` tinyint(1) DEFAULT 0 COMMENT '是否允许多终端同时登录，0或空无限制，1不允许多客户端同时登陆';
