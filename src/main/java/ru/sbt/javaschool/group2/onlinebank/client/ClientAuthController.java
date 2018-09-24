package ru.sbt.javaschool.group2.onlinebank.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sbt.javaschool.group2.onlinebank.error.OnlineBankException;

import javax.validation.Valid;

@Controller
public class ClientAuthController {

    private static Logger LOG = LoggerFactory.getLogger(ClientAuthController.class);

    private ClientService clientService;

    @Autowired
    public ClientAuthController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute(new ClientForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid ClientForm clientForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            clientService.saveClient(clientForm);
        }
        catch (OnlineBankException e) {
            LOG.error("Ошибка создания клиента", e);
            model.addAttribute("message", e.getClientMessage());
            return "register";
        }

        return "redirect:/login";
    }
}
