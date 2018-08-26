package ru.sbt.javaschool.group2.onlinebank.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.sbt.javaschool.group2.onlinebank.OnlineBankException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Client> findClientByPassportNum(String passportNum) {
        return clientRepository.findByPassportNum(passportNum);
    }

    @Override
    @Transactional
    public void saveClient(ClientDto clientDto) {
        findClientByPassportNum(clientDto.getPassportNum()).ifPresent(c -> {
            throw new OnlineBankException(String.format("Клиент с номером паспорта %s уже существует", c.getPassportNum()));
        });

        clientRepository.save(new Client(
                clientDto.getPassportNum(),
                clientDto.getLastName(),
                clientDto.getFirstName(),
                clientDto.getMiddleName(),
                clientDto.getAddress(),
                clientDto.getDob(),
                passwordEncoder.encode(clientDto.getPassword())
        ));
    }
}
