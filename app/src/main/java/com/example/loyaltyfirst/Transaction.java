package com.example.loyaltyfirst;

public class Transaction {

    String tref;
    String date;
    String points;
    String amount;

    public Transaction(String tref, String date, String points, String amount) {
        this.tref = tref;
        this.date = date;
        this.points = points;
        this.amount = amount;
    }

    public String getTref() {
        return tref;
    }

    public void setTref(String tref) {
        this.tref = tref;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
