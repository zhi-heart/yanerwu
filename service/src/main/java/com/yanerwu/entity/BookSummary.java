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
 * @Description 列表
 */
@Table(name="book_summary")
public class BookSummary extends BaseEntity implements java.io.Serializable{
    private static final long serialVersionUID = 5454155825314635342L;

    //alias
    public static final String TABLE_ALIAS = "BookSummary";
    public static final String ALIAS_ID = "id";
    public static final String ALIAS_QIDIAN_ID = "qidianId";
    public static final String ALIAS_NAME = "name";
    public static final String ALIAS_TYPE_ID = "typeId";
    public static final String ALIAS_TYPE = "type";
    public static final String ALIAS_STATUS = "status";
    public static final String ALIAS_SUMMARY = "summary";
    public static final String ALIAS_AUTHOR = "author";
    public static final String ALIAS_CREATE_TIME = "createTime";
    public static final String ALIAS_UPDATE_TIME = "updateTime";

    //date formats

    //columns START
    /**序号*/
    @Id
    @Column(name="id")
    private java.lang.Long id;
    /***/
    @Column(name="qidian_id")
    private java.lang.Integer qidianId;
    /**书名*/
    @Column(name="name")
    private java.lang.String name;
    /**类别?{"0":"未知","1":"武侠"}*/
    @Column(name="type_id")
    private java.lang.Integer typeId;
    /***/
    @Column(name="type")
    private java.lang.String type;
    /**状态?{"0":"连载","1":"全本","2":"未知"}*/
    @Column(name="status")
    private java.lang.Integer status;
    /**简介*/
    @Column(name="summary")
    private java.lang.String summary;
    /**作者*/
    @Column(name="author")
    private java.lang.String author;
    /**创建时间*/
    @Column(name="create_time")
    private String createTime;
    /**更新时间*/
    @Column(name="update_time")
    private String updateTime;
    //columns END
    @Column(name="biquge_url")
    private String biqugeUrl;

    public BookSummary(){
    }

    public BookSummary(
            java.lang.Long id
    ){
        this.id = id;
    }

    public void setId(java.lang.Long value) {
        this.id = value;
    }

    public java.lang.Long getId() {
        return this.id;
    }
    public void setQidianId(java.lang.Integer value) {
        this.qidianId = value;
    }

    public java.lang.Integer getQidianId() {
        return this.qidianId;
    }
    public void setName(java.lang.String value) {
        this.name = value;
    }

    public java.lang.String getName() {
        return this.name;
    }
    public void setTypeId(java.lang.Integer value) {
        this.typeId = value;
    }

    public java.lang.Integer getTypeId() {
        return this.typeId;
    }
    public void setType(java.lang.String value) {
        this.type = value;
    }

    public java.lang.String getType() {
        return this.type;
    }
    public void setStatus(java.lang.Integer value) {
        this.status = value;
    }

    public java.lang.Integer getStatus() {
        return this.status;
    }
    public void setSummary(java.lang.String value) {
        this.summary = value;
    }

    public java.lang.String getSummary() {
        return this.summary;
    }
    public void setAuthor(java.lang.String value) {
        this.author = value;
    }

    public java.lang.String getAuthor() {
        return this.author;
    }
    public void setCreateTime(String value) {
        this.createTime = value;
    }

    public String getCreateTime() {
        return this.createTime;
    }
    public void setUpdateTime(String value) {
        this.updateTime = value;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public String getBiqugeUrl() {
        return biqugeUrl;
    }

    public void setBiqugeUrl(String biqugeUrl) {
        this.biqugeUrl = biqugeUrl;
    }

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("Id",getId())
                .append("QidianId",getQidianId())
                .append("Name",getName())
                .append("TypeId",getTypeId())
                .append("Type",getType())
                .append("Status",getStatus())
                .append("Summary",getSummary())
                .append("Author",getAuthor())
                .append("CreateTime",getCreateTime())
                .append("UpdateTime",getUpdateTime())
                .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if(obj instanceof BookSummary == false) return false;
        if(this == obj) return true;
        BookSummary other = (BookSummary)obj;
        return new EqualsBuilder()
                .append(getId(),other.getId())
                .isEquals();
    }
}

