package ru.sbt.javaschool.group2.onlinebank.client;

import ru.sbt.javaschool.group2.onlinebank.account.AccountForm;


public interface ClientService {
    Client getClient(String passportNum);
    void saveClient(ClientForm clientForm);
    void saveAccount(String passportNum, AccountForm accountForm);
}
