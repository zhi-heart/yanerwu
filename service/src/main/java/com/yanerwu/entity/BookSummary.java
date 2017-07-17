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
@Table(name = "book_summary")
public class BookSummary extends BaseEntity implements java.io.Serializable {
    //alias
    public static final String TABLE_ALIAS = "BookSummary";
    public static final String ALIAS_ID = "id";
    public static final String ALIAS_QIDIAN_ID = "qidianId";
    public static final String ALIAS_QIDIAN_URL = "qidianUrl";
    public static final String ALIAS_BIQUGE_ID = "biqugeId";
    public static final String ALIAS_BIQUGE_URL = "biqugeUrl";
    public static final String ALIAS_NAME = "name";
    public static final String ALIAS_TYPE_ID = "typeId";
    public static final String ALIAS_TYPE = "type";
    public static final String ALIAS_STATUS = "status";
    public static final String ALIAS_SUMMARY = "summary";
    public static final String ALIAS_AUTHOR = "author";
    public static final String ALIAS_CREATE_TIME = "createTime";
    public static final String ALIAS_UPDATE_TIME = "updateTime";
    private static final long serialVersionUID = 5454155825314635342L;

    //date formats

    //columns START
    /**
     * 序号
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 起点序号
     */
    @Column(name = "qidian_id")
    private Integer qidianId;
    /**
     * 起点链接
     */
    @Column(name = "qidian_url")
    private String qidianUrl;
    /**
     * 笔趣阁序号
     */
    @Column(name = "biquge_id")
    private Integer biqugeId;
    /**
     * 笔趣阁链接
     */
    @Column(name = "biquge_url")
    private String biqugeUrl;
    /**
     * 书名
     */
    @Column(name = "name")
    private String name;
    /**
     * 类别?{"0":"未知","1":"武侠"}
     */
    @Column(name = "type_id")
    private Integer typeId;
    /**
     * 类别
     */
    @Column(name = "type")
    private String type;
    /**
     * 状态?{"0":"连载","1":"全本","2":"未知"}
     */
    @Column(name = "status")
    private Integer status;
    /**
     * 简介
     */
    @Column(name = "summary")
    private String summary;
    /**
     * 作者
     */
    @Column(name = "author")
    private String author;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private String updateTime;
    /**
     * 点击量
     */
    @Column(name = "cnt")
    private Integer cnt;
    /**
     * 月票
     */
    @Column(name = "rank_cnt")
    private Integer rankCnt;
    //columns END

    public BookSummary() {
    }

    public BookSummary(
            Long id
    ) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public Integer getQidianId() {
        return this.qidianId;
    }

    public void setQidianId(Integer value) {
        this.qidianId = value;
    }

    public String getQidianUrl() {
        return this.qidianUrl;
    }

    public void setQidianUrl(String value) {
        this.qidianUrl = value;
    }

    public Integer getBiqugeId() {
        return this.biqugeId;
    }

    public void setBiqugeId(Integer value) {
        this.biqugeId = value;
    }

    public String getBiqugeUrl() {
        return this.biqugeUrl;
    }

    public void setBiqugeUrl(String value) {
        this.biqugeUrl = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Integer getTypeId() {
        return this.typeId;
    }

    public void setTypeId(Integer value) {
        this.typeId = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer value) {
        this.status = value;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String value) {
        this.summary = value;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String value) {
        this.author = value;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String value) {
        this.createTime = value;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String value) {
        this.updateTime = value;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Integer getRankCnt() {
        return rankCnt;
    }

    public void setRankCnt(Integer rankCnt) {
        this.rankCnt = rankCnt;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Id", getId())
                .append("QidianId", getQidianId())
                .append("QidianUrl", getQidianUrl())
                .append("BiqugeId", getBiqugeId())
                .append("BiqugeUrl", getBiqugeUrl())
                .append("Name", getName())
                .append("TypeId", getTypeId())
                .append("Type", getType())
                .append("Status", getStatus())
                .append("Summary", getSummary())
                .append("Author", getAuthor())
                .append("CreateTime", getCreateTime())
                .append("UpdateTime", getUpdateTime())
                .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof BookSummary == false) return false;
        if (this == obj) return true;
        BookSummary other = (BookSummary) obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

