package org.gasimzade.statistics.rest;

import org.gasimzade.statistics.domain.Statistic;
import org.gasimzade.statistics.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pgasimzade on 3/2/2017.
 */
@RestController
@RequestMapping("/statistics")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @RequestMapping(method = RequestMethod.GET)
    public Statistic getStatistics() {
        return statisticService.getStatistic();
    }
}
