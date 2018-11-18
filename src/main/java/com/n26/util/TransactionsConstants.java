package com.n26.util;

public interface TransactionsConstants {

    long SECONDS_IN_THE_PAST = 60;
    long TRANSACTION_REFRESH_RATE = 3600_000;

    String NULL_TRANSACTION_MESSAGE = "Transaction cannot be null.";
    String NULL_TRANSACTION_PARAMS_MESSAGE = "Transaction has required params null.";
    String TRANSACTION_HAS_FUTURE_TIMESTAMP_MESSAGE = "Transaction timestamp cannot be in the future";
    String TRANSACTION_IS_TOO_OLD_MESSAGE = "Transaction is too old.";
    String TRANSACTION_LIST_IS_FULL_MESSAGE = "Transactions list is full and cannot accept any more transactions for now.";

}
