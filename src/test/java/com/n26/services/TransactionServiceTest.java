package com.n26.services;

import com.n26.api.v1.mappers.TransactionMapper;
import com.n26.api.v1.model.TransactionDTO;
import com.n26.domain.Transaction;
import com.n26.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    private TransactionService transactionService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        transactionService = new TransactionServiceImpl(transactionRepository, TransactionMapper.INSTANCE);
    }

    @Test
    public void addTransaction() {
        //when
        transactionService.addTransaction(new TransactionDTO());

        //then
        verify(transactionRepository, times(1)).save(any(Transaction.class));
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