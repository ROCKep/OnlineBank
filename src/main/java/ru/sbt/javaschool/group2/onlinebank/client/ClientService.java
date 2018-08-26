package ru.sbt.javaschool.group2.onlinebank.client;

import java.util.Optional;

public interface ClientService {
    Optional<Client> findClientByPassportNum(String passportNum);
    void saveClient(ClientDto clientDto);
}
