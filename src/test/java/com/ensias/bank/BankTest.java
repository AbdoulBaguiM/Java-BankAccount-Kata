package com.ensias.bank;

import com.ensias.customers.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    private Bank bankUnderTest;
    private Client client;
    private Client random;

    @BeforeEach
    void setUp() {
        bankUnderTest = new Bank("Bank Of Maghrib");
        client = new Client("Mohamadou", "Abdoul-Bagui");
        bankUnderTest.createAccount(client);

        random = new Client("ZSoft","");
    }

    @Test
    void deposit() {

        assertTrue(
                bankUnderTest.deposit(client, Instant.parse("2022-07-01T10:00:00.00Z"),new BigDecimal(200))
        );
        // The amount of the transaction is 0
        assertFalse(
                bankUnderTest.deposit(client,Instant.parse("2022-07-01T10:00:00.00Z"),new BigDecimal(0))
        );
        // The user doesn't have an account
        assertFalse(
                bankUnderTest.deposit(random, Instant.parse("2022-07-01T10:00:00.00Z"),new BigDecimal(200))
        );
    }

    @Test
    void withdraw() {

        assertTrue(
                bankUnderTest.deposit(client, Instant.parse("2022-07-01T10:00:00.00Z"), new BigDecimal(100))
        );
        assertTrue(
                bankUnderTest.withdraw(client, Instant.parse("2022-07-02T10:00:00.00Z"), new BigDecimal(50))
        );
        // The account balance isn't sufficient
        assertFalse(
                bankUnderTest.withdraw(client, Instant.parse("2022-07-01T10:00:00.00Z"), new BigDecimal(220))
        );
        // The amount of the transaction is 0
        assertFalse(
                bankUnderTest.deposit(client, Instant.parse("2022-07-01T10:00:00.00Z"),new BigDecimal(0))
        );
    }

    @Test
    void getTransactionHistory() {
        // Run the test & Verify the result
        assertTrue(
                bankUnderTest.deposit(client, Instant.parse("2022-07-01T10:00:00.00Z"), new BigDecimal("450.50"))
        );
        assertTrue(
                bankUnderTest.withdraw(client, Instant.parse("2022-07-12T10:00:00.00Z"), new BigDecimal(150))
        );
        assertTrue(
                bankUnderTest.deposit(client,Instant.parse("2022-07-14T10:00:00.00Z"), new BigDecimal(200))
        );
        assertEquals(new BigDecimal("500.50"), bankUnderTest.getBalance(client));

        List<AccountTransaction> accountTransactions = bankUnderTest.getTransactionHistory(client);
        assertNotNull(accountTransactions);
        assertEquals(3, accountTransactions.size());
        assertEquals(new BigDecimal("450.50"), accountTransactions.get(0).getAmount());
        assertEquals(new BigDecimal(-150), accountTransactions.get(1).getAmount());
        assertEquals(new BigDecimal(200), accountTransactions.get(2).getAmount());
    }

    @Test
    void testGetAccountByClient() {
        // Run the test & Verify the result
        assertNotNull(bankUnderTest.getAccountByClient(client));
        assertTrue(bankUnderTest.getAccountByClient(random).isEmpty());
    }

    @Test
    void testGetBalance() {
        // Run the test & Verify the result
        assertEquals(BigDecimal.ZERO, bankUnderTest.getBalance(client));
    }
}