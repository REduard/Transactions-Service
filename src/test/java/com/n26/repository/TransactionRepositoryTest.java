package com.n26.repository;

import com.n26.domain.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class TransactionRepositoryTest {

    private TransactionRepository transactionRepository;

    private final BigDecimal AMOUNT = BigDecimal.valueOf(1223.45);
    private final LocalDateTime TIMESTAMP = LocalDateTime.now(Clock.systemUTC());

    @Before
    public void setUp() throws Exception {
        transactionRepository = new TransactionRepository();
    }

    @Test
    public void save() {
        //given
        Transaction transaction = new Transaction();
        transaction.setAmount(AMOUNT);
        transaction.setTimestamp(TIMESTAMP);

        //when
        transactionRepository.save(transaction);

        //then
        assertEquals(1, transactionRepository.getLatestTransactions().size());

    }

    @Test
    public void getLatestTransactions() {
        //given
        Transaction transaction = new Transaction();
        transaction.setAmount(AMOUNT);
        transaction.setTimestamp(TIMESTAMP);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(AMOUNT);
        transaction2.setTimestamp(TIMESTAMP.minusSeconds(61));

        transactionRepository.save(transaction);
        transactionRepository.save(transaction2);

        //when
        int result = transactionRepository.getLatestTransactions().size();

        //then
        assertEquals(1, result);
    }

    @Test
    public void deleteAll() {
        //given
        Transaction transaction = new Transaction();
        transaction.setAmount(AMOUNT);
        transaction.setTimestamp(TIMESTAMP);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(AMOUNT);
        transaction2.setTimestamp(TIMESTAMP.minusSeconds(61));
        transactionRepository.save(transaction);
        transactionRepository.save(transaction2);

        //when
        transactionRepository.deleteAll();

        //then
        assertEquals(0, transactionRepository.getLatestTransactions().size());
    }
}