package com.n26.api.v1.mappers;

import com.n26.api.v1.model.TransactionDTO;
import com.n26.domain.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TransactionMapperTest {
    private final BigDecimal AMOUNT = BigDecimal.valueOf(1223.45);
    private final LocalDateTime TIMESTAMP = LocalDateTime.of(2018, 11, 17, 11, 12, 12);
    private TransactionMapper transactionMapper = TransactionMapper.INSTANCE;

    @Test
    public void testConversion() {
        //given
        TransactionDTO transaction = new TransactionDTO();
        transaction.setAmount(AMOUNT);
        transaction.setTimestamp(TIMESTAMP);

        //when
        Transaction transactionDTO = transactionMapper.transactionDTOtoTransaction(transaction);

        //then
        assertEquals(AMOUNT, transactionDTO.getAmount());
        assertEquals(TIMESTAMP, transactionDTO.getTimestamp());
    }

    @Test
    public void testConversionWithNullValues() {
        //given
        TransactionDTO transaction = new TransactionDTO();
        transaction.setAmount(null);
        transaction.setTimestamp(null);

        //when
        Transaction transactionDTO = transactionMapper.transactionDTOtoTransaction(transaction);

        //then
        assertNull(transactionDTO.getAmount());
        assertNull(transactionDTO.getTimestamp());
    }
}