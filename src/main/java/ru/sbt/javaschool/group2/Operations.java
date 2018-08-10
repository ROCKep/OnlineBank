package ru.sbt.javaschool.group2;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;

public class Operations {

    public static void registerClient(final Client newClient) {
        final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {
            final Client existingClient = session
                    .createQuery("from Client c where c.passportNum = :passportNum", Client.class)
                    .setParameter("passportNum", newClient.getPassportNum())
                    .uniqueResult();

            if (existingClient != null) {
                throw new IllegalArgumentException(String.format("Клиент с номером паспорта %s уже существует", newClient.getPassportNum()));
            }
            session.persist(newClient);
            tx.commit();
        }
        catch (final Exception e) {
            tx.rollback();
            throw e;
        }
    }

    public static void createAccount(final Account newAccount) {
        final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {
            final Account existingAccount = session
                    .createQuery("from Account a where a.number = :number", Account.class)
                    .setParameter("number", newAccount.getNumber())
                    .uniqueResult();

            if (existingAccount != null) {
                throw new IllegalArgumentException(String.format("Счет с номером %s уже существует", newAccount.getNumber()));
            }
            session.persist(newAccount);
            tx.commit();
        }
        catch (final Exception e) {
            tx.rollback();
            throw e;
        }
    }

    public static void addMoney(final Long accountId, final BigDecimal amount) {
        final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {
            final Account account = session.load(Account.class, accountId);
            account.addMoney(amount);
            tx.commit();
        }
        catch (final Exception e) {
            tx.rollback();
            throw e;
        }
    }

    public static void withdrawMoney(final Long accountId, final BigDecimal amount) {
        final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {
            final Account account = session.load(Account.class, accountId);
            account.withdrawMoney(amount);
            tx.commit();
        }
        catch (final Exception e) {
            tx.rollback();
            throw e;
        }
    }

    public static void transferWithinBank(final BigDecimal amount, final Long senderId, final Long receiverId) {
        final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        final Transaction tx = session.beginTransaction();
        try {
            final Account sender = session.load(Account.class, senderId);
            final Account receiver = session.load(Account.class, receiverId);
            sender.withdrawMoney(amount);
            receiver.addMoney(amount);
            final Transfer transfer = new Transfer(amount, sender, receiver);
            session.persist(transfer);
            tx.commit();
        }
        catch (final Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
