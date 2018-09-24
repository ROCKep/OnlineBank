package ru.sbt.javaschool.group2.onlinebank.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sbt.javaschool.group2.onlinebank.account.Account;
import ru.sbt.javaschool.group2.onlinebank.account.AccountForm;
import ru.sbt.javaschool.group2.onlinebank.account.AccountRepository;
import ru.sbt.javaschool.group2.onlinebank.error.NotFoundException;
import ru.sbt.javaschool.group2.onlinebank.error.OnlineBankException;
import ru.sbt.javaschool.group2.onlinebank.transaction.Transaction;
import ru.sbt.javaschool.group2.onlinebank.transaction.TransactionRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Client getClient(String passportNum) {
        return clientRepository.findByPassportNum(passportNum)
                .orElseThrow(() -> new NotFoundException(String.format("Клиент с номером паспорта %s не найден", passportNum)));
    }

    @Override
    public void saveClient(ClientForm clientForm) {
        clientRepository.findByPassportNum(clientForm.getPassportNum()).ifPresent(c -> {
            throw new OnlineBankException(String.format("Клиент с номером паспорта %s уже существует", c.getPassportNum()));
        });

        clientRepository.save(new Client(
                clientForm.getPassportNum(),
                clientForm.getLastName(),
                clientForm.getFirstName(),
                clientForm.getMiddleName(),
                passwordEncoder.encode(clientForm.getPassword())
        ));
    }

    @Override
    public void saveAccount(String passportNum, AccountForm accountForm) {
        Client client = getClient(passportNum);
        Account account = new Account(
                accountForm.getDescription(),
                accountForm.getMoney()
        );
        client.addAccount(account);

        accountRepository.save(account);
        transactionRepository.save(Transaction.createOpen(account.getNumber(), account.getMoney()));
    }
}
