package com.a2a.app.a2aapp;

/**
 * Created by root on 4/5/16.
 */
public class Bean {
int priceq;
    String id;
    String product_name;
    String price;
    int quantity;

    public int getPriceq() {
        return priceq;
    }

    public void setPriceq(int priceq) {
        this.priceq = priceq;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}