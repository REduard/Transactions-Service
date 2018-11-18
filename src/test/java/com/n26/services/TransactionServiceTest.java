package com.n26.services;

import com.n26.api.v1.mappers.TransactionMapper;
import com.n26.api.v1.model.TransactionDTO;
import com.n26.domain.Transaction;
import com.n26.repository.TransactionRepository;
import com.n26.services.exceptions.InvalidTransactionException;
import com.n26.services.exceptions.TooOldTransactionException;
import com.n26.services.exceptions.TransactionHasFutureDateException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import static com.n26.util.TransactionsConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private TransactionRepository transactionRepository;

    private TransactionService transactionService;


    private final static TransactionDTO testTransactionDTO = new TransactionDTO(BigDecimal.valueOf(100.51), LocalDateTime.now(Clock.systemUTC()));
    private final static TransactionDTO testTransactionDTOWithFutureDate
            = new TransactionDTO(BigDecimal.valueOf(100.51), LocalDateTime.now(Clock.systemUTC()).plusHours(2));
    private final static TransactionDTO testTransactionDTOWithOldDate
            = new TransactionDTO(BigDecimal.valueOf(100.51), LocalDateTime.now(Clock.systemUTC()).minusHours(2));

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        transactionService = new TransactionServiceImpl(transactionRepository, TransactionMapper.INSTANCE);
    }

    @Test
    public void addTransaction() {

        //when
        transactionService.addTransaction(testTransactionDTO);

        //then
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verifyNoMoreInteractions(transactionRepository);
    }

    @Test
    public void addNullTransaction() {
        //expect
        exception.expect(InvalidTransactionException.class);
        exception.expectMessage(NULL_TRANSACTION_MESSAGE);

        //when
        transactionService.addTransaction(null);

        //then
        verifyNoMoreInteractions(transactionRepository);
    }

    @Test
    public void addNullParamsTransaction() {
        //expect
        exception.expect(InvalidTransactionException.class);
        exception.expectMessage(NULL_TRANSACTION_PARAMS_MESSAGE);

        //when
        transactionService.addTransaction(new TransactionDTO());

        //then
        verifyNoMoreInteractions(transactionRepository);
    }

    @Test
    public void addTransactionWithFutureDate() {
        //expect
        exception.expect(TransactionHasFutureDateException.class);
        exception.expectMessage(TRANSACTION_HAS_FUTURE_TIMESTAMP_MESSAGE);

        //when
        transactionService.addTransaction(testTransactionDTOWithFutureDate);

        //then
        verifyNoMoreInteractions(transactionRepository);
    }

    @Test
    public void addOldTransaction() {
        //expect
        exception.expect(TooOldTransactionException.class);
        exception.expectMessage(TRANSACTION_IS_TOO_OLD_MESSAGE);

        //when
        transactionService.addTransaction(testTransactionDTOWithOldDate);

        //then
        verifyNoMoreInteractions(transactionRepository);
    }

    @Test
    public void deleteAllTransactions() {
        //when
        transactionService.deleteAllTransactions();

        //then
        verify(transactionRepository, times(1)).deleteAll();
        verifyNoMoreInteractions(transactionRepository);
    }
}