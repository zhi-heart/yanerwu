package com.yanerwu.cas.authentication.utils;


import com.yanerwu.annotation.Column;
import com.yanerwu.annotation.Id;
import com.yanerwu.annotation.Table;

/**
 * @Author Zuz
 * @Date 2017/5/21
 * @Description 用户vo
 */
@Table(name = "WDZJ_ADMIN_INFO")
public class AdminInfoVo implements java.io.Serializable {

    //管理员ID
    @Id
    @Column(name = "ADMIN_USER_ID")
    private Integer adminUserId;
    //管理员名
    @Column(name = "ADMIN_USER_NAME")
    private String adminUserName;
    //昵称
    @Column(name = "ADMIN_USER_ID")
    private String adminNickName;
    //密码
    @Column(name = "PASSWORD")
    private String password;
    //最后访问时间
    @Column(name = "LAST_ACCESS_DATE")
    private String lastAccessDate;
    //登录IP
    @Column(name = "LOGIN_IP")
    private String loginIp;
    //所在组
    @Column(name = "GROUP_ID")
    private int groupId;
    //所在平台
    @Column(name = "PLAT_ID")
    private String platId;
    //授权访问IP
    @Column(name = "ACCEPT_IP")
    private String acceptIp;

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getAdminNickName() {
        return adminNickName;
    }

    public void setAdminNickName(String adminNickName) {
        this.adminNickName = adminNickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(String lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public String getAcceptIp() {
        return acceptIp;
    }

    public void setAcceptIp(String acceptIp) {
        this.acceptIp = acceptIp;
    }
}
