package com.n26.services;

import com.n26.api.v1.mappers.TransactionMapper;
import com.n26.api.v1.model.TransactionDTO;
import com.n26.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        transactionRepository.save(transactionMapper.transactionDTOtoTransaction(transactionDTO));
    }

    @Override
    public void deleteAllTransactions() {
        transactionRepository.deleteAll();
    }
}
