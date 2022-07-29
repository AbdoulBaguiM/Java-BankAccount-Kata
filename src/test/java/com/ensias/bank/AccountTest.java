package com.ensias.bank;

import com.ensias.customers.Client;
import com.ensias.customers.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account accountUnderTest;

    @BeforeEach
    void setUp() {
        Client accountHolder = new Client("Mohamadou", "Abdoul-Bagui");
        accountUnderTest = new Account(accountHolder, "RIB12345");
    }

    @Test
    void testAddTransaction_ThrowsInvalidTransactionException() {
        // Setup
        final User user = null;

        // Run the test & Verify the result
        assertThrows(InvalidTransactionException.class, () -> accountUnderTest.addTransaction(user, Instant.now(), new BigDecimal(200)));
    }

    @Test
    void testGetBalance() {
        // Run the test & Verify the result
        assertEquals(BigDecimal.ZERO, accountUnderTest.getBalance());
    }
}
