package com.app.accountmanagement.service;

import com.app.accountmanagement.model.Account;
import com.app.accountmanagement.model.Transaction;
import com.app.accountmanagement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private TransactionService transactionService;

    /**
     * Opret bruger i Database
     * @param account  account
     * @return  account
     */
    public void createAccount(Account account) {
        accountRepository.save(account);
    }


    /**
     * Henter alle brugere fra Databasen
     * @return  alle accounts
     */
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /**
     * Find bruger ved ID
     * @param id  id
     * @return  optional
     */
    public Optional<Account> findAccountById(Long id) {
        return accountRepository.findById(id);
    }

    /**
     * Først checker vi om brugeren findes ved at checke ID hos databasen, vi får som input parameter
     * Derefter checker vi om account har nok i sin balance, hvis de ikke har så returner false else true.
     *
     * @param id       id
     * @param balance  balance
     * @return  boolean
     */
    public boolean withdrawMoney(Long id, Double balance) {
        Optional<Account> account = findAccountById(id);
        if (account.isPresent()) {
            if (account.get().getBalance() - balance < 0) {
                return false;
            } else {
                account.get().setBalance(account.get().getBalance() - balance);
                accountRepository.save(account.get());
            }

            Transaction transaction = new Transaction();
            transaction.setAccount(account.get());
            transaction.setAmount(balance);
            transaction.setStatus("withdraw");
            transaction.setTransactionTime(new Date());
            transactionService.saveTransaction(transaction);
            return true;
        }
        return false;
    }

    /**
     * Deposit money in account.
     * Først checker vi om brugeren findes ved at checke ID hos databasen, vi får som input parameter
     * Derefter tilføjer vi mængden som ønskes i kontoens balance.
     *
     * @param id       id
     * @param balance  balance
     * @return  boolean
     */
    public boolean depositMoney(Long id, Double balance) {
        Optional<Account> account = findAccountById(id);
        if (account.isPresent()) {
            account.get().setBalance(account.get().getBalance() + balance);
            accountRepository.save(account.get());
            Transaction transaction = new Transaction();
            transaction.setAccount(account.get());
            transaction.setAmount(balance);
            transaction.setStatus("deposit");
            transaction.setTransactionTime(new Date());
            transactionService.saveTransaction(transaction);
            return true;
        }
        return false;
    }
}
