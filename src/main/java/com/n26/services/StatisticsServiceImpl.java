package com.n26.services;

import com.n26.api.v1.model.StatisticsDTO;
import com.n26.domain.Transaction;
import com.n26.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private TransactionRepository transactionRepository;

    @Autowired
    public StatisticsServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public StatisticsDTO getStatistics() {
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        List<Transaction> latestTransactions = transactionRepository.getLatestTransactions();

        statisticsDTO.setSum(getSum(latestTransactions));
        statisticsDTO.setAvg(getAvg(latestTransactions));
        statisticsDTO.setMax(getMax(latestTransactions));
        statisticsDTO.setMin(getMin(latestTransactions));
        statisticsDTO.setCount(latestTransactions.size());
        return statisticsDTO;
    }

    private BigDecimal getSum(List<Transaction> transaction) {
        return null;
    }

    private BigDecimal getAvg(List<Transaction> transaction) {
        return null;
    }

    private BigDecimal getMax(List<Transaction> transaction) {
        return null;
    }

    private BigDecimal getMin(List<Transaction> transaction) {
        return null;
    }
}
