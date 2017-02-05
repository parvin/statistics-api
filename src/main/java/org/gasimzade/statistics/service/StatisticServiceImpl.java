package org.gasimzade.statistics.service;

import org.gasimzade.statistics.Application;
import org.gasimzade.statistics.common.DateUtils;
import org.gasimzade.statistics.domain.Statistic;
import org.gasimzade.statistics.domain.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pgasimzade on 5/2/2017.
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private static final int DATA_SIZE = 60;

    private static final ConcurrentHashMap<Integer, StatisticEntry> statistics = new ConcurrentHashMap<>(DATA_SIZE);

    @PostConstruct
    public void initMap() {
        for (int i = 0; i<DATA_SIZE; i++) {
            statistics.put(i, new StatisticEntry());
        }

        logger.info("Statistics MAP Initialized!");
    }

    @Override
    @Async("transactionProcessingExecutor")
    public void processTransaction(Transaction transaction) {
        if (transaction != null && transaction.getAmount() != null && transaction.getTimestamp() != null) {

            long ts = transaction.getTimestamp();
            double amount = transaction.getAmount();
            int second = DateUtils.getSecond(ts);

            statistics.computeIfPresent(second, (k,v) -> {
                if (DateUtils.isInLastMin(v.ts)) {
                    v.count += 1;
                    v.ts = ts;
                    v.sum += amount;
                    v.min = v.min > amount ? amount : v.min;
                    v.max = v.max < amount ? amount : v.max;
                } else {
                    v.count = 1;
                    v.ts = ts;
                    v.sum = amount;
                    v.min = amount;
                    v.max = amount;

                }

                return v;
            });
        }
    }

    @Override
    public Statistic getStatistic() {
        double sum = 0d;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        long count = 0;

        for(StatisticEntry statisticEntry : statistics.values()) {
            if (statisticEntry != null && statisticEntry.count > 0 && DateUtils.isInLastMin(statisticEntry.ts)) {
                sum += statisticEntry.sum;
                count += statisticEntry.count;

                if (max < statisticEntry.max) {
                    max = statisticEntry.max;
                }

                if (min > statisticEntry.min) {
                    min = statisticEntry.min;
                }
            }
        }

        Statistic statistic = new Statistic();
        statistic.setSum(sum);
        statistic.setCount(count);
        statistic.setMax(max == Double.MAX_VALUE ? 0 : max);
        statistic.setMin(min == Double.MIN_VALUE ? 0 : min);
        statistic.setAvg(count > 0 ? sum/count : 0);

        logger.info("Statistics Data = {}", statistic);

        return statistic;
    }

    private static class StatisticEntry {
        double sum;
        long count;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        long ts;
    }
}
