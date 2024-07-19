package com.example.loyaltyfirst;

public class PrizeDescription {

    String redemptionDate;
    String center;

    public PrizeDescription(String redemptionDate, String center) {
        this.redemptionDate = redemptionDate;
        this.center = center;
    }

    public String getRedemptionDate() {
        return redemptionDate;
    }

    public void setRedemptionDate(String redemptionDate) {
        this.redemptionDate = redemptionDate;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
}
