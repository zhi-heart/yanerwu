package com.wdzj;


import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author LiChangchun
 */
public class AdminInfoVo implements java.io.Serializable {

    //管理员ID
    private String adminUserId;
    //管理员名
    private String adminUserName;
    //昵称
    private String adminNickName;
    //密码
    private String password;
    //最后访问时间
    private String lastAccessDate;
    //登录IP
    private String loginIp;
    //所在组
    private int groupId;
    //所在组名
    private String groupName;
    //所在平台
    private String platId;
    //所在平台名
    private String platName;

    //授权访问IP
    private String acceptIp;

    //用户权限
    private UserAuth userAuth;
    //全部权限
    private Map<String, List<Map<String, Object>>> allAuth;

    /**
     * @return the adminUserId
     */
    public String getAdminUserId() {
        return adminUserId;
    }

    /**
     * @param adminUserId the adminUserId to set
     */
    public void setAdminUserId(String adminUserId) {
        this.adminUserId = adminUserId;
    }

    /**
     * @return the adminUserName
     */
    public String getAdminUserName() {
        return adminUserName;
    }

    /**
     * @param adminUserName the adminUserName to set
     */
    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    /**
     * @return the adminNickName
     */
    public String getAdminNickName() {
        return adminNickName;
    }

    /**
     * @param adminNickName the adminNickName to set
     */
    public void setAdminNickName(String adminNickName) {
        this.adminNickName = adminNickName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the lastAccessDate
     */
    public String getLastAccessDate() {
        return lastAccessDate;
    }

    /**
     * @param lastAccessDate the lastAccessDate to set
     */
    public void setLastAccessDate(String lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    /**
     * @return the loginIp
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * @param loginIp the loginIp to set
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getAcceptIp() {
        return acceptIp;
    }

    public void setAcceptIp(String acceptIp) {
        this.acceptIp = acceptIp;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public Map<String, List<Map<String, Object>>> getAllAuth() {
        return allAuth;
    }

    public void setAllAuth(Map<String, List<Map<String, Object>>> allAuth) {
        this.allAuth = allAuth;
    }
}
