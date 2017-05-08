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
 * @Description MvList
 */@Table(name="movie.mv_list")
public class MvList extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MvList";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_UUID = "uuid";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_STATUS = "status";
	public static final String ALIAS_TYPE = "type";
	public static final String ALIAS_AREA = "area";
	public static final String ALIAS_ACTOR = "actor";
	public static final String ALIAS_SUMMARY = "summary";
	public static final String ALIAS_ORIGINAL_URL = "originalUrl";
	public static final String ALIAS_DOWNLOAD_URL = "downloadUrl";
	public static final String ALIAS_PUB_TIME = "pubTime";
	public static final String ALIAS_UPD_TIME = "updTime";
	
	//date formats
	
	//columns START
	@Id
	@Column(name="id")
	private Integer id;
	@Column(name="uuid")
	private String uuid;
	@Column(name="name")
	private String name;
	@Column(name="status")
	private String status;
	@Column(name="type")
	private String type;
	@Column(name="area")
	private String area;
	@Column(name="actor")
	private String actor;
	@Column(name="summary")
	private String summary;
	@Column(name="original_url")
	private String originalUrl;
	@Column(name="download_url")
	private String downloadUrl;
	@Column(name="pub_time")
	private String pubTime;
	@Column(name="upd_time")
	private String updTime;
	//columns END

	public MvList(){
	}

	public MvList(
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
	public void setUuid(String value) {
		this.uuid = value;
	}
	
	public String getUuid() {
		return this.uuid;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setType(String value) {
		this.type = value;
	}
	
	public String getType() {
		return this.type;
	}
	public void setActor(String value) {
		this.actor = value;
	}
	
	public String getActor() {
		return this.actor;
	}
	public void setSummary(String value) {
		this.summary = value;
	}
	
	public String getSummary() {
		return this.summary;
	}
	public void setOriginalUrl(String value) {
		this.originalUrl = value;
	}
	
	public String getOriginalUrl() {
		return this.originalUrl;
	}
	public void setDownloadUrl(String value) {
		this.downloadUrl = value;
	}
	
	public String getDownloadUrl() {
		return this.downloadUrl;
	}
	public void setPubTime(String value) {
		this.pubTime = value;
	}
	
	public String getPubTime() {
		return this.pubTime;
	}
	public void setUpdTime(String value) {
		this.updTime = value;
	}
	
	public String getUpdTime() {
		return this.updTime;
	}
 
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Uuid",getUuid())
			.append("Name",getName())
			.append("Status",getStatus())
			.append("Type",getType())
			.append("Area",getArea())
			.append("Actor",getActor())
			.append("Summary",getSummary())
			.append("OriginalUrl",getOriginalUrl())
			.append("DownloadUrl",getDownloadUrl())
			.append("PubTime",getPubTime())
			.append("UpdTime",getUpdTime())
			.toString();
	}

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MvList == false) return false;
		if(this == obj) return true;
		MvList other = (MvList)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

