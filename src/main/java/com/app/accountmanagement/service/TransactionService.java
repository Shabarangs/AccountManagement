package com.app.accountmanagement.service;

import com.app.accountmanagement.model.Account;
import com.app.accountmanagement.model.Transaction;
import com.app.accountmanagement.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    public List<Transaction> getLast10Transactions() {
        return transactionRepository.getLast10Transactions();
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> getSpecificAccountsTransactions(Long accountId) {
        Optional<Account> account = accountService.findAccountById(accountId);
        if (account.isPresent()) {
            List<Transaction> dbTransactions = transactionRepository.findAllByAccount(account.get());

            if (dbTransactions.size() > 10) {
                List<Transaction> tenTransactions = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    tenTransactions.add(dbTransactions.get(i));
                }
                return tenTransactions;
            } else {
                return dbTransactions;
            }
        }
        return new ArrayList<>();
    }
}
