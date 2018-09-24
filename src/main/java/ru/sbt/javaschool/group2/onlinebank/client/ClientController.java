package ru.sbt.javaschool.group2.onlinebank.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sbt.javaschool.group2.onlinebank.account.AccountForm;
import ru.sbt.javaschool.group2.onlinebank.error.OnlineBankException;

import javax.validation.Valid;
import java.security.Principal;


@Controller
public class ClientController {

    private static Logger LOG = LoggerFactory.getLogger(ClientController.class);

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/client")
    public String showClient(Principal principal, Model model) {
        Client client = clientService.getClient(principal.getName());

        model.addAttribute(client);
        return "showClient";
    }

    @GetMapping("/client/accounts/create")
    public String createAccountForm(Model model) {
        model.addAttribute(new AccountForm());
        return "createAccount";
    }

    @PostMapping("/client/accounts/create")
    public String createAccount(Principal principal, @Valid AccountForm accountForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "createAccount";
        }

        try {
            clientService.saveAccount(principal.getName(), accountForm);
        }
        catch (OnlineBankException e) {
            LOG.error("Ошибка создания счета", e);
            model.addAttribute("message", e.getClientMessage());
            return "createAccount";
        }

        return "redirect:/client";
    }
}
