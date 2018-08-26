package ru.sbt.javaschool.group2.onlinebank.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.sbt.javaschool.group2.onlinebank.ResourceNotFoundException;

import java.security.Principal;


@Controller
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String showClient(Principal principal, Model model) {
        Client client = clientService.findClientByPassportNum(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Клиента не существует"));

        model.addAttribute(client);
        return "showClient";
    }
}
