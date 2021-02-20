package com.starter.auth.model;

import java.io.Serializable;

/**
 * @author dingxl
 * @date 2/19/2021 5:19 PM
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String username;
    private String realname;
    private String phone;
    private String relTenantIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRelTenantIds() {
        return relTenantIds;
    }

    public void setRelTenantIds(String relTenantIds) {
        this.relTenantIds = relTenantIds;
    }
}
