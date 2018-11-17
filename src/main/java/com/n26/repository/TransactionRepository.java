package com.n26.repository;

import com.n26.domain.Transaction;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

import static com.n26.util.TransactionsConstants.SECONDS_IN_THE_PAST;

@Component
public class TransactionRepository {
    //TODO take care of concurrent access
    private static LinkedList<Transaction> transactions;

    public TransactionRepository() {
        transactions = new LinkedList<>();
    }

    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getLatestTransactions() {
        ArrayList<Transaction> latestTransactions = new ArrayList<>();
        Iterator<Transaction> interator = transactions.iterator();

        while (interator.hasNext()) {
            Transaction transaction = interator.next();
            if (transaction.getTimestamp().plusSeconds(SECONDS_IN_THE_PAST).isAfter(LocalDateTime.now(Clock.systemUTC()))) {
                latestTransactions.add(transaction);
            } else {
                interator.remove();
            }
        }
        return latestTransactions;
    }


    public void deleteAll() {
        transactions = new LinkedList<>();
    }
}

