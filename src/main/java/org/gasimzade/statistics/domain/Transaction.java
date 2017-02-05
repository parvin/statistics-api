package org.gasimzade.statistics.domain;

/**
 * Created by pgasimzade on 3/2/2017.
 */
public class Transaction {

    private Double amount;
    private Long timestamp;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
