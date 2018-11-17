package com.n26.services.exceptions;

public class NoDataForStatistics extends RuntimeException {

    public NoDataForStatistics() {
    }

    public NoDataForStatistics(String message) {
        super(message);
    }
}
