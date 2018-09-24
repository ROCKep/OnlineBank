package ru.sbt.javaschool.group2.onlinebank.transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;

@Entity
@Table(name = "transactions")
public class Transaction {
    private enum TransactionType {
        OPEN(0, "Открытие счета"),
        ADD(1, "Пополнение счета"),
        WITHDRAW(2, "Снятие денег"),
        TRANSFER_INNER(3, "Перевод"),
        TRANSFER_OUTER_TO(4, "Перевод в другой банк"),
        TRANSFER_OUTER_FROM(5, "Перевод из другого банка");

        private int number;
        private String description;

        TransactionType(int number, String description) {
            this.number = number;
            this.description = description;
        }

        public int getNumber() {
            return number;
        }

        public String getDescription() {
            return description;
        }

        public static TransactionType of(int number) {
            return Arrays.stream(values()).filter(t -> t.getNumber() == number).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Указан неверный номер типа: %s", number)));
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int type;

    @Column(name = "account1_num")
    private String account1Num;

    @Column(name = "account2_num")
    private String account2Num;

    private BigDecimal sum;
    private OffsetDateTime created;

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return TransactionType.of(type);
    }

    public void setType(TransactionType type) {
        this.type = type.getNumber();
    }

    public String getAccount1Num() {
        return account1Num;
    }

    public void setAccount1Num(String account1Num) {
        this.account1Num = account1Num;
    }

    public String getAccount2Num() {
        return account2Num;
    }

    public void setAccount2Num(String account2Num) {
        this.account2Num = account2Num;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    private void setCreated(OffsetDateTime created) {
        this.created = created;
    }

    protected Transaction() {}

    private Transaction(TransactionType type, String account1Num, String account2Num, BigDecimal sum) {
        setType(type);
        setAccount1Num(account1Num);
        setAccount2Num(account2Num);
        setSum(sum);
        setCreated(OffsetDateTime.now());
    }

    private Transaction(TransactionType type, String accountNum, BigDecimal sum) {
        this(type, accountNum, null, sum);
    }

    public static Transaction createOpen(String accountNum, BigDecimal sum) {
        return new Transaction(TransactionType.OPEN, accountNum, sum);
    }

    public static Transaction createAdd(String accountNum, BigDecimal sum) {
        return new Transaction(TransactionType.ADD, accountNum, sum);
    }

    public static Transaction createWithdraw(String accountNum, BigDecimal sum) {
        return new Transaction(TransactionType.WITHDRAW, accountNum, sum);
    }

    public static Transaction createTransferInner(String senderNum, String receiverNum, BigDecimal sum) {
        return new Transaction(TransactionType.TRANSFER_INNER, senderNum, receiverNum, sum);
    }

    public static Transaction createTransferOuterTo(String accountNum, String receiverNum, BigDecimal sum) {
        return new Transaction(TransactionType.TRANSFER_OUTER_TO, accountNum, receiverNum, sum);
    }

    public static Transaction createTransferOuterFrom(String accountNum, String senderNum, BigDecimal sum) {
        return new Transaction(TransactionType.TRANSFER_OUTER_FROM, accountNum, senderNum, sum);
    }
}
