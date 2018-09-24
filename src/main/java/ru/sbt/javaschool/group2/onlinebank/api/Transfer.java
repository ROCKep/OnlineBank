package ru.sbt.javaschool.group2.onlinebank.api;

import java.math.BigDecimal;

public class Transfer {

    private String senderNum;
    private String receiverNum;
    private BigDecimal sum;

    public String getSenderNum() {
        return senderNum;
    }

    public void setSenderNum(String senderNum) {
        this.senderNum = senderNum;
    }

    public String getReceiverNum() {
        return receiverNum;
    }

    public void setReceiverNum(String receiverNum) {
        this.receiverNum = receiverNum;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    private Transfer() {}

    public Transfer(String senderNum, String receiverNum, BigDecimal sum) {
        setSenderNum(senderNum);
        setReceiverNum(receiverNum);
        setSum(sum);
    }
}
