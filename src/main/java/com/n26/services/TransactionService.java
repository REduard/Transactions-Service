package com.n26.services;

import com.n26.api.v1.model.TransactionDTO;

public interface TransactionService {

    void addTransaction(TransactionDTO transactionDTO);

    void deleteAllTransactions();
}
