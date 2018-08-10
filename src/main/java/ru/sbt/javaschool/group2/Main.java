package ru.sbt.javaschool.group2;

import org.hibernate.SessionFactory;

import java.math.BigDecimal;

public class Main {
    public static void main(final String[] args) {
        try (final SessionFactory sessionFactory = HibernateUtil.getSessionFactory()){
            final Client client = new Client("0123456789", "Щербаков", "Владислав", "Москва", null, "vScherbakov");
            Operations.registerClient(client);

            final Account account1 = new Account("1234123412341234", null, BigDecimal.valueOf(100000,2), client);
            Operations.createAccount(account1);

            Operations.addMoney(account1.getId(), BigDecimal.valueOf(15000,2));
            Operations.withdrawMoney(account1.getId(), BigDecimal.valueOf(15000, 2));

            final Account account2 = new Account("4321", "Запасной", BigDecimal.valueOf(654321,2), client);
            Operations.createAccount(account2);

            Operations.transferWithinBank(BigDecimal.valueOf(100000,2), account1.getId(), account2.getId());
            Operations.transferWithinBank(BigDecimal.valueOf(50000,2), account2.getId(), account1.getId());
            Operations.transferWithinBank(BigDecimal.valueOf(30000,2), account2.getId(), account1.getId());
        }
    }
}
