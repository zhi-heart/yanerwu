package com.yanerwu.entity;

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
 * @Description Upower
 */@Table(name="movie.u_power")
public class Upower extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Upower";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_POWER_PATH = "powerPath";
	public static final String ALIAS_PARENT_UPOWER_ID = "parentUpowerId";
	public static final String ALIAS_POWER_TYPE = "powerType";
	public static final String ALIAS_REMARK = "remark";
	
	//date formats
	
	//columns START
	@Id
	@Column(name="id")
	private Integer id;
	@Column(name="name")
	private String name;
	@Column(name="power_path")
	private String powerPath;
	@Column(name="parent_u_power_id")
	private Integer parentUpowerId;
	@Column(name="power_type")
	private Integer powerType;
	@Column(name="remark")
	private String remark;
	//columns END

	public Upower(){
	}

	public Upower(
		Integer id
	){
		this.id = id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setPowerPath(String value) {
		this.powerPath = value;
	}
	
	public String getPowerPath() {
		return this.powerPath;
	}
	public void setParentUpowerId(Integer value) {
		this.parentUpowerId = value;
	}
	
	public Integer getParentUpowerId() {
		return this.parentUpowerId;
	}
	public void setPowerType(Integer value) {
		this.powerType = value;
	}
	
	public Integer getPowerType() {
		return this.powerType;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
 
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("PowerPath",getPowerPath())
			.append("ParentUpowerId",getParentUpowerId())
			.append("PowerType",getPowerType())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Upower == false) return false;
		if(this == obj) return true;
		Upower other = (Upower)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

