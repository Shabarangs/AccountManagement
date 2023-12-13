package com.app.accountmanagement.controller;

import com.app.accountmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public String getLast10Transactions(Model model) {
        model.addAttribute("transactions", transactionService.getLast10Transactions());
        return "transactions";
    }


    @GetMapping("/transactions/account/{id}")
    public String accountDeposit(@PathVariable(name = "id") Long accountId, Model model) {
        model.addAttribute("id", accountId);
        model.addAttribute("specificAccountTransactions", transactionService.getSpecificAccountsTransactions(accountId));
        return "account_transactions";
    }


}
