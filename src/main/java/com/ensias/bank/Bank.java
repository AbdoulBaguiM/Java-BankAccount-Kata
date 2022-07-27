package com.ensias.bank;

import com.ensias.customers.Client;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Bank {

    private final String name;
    private List<Account> accounts = new ArrayList<>();

    public Bank(String name) {
        this.name = name;
    }

    /**
     * US1 : Deposit money on a client's account
     */
    public boolean deposit(Client creditor, Instant date, BigDecimal amount) {
        Optional<Account> accountToCredit = getAccountByClient(creditor);

        if(!accountToCredit.isPresent())
            return false;

        try {
            if(accountToCredit.get().addTransaction(creditor, date, amount) != null)
                return true;
        } catch(InvalidTransactionException e) {
            System.err.println(e);
            return false;
        }

        return false;
    }

    /**
     * US2 : Withdraw money from a client's account
     */
    public boolean withdraw(Client debtor, Instant date, BigDecimal amount) {
        Optional<Account> accountToCredit = getAccountByClient(debtor);

        if(!accountToCredit.isPresent())
            return false;

        try {
            accountToCredit.get().addTransaction(debtor, date, amount.negate());
            return true;
        } catch(InvalidTransactionException e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * US3 : Get transactions history
     */
    public List<AccountTransaction> getTransactionHistory(Client client) {
        Optional<Account> account = getAccountByClient(client);
        return account.map(Account::getHistory).orElse(null);
    }

    /**
     * Get a client's account
     */
    protected Optional<Account> getAccountByClient(Client client) {
        if(client == null)
            return Optional.empty();
        return accounts.stream()
                .filter(account -> client.equals(account.getAccountHolder()) )
                .findAny();
    }

    /**
     * Create an account for a User
     */
    public void createAccount(Client client) {
        accounts.add(new Account(client, generateUniqueAccountNumber(client)));
    }

    private String generateUniqueAccountNumber(Client client) {
        return accounts.size()+1+client.getFirstName()+client.getLastName();
    }

    /**
     * Get client's balance
     */
    public BigDecimal getBalance(Client client) {
        Optional<Account> account = getAccountByClient(client);
        if(!account.isPresent())
            return new BigDecimal(0);
        return account.get().getBalance();
    }
}
