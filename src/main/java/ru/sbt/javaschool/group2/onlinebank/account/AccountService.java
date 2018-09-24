package ru.sbt.javaschool.group2.onlinebank.account;

import java.math.BigDecimal;

public interface AccountService {
    Account getAccount(String passportNum, String accountNum);

    void addMoney(Account account, BigDecimal sum);

    void withdrawMoney(Account account, BigDecimal sum);

    void transferMoney(Account account, TransferForm transferForm);

    void transferMoneyOuter(Account account, TransferForm transferForm);
}
