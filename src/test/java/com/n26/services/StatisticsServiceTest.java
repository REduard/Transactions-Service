package com.n26.services;

import com.n26.api.v1.model.StatisticsDTO;
import com.n26.domain.Transaction;
import com.n26.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class StatisticsServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    private StatisticsService statisticsService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        statisticsService = new StatisticsServiceImpl(transactionRepository);
    }

    @Test
    public void getStatistics() {
        //given
        when(transactionRepository.getLatestTransactions()).thenReturn(Arrays.asList(new Transaction(), new Transaction()));

        //when
        StatisticsDTO statisticsDTO = statisticsService.getStatistics();

        //then
        assertEquals(statisticsDTO.getCount(), new Integer(2));
    }
}