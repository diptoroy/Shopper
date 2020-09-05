package com.atcampus.shopper.Model;

public class WishlistModel {

    private String productId;
    private String productImage;
    private String productName;
    private long productCuepon;
    private String productRating;
    private long productTotalRating;
    private String productPrice;
    private String productCuttedPrice;
    private boolean cod;

    public WishlistModel(String productId,String productImage, String productName, long productCuepon, String productRating, long productTotalRating, String productPrice, String productCuttedPrice, boolean cod) {
        this.productId = productId;
        this.productImage = productImage;
        this.productName = productName;
        this.productCuepon = productCuepon;
        this.productRating = productRating;
        this.productTotalRating = productTotalRating;
        this.productPrice = productPrice;
        this.productCuttedPrice = productCuttedPrice;
        this.cod = cod;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getProductCuepon() {
        return productCuepon;
    }

    public void setProductCuepon(long productCuepon) {
        this.productCuepon = productCuepon;
    }

    public String getProductRating() {
        return productRating;
    }

    public void setProductRating(String productRating) {
        this.productRating = productRating;
    }

    public long getProductTotalRating() {
        return productTotalRating;
    }

    public void setProductTotalRating(long productTotalRating) {
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

    public boolean isCod() {
        return cod;
    }

    public void setCod(boolean cod) {
        this.cod = cod;
    }
}
