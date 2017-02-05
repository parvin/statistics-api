package org.gasimzade.statistics.rest;

import org.gasimzade.statistics.common.DateUtils;
import org.gasimzade.statistics.domain.Transaction;
import org.gasimzade.statistics.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pgasimzade on 3/2/2017.
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private StatisticService statisticService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> newTransaction(@RequestBody Transaction transaction) {

        if (transaction == null ||
            transaction.getAmount() == null ||
            transaction.getTimestamp() == null ||
            !DateUtils.isInLastMin(transaction.getTimestamp())) {

            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        }

        statisticService.processTransaction(transaction);
        return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
    }

}
