package com.atcampus.shopper.Model;

public class DealsModel {

    private int dealsImage;
    private String dealsName;
    private String dealsSpec;
    private String dealsPrice;

    public DealsModel(int dealsImage, String dealsName, String dealsSpec, String dealsPrice) {
        this.dealsImage = dealsImage;
        this.dealsName = dealsName;
        this.dealsSpec = dealsSpec;
        this.dealsPrice = dealsPrice;
    }

    public int getDealsImage() {
        return dealsImage;
    }

    public void setDealsImage(int dealsImage) {
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
