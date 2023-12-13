package com.app.accountmanagement.model;

import javax.persistence.*;


@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountHolderName;
    private String phoneNumber;
    private String email;
    private String accountNumber;
    private Double balance;

    /**
     * Henter account navn.
     *
     * @return account navn
     */
    public String getAccountHolderName() {
        return accountHolderName;
    }

    /**
     * Sætter account navn.
     *
     * @param accountHolderName  account navn
     */
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    /**
     * Henter telefon nummer.
     *
     * @return  telefon nummer
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sætter telefon nummer.
     *
     * @param phoneNumber  telefon nummer
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Henter email.
     *
     * @return  email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sætter email.
     *
     * @param email  email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Henter id.
     *
     * @return  id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sætter id.
     *
     * @param id  id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Henter account Nummer.
     *
     * @return  account nummer
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sætter account Nummer.
     *
     * @param accountNumber account nummer
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Henter balance.
     *
     * @return  balance
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * Sætter balance.
     *
     * @param balance  balance
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
