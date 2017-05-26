package com.wdzj;

import java.util.List;
import java.util.Map;

/**
 * @Author Zuz
 * @Date 2017/5/25 15:33
 * @Description
 */
public class UserAuth implements java.io.Serializable{
    private String roleCode;
    private List<Map<String, Object>> authorities;
    private List<Map<String, Object>> authoritiesTree;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public List<Map<String, Object>> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Map<String, Object>> authorities) {
        this.authorities = authorities;
    }

    public List<Map<String, Object>> getAuthoritiesTree() {
        return authoritiesTree;
    }

    public void setAuthoritiesTree(List<Map<String, Object>> authoritiesTree) {
        this.authoritiesTree = authoritiesTree;
    }
}
