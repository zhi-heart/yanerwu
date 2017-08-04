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
 * @Description UserGoods
 */
@Table(name="user_goods")
public class UserGoods extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;

	//alias
	public static final String TABLE_ALIAS = "UserGoods";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_GOODS_ID = "goodsId";
	public static final String ALIAS_GID = "gid";
	public static final String ALIAS_CONVER_LINK = "converLink";
	public static final String ALIAS_CONVER_WORD = "converWord";
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
    /**商品序号*/
	@Column(name="goods_id")
	private Long goodsId;
    /**淘大客序号*/
	@Column(name="gid")
	private Long gid;
    /**淘链接*/
	@Column(name="conver_link")
	private String converLink;
    /**淘口令*/
	@Column(name="conver_word")
	private String converWord;
    /**状态?{"0":"待转换","1":"待发布","2":"已发布","3":"发布异常"}*/
	@Column(name="status")
	private Integer status;
	//columns END

	public UserGoods(){
	}

	public UserGoods(
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
	public void setGoodsId(Long value) {
		this.goodsId = value;
	}

	public Long getGoodsId() {
		return this.goodsId;
	}
	public void setGid(Long value) {
		this.gid = value;
	}

	public Long getGid() {
		return this.gid;
	}
	public void setConverLink(String value) {
		this.converLink = value;
	}

	public String getConverLink() {
		return this.converLink;
	}
	public void setConverWord(String value) {
		this.converWord = value;
	}

	public String getConverWord() {
		return this.converWord;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("GoodsId",getGoodsId())
			.append("Gid",getGid())
			.append("ConverLink",getConverLink())
			.append("ConverWord",getConverWord())
			.append("Status",getStatus())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof UserGoods == false) return false;
		if(this == obj) return true;
		UserGoods other = (UserGoods)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

