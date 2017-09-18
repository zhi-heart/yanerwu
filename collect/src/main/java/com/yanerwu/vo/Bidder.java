package com.yanerwu.vo;

/**
 * @Author Zuz
 * @Date 2017/9/8 10:42
 * @Description
 */
public class Bidder {
    private String bidderName;
    private double amountBid;
    private String startTime;
    private String expireTime;
    private String loanId;

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public double getAmountBid() {
        return amountBid;
    }

    public void setAmountBid(double amountBid) {
        this.amountBid = amountBid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}
