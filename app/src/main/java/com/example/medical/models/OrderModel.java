package com.example.medical.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderModel {

    @SerializedName("httpMethod")
    public String httpMethod;
    @SerializedName("TableName")
    public String TableName;
    @SerializedName("userId")
    public String userId;

    public OrderModel(String httpMethod, String tableName, String userId) {
        this.httpMethod = httpMethod;
        TableName = tableName;
        this.userId = userId;
    }

    @SerializedName("Items")
    @Expose
    private ArrayList<Item> items = null;
    @SerializedName("Count")
    @Expose
    private Integer count;
    @SerializedName("ScannedCount")
    @Expose
    private Integer scannedCount;

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getScannedCount() {
        return scannedCount;
    }

    public void setScannedCount(Integer scannedCount) {
        this.scannedCount = scannedCount;
    }

    public class Item {

        @SerializedName("cat_name")
        @Expose
        private String catName;
        @SerializedName("product_image")
        @Expose
        private String productImage;
        @SerializedName("addTo")
        @Expose
        private String addTo;
        @SerializedName("flag")
        @Expose
        private String flag;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("product_price")
        @Expose
        private String productPrice;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("itemId")
        @Expose
        private String itemId;
        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("Cart_id")
        @Expose
        private String cartId;
        @SerializedName("name")
        @Expose
        private String name;

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getAddTo() {
            return addTo;
        }

        public void setAddTo(String addTo) {
            this.addTo = addTo;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
