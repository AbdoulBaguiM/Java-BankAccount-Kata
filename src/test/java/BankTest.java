import com.ensias.bank.AccountTransaction;
import com.ensias.bank.Bank;
import com.ensias.customers.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    private Bank bank;
    private Client client;
    private Client random;

    @BeforeEach
    void setUp() {
        bank = new Bank("Bank Of Maghrib");
        client = new Client("Mohamadou", "Abdoul-Bagui");
        bank.createAccount(client);

        random = new Client("ZSoft","");
    }

    @Test
    void deposit() {
        assertTrue(
                bank.deposit(client, Instant.parse("2022-07-01T10:00:00.00Z"),new BigDecimal(200))
        );
        assertFalse(
                bank.deposit(client,Instant.parse("2022-07-01T10:00:00.00Z"),new BigDecimal(0))
        );
    }

    @Test
    void withdraw() {
        assertTrue(
                bank.deposit(client, Instant.parse("2022-07-01T10:00:00.00Z"), new BigDecimal(100))
        );
        assertTrue(
                bank.withdraw(client, Instant.parse("2022-07-02T10:00:00.00Z"), new BigDecimal(50))
        );
        assertFalse(
                bank.withdraw(client, Instant.parse("2022-07-01T10:00:00.00Z"), new BigDecimal(220))
        );
        assertFalse(
                bank.deposit(client, Instant.parse("2022-07-01T10:00:00.00Z"),new BigDecimal(0))
        );
    }

    @Test
    void getTransactionHistory() {
        assertTrue(
                bank.deposit(client, Instant.parse("2022-07-01T10:00:00.00Z"), new BigDecimal("450.50"))
        );
        assertTrue(
                bank.withdraw(client, Instant.parse("2022-07-12T10:00:00.00Z"), new BigDecimal(150))
        );
        assertTrue(
                bank.deposit(client,Instant.parse("2022-07-14T10:00:00.00Z"), new BigDecimal(200))
        );
        assertEquals(new BigDecimal("500.50"), bank.getBalance(client));

        List<AccountTransaction> accountTransactions = bank.getTransactionHistory(client);
        assertNotNull(accountTransactions);
        assertEquals(3, accountTransactions.size());
        assertEquals(new BigDecimal("450.50"), accountTransactions.get(0).getAmount());
        assertEquals(new BigDecimal(-150), accountTransactions.get(1).getAmount());
        assertEquals(new BigDecimal(200), accountTransactions.get(2).getAmount());
    }
}