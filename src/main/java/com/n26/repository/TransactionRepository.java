package com.n26.repository;

import com.n26.domain.Transaction;
import com.n26.services.exceptions.TransactionsListFullException;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

import static com.n26.util.TransactionUtil.isTransactionValid;
import static com.n26.util.TransactionsConstants.SECONDS_IN_THE_PAST;
import static com.n26.util.TransactionsConstants.TRANSACTION_LIST_IS_FULL_MESSAGE;

/**
 * Class that manage transactions storage
 */
@Component
public class TransactionRepository {
    private static List<Transaction> transactions;

    public TransactionRepository() {
        transactions = Collections.synchronizedList(new LinkedList<>());
    }

    /**
     * Store provided transaction if it's not null and it don't have null amount or timestamp
     * Also calls discardOldTransactions method.
     *
     * @param transaction transaction to be stored
     * @see #discardOldTransactions()
     */
    public void save(Transaction transaction) {
        discardOldTransactions();

        if (transactions.size() > Integer.MAX_VALUE - 100) {
            throw new TransactionsListFullException(TRANSACTION_LIST_IS_FULL_MESSAGE);
        }
        if (isTransactionValid(transaction)) {
            transactions.add(transaction);
        }
    }

    /**
     * Retrieve all fresh transactions.
     * Transaction freshness can be adjusted by changing the SECONDS_IN_THE_PAST variable.
     * Also calls discardOldTransactions method.
     *
     * @return returns list transactions within preset time
     * @see #discardOldTransactions()
     */
    public List<Transaction> getLatestTransactions() {
        discardOldTransactions();
        synchronized (transactions) {
            return new ArrayList<>(transactions);
        }
    }

    /**
     * Deletes all transactions
     */
    public void deleteAll() {
        transactions = Collections.synchronizedList(new LinkedList<>());
    }

    /**
     * Discards all transactions that too old
     */
    public void discardOldTransactions() {
        Iterator<Transaction> interator = transactions.iterator();
        LocalDateTime timeLimit = LocalDateTime.now(Clock.systemUTC()).minusSeconds(SECONDS_IN_THE_PAST);

        while (interator.hasNext()) {
            Transaction transaction = interator.next();
            if (transaction.getTimestamp().isBefore(timeLimit)) {
                interator.remove();
            }
        }
    }

    /**
     * Retrives all transactions.
     *
     * @return list of all transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }
}

