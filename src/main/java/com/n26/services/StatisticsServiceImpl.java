package com.n26.services;

import com.n26.api.v1.model.StatisticsDTO;
import com.n26.domain.Transaction;
import com.n26.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.n26.api.v1.model.StatisticsDTO.getEmptyStatistics;
import static com.n26.util.LoggingUtil.getEnteringMethodMessage;
import static com.n26.util.LoggingUtil.getExitingMethodMessage;
import static com.n26.util.TransactionUtil.*;

@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private TransactionRepository transactionRepository;

    @Autowired
    public StatisticsServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public StatisticsDTO getStatistics() {
        log.debug(getEnteringMethodMessage("StatisticsServiceImpl.getStatistics"));

        StatisticsDTO statisticsDTO = new StatisticsDTO();
        List<Transaction> latestTransactions = transactionRepository.getLatestTransactions();

        if (latestTransactions == null || latestTransactions.size() == 0) {
            return getEmptyStatistics();
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

        log.debug(getExitingMethodMessage("StatisticsServiceImpl.getStatistics"));
        return statisticsDTO;
    }
}
