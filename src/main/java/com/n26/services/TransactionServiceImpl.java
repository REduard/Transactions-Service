package com.n26.services;

import com.n26.api.v1.mappers.TransactionMapper;
import com.n26.api.v1.model.TransactionDTO;
import com.n26.domain.Transaction;
import com.n26.repository.TransactionRepository;
import com.n26.services.exceptions.InvalidTransactionException;
import com.n26.services.exceptions.TooOldTransactionException;
import com.n26.services.exceptions.TransactionHasFutureDateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

import static com.n26.util.TransactionUtil.isTransactionValid;
import static com.n26.util.TransactionsConstants.*;

@Slf4j
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
        Transaction transaction;

        if (transactionDTO == null) {
            throw new InvalidTransactionException(NULL_TRANSACTION_MESSAGE);
        }

        transaction = transactionMapper.transactionDTOtoTransaction(transactionDTO);

        if (!isTransactionValid(transaction)) {
            throw new InvalidTransactionException(NULL_TRANSACTION_PARAMS_MESSAGE);
        }

        if (transaction.getTimestamp().isAfter(LocalDateTime.now(Clock.systemUTC()))) {
            throw new TransactionHasFutureDateException(TRANSACTION_HAS_FUTURE_TIMESTAMP_MESSAGE);
        }

        if (transaction.getTimestamp().isBefore(LocalDateTime.now(Clock.systemUTC()).minusSeconds(SECONDS_IN_THE_PAST))) {
            throw new TooOldTransactionException(TRANSACTION_IS_TOO_OLD_MESSAGE);
        }
        transactionRepository.save(transaction);
    }

    @Override
    public void deleteAllTransactions() {
        transactionRepository.deleteAll();
    }
}
