package com.ensias.bank;

import java.math.BigDecimal;
import java.time.Instant;

public class AccountTransaction {

    private final BigDecimal amount;
    private final Instant date;
    private final TransactionType transactionType;

    public AccountTransaction(BigDecimal amount, Instant date) {
        this.amount = amount;
        this.date = date;
        this.transactionType = amount.compareTo(BigDecimal.ZERO) > 0 ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "AccountTransaction{" +
                "amount=" + amount +
                ", date=" + date +
                ", type=" + transactionType +
                '}'+'\n';
    }
}
