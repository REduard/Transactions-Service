package com.n26.bootstrap;

import com.n26.domain.Transaction;
import com.n26.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class Bootstrap implements CommandLineRunner {

    private TransactionRepository transactionRepository;

    @Autowired
    public Bootstrap(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) {
        loadDummyData();
    }

    private void loadDummyData() {
        Transaction transaction = new Transaction();
        Transaction transaction2 = new Transaction();
        Transaction transaction3 = new Transaction();
        transaction.setAmount(new BigDecimal(150.2333));
        transaction2.setAmount(new BigDecimal(100.2333));
        transaction3.setAmount(new BigDecimal(50.2333));
        transaction.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
        transaction2.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
        transaction3.setTimestamp(LocalDateTime.now(Clock.systemUTC()));

        for (int i = 0; i <2000; i++) {
            transactionRepository.save(transaction);
        }

    }
}
