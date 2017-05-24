package com.yanerwu.entity;

import com.yanerwu.annotation.Column;
import com.yanerwu.annotation.Id;
import com.yanerwu.annotation.Table;
import com.yanerwu.common.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

/**
 * @author Zuz
 * @version 1.0
 * @Description Uuser
 */
@Table(name = "movie.u_user")
public class Uuser extends BaseEntity implements java.io.Serializable {
    //alias
    public static final String TABLE_ALIAS = "Uuser";
    public static final String ALIAS_ID = "id";
    public static final String ALIAS_LOGIN_NAME = "loginName";
    public static final String ALIAS_LOGIN_PWD = "loginPwd";
    public static final String ALIAS_NAME = "name";
    public static final String ALIAS_PHONE = "phone";
    public static final String ALIAS_CREATE_DATE = "createDate";
    public static final String ALIAS_VALID = "valid";
    private static final long serialVersionUID = 5454155825314635342L;

    //date formats
    //columns START
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "login_name")
    private String loginName;
    @Column(name = "login_pwd")
    private String loginPwd;
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "create_date")
    private String createDate;
    @Column(name = "valid")
    private String valid;
    //columns END
    private List<Upower> upowers;
    private Map<Integer, Upower> powerIds;
    private Map<String, Upower> powerPaths;

    public Uuser() {
    }

    public Uuser(
            Integer id
    ) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String value) {
        this.loginName = value;
    }

    public String getLoginPwd() {
        return this.loginPwd;
    }

    public void setLoginPwd(String value) {
        this.loginPwd = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String value) {
        this.phone = value;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String value) {
        this.createDate = value;
    }

    public String getValid() {
        return this.valid;
    }

    public void setValid(String value) {
        this.valid = value;
    }

    public List<Upower> getUpowers() {
        return upowers;
    }

    public void setUpowers(List<Upower> upowers) {
        this.upowers = upowers;
    }

    public Map<Integer, Upower> getPowerIds() {
        return powerIds;
    }

    public void setPowerIds(Map<Integer, Upower> powerIds) {
        this.powerIds = powerIds;
    }

    public Map<String, Upower> getPowerPaths() {
        return powerPaths;
    }

    public void setPowerPaths(Map<String, Upower> powerPaths) {
        this.powerPaths = powerPaths;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Id", getId())
                .append("LoginName", getLoginName())
                .append("LoginPwd", getLoginPwd())
                .append("Name", getName())
                .append("Phone", getPhone())
                .append("CreateDate", getCreateDate())
                .append("Valid", getValid())
                .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Uuser == false) return false;
        if (this == obj) return true;
        Uuser other = (Uuser) obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

