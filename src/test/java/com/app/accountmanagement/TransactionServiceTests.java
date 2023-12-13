package com.app.accountmanagement;


import com.app.accountmanagement.model.Account;
import com.app.accountmanagement.model.Transaction;
import com.app.accountmanagement.repository.TransactionRepository;
import com.app.accountmanagement.service.AccountService;
import com.app.accountmanagement.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionServiceTests {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetLast10Transactions() {
        List<Transaction> mockTransactions = createMockTransactions(15);

        when(transactionRepository.getLast10Transactions()).thenReturn(getLast10Transactions(mockTransactions));

        List<Transaction> result = transactionService.getLast10Transactions();

        assertEquals(getLast10Transactions(mockTransactions), result);
        verify(transactionRepository, times(1)).getLast10Transactions();
    }

    @Test
    void testSaveTransaction() {
        Transaction transaction = createSampleTransaction();
        transactionService.saveTransaction(transaction);

        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testGetSpecificAccountsTransactions() {
        Long accountId = 1L;
        Account account = createSampleAccount(); // Create a sample account

        when(accountService.findAccountById(accountId)).thenReturn(Optional.of(account));

        List<Transaction> mockTransactions = createMockTransactions(12);
        when(transactionRepository.findAllByAccount(account)).thenReturn(mockTransactions);

        List<Transaction> result = transactionService.getSpecificAccountsTransactions(accountId);

        assertEquals(getLast10Transactions(mockTransactions), result);
        verify(accountService, times(1)).findAccountById(accountId);
        verify(transactionRepository, times(1)).findAllByAccount(account);
    }


    private Transaction createSampleTransaction() {
        Transaction transaction = new Transaction();
        transaction.setAmount(100.0);
        transaction.setStatus("Deposit");
        transaction.setTransactionTime(new Date());
        return transaction;
    }


    private Account createSampleAccount() {
        Account account = new Account();
        account.setAccountHolderName("John Doe");
        account.setPhoneNumber("1234567890");
        account.setEmail("john@example.com");
        account.setAccountNumber("ACC123");
        account.setBalance(500.0);
        return account;
    }


    private List<Transaction> getLast10Transactions(List<Transaction> transactions) {
        if (transactions.size() > 10) {
            return transactions.subList(0, 10);
        } else {
            return transactions;
        }
    }


    private List<Transaction> createMockTransactions(int numberOfTransactions) {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < numberOfTransactions; i++) {
            Transaction transaction = new Transaction();
            transaction.setAmount(50.0 * i);
            transaction.setStatus("Withdraw");
            transaction.setTransactionTime(new Date());
            transactions.add(transaction);
        }
        return transactions;
    }
}

