package com.n26.services.exceptions;

/**
 * Exception thrown when an invalid transaction is detected
 */
public class InvalidTransactionException extends RuntimeException {

    public InvalidTransactionException(String message) {
        super(message);
    }
}
