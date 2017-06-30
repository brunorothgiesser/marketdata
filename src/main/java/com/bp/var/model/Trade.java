package com.bp.var.model;

/**
 *
 * @author rothb1
 */
public class Trade {

    public enum OptionPutCall { PUT, CALL}
    public enum BuySell { BUY, SELL}

    private String orderReference;
    private String product;
    private String deliveryPeriod;
    private Double optionStrikePrice;
    private OptionPutCall optionPutCall;
    private BuySell buySell;
    private Integer lotsFilled;
    private Double price;

    public String getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public void setDeliveryPeriod(String deliveryPeriod) {
        this.deliveryPeriod = deliveryPeriod;
    }

    public Double getOptionStrikePrice() {
        return optionStrikePrice;
    }

    public void setOptionStrikePrice(Double optionStrikePrice) {
        this.optionStrikePrice = optionStrikePrice;
    }

    public OptionPutCall getOptionPutCall() {
        return optionPutCall;
    }

    public void setOptionPutCall(OptionPutCall optionPutCall) {
        this.optionPutCall = optionPutCall;
    }

    public BuySell getBuySell() {
        return buySell;
    }

    public void setBuySell(BuySell buySell) {
        this.buySell = buySell;
    }

    public Integer getLotsFilled() {
        return lotsFilled;
    }

    public void setLotsFilled(Integer lotsFilled) {
        this.lotsFilled = lotsFilled;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "orderReference='" + orderReference + '\'' +
                ", product='" + product + '\'' +
                ", deliveryPeriod='" + deliveryPeriod + '\'' +
                ", optionStrikePrice=" + optionStrikePrice +
                ", optionPutCall=" + optionPutCall +
                ", buySell=" + buySell +
                ", lotsFilled=" + lotsFilled +
                ", price=" + price +
                '}';
    }
}
