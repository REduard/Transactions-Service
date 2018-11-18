package com.n26.services.exceptions;

/**
 * Exception thrown when an transaction is too old (not within preset time).
 */
public class TooOldTransactionException extends RuntimeException {

    public TooOldTransactionException(String message) {
        super(message);
    }
}

