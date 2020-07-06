package com.atcampus.shopper.Model;

public class OrderItemModel {

    private int productImage;
    private String orderProductTitle;
    private String orderDeliveryDate;
    private int rating;

    public OrderItemModel(int productImage, String orderProductTitle, String orderDeliveryDate, int rating) {
        this.productImage = productImage;
        this.orderProductTitle = orderProductTitle;
        this.orderDeliveryDate = orderDeliveryDate;
        this.rating = rating;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getOrderProductTitle() {
        return orderProductTitle;
    }

    public void setOrderProductTitle(String orderProductTitle) {
        this.orderProductTitle = orderProductTitle;
    }

    public String getOrderDeliveryDate() {
        return orderDeliveryDate;
    }

    public void setOrderDeliveryDate(String orderDeliveryDate) {
        this.orderDeliveryDate = orderDeliveryDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
