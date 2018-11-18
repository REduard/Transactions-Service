package com.n26.services;

import com.n26.api.v1.model.StatisticsDTO;
import com.n26.controllers.v1.AbstractRestControllerTest;
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
import java.util.Collections;

import static com.n26.api.v1.model.StatisticsDTO.getEmptyStatistics;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

public class StatisticsServiceTest implements AbstractRestControllerTest {

    @Mock
    private TransactionRepository transactionRepository;

    private StatisticsService statisticsService;

    private final static Transaction testTransaction = new Transaction(BigDecimal.valueOf(100.51), LocalDateTime.now(Clock.systemUTC()));
    private final static Transaction testTransaction2 = new Transaction(BigDecimal.valueOf(50.11), LocalDateTime.now(Clock.systemUTC()));
    private final static Transaction testTransaction3 = new Transaction(BigDecimal.valueOf(150.31), LocalDateTime.now(Clock.systemUTC()));

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        statisticsService = new StatisticsServiceImpl(transactionRepository);
    }

    @Test
    public void getStatisticsForNullList() {
        //
        //given
        //
        when(transactionRepository.getLatestTransactions()).thenReturn(null);

        //
        //when
        //
        StatisticsDTO statisticsDTO = statisticsService.getStatistics();

        //
        //then
        //
        assertThat(statisticsDTO).isEqualTo(getEmptyStatistics());
    }

    @Test
    public void getStatisticsForEmptyList() {
        //
        //given
        //
        when(transactionRepository.getLatestTransactions()).thenReturn(Collections.emptyList());

        //
        //when
        //
        StatisticsDTO statisticsDTO = statisticsService.getStatistics();

        //
        //then
        //
        assertThat(statisticsDTO).isEqualTo(getEmptyStatistics());
    }

    @Test
    public void getStatisticsForOneElementList() {
        //
        //given
        //
        when(transactionRepository.getLatestTransactions()).thenReturn(Collections.singletonList(testTransaction));

        //
        //when
        //
        StatisticsDTO statisticsDTO = statisticsService.getStatistics();

        //
        //then
        //
        assertAll(() -> assertThat(statisticsDTO.getCount()).isEqualTo(1),
                () -> assertThat(statisticsDTO.getSum()).isEqualTo(testTransaction.getAmount()),
                () -> assertThat(statisticsDTO.getAvg()).isEqualTo(testTransaction.getAmount()),
                () -> assertThat(statisticsDTO.getMax()).isEqualTo(testTransaction.getAmount()),
                () -> assertThat(statisticsDTO.getMin()).isEqualTo(testTransaction.getAmount()));
    }

    @Test
    public void getStatisticsForMultiTransaction() {
        //
        //given
        //
        when(transactionRepository.getLatestTransactions()).thenReturn(Arrays.asList(testTransaction, testTransaction2, testTransaction3));

        //
        //when
        //
        StatisticsDTO statisticsDTO = statisticsService.getStatistics();

        //
        //then
        //
        assertAll(() -> assertThat(statisticsDTO.getCount()).isEqualTo(3),
                () -> assertThat(statisticsDTO.getSum()).isEqualTo(new BigDecimal("300.93")),
                () -> assertThat(statisticsDTO.getAvg()).isEqualTo(new BigDecimal("100.31")),
                () -> assertThat(statisticsDTO.getMax()).isEqualTo(new BigDecimal("150.31")),
                () -> assertThat(statisticsDTO.getMin()).isEqualTo(new BigDecimal("50.11")));
    }
}