package ru.sbt.javaschool.group2.onlinebank.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.sbt.javaschool.group2.onlinebank.api.TransferApiRequest;
import ru.sbt.javaschool.group2.onlinebank.api.Transfer;
import ru.sbt.javaschool.group2.onlinebank.api.TransferApiResponse;
import ru.sbt.javaschool.group2.onlinebank.error.ForbiddenException;
import ru.sbt.javaschool.group2.onlinebank.error.OnlineBankException;
import ru.sbt.javaschool.group2.onlinebank.transaction.Transaction;
import ru.sbt.javaschool.group2.onlinebank.transaction.TransactionRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Objects;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private RestTemplate restTemplate;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, RestTemplateBuilder restTemplateBuilder) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Account getAccount(String passportNum, String accountNum) {
        return accountRepository.findByNumberAndClientPassportNum(accountNum, passportNum).orElseThrow(() -> new ForbiddenException(String.format("Нет доступа к счету №%s для клиента с номером паспорта %s", accountNum, passportNum)));
    }

    @Override
    public void addMoney(Account account, BigDecimal sum) {
        account.addMoney(sum);
        transactionRepository.save(Transaction.createAdd(account.getNumber(), sum));
    }

    @Override
    public void withdrawMoney(Account account, BigDecimal sum) {
        try {
            account.withdrawMoney(sum);
        }
        catch (IllegalArgumentException e) {
            throw new OnlineBankException(String.format("Недостаточно средств на счету №%s", account.getNumber()), e);
        }

        transactionRepository.save(Transaction.createWithdraw(account.getNumber(), sum));
    }

    @Override
    public void transferMoney(Account sender, TransferForm transferForm) {
        Account receiver = accountRepository.findByNumber(transferForm.getReceiverNum())
                .orElseThrow(() -> new OnlineBankException(String.format("Счет-получатель №%s не найден", transferForm.getReceiverNum())));

        try {
            sender.withdrawMoney(transferForm.getSum());
        }
        catch (IllegalArgumentException e) {
            throw new OnlineBankException(String.format("Недостаточно средств на счету №%s", sender.getNumber()), e);
        }

        receiver.addMoney(transferForm.getSum());

        transactionRepository.save(Transaction.createTransferInner(sender.getNumber(), receiver.getNumber(), transferForm.getSum()));
    }

    @Override
    public void transferMoneyOuter(Account sender, TransferForm transferForm) {

        try {
            sender.withdrawMoney(transferForm.getSum());
        }
        catch (IllegalArgumentException e) {
            throw new OnlineBankException(String.format("Недостаточно средств на счету №%s", sender.getNumber()), e);
        }

        try {
            // Считать JSON с банками
            InputStream banksStream = new ClassPathResource("banks.json").getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            Bank[] banks = objectMapper.readValue(banksStream, Bank[].class);

            // Найти банк по первым цифрам номера счета
            String bankNum = transferForm.getReceiverNum().substring(0, 2);
            for (Bank bank : banks) {
                if (Objects.equals(bank.getNum(), bankNum)) {

                    // Обратиться к API банка
                    TransferApiRequest request = new TransferApiRequest(
                            new Transfer(sender.getNumber(), transferForm.getReceiverNum(), transferForm.getSum())
                    );

                    HttpHeaders headers = new HttpHeaders();
                    headers.add("content-type", "application/json;charset=UTF-8");
                    headers.add("x-api-key", bank.getKey());

                    HttpEntity<TransferApiRequest> requestEntity = new HttpEntity<>(request, headers);
                    try {
                        ResponseEntity<TransferApiResponse> responseEntity = restTemplate.postForEntity(bank.getApi(), requestEntity, TransferApiResponse.class);
                    }
                    catch (HttpStatusCodeException e) {
                        TransferApiResponse response = objectMapper.readValue(e.getResponseBodyAsByteArray(), TransferApiResponse.class);
                        throw new OnlineBankException(String.format("Сервер вернул %s: %s", response.getStatus(), response.getMessage()), e);
                    }

                    transactionRepository.save(Transaction.createTransferOuterTo(sender.getNumber(), transferForm.getReceiverNum(), transferForm.getSum()));
                    return;
                }
            }
            throw new OnlineBankException(String.format("Банк с номером %s не найден", bankNum));
        }
        catch (IOException e) {
            throw new OnlineBankException(e);
        }
    }
}
