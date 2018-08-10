package ru.sbt.javaschool.group2;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

import static com.google.common.base.Preconditions.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 16)
    private String number;

    @Column(length = 50)
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal money;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "sender")
    private Set<Transfer> outgoingTransfers;

    @OneToMany(mappedBy = "receiver")
    private Set<Transfer> incomingTransfers;

    public Account() {}

    public Account(final String number, final String description, final BigDecimal money, final Client client) {
        setNumber(number);
        setDescription(description);
        setMoney(money);
        setClient(client);
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
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

    public Set<Transfer> getOutgoingTransfers() {
        return outgoingTransfers;
    }

    public Set<Transfer> getIncomingTransfers() {
        return incomingTransfers;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setMoney(final BigDecimal money) {
        checkArgument(money.compareTo(BigDecimal.ZERO) >= 0, "Сумма на счету не может быть отрицательной");
        this.money = money;
    }

    public void setClient(final Client client) {
        this.client = client;
    }

    public void addMoney(final BigDecimal amount) {
        checkArgument(amount.compareTo(BigDecimal.ZERO) > 0, "Сумма снятия должна быть положительной");
        setMoney(money.add(amount));
    }

    public void withdrawMoney(final BigDecimal amount) {
        checkArgument(amount.compareTo(BigDecimal.ZERO) > 0, "Сумма снятия должна быть положительной");
        setMoney(money.subtract(amount));
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", description='" + description + '\'' +
                ", money=" + money +
                ", client=" + client +
                '}';
    }
}
