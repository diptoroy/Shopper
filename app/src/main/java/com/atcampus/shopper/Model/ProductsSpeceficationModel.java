package com.atcampus.shopper.Model;

public class ProductsSpeceficationModel {

    private String productSpecName;
    private String productSpecValue;

    public ProductsSpeceficationModel(String productSpecName, String productSpecValue) {
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
