package com.n26.services;

import com.n26.api.v1.model.TransactionDTO;

/**
 * Clasa to handle transactions operations
 */
public interface TransactionService {

    /**
     * Method to add a new transaction
     *
     * @param transactionDTO transcation to be added
     * @throws com.n26.services.exceptions.InvalidTransactionException
     * @throws com.n26.services.exceptions.TooOldTransactionException
     * @throws com.n26.services.exceptions.TransactionHasFutureDateException
     */
    void addTransaction(TransactionDTO transactionDTO);

    /**
     * Method to delete all transactions
     */
    void deleteAllTransactions();
}
