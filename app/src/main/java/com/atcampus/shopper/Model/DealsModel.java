package com.atcampus.shopper.Model;

public class DealsModel {

    private String dealsID;
    private String dealsImage;
    private String dealsName;
    private String dealsSpec;
    private String dealsPrice;

    public DealsModel(String dealsID, String dealsImage, String dealsName, String dealsSpec, String dealsPrice) {
        this.dealsID = dealsID;
        this.dealsImage = dealsImage;
        this.dealsName = dealsName;
        this.dealsSpec = dealsSpec;
        this.dealsPrice = dealsPrice;
    }

    public String getDealsID() {
        return dealsID;
    }

    public void setDealsID(String dealsID) {
        this.dealsID = dealsID;
    }

    public String getDealsImage() {
        return dealsImage;
    }

    public void setDealsImage(String dealsImage) {
        this.dealsImage = dealsImage;
    }

    public String getDealsName() {
        return dealsName;
    }

    public void setDealsName(String dealsName) {
        this.dealsName = dealsName;
    }

    public String getDealsSpec() {
        return dealsSpec;
    }

    public void setDealsSpec(String dealsSpec) {
        this.dealsSpec = dealsSpec;
    }

    public String getDealsPrice() {
        return dealsPrice;
    }

    public void setDealsPrice(String dealsPrice) {
        this.dealsPrice = dealsPrice;
    }
}
