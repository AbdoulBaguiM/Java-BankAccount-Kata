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

        if(accountToCredit.isEmpty())
            return false;

        try {
            if(accountToCredit.get().addTransaction(creditor, date, amount) != null)
                return true;
        } catch(InvalidTransactionException e) {
            return false;
        }

        return false;
    }

    /**
     * US2 : Withdraw money from a client's account
     */
    public boolean withdraw(Client debtor, Instant date, BigDecimal amount) {
        Optional<Account> accountToCredit = getAccountByClient(debtor);

        if(accountToCredit.isEmpty())
            return false;

        try {
            accountToCredit.get().addTransaction(debtor, date, amount.negate());
            return true;
        } catch(InvalidTransactionException e) {
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

}
