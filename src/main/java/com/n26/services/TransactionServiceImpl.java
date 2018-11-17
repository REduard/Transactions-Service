package com.n26.services;

import com.n26.api.v1.mappers.TransactionMapper;
import com.n26.api.v1.model.TransactionDTO;
import com.n26.repository.TransactionRepository;
import com.n26.services.exceptions.TooOldTransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

import static com.n26.util.TransactionsConstants.SECONDS_IN_THE_PAST;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private TransactionMapper transactionMapper;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public void addTransaction(TransactionDTO transactionDTO) {
        if (transactionDTO.getTimestamp().isAfter(LocalDateTime.now(Clock.systemUTC()))) {
            throw new HttpMessageNotReadableException("");
        }
        if (transactionDTO.getTimestamp().isBefore(LocalDateTime.now(Clock.systemUTC()).minusSeconds(SECONDS_IN_THE_PAST))) {
            throw new TooOldTransactionException();
        }
        transactionRepository.save(transactionMapper.transactionDTOtoTransaction(transactionDTO));
    }

    @Override
    public void deleteAllTransactions() {
        transactionRepository.deleteAll();
    }
}
