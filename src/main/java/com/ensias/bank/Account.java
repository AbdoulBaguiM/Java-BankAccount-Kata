package com.ensias.bank;

import com.ensias.customers.Client;
import com.ensias.customers.User;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

public class Account {

    private final Client accountHolder;
    private final String accountNumber;
    private List<AccountTransaction> transactions = new ArrayList<>();

    public Account(Client accountHolder, String accountNumber) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
    }

    public AccountTransaction addTransaction(User user, Instant date, BigDecimal amount) throws InvalidTransactionException {
        verifyValidity(user, date, amount);
        AccountTransaction newTransaction = new AccountTransaction(amount, date);
        transactions.add(newTransaction);
        return newTransaction;
    }

    /**
     * Verifies that a transaction is valid
     */
    private void verifyValidity(User user, Instant date, BigDecimal amount) throws InvalidTransactionException {
        if(user == null)
            throw new InvalidTransactionException("Please specify the account holder !");
        if(!user.equals(accountHolder))
            throw new InvalidTransactionException("Wrong account");
        if(amount.compareTo(new BigDecimal(0)) < 0 && amount.abs().compareTo(getBalance()) > 0)
            throw new InvalidTransactionException("Account not sufficiently provisionned : " +  getBalance() + " < " + amount);
        if(amount.compareTo(new BigDecimal(0)) == 0)
            throw new InvalidTransactionException("You better find a job ! Just kidding");
    }

    /**
     * Returns the account balance
     */
    protected BigDecimal getBalance() {
        BigDecimal balance = new BigDecimal(0);
        for(AccountTransaction transaction : transactions)
            balance = balance.add(transaction.getAmount());
        return balance;
    }

    /**
     * Get the transactions history sorted by date
     */
    public List<AccountTransaction> getHistory() {
        if(transactions instanceof ArrayList) {
            List<AccountTransaction> sortedHistory = (List<AccountTransaction>) ((ArrayList<AccountTransaction>) transactions).clone();
            sortedHistory.sort(Comparator.comparing(AccountTransaction::getDate));
            return sortedHistory;
        }
        return transactions;
    }

    public Client getAccountHolder() {
        return accountHolder;
    }

}
