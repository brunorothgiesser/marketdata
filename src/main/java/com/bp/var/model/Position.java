package com.bp.var.model;

/**
 * Created by rothb1 on 24/05/2016.
 */
public class Position {

    private PositionKey key;
    private Integer quantity;
    private Double price;

    @Override
    public String toString() {
        return "Position{" +
                "key=" + key +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PositionKey getKey() {
        return key;
    }

    public void setKey(PositionKey key) {
        this.key = key;
    }
}
