package com.n26.util;

import com.n26.domain.Transaction;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TransactionUtilTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private final static Transaction testTransaction = new Transaction(BigDecimal.valueOf(100.51), LocalDateTime.now(Clock.systemUTC()));
    private final static Transaction testTransaction2 = new Transaction(BigDecimal.valueOf(50.11), LocalDateTime.now(Clock.systemUTC()));
    private final static Transaction testTransaction3 = new Transaction(BigDecimal.valueOf(150.31), LocalDateTime.now(Clock.systemUTC()));

    @Test
    public void getSum() {
        //given
        List<Transaction> transactions = Arrays.asList(testTransaction, testTransaction2, testTransaction3);

        //when
        BigDecimal result = TransactionUtil.getSum(transactions);

        //then
        assertThat(result).isEqualTo(new BigDecimal("300.93"));
    }

    @Test
    public void getSumOneElementList() {
        //given
        List<Transaction> transactions = Collections.singletonList(testTransaction);

        //when
        BigDecimal result = TransactionUtil.getSum(transactions);

        //then
        assertThat(result).isEqualTo(testTransaction.getAmount());
    }

    @Test
    public void getSumNullList() {
        //expect
        exception.expect(NullPointerException.class);

        //when
        TransactionUtil.getSum(null);
    }

    @Test
    public void getAvg() {
        //when
        BigDecimal result = TransactionUtil.getAvg(new BigDecimal("300.93"), new BigDecimal("3"));

        //then
        assertThat(result).isEqualTo(new BigDecimal("100.31"));
    }

    @Test
    public void getAvgNullSum() {
        //expect
        exception.expect(NullPointerException.class);

        //when
        TransactionUtil.getAvg(null, new BigDecimal("3"));
    }


    @Test
    public void getAvgNullCount() {
        //expect
        exception.expect(NullPointerException.class);

        //when
        TransactionUtil.getAvg(new BigDecimal("300.93"), null);
    }


    @Test
    public void getMax() {
        //given
        List<Transaction> transactions = Arrays.asList(testTransaction, testTransaction2, testTransaction3);

        //when
        BigDecimal result = TransactionUtil.getMax(transactions);

        //then
        assertThat(result).isEqualTo(testTransaction3.getAmount());
    }

    @Test
    public void getMaxSingleElementList() {
        //given
        List<Transaction> transactions = Collections.singletonList(testTransaction);

        //when
        BigDecimal result = TransactionUtil.getMax(transactions);

        //then
        assertThat(result).isEqualTo(testTransaction.getAmount());
    }

    @Test
    public void getMaxNullList() {
        //expect
        exception.expect(NullPointerException.class);

        //when
        TransactionUtil.getMax(null);
    }

    @Test
    public void getMin() {
        //given
        List<Transaction> transactions = Arrays.asList(testTransaction, testTransaction2, testTransaction3);

        //when
        BigDecimal result = TransactionUtil.getMin(transactions);

        //then
        assertThat(result).isEqualTo(testTransaction2.getAmount());
    }

    @Test
    public void getMinSingleElementList() {
        //given
        List<Transaction> transactions = Collections.singletonList(testTransaction);

        //when
        BigDecimal result = TransactionUtil.getMin(transactions);

        //then
        assertThat(result).isEqualTo(testTransaction.getAmount());
    }

    @Test
    public void getMinNullList() {
        //expect
        exception.expect(NullPointerException.class);

        //when
        TransactionUtil.getMin(null);
    }
}