package com.example.medical.models;

public class HomeModel {

    int id;
    String imageUrl;
    String title;
    String desc;
    String price;
    String discountedPrice;

    public HomeModel(int id, String imageUrl, String title, String desc, String price, String discountedPrice) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.discountedPrice = discountedPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
}
