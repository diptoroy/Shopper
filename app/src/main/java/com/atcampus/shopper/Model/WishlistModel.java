package com.atcampus.shopper.Model;

public class WishlistModel {

    private int productImage;
    private String productName;
    private int productCuepon;
    private String productRating;
    private int productTotalRating;
    private String productPrice;
    private String productCuttedPrice;
    private String productDeliverySystem;

    public WishlistModel(int productImage, String productName, int productCuepon, String productRating, int productTotalRating, String productPrice, String productCuttedPrice, String productDeliverySystem) {
        this.productImage = productImage;
        this.productName = productName;
        this.productCuepon = productCuepon;
        this.productRating = productRating;
        this.productTotalRating = productTotalRating;
        this.productPrice = productPrice;
        this.productCuttedPrice = productCuttedPrice;
        this.productDeliverySystem = productDeliverySystem;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductCuepon() {
        return productCuepon;
    }

    public void setProductCuepon(int productCuepon) {
        this.productCuepon = productCuepon;
    }

    public String getProductRating() {
        return productRating;
    }

    public void setProductRating(String productRating) {
        this.productRating = productRating;
    }

    public int getProductTotalRating() {
        return productTotalRating;
    }

    public void setProductTotalRating(int productTotalRating) {
        this.productTotalRating = productTotalRating;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCuttedPrice() {
        return productCuttedPrice;
    }

    public void setProductCuttedPrice(String productCuttedPrice) {
        this.productCuttedPrice = productCuttedPrice;
    }

    public String getProductDeliverySystem() {
        return productDeliverySystem;
    }

    public void setProductDeliverySystem(String productDeliverySystem) {
        this.productDeliverySystem = productDeliverySystem;
    }
}
