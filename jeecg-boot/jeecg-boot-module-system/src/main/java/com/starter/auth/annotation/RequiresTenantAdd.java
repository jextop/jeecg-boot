package com.starter.auth.annotation;

import com.starter.auth.config.Permission;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dingxl
 * @date 2/20/2021 10:43 PM
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@RequiresPermissions(Permission.TENANT_ADD)
public @interface RequiresTenantAdd {
}
