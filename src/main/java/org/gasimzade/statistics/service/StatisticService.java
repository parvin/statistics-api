package org.gasimzade.statistics.service;

import org.gasimzade.statistics.domain.Statistic;
import org.gasimzade.statistics.domain.Transaction;

/**
 * Created by pgasimzade on 5/2/2017.
 */
public interface StatisticService {

    void processTransaction(Transaction transaction);

    Statistic getStatistic();
}
