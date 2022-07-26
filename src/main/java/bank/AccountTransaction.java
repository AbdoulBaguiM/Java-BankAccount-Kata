package bank;

import customers.User;

import java.math.BigDecimal;
import java.time.Instant;

public class AccountTransaction {

    private final BigDecimal amount;
    private final User creditor;
    private final User debtor;
    private final Instant date;


    public AccountTransaction(User creditor, User debtor, BigDecimal amount, Instant date) {
        this.creditor = creditor;
        this.debtor = debtor;
        this.amount = amount;
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getDate() {
        return date;
    }
}
