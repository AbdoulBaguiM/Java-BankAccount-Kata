package bank;

import customers.Client;
import customers.User;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Bank {

    private final String name;
    private List<Account> accounts = new ArrayList<Account>();


    public Bank(String name) {
        this.name = name;
    }

    public void createAccount(Client client){
        accounts.add(new Account(client, this, this.generateAccountNumber()));
    }

    private int generateAccountNumber() {
        return accounts.size()+1;
    }

    /**
     * US1 : Deposit money on a client's account
     */
    public boolean deposit(Client creditor, Instant date, BigDecimal amount, User debtor) {
        Optional<Account> accountToCredit = getAccountByClient(creditor);

        if(!accountToCredit.isPresent())
            return false;

        try {
            if(accountToCredit.get().addTransaction(debtor, creditor, date, amount) != null)
                return true;
        } catch(InvalidTransactionException e) {
            return false;
        }

        return false;
    }

    /**
     * US2 : Withdraw money from a client's account
     */
    public boolean withdraw(Client debtor, Instant date, BigDecimal amount, User creditor) {
        Optional<Account> accountToCredit = getAccountByClient(debtor);

        if(!accountToCredit.isPresent())
            return false;

        try {
            accountToCredit.get().addTransaction(debtor, creditor, date, amount.negate());
            return true;
        } catch(InvalidTransactionException e) {
            return false;
        }
    }

    protected Optional<Account> getAccountByClient(Client client) {
        if(client == null)
            return Optional.empty();
        return accounts.stream()
                .filter(account -> client.equals(account.getClient()) )
                .findAny();
    }

    public BigDecimal getBalanceByClient(Client client) {
        Optional<Account> account = getAccountByClient(client);
        if(!account.isPresent())
            return new BigDecimal(0);
        return account.get().getBalance();
    }

    public List<AccountTransaction> getTransactionHistory(Client client) {
        Optional<Account> account = getAccountByClient(client);
        if(!account.isPresent())
            return null;
        return account.get().getHistory();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
