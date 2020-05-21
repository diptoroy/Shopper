package com.atcampus.shopper.Model;

public class CategoryModel {

    private String categoryImage;
    private String category_Name;

    public CategoryModel(String categoryImage, String category_Name) {
        this.categoryImage = categoryImage;
        this.category_Name = category_Name;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategory_Name() {
        return category_Name;
    }

    public void setCategory_Name(String category_Name) {
        this.category_Name = category_Name;
    }
}
