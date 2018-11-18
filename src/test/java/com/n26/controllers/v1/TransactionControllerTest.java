package com.n26.controllers.v1;

import com.n26.api.v1.model.TransactionDTO;
import com.n26.controllers.RestResponseEntityExceptionHandler;
import com.n26.services.TransactionService;
import com.n26.services.exceptions.TooOldTransactionException;
import com.n26.services.exceptions.TransactionHasFutureDateException;
import com.n26.services.exceptions.TransactionsListFullException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import static com.n26.controllers.v1.AbstractRestControllerTest.asJsonString;
import static com.n26.util.TransactionsConstants.TRANSACTION_HAS_FUTURE_TIMESTAMP_MESSAGE;
import static com.n26.util.TransactionsConstants.TRANSACTION_IS_TOO_OLD_MESSAGE;
import static com.n26.util.TransactionsConstants.TRANSACTION_LIST_IS_FULL_MESSAGE;
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

    private final TransactionDTO dummyTransaction = new TransactionDTO(BigDecimal.valueOf(100.51), LocalDateTime.now(Clock.systemUTC()));

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testAddValidTransaction() throws Exception {
        //when
        mockMvc.perform(post(TransactionController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dummyTransaction)))
                .andExpect(status().isCreated());

        //then
        verify(transactionService, times(1)).addTransaction(any(TransactionDTO.class));
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void testAddNullTransaction() throws Exception {
        //when
        mockMvc.perform(post(TransactionController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(null)))
                .andExpect(status().isBadRequest());

        //then
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void testAddTransactionOldTimestamp() throws Exception {
        //given
        doThrow(new TooOldTransactionException(TRANSACTION_IS_TOO_OLD_MESSAGE)).when(transactionService).addTransaction(any());

        //when
        mockMvc.perform(post(TransactionController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dummyTransaction)))
                .andExpect(status().isNoContent());

        //then
        verify(transactionService, times(1)).addTransaction(any(TransactionDTO.class));
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void testAddTransactionWithFutureTimestamp() throws Exception {
        //given
        doThrow(new TransactionHasFutureDateException(TRANSACTION_HAS_FUTURE_TIMESTAMP_MESSAGE)).when(transactionService).addTransaction(any());

        //when
        mockMvc.perform(post(TransactionController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dummyTransaction)))
                .andExpect(status().isUnprocessableEntity());

        //then
        verify(transactionService, times(1)).addTransaction(any(TransactionDTO.class));
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void testAddTransactionWithBadParams() throws Exception {
        //when
        mockMvc.perform(post(TransactionController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":\"2344\",\"timestamp\":\"343434\"}"))
                .andExpect(status().isUnprocessableEntity());

        //then
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void testAddTransactionWithBadJson() throws Exception {
        //when
        mockMvc.perform(post(TransactionController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":\"2344\",\"timestamp\":\"2018-11-17T16:44:12.244Z\",,,}"))
                .andExpect(status().isBadRequest());

        //then
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void testAddTooManyTransactions() throws Exception {
        //given
        doThrow(new TransactionsListFullException(TRANSACTION_LIST_IS_FULL_MESSAGE)).when(transactionService).addTransaction(any());

        //when
        mockMvc.perform(post(TransactionController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dummyTransaction)))
                .andExpect(status().isTooManyRequests());

        //then
        verify(transactionService, times(1)).addTransaction(any(TransactionDTO.class));
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void deleteAllTransactions() throws Exception {

        //when
        mockMvc.perform(delete(TransactionController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //then
        verify(transactionService, times(1)).deleteAllTransactions();
        verifyNoMoreInteractions(transactionService);

    }
}