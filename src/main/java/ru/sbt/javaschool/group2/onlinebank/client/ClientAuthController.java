package ru.sbt.javaschool.group2.onlinebank.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sbt.javaschool.group2.onlinebank.OnlineBankException;

import javax.validation.Valid;

@Controller
public class ClientAuthController {

    private ClientService clientService;

    @Autowired
    public ClientAuthController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute(new ClientDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid ClientDto clientDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            clientService.saveClient(clientDto);
        }
        catch (OnlineBankException e) {
            model.addAttribute("message", "Клиент с таким номером паспорта уже существует");
            return "register";
        }

        return "redirect:/login";
    }
}
