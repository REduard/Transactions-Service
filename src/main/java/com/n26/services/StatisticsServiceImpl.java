package com.n26.services;

import com.n26.api.v1.model.StatisticsDTO;
import com.n26.domain.Transaction;
import com.n26.repository.TransactionRepository;
import com.n26.services.exceptions.NoDataForStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.n26.util.TransactionsConstants.SECONDS_IN_THE_PAST;

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

        if (latestTransactions.size() == 0) {
            throw new NoDataForStatistics(String.format("No transactions in the past %d seconds.", SECONDS_IN_THE_PAST));
        }

        Integer count = latestTransactions.size();
        BigDecimal sum = getSum(latestTransactions);
        BigDecimal avg = getAvg(sum, new BigDecimal(count));
        BigDecimal max = getMax(latestTransactions);
        BigDecimal min = getMin(latestTransactions);

        statisticsDTO.setSum(sum);
        statisticsDTO.setAvg(avg);
        statisticsDTO.setMax(max);
        statisticsDTO.setMin(min);
        statisticsDTO.setCount(latestTransactions.size());
        return statisticsDTO;
    }

    private BigDecimal getSum(List<Transaction> transactions) {
        BigDecimal sum = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            sum = sum.add(transaction.getAmount());
        }
        return sum;
    }

    private BigDecimal getAvg(BigDecimal sum, BigDecimal count) {
        return sum.divide(count, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal getMax(List<Transaction> transactions) {
        BigDecimal max = new BigDecimal(Long.MIN_VALUE);

        for (Transaction transaction : transactions) {
            if (max.compareTo(transaction.getAmount()) < 0) {
                max = transaction.getAmount();
            }
        }
        return max;
    }

    private BigDecimal getMin(List<Transaction> transactions) {
        BigDecimal min = new BigDecimal(Long.MAX_VALUE);

        for (Transaction transaction : transactions) {
            if (min.compareTo(transaction.getAmount()) > 0) {
                min = transaction.getAmount();
            }
        }
        return min;
    }
}
