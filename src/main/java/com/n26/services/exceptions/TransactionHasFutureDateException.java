package com.n26.services.exceptions;

/**
 * Exception thrown when a transaction has the timestamp in the future
 */
public class TransactionHasFutureDateException extends RuntimeException {

    public TransactionHasFutureDateException(String message) {
        super(message);
    }
}
