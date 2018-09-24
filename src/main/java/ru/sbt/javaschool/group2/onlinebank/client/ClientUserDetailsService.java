package ru.sbt.javaschool.group2.onlinebank.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Transactional
public class ClientUserDetailsService implements UserDetailsService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String passportNum) throws UsernameNotFoundException {
        Client client = clientRepository.findByPassportNum(passportNum)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Клиента с номером паспорта %s не существует", passportNum)));

        return new User(
                client.getPassportNum(),
                client.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("USER"))
        );
    }

}
