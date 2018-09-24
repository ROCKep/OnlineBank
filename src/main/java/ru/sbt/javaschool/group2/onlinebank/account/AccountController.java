package ru.sbt.javaschool.group2.onlinebank.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sbt.javaschool.group2.onlinebank.error.OnlineBankException;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/account/{accountNum}")
public class AccountController {

    private static Logger LOG = LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ModelAttribute
    public void getAccount(Principal principal, @PathVariable String accountNum, Model model) {
        model.addAttribute(accountService.getAccount(principal.getName(), accountNum));
    }

    @GetMapping("/add")
    public String addMoneyForm(Model model) {
        model.addAttribute(new MoneyForm());
        return "addMoney";
    }

    @PostMapping("/add")
    public String addMoney(@Valid MoneyForm moneyForm, BindingResult result, @ModelAttribute Account account) {
        if (result.hasErrors()) {
            return "addMoney";
        }
        accountService.addMoney(account, moneyForm.getSum());
        return "redirect:/client";
    }

    @GetMapping("/withdraw")
    public String withdrawMoneyForm(Model model) {
        model.addAttribute(new MoneyForm());
        return "withdrawMoney";
    }

    @PostMapping("/withdraw")
    public String withdrawMoney(@Valid MoneyForm moneyForm, BindingResult result, @ModelAttribute Account account, Model model) {
        if (result.hasErrors()) {
            return "withdrawMoney";
        }

        try {
            accountService.withdrawMoney(account, moneyForm.getSum());
        }
        catch (OnlineBankException e) {
            LOG.error("Ошибка снятия денег", e);
            model.addAttribute("message", e.getClientMessage());
            return "withdrawMoney";
        }

        return "redirect:/client";
    }

    @GetMapping("/transfer")
    public String transferMoneyForm(Model model) {
        model.addAttribute(new TransferForm());
        return "transferMoney";
    }

    @PostMapping("/transfer")
    public String transferMoney(@Valid TransferForm transferForm, BindingResult result, @ModelAttribute Account account, Model model) {
        if (result.hasErrors()) {
            return "transferMoney";
        }

        try {
            if (!transferForm.isOuter()) {
                accountService.transferMoney(account, transferForm);
            }
            else {
                accountService.transferMoneyOuter(account, transferForm);
            }
        }
        catch (OnlineBankException e) {
            LOG.error("Ошибка перевода денег{}", transferForm.isOuter() ? " в другой банк" : "", e);
            model.addAttribute("message", e.getClientMessage());
            return "transferMoney";
        }
        return "redirect:/client";
    }
}
