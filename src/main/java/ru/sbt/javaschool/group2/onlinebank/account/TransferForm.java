package ru.sbt.javaschool.group2.onlinebank.account;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class TransferForm {
    @NotNull
    @Size(min = 3)
    private String receiverNum;

    @NotNull
    @Digits(integer = 14, fraction = 2)
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal sum;

    private boolean outer;

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

    public boolean isOuter() {
        return outer;
    }

    public void setOuter(boolean outer) {
        this.outer = outer;
    }
}
