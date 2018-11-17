package com.n26.controllers.v1;

import com.n26.api.v1.model.TransactionDTO;
import com.n26.controllers.RestResponseEntityExceptionHandler;
import com.n26.services.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.n26.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerTest {


    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;

    private final BigDecimal AMOUNT = BigDecimal.valueOf(1223.45);
    private final LocalDateTime TIMESTAMP = LocalDateTime.of(2018, 11, 17, 11, 12, 12);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void addTransaction() throws Exception {
        //given
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(AMOUNT);
        transactionDTO.setTimestamp(TIMESTAMP);

        //when
        ResultActions resultActions = mockMvc.perform(post(TransactionController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(transactionDTO)));
//                .andExpect(status().isCreated());
        System.out.println(resultActions.andReturn());
        verify(transactionService, times(1)).addTransaction(any(TransactionDTO.class));
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void deleteAllTransactions() throws Exception {

        //when
        mockMvc.perform(delete(TransactionController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(transactionService, times(1)).deleteAllTransactions();
        verifyNoMoreInteractions(transactionService);

    }
}