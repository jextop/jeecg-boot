package com.starter.auth.annotation;

import com.starter.auth.config.UserRole;
import org.apache.shiro.authz.annotation.RequiresRoles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dingxl
 * @date 2/20/2021 10:12 PM
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@RequiresRoles(UserRole.ADMIN)
public @interface RequiresAdmin {
}
