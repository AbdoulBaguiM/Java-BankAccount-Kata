package bank;

import customers.Client;
import customers.User;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

public class Account {

    private Client client;
    private Bank bank;
    private int accountNumber;
    private List<AccountTransaction> transactions = new ArrayList<AccountTransaction>();

    public Account(Client client, Bank bank, int accountNumber) {
        this.client = client;
        this.bank = bank;
        this.accountNumber = accountNumber;
    }

    public AccountTransaction addTransaction(User debtor, User creditor, Instant date, BigDecimal amount) throws InvalidTransactionException {
        verifyValidity(debtor, creditor, date, amount);
        AccountTransaction newTransaction = new AccountTransaction(debtor, creditor, amount, date);
        transactions.add(newTransaction);
        return newTransaction;
    }

    /**
     * Verifies that a transaction is valid
     */
    private void verifyValidity(User debtor, User creditor, Instant date, BigDecimal amount) throws InvalidTransactionException {
        if(debtor == null)
            throw new InvalidTransactionException("Specify the debtor !");
        if(creditor == null)
            throw new InvalidTransactionException("Specify the creditor !");
        if(!creditor.equals(client) && !debtor.equals(client))
            throw new InvalidTransactionException("Wrong account");
        if(creditor.equals(client) && amount.compareTo(new BigDecimal(0)) < 0)
            throw new InvalidTransactionException("Incorrect amount for the credit transaction");
        if(debtor.equals(client) && amount.compareTo(new BigDecimal(0)) > 0)
            throw new InvalidTransactionException("Incorrect amount for the debit transaction");
        if(amount.compareTo(new BigDecimal(0)) < 0
                && amount.abs().compareTo(getBalance()) > 0
        )
            throw new InvalidTransactionException("Account not sufficiently provisionned : " +  getBalance() + " < " + amount);
        if(amount.compareTo(new BigDecimal(0)) == 0)
            throw new InvalidTransactionException("I have better things to do with my time");

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
     * Gets the history sorted by date
     */
    public List<AccountTransaction> getHistory() {
        if(transactions instanceof ArrayList) {
            List<AccountTransaction> sortedHistory = (List<AccountTransaction>) ((ArrayList<AccountTransaction>) transactions).clone();
            Collections.sort(sortedHistory, new Comparator<AccountTransaction>() {
                @Override
                public int compare(AccountTransaction t1, AccountTransaction t2) {
                    return t1.getDate().compareTo(t2.getDate());
                }});
            return sortedHistory;
        }
        return transactions;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClient());
    }
}
