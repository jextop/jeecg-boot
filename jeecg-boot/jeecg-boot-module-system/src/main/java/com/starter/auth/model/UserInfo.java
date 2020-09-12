package com.starter.auth.model;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author dingxl
 * @date 2/19/2021 5:19 PM
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String username;
    private String phone;
    private Integer soloLogin;
    private Set<String> tokenSet;
    private List<String> roleList;
    private List<String> actionList;

    /**
     * 所属租户只支持一个。如果同时属于多个租户，前端选择，后端保存，功能待扩展。
     */
    private String relTenantIds;

    /**
     * 记录token
     */
    public void addToken(String token) {
        if (this.tokenSet == null) {
            this.tokenSet = new HashSet<String>();
        }

        this.tokenSet.add(token);
    }

    public void addToken(Set<String> tokenSet) {
        if (this.tokenSet == null) {
            this.tokenSet = tokenSet;
        } else if (CollectionUtils.isNotEmpty(tokenSet)) {
            this.tokenSet.addAll(tokenSet);
        }
    }

    public void deleteToken(String token) {
        if (CollectionUtils.isNotEmpty(tokenSet) && StringUtils.isNotEmpty(token)) {
            tokenSet.remove(token);
        }
    }

    public Set<String> getTokenSet() {
        return tokenSet;
    }

    public void setTokenSet(Set<String> tokenSet) {
        this.tokenSet = tokenSet;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSoloLogin() {
        return soloLogin;
    }

    public void setSoloLogin(Integer soloLogin) {
        this.soloLogin = soloLogin;
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
