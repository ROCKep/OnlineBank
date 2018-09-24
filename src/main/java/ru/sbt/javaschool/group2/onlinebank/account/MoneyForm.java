package ru.sbt.javaschool.group2.onlinebank.account;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class MoneyForm {
    @NotNull
    @Digits(integer = 14, fraction = 2)
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal sum;

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
