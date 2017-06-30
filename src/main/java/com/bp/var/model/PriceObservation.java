package com.bp.var.model;

import java.util.Date;

/**
 * Created by rothb1 on 25/05/2016.
 */

public class PriceObservation {

    private Date observationDate;
    private PositionKey key;
    private Double price;

    public PositionKey getKey() {
        return key;
    }

    public void setKey(PositionKey key) {
        this.key = key;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getObservationDate() {
        return observationDate;
    }

    public void setObservationDate(Date observationDate) {
        this.observationDate = observationDate;
    }

    @Override
    public String toString() {
        return "PriceObservation{" +
                "observationDate=" + observationDate +
                ", key=" + key +
                ", price=" + price +
                '}';
    }
}
