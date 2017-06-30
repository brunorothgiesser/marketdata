package com.bp.var.model;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by rothb1 on 24/05/2016.
 */
public class PositionKey {
    private String product;
    private String deliveryPeriod;
    private Double optionStrikePrice;
    private Trade.OptionPutCall optionPutCall;
    private Integer monthsAhead;

    public boolean matches(PositionKey that) {
        if (this == that) return true;

        if (!product.equals(that.product)) return false;
        if (optionStrikePrice != null ? !optionStrikePrice.equals(that.optionStrikePrice) : that.optionStrikePrice != null)
            return false;
        if (optionPutCall != that.optionPutCall) return false;
        return monthsAhead.equals(that.monthsAhead);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PositionKey that = (PositionKey) o;

        if (!product.equals(that.product)) return false;
        if (!deliveryPeriod.equals(that.deliveryPeriod)) return false;
        if (optionStrikePrice != null ? !optionStrikePrice.equals(that.optionStrikePrice) : that.optionStrikePrice != null)
            return false;
        if (optionPutCall != that.optionPutCall) return false;
        return monthsAhead.equals(that.monthsAhead);

    }

    @Override
    public int hashCode() {
        int result = product.hashCode();
        result = 31 * result + deliveryPeriod.hashCode();
        result = 31 * result + (optionStrikePrice != null ? optionStrikePrice.hashCode() : 0);
        result = 31 * result + (optionPutCall != null ? optionPutCall.hashCode() : 0);
        result = 31 * result + monthsAhead.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PositionKey{" +
                "product='" + product + '\'' +
                ", deliveryPeriod='" + deliveryPeriod + '\'' +
                ", optionStrikePrice=" + optionStrikePrice +
                ", optionPutCall=" + optionPutCall +
                ", monthsAhead=" + monthsAhead +
                '}';
    }

    @XmlAttribute
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @XmlAttribute
    public String getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public void setDeliveryPeriod(String deliveryPeriod) {
        this.deliveryPeriod = deliveryPeriod;
    }

    public Integer getMonthsAhead() {
        return monthsAhead;
    }

    public void setMonthsAhead(Integer monthsAhead) {
        this.monthsAhead = monthsAhead;
    }

    @XmlAttribute
    public Double getOptionStrikePrice() {
        return optionStrikePrice;
    }

    public void setOptionStrikePrice(Double optionStrikePrice) {
        this.optionStrikePrice = optionStrikePrice;
    }

    @XmlAttribute
    public Trade.OptionPutCall getOptionPutCall() {
        return optionPutCall;
    }

    public void setOptionPutCall(Trade.OptionPutCall optionPutCall) {
        this.optionPutCall = optionPutCall;
    }

}
