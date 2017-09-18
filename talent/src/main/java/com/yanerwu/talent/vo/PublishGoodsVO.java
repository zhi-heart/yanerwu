package com.yanerwu.talent.vo;

/**
 * @Author Zuz
 * @Date 2017/7/28 14:45
 * @Description
 */
public class PublishGoodsVO {
//    u.id,g.title,g.introduce,g.is_tmall,u.conver_word,u.goods_id,g.org_price,g.quan_price,g.price
    private Long id;
    private Long userId;
    private Double orgPrice;
    private Double quanPrice;
    private Double price;
    private String title;
    private String titleSimple;
    private String introduce;
    private boolean isTmall;
    private String converWord;
    private String goodsId;
    private String taobaoLoginName;
    private String mailLoginName;
    private String mailLoginPwd;
    private int salesNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public boolean isTmall() {
        return isTmall;
    }

    public void setTmall(boolean tmall) {
        isTmall = tmall;
    }

    public String getConverWord() {
        return converWord;
    }

    public void setConverWord(String converWord) {
        this.converWord = converWord;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Double getOrgPrice() {
        return orgPrice;
    }

    public void setOrgPrice(Double orgPrice) {
        this.orgPrice = orgPrice;
    }

    public Double getQuanPrice() {
        return quanPrice;
    }

    public void setQuanPrice(Double quanPrice) {
        this.quanPrice = quanPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitleSimple() {
        return titleSimple;
    }

    public void setTitleSimple(String titleSimple) {
        this.titleSimple = titleSimple;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTaobaoLoginName() {
        return taobaoLoginName;
    }

    public void setTaobaoLoginName(String taobaoLoginName) {
        this.taobaoLoginName = taobaoLoginName;
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

    public int getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(int salesNum) {
        this.salesNum = salesNum;
    }
}
