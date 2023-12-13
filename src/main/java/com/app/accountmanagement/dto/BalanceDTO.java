package com.app.accountmanagement.dto;

/**
 * Denne BalanceDTO bliver brugt til at tage input fra frontend for deposit og withdrawl
 */
public class BalanceDTO {
    private Double balance;

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
