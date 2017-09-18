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
 * @Description 交易信息
 */

@Table(name="information")
public class Information extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;

	//alias
	public static final String TABLE_ALIAS = "Information";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_PHONE = "phone";
	public static final String ALIAS_SOURCE_NAME = "sourceName";
	public static final String ALIAS_TYPE = "type";
	public static final String ALIAS_PROVINCE = "province";
	public static final String ALIAS_CITY = "city";
	public static final String ALIAS_AREA_CODE = "areaCode";
	public static final String ALIAS_ZIP_CODE = "zipCode";

	//date formats

	//columns START
    /**序号*/
	@Id
	@Column(name="id")
	private Integer id;
    /**手机号*/
	@Column(name="phone")
	private String phone;
    @Column(name="amount")
    private Double amount;
    @Column(name="interest_rate")
    private Double interestRate;
    @Column(name="use_for")
    private String useFor;
    @Column(name="start_date")
    private String startDate;
    @Column(name="expire_date")
    private String expireDate;
    @Column(name = "loan_id")
    private String loanId;
    /**来源*/
	@Column(name="source_name")
	private String sourceName;
    /**类别?{"-1":"未知", "1":"借款人", "2":"投资人"}*/
	@Column(name="type")
	private Integer type;
    /**省份*/
	@Column(name="province")
	private String province;
    /**城市*/
	@Column(name="city")
	private String city;
    /**电话区号*/
	@Column(name="area_code")
	private String areaCode;
    /**邮政编码*/
	@Column(name="zip_code")
	private String zipCode;
	//columns END

	public Information(){
	}

	public Information(
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
	public void setPhone(String value) {
		this.phone = value;
	}

	public String getPhone() {
		return this.phone;
	}
	public void setSourceName(String value) {
		this.sourceName = value;
	}

	public String getSourceName() {
		return this.sourceName;
	}
	public void setType(Integer value) {
		this.type = value;
	}

	public Integer getType() {
		return this.type;
	}
	public void setProvince(String value) {
		this.province = value;
	}

	public String getProvince() {
		return this.province;
	}
	public void setCity(String value) {
		this.city = value;
	}

	public String getCity() {
		return this.city;
	}
	public void setAreaCode(String value) {
		this.areaCode = value;
	}

	public String getAreaCode() {
		return this.areaCode;
	}
	public void setZipCode(String value) {
		this.zipCode = value;
	}

	public String getZipCode() {
		return this.zipCode;
	}

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public String getUseFor() {
        return useFor;
    }

    public void setUseFor(String useFor) {
        this.useFor = useFor;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Phone",getPhone())
			.append("SourceName",getSourceName())
			.append("Type",getType())
			.append("Province",getProvince())
			.append("City",getCity())
			.append("AreaCode",getAreaCode())
			.append("ZipCode",getZipCode())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof Information == false) return false;
		if(this == obj) return true;
		Information other = (Information)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }
}

