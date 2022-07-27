package com.ensias;

import com.ensias.bank.Bank;
import com.ensias.customers.Client;

import java.math.BigDecimal;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank("BMCE");
        Client client = new Client("Mohamadou", "Abdoul");
        Client random = new Client("ZSoft","");
        bank.createAccount(client);

        bank.deposit(client, Instant.now(), new BigDecimal(-400));
        System.out.println(bank.getBalance(client));
    }
}
