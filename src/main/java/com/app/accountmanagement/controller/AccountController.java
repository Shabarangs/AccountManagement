package com.app.accountmanagement.controller;


import com.app.accountmanagement.dto.BalanceDTO;
import com.app.accountmanagement.model.Account;
import com.app.accountmanagement.service.AccountService;
import com.app.accountmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Dette er Account controller.
 */
@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * Denne API viser listen af alle brugere til index siden.
     * Jeg henter listen af brugere fra databasen og viser den til frontend HTMl side
     * @param model model
     * @return string
     */
    @GetMapping("/")
    public String home(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accountsList", accounts);
        return "index";
    }

    /**
     * Denne API viser brugere oprettelsesformen til frontend, og tager input til account objectet
     * @param model model
     * @return string
     */
    @GetMapping("/account/create")
    public String accountCreate(Model model) {
        model.addAttribute("successfully", false);
        model.addAttribute("account", new Account());
        return "account_form";
    }

    /**
     * Bruger oprettelsesformen kommer her til og jeg modtager bruger detaljerne til denne API og derefter
     * opbevare jeg data'en i databasen.
     *
     * @param account account
     * @param result  result
     * @param model   model
     * @return  string
     */
    @PostMapping("/account/create")
    public String accountCreateFormSubmission(@Valid @ModelAttribute(name = "account") Account account, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("successfully", false);
        } else {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            uuid = uuid.substring(0, 8);
            account.setAccountNumber(uuid);
            accountService.createAccount(account);
            model.addAttribute("successfully", true);
            model.addAttribute("account", new Account());
        }
        return "account_form";
    }


    /**
     * Denne API viser formularen til frontend for at indtaste beløbet for kontoen, som brugeren har valgt.
     * @param id     id
     * @param model  model
     * @return  string
     */
    @GetMapping("/account/deposit/{id}")
    public String accountDeposit(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("balanceDTO", new BalanceDTO());
        model.addAttribute("accountId", id);
        model.addAttribute("successfully", false);
        return "deposit_balance";
    }

    /**
     * Denne API tager imod balancen på brugeren og opbevare det under brugerens id og opbevare den i databasen
     * @param balanceDTO balance dto
     * @param result     result
     * @param id          id
     * @param model       model
     * @return  string
     */
    @PostMapping("/account/deposit/{id}")
    public String accountDeposit(@ModelAttribute(name = "balanceDTO") BalanceDTO balanceDTO, BindingResult result, @PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("accountId", id);

        if (result.hasErrors()) {
            model.addAttribute("successfully", false);
            return "deposit_balance";
        } else {
            accountService.depositMoney(id, balanceDTO.getBalance());
            model.addAttribute("successfully", true);
            model.addAttribute("balanceDTO", new BalanceDTO());
        }
        return "deposit_balance";
    }


    /**
     * Denne API viser formularen til frontend til withdrawl
     * @param id     id
     * @param model  model
     * @return  string
     */
    @GetMapping("/account/withdraw/{id}")
    public String accountWithdraw(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("balanceDTO", new BalanceDTO());
        model.addAttribute("accountId", id);
        model.addAttribute("successfully", false);
        model.addAttribute("failure", false);
        return "withdraw_balance";
    }

    /**
     * Denne API er for balancen i kontoen, først bliver der checket om der er nok i balancen for withdrawl og hvis ikke så vis en fejl ellers
     * træk pengene og opdater databasen
     *
     * @param balanceDTO  balance dto
     * @param result      result
     * @param id          id
     * @param model       model
     * @return  string
     */
    @PostMapping("/account/withdraw/{id}")
    public String accountWithdraw(@ModelAttribute(name = "balanceDTO") BalanceDTO balanceDTO, BindingResult result, @PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("accountId", id);
        if (result.hasErrors()) {
            model.addAttribute("successfully", false);
            model.addAttribute("failure", false);
            return "withdraw_balance";
        } else {
            if (accountService.withdrawMoney(id, balanceDTO.getBalance())) {
                model.addAttribute("successfully", true);
                model.addAttribute("balanceDTO", new BalanceDTO());
                model.addAttribute("failure", false);
            } else {
                model.addAttribute("successfully", false);
                model.addAttribute("balanceDTO", balanceDTO);
                model.addAttribute("failure", true);
            }
        }
        return "withdraw_balance";
    }


}
