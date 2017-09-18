package com.yanerwu.vo;

/**
 * @Author Zuz
 * @Date 2017/9/8 10:38
 * @Description
 */
public class Borrow {
    private String borrower;
    private double amount;
    private double interestRate;
    private String useFor;
    private String loanId;
    private String successTimeDate;
    private Double loanPeriod;

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getUseFor() {
        return useFor;
    }

    public void setUseFor(String useFor) {
        this.useFor = useFor;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getSuccessTimeDate() {
        return successTimeDate;
    }

    public void setSuccessTimeDate(String successTimeDate) {
        this.successTimeDate = successTimeDate;
    }

    public Double getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Double loanPeriod) {
        this.loanPeriod = loanPeriod;
    }
}
