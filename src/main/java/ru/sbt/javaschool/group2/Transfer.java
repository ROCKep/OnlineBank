package ru.sbt.javaschool.group2;

import javax.persistence.*;
import java.math.BigDecimal;

import static com.google.common.base.Preconditions.*;

@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Account receiver;


    public Transfer() {}

    public Transfer(final BigDecimal amount, final Account sender, final Account receiver) {
        setAmount(amount);
        setSender(sender);
        setReceiver(receiver);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Account getSender() {
        return sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setAmount(final BigDecimal amount) {
        checkArgument(amount.compareTo(BigDecimal.ZERO) > 0, "Сумма перевода должна быть положительной");
        this.amount = amount;
    }

    public void setSender(final Account sender) {
        this.sender = sender;
    }

    public void setReceiver(final Account receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", amount=" + amount +
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }
}
