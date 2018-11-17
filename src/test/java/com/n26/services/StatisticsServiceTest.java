package com.n26.services;

import com.n26.api.v1.model.StatisticsDTO;
import com.n26.domain.Transaction;
import com.n26.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class StatisticsServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    private StatisticsService statisticsService;

    private final BigDecimal AMOUNT = BigDecimal.valueOf(1223.45);
    private final LocalDateTime TIMESTAMP = LocalDateTime.now(Clock.systemUTC());
    private final BigDecimal AMOUNT2 = BigDecimal.valueOf(12563.45);
    private final LocalDateTime TIMESTAMP2 = LocalDateTime.now(Clock.systemUTC());


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        statisticsService = new StatisticsServiceImpl(transactionRepository);
    }

    @Test
    public void getStatistics() {
        //given
        Transaction transaction = new Transaction();
        transaction.setAmount(AMOUNT);
        transaction.setTimestamp(TIMESTAMP);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(AMOUNT2);
        transaction2.setTimestamp(TIMESTAMP2.minusSeconds(61));
        when(transactionRepository.getLatestTransactions()).thenReturn(Arrays.asList(transaction, transaction2));

        //when
        StatisticsDTO statisticsDTO = statisticsService.getStatistics();

        //then
        assertEquals(statisticsDTO.getCount(), new Integer(2));
    }
}