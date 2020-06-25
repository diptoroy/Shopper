package com.atcampus.shopper.Model;

public class ProductsSpeceficationModel {

    public static final int SPECIFICATION_TITLE = 0;
    public static final int SPECIFICATION_BODY = 1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //title
    private String title;

    public ProductsSpeceficationModel(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //body
    private String productSpecName;
    private String productSpecValue;

    public ProductsSpeceficationModel(int type, String productSpecName, String productSpecValue) {
        this.type = type;
        this.productSpecName = productSpecName;
        this.productSpecValue = productSpecValue;
    }

    public String getProductSpecName() {
        return productSpecName;
    }

    public void setProductSpecName(String productSpecName) {
        this.productSpecName = productSpecName;
    }

    public String getProductSpecValue() {
        return productSpecValue;
    }

    public void setProductSpecValue(String productSpecValue) {
        this.productSpecValue = productSpecValue;
    }
}
