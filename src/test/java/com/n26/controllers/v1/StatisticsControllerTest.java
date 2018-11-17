package com.n26.controllers.v1;

import com.n26.api.v1.model.StatisticsDTO;
import com.n26.controllers.RestResponseEntityExceptionHandler;
import com.n26.services.StatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//FIXME to improve
public class StatisticsControllerTest {

    @Mock
    StatisticsService statisticsService;

    @InjectMocks
    StatisticsController statisticsController;

    MockMvc mockMvc;

    final BigDecimal SUM = BigDecimal.valueOf(10.345);
    final BigDecimal AVG = BigDecimal.valueOf(12.345);
    final BigDecimal MAX = BigDecimal.valueOf(11.345);
    final BigDecimal MIN = BigDecimal.valueOf(17.345);
    final Integer COUNT = 10;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(statisticsController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getStatistics() throws Exception {
        //given
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setSum(SUM);
        statisticsDTO.setAvg(AVG);
        statisticsDTO.setMax(MAX);
        statisticsDTO.setMin(MIN);
        statisticsDTO.setCount(COUNT);
        when(statisticsService.getStatistics()).thenReturn(statisticsDTO);

        //when
        mockMvc.perform(get(StatisticsController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.sum", equalTo(SUM.setScale(2, BigDecimal.ROUND_HALF_UP).toString())))
                .andExpect(jsonPath("$.avg", equalTo(AVG.setScale(2, BigDecimal.ROUND_HALF_UP).toString())))
                .andExpect(jsonPath("$.max", equalTo(MAX.setScale(2, BigDecimal.ROUND_HALF_UP).toString())))
                .andExpect(jsonPath("$.min", equalTo(MIN.setScale(2, BigDecimal.ROUND_HALF_UP).toString())))
                .andExpect(jsonPath("$.count", equalTo(COUNT)));

        verify(statisticsService, times(1)).getStatistics();
        verifyNoMoreInteractions(statisticsService);
    }
}