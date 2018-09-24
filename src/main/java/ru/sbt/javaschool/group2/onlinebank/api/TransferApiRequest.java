package ru.sbt.javaschool.group2.onlinebank.api;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransferApiRequest {

    private String id;

    @NotNull
    @Digits(integer = 14, fraction = 2)
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal amount;

    @NotNull
    private String fromAccount;

    @NotNull
    private String toAccount;

    private String currency;
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private TransferApiRequest() {}

    public TransferApiRequest(String id, BigDecimal amount, String fromAccount, String toAccount, String currency, String comment) {
        setId(id);
        setAmount(amount);
        setFromAccount(fromAccount);
        setToAccount(toAccount);
        setCurrency(currency);
        setComment(comment);
    }

    public TransferApiRequest(Transfer request) {
        this("20", request.getSum(), request.getSenderNum(), request.getReceiverNum(), "RUB", "");
    }

    public Transfer getTransfer() {
        return new Transfer(getFromAccount(), getToAccount(), getAmount());
    }
}
