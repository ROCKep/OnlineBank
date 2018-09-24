package ru.sbt.javaschool.group2.onlinebank.account;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class AccountForm {
    @NotNull
    @Size(max = 50)
    private String description;

    @NotNull
    @Digits(integer = 14, fraction = 2)
    @DecimalMin("0.00")
    private BigDecimal money = new BigDecimal("0.00");

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
