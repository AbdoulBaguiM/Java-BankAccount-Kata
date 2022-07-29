package com.ensias;

import com.ensias.bank.Bank;
import com.ensias.customers.Client;

import java.math.BigDecimal;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank("BMCE");
        Client client = new Client("Mohamadou", "Abdoul");
        bank.createAccount(client);

        bank.deposit(client, Instant.now(), new BigDecimal(400));
        bank.withdraw(client, Instant.now(), new BigDecimal(50));
        bank.deposit(client, Instant.now(), new BigDecimal(30));
        bank.withdraw(client, Instant.now(), new BigDecimal(320));

        System.out.println(bank.getTransactionHistory(client));
    }
}
