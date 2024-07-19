package com.example.loyaltyfirst;

public class TransactionDetails {

    String productName;
    String points;
    String quantity;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public TransactionDetails(String productName, String points, String quantity) {
        this.productName = productName;
        this.points = points;
        this.quantity = quantity;
    }
}
