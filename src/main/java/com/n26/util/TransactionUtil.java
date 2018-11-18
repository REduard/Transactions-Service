package com.n26.util;

import com.n26.domain.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class that handles operations on transactions
 */
public class TransactionUtil {
    /**
     * Method to calculate sum of all transaction amounts
     *
     * @param transactions list of transactions to calculate sum
     * @return sum of transactions amounts
     */
    public static BigDecimal getSum(List<Transaction> transactions) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Transaction transaction : transactions) {
            sum = sum.add(transaction.getAmount());
        }
        return sum;
    }

    /**
     * Method to calculate the average of all transaction amounts
     *
     * @param sum   sum of transactions amounts
     * @param count number of transactions
     * @return average of all transaction amounts
     */
    public static BigDecimal getAvg(BigDecimal sum, BigDecimal count) {
        BigDecimal avg = sum;
        avg = avg.setScale(2, BigDecimal.ROUND_HALF_UP);
        return avg.divide(count, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Method to get the biggest amount in a list of transactions
     *
     * @param transactions list of transactions to calculate max
     * @return biggest amount in a list of transactions
     */
    public static BigDecimal getMax(List<Transaction> transactions) {
        BigDecimal max = new BigDecimal(Long.MIN_VALUE);

        for (Transaction transaction : transactions) {
            if (max.compareTo(transaction.getAmount()) < 0) {
                max = transaction.getAmount();
            }
        }
        return max;
    }

    /**
     * Method to get the lowest amount in a list of transactions
     *
     * @param transactions list of transactions to calculate min
     * @return lowest amount in a list of transactions
     */
    public static BigDecimal getMin(List<Transaction> transactions) {
        BigDecimal min = new BigDecimal(Long.MAX_VALUE);

        for (Transaction transaction : transactions) {
            if (min.compareTo(transaction.getAmount()) > 0) {
                min = transaction.getAmount();
            }
        }
        return min;
    }

    /**
     * Method to check if a transaction is null or has required parameters null
     *
     * @param transaction to be checked
     * @return true if transaction is valid and false other wise
     */
    public static boolean isTransactionValid(Transaction transaction) {
        return transaction != null
                && transaction.getAmount() != null
                && transaction.getTimestamp() != null;
    }
}
