package com.n26.services.exceptions;

/**
 * Exception thrown when repository cannot accept anymore transactions
 */
public class TransactionsListFullException extends RuntimeException {
    public TransactionsListFullException(String message) {
        super(message);
    }
}
