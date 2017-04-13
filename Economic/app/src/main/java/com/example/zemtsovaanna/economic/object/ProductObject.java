package com.example.zemtsovaanna.economic.object;

/**
 * Created by zemtsovaanna on 08.09.16.
 */
public class ProductObject {
    private String productName;
    private Double price;

    public ProductObject(){
    }
    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
