package com.starter.auth.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author dingxl
 * @date 2/19/2021 5:19 PM
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String phone;
    private List<String> roleList;
    private List<String> actionList;

    /**
     * 所属租户只支持一个。如果同时属于多个租户，前端选择，后端保存，功能待扩展。
     */
    private String relTenantIds;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public List<String> getActionList() {
        return actionList;
    }

    public void setActionList(List<String> actionList) {
        this.actionList = actionList;
    }
}
