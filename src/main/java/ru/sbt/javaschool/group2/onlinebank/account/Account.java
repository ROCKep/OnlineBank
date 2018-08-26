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

    private String description;

    private BigDecimal money = new BigDecimal("0.00");

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Account() {}

    public Account(String description, BigDecimal money, Client client) {
        this.description = description;
        this.money = money;
        this.client = client;
    }

    public Account(String description) {
        this(description, BigDecimal.ZERO, null);
    }

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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
