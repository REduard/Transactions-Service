package com.n26.services;

import com.n26.api.v1.model.StatisticsDTO;
import com.n26.domain.Transaction;
import com.n26.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.n26.util.StatisticsUtil.*;

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
            return createEmptyStatistics();
        }

        Integer count = latestTransactions.size();
        BigDecimal sum = getSum(latestTransactions);
        BigDecimal avg = getAvg(sum, new BigDecimal(count));
        BigDecimal max = getMax(latestTransactions);
        BigDecimal min = getMin(latestTransactions);

        statisticsDTO.setSum(sum)
                .setAvg(avg)
                .setMax(max)
                .setMin(min)
                .setCount(latestTransactions.size());

        return statisticsDTO;
    }

    private StatisticsDTO createEmptyStatistics() {
        return new StatisticsDTO().setSum(BigDecimal.ZERO)
                .setAvg(BigDecimal.ZERO)
                .setMax(BigDecimal.ZERO)
                .setMin(BigDecimal.ZERO)
                .setCount(0);
    }
}
