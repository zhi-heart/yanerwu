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
 * @Description BookDetail
 */
@Table(name="book_detail")
public class BookDetail extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;

	//alias
	public static final String TABLE_ALIAS = "BookDetail";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_BOOK_ID = "bookId";
	public static final String ALIAS_NO = "no";
	public static final String ALIAS_TITLE = "title";
	public static final String ALIAS_CONTENT = "content";
	public static final String ALIAS_UPDATE_TIME = "updateTime";

	//date formats

	//columns START
    /**序号*/
	@Id
	@Column(name="id")
	private Long id;
    /**书籍序号*/
	@Column(name="book_id")
	private Long bookId;
    /**章节序号*/
	@Column(name="no")
	private Integer no;
    /**章节名*/
	@Column(name="title")
	private String title;
    /**章节名md5*/
    @Column(name="title_md5")
    private String titleMd5;
    /**内容*/
	@Column(name="content")
	private String content;
    /**更新时间*/
	@Column(name="update_time")
	private String updateTime;
	//columns END

	public BookDetail(){
	}

	public BookDetail(
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
	public void setBookId(Long value) {
		this.bookId = value;
	}

	public Long getBookId() {
		return this.bookId;
	}
	public void setNo(Integer value) {
		this.no = value;
	}

	public Integer getNo() {
		return this.no;
	}
	public void setTitle(String value) {
		this.title = value;
	}

	public String getTitle() {
		return this.title;
	}
	public void setContent(String value) {
		this.content = value;
	}

	public String getContent() {
		return this.content;
	}
	public void setUpdateTime(String value) {
		this.updateTime = value;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

    public String getTitleMd5() {
        return titleMd5;
    }

    public void setTitleMd5(String titleMd5) {
        this.titleMd5 = titleMd5;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("BookId",getBookId())
			.append("No",getNo())
			.append("Title",getTitle())
			.append("Content",getContent())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof BookDetail == false) return false;
		if(this == obj) return true;
		BookDetail other = (BookDetail)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

