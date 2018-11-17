package com.n26.util;

import com.n26.domain.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class StatisticsUtil {
    public static BigDecimal getSum(List<Transaction> transactions) {
        BigDecimal sum = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            sum = sum.add(transaction.getAmount());
        }
        return sum;
    }

    public static BigDecimal getAvg(BigDecimal sum, BigDecimal count) {
        BigDecimal avg = sum;
        avg = avg.setScale(2, BigDecimal.ROUND_HALF_UP);
        return avg.divide(count, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal getMax(List<Transaction> transactions) {
        BigDecimal max = new BigDecimal(Long.MIN_VALUE);

        for (Transaction transaction : transactions) {
            if (max.compareTo(transaction.getAmount()) < 0) {
                max = transaction.getAmount();
            }
        }
        return max;
    }

    public static BigDecimal getMin(List<Transaction> transactions) {
        BigDecimal min = new BigDecimal(Long.MAX_VALUE);

        for (Transaction transaction : transactions) {
            if (min.compareTo(transaction.getAmount()) > 0) {
                min = transaction.getAmount();
            }
        }
        return min;
    }
}
