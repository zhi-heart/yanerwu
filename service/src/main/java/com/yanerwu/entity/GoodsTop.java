package com.yanerwu.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanerwu.annotation.Column;
import com.yanerwu.annotation.Id;
import com.yanerwu.annotation.Table;
import com.yanerwu.common.BaseEntity;

/**
 * @author Zuz
 * @version 1.0
 * @Description 人气商品
 */
@Table(name = "goods_top")
public class GoodsTop extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;

    public GoodsTop(){}

    public GoodsTop(long seqId){
        this.seqId = seqId;
    }

    //columns START
    /**
     * 序号
     */
    @Id
    @JSONField(name = "seqId")
    @Column(name = "seqId")
    private Long seqId;
    /**
     * 商品id
     */
    @JSONField(name = "ID")
    @Column(name = "id")
    private Long id;
    /**
     * 商品淘宝id
     */
    @JSONField(name = "GoodsID")
    @Column(name = "goods_id")
    private Long goodsId;
    /**
     * 商品标题
     */
    @JSONField(name = "Title")
    @Column(name = "title")
    private String title;
    /**
     * 商品短标题
     */
    @JSONField(name = "D_title")
    @Column(name = "title_simple")
    private String titleSimple;
    /**
     * 商品主图
     */
    @JSONField(name = "Pic")
    @Column(name = "pic")
    private String pic;
    /**
     * 分类ID
     */
    @JSONField(name = "Cid")
    @Column(name = "cid")
    private Integer cid;
    /**
     * 正常售价
     */
    @JSONField(name = "Org_Price")
    @Column(name = "org_price")
    private Double orgPrice;
    /**
     * 券后价
     */
    @JSONField(name = "Price")
    @Column(name = "price")
    private Double price;
    /**
     * 是否天猫
     */
    @JSONField(name = "IsTmall")
    @Column(name = "is_tmall")
    private Integer isTmall;
    /**
     * 商品销量
     */
    @JSONField(name = "Sales_num")
    @Column(name = "sales_num")
    private Integer salesNum;
    /**
     * 商品描述分
     */
    @JSONField(name = "Dsr")
    @Column(name = "dsr")
    private Double dsr;
    /**
     * 卖家ID
     */
    @JSONField(name = "SellerId")
    @Column(name = "seller_id")
    private String sellerId;
    /**
     * 计划通用佣金比例
     */
    @JSONField(name = "Commission_jihua")
    @Column(name = "commission_jihua")
    private Double commissionJihua;
    /**
     * 高佣鹊桥佣金比例
     */
    @JSONField(name = "Commission_queqiao")
    @Column(name = "commission_queqiao")
    private Double commissionQueqiao;
    /**
     * 计划链接
     */
    @JSONField(name = "Jihua_link")
    @Column(name = "jihua_link")
    private String jihuaLink;
    /**
     * 计划审核状态
     */
    @JSONField(name = "Jihua_shenhe")
    @Column(name = "jihua_shenhe")
    private Integer jihuaShenhe;
    /**
     * 男女通用，透气凉爽
     */
    @JSONField(name = "Introduce")
    @Column(name = "introduce")
    private String introduce;
    /**
     * 优惠券ID
     */
    @JSONField(name = "Quan_id")
    @Column(name = "quan_id")
    private String quanId;
    /**
     * 优惠券金额
     */
    @JSONField(name = "Quan_price")
    @Column(name = "quan_price")
    private Double quanPrice;
    /**
     * 优惠券结束时间
     */
    @JSONField(name = "Quan_time")
    @Column(name = "quan_time")
    private String quanTime;
    /**
     * 优惠券剩余数量
     */
    @JSONField(name = "Quan_surplus")
    @Column(name = "quan_surplus")
    private Integer quanSurplus;
    /**
     * 已领券数量
     */
    @JSONField(name = "Quan_receive")
    @Column(name = "quan_receive")
    private Integer quanReceive;
    /**
     * 优惠券使用条件
     */
    @Column(name = "quan_condition")
    @JSONField(name = "Quan_condition" )
    private String quanCondition;
    /**
     * 手机券短接
     */
    @JSONField(name = "Quan_mlink")
    @Column(name = "quan_m_link")
    private String quanMlink;
    /**
     * 手机券链接
     */
    @JSONField(name = "Quan_link")
    @Column(name = "Quan_link")
    private String quanLink;

    //columns END


    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getTitleSimple() {
        return this.titleSimple;
    }

    public void setTitleSimple(String value) {
        this.titleSimple = value;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String value) {
        this.pic = value;
    }

    public Integer getCid() {
        return this.cid;
    }

    public void setCid(Integer value) {
        this.cid = value;
    }

    public Double getOrgPrice() {
        return this.orgPrice;
    }

    public void setOrgPrice(Double value) {
        this.orgPrice = value;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double value) {
        this.price = value;
    }

    public Integer getIsTmall() {
        return this.isTmall;
    }

    public void setIsTmall(Integer value) {
        this.isTmall = value;
    }

    public Integer getSalesNum() {
        return this.salesNum;
    }

    public void setSalesNum(Integer value) {
        this.salesNum = value;
    }

    public Double getDsr() {
        return this.dsr;
    }

    public void setDsr(Double value) {
        this.dsr = value;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Double getCommissionJihua() {
        return this.commissionJihua;
    }

    public void setCommissionJihua(Double value) {
        this.commissionJihua = value;
    }

    public Double getCommissionQueqiao() {
        return this.commissionQueqiao;
    }

    public void setCommissionQueqiao(Double value) {
        this.commissionQueqiao = value;
    }

    public String getJihuaLink() {
        return this.jihuaLink;
    }

    public void setJihuaLink(String value) {
        this.jihuaLink = value;
    }

    public Integer getJihuaShenhe() {
        return this.jihuaShenhe;
    }

    public void setJihuaShenhe(Integer value) {
        this.jihuaShenhe = value;
    }

    public String getIntroduce() {
        return this.introduce;
    }

    public void setIntroduce(String value) {
        this.introduce = value;
    }

    public String getQuanId() {
        return this.quanId;
    }

    public void setQuanId(String value) {
        this.quanId = value;
    }

    public Double getQuanPrice() {
        return this.quanPrice;
    }

    public void setQuanPrice(Double value) {
        this.quanPrice = value;
    }

    public String getQuanTime() {
        return this.quanTime;
    }

    public void setQuanTime(String value) {
        this.quanTime = value;
    }

    public Integer getQuanSurplus() {
        return this.quanSurplus;
    }

    public void setQuanSurplus(Integer value) {
        this.quanSurplus = value;
    }

    public Integer getQuanReceive() {
        return this.quanReceive;
    }

    public void setQuanReceive(Integer value) {
        this.quanReceive = value;
    }

    public String getQuanCondition() {
        return this.quanCondition;
    }

    public void setQuanCondition(String value) {
        this.quanCondition = value;
    }

    public String getQuanMlink() {
        return this.quanMlink;
    }

    public void setQuanMlink(String value) {
        this.quanMlink = value;
    }

    public String getQuanLink() {
        return this.quanLink;
    }

    public void setQuanLink(String value) {
        this.quanLink = value;
    }

    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

}

