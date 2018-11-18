package com.n26.services;

import com.n26.api.v1.model.StatisticsDTO;

/**
 * Class to handle statistics operations
 */
public interface StatisticsService {

    /**
     * Method that calculates statistics on current valid transactions
     *
     * @return statistics data
     */
    StatisticsDTO getStatistics();
}
