package com.app.accountmanagement.repository;

import com.app.accountmanagement.model.Account;
import com.app.accountmanagement.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select * from transactions limit 10", nativeQuery = true)
    List<Transaction> getLast10Transactions();


    List<Transaction> findAllByAccount(Account account);
}
