package ru.sbt.javaschool.group2.onlinebank.account;

import ru.sbt.javaschool.group2.onlinebank.client.Client;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String description;
    private BigDecimal money = new BigDecimal("0.00");

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public Client getClient() {
        return client;
    }

    public String getNumber() {
        return number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMoney(BigDecimal money) {
        if (money.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Сумма не может быть отрицательным числом");
        }
        this.money = money;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    protected Account() {}

    public Account(String description, BigDecimal money, Client client) {
        setDescription(description);
        setMoney(money);
        setClient(client);
    }

    public Account(String description, BigDecimal money) {
        this(description, money, null);
    }

    public void addMoney(BigDecimal sum) {
        setMoney(getMoney().add(sum));
    }

    public void withdrawMoney(BigDecimal sum) {
        setMoney(getMoney().subtract(sum));
    }

    @PostPersist
    private void setNumber() {
        this.number = String.format("20%s", getId());
    }
}
