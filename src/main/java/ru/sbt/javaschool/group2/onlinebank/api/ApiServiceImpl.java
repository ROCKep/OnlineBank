package ru.sbt.javaschool.group2.onlinebank.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sbt.javaschool.group2.onlinebank.account.Account;
import ru.sbt.javaschool.group2.onlinebank.account.AccountRepository;
import ru.sbt.javaschool.group2.onlinebank.error.NotFoundException;
import ru.sbt.javaschool.group2.onlinebank.transaction.Transaction;
import ru.sbt.javaschool.group2.onlinebank.transaction.TransactionRepository;

import javax.transaction.Transactional;

@Service
public class ApiServiceImpl implements ApiService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public ApiServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public void transfer(Transfer transfer) {
        Account receiver = accountRepository.findByNumber(transfer.getReceiverNum())
                .orElseThrow(() -> new NotFoundException(String.format("Счет с номером %s не найден", transfer.getReceiverNum())));
        receiver.addMoney(transfer.getSum());
        transactionRepository.save(Transaction.createTransferOuterFrom(receiver.getNumber(), transfer.getSenderNum(), transfer.getSum()));
    }
}
