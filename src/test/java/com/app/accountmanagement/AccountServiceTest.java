package com.app.accountmanagement;

import com.app.accountmanagement.model.Account;
import com.app.accountmanagement.repository.AccountRepository;
import com.app.accountmanagement.service.AccountService;
import com.app.accountmanagement.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateAccount() {
        Account account = createSampleAccount();

        accountService.createAccount(account);

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testGetAllAccounts() {
        List<Account> mockAccounts = createMockAccounts(5);

        when(accountRepository.findAll()).thenReturn(mockAccounts);

        List<Account> result = accountService.getAllAccounts();

        assertEquals(mockAccounts.size(), result.size());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void testFindAccountById() {
        Long accountId = 1L;
        Account mockAccount = createSampleAccount();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));

        Optional<Account> result = accountService.findAccountById(accountId);

        assertTrue(result.isPresent());
        assertEquals(mockAccount.getId(), result.get().getId());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void testWithdrawMoney() {
        Long accountId = 1L;
        Double balance = 100.0;

        Account account = createSampleAccount();
        account.setBalance(500.0);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        boolean result = accountService.withdrawMoney(accountId, balance);

        assertTrue(result);
        assertEquals(400.0, account.getBalance());
        verify(accountRepository, times(1)).save(account);
        verify(transactionService, times(1)).saveTransaction(any());
    }

    @Test
    void testDepositMoney() {
        Long accountId = 1L;
        Double balance = 200.0;

        Account account = createSampleAccount();
        account.setBalance(500.0);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        boolean result = accountService.depositMoney(accountId, balance);

        assertTrue(result);
        assertEquals(700.0, account.getBalance());
        verify(accountRepository, times(1)).save(account);
        verify(transactionService, times(1)).saveTransaction(any());
    }


    private Account createSampleAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setAccountHolderName("John Doe");
        account.setPhoneNumber("1234567890");
        account.setEmail("john@example.com");
        account.setAccountNumber("ACC123");
        account.setBalance(500.0);
        return account;
    }


    private List<Account> createMockAccounts(int numberOfAccounts) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < numberOfAccounts; i++) {
            Account account = new Account();
            account.setId((long) i);
            account.setAccountHolderName("Account Holder " + i);
            accounts.add(account);
        }
        return accounts;
    }
}
