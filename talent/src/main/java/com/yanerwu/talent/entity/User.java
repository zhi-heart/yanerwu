package com.yanerwu.talent.entity;

import com.yanerwu.annotation.Column;
import com.yanerwu.annotation.Id;
import com.yanerwu.annotation.Table;
import com.yanerwu.common.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Zuz
 * @version 1.0
 * @Description User
 */
@Table(name="user")
public class User extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;

	//alias
	public static final String TABLE_ALIAS = "User";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_USER_EMAIL = "userEmail";
	public static final String ALIAS_TOKEN = "token";
	public static final String ALIAS_STATUS = "status";

	//date formats

	//columns START
    /**序号*/
	@Id
	@Column(name="id")
	private Long id;
    /**用户序号*/
	@Column(name="user_id")
	private Long userId;
    /**手机或邮箱*/
	@Column(name="user_email")
	private String userEmail;
    /**令牌*/
	@Column(name="token")
	private String token;
    /**淘宝登录名*/
    @Column(name="taobao_login_name")
    private String taobaoLoginName;
    /**淘宝登录密码*/
    @Column(name="taobao_login_pwd")
    private String taobaoLoginPwd;
    /**状态?{"0":"正常","1":"删除","2":"异常"}*/
	@Column(name="status")
	private Integer status;
    @Column(name="mail_login_name")
    private String mailLoginName;
    @Column(name="mail_login_pwd")
    private String mailLoginPwd;
    @Column(name="pid")
    private Long pid;
	//columns END

	public User(){
	}

	public User(
		Long id
	){
		this.id = id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Long getId() {
		return this.id;
	}
	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}
	public void setUserEmail(String value) {
		this.userEmail = value;
	}

	public String getUserEmail() {
		return this.userEmail;
	}
	public void setToken(String value) {
		this.token = value;
	}

	public String getToken() {
		return this.token;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}

	public Integer getStatus() {
		return this.status;
	}

    public String getTaobaoLoginName() {
        return taobaoLoginName;
    }

    public void setTaobaoLoginName(String taobaoLoginName) {
        this.taobaoLoginName = taobaoLoginName;
    }

    public String getTaobaoLoginPwd() {
        return taobaoLoginPwd;
    }

    public void setTaobaoLoginPwd(String taobaoLoginPwd) {
        this.taobaoLoginPwd = taobaoLoginPwd;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("UserEmail",getUserEmail())
			.append("Token",getToken())
			.append("Status",getStatus())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof User == false) return false;
		if(this == obj) return true;
		User other = (User)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

    public String getMailLoginName() {
        return mailLoginName;
    }

    public void setMailLoginName(String mailLoginName) {
        this.mailLoginName = mailLoginName;
    }

    public String getMailLoginPwd() {
        return mailLoginPwd;
    }

    public void setMailLoginPwd(String mailLoginPwd) {
        this.mailLoginPwd = mailLoginPwd;
    }
}

