package com.example.medical.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductModel {

    @SerializedName("httpMethod")
    public String httpMethod;
    @SerializedName("TableName")
    public String TableName;
    @SerializedName("sub_category")
    public String sub_category;
    @SerializedName("cat_name")
    public String cat_name;
    @SerializedName("flag")
    public String flag;

    public ProductModel(String httpMethod, String tableName, String sub_category, String cat_name, String flag) {
        this.httpMethod = httpMethod;
        TableName = tableName;
        this.sub_category = sub_category;
        this.cat_name = cat_name;
        this.flag = flag;
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

    public class Item implements Serializable {

        @SerializedName("sub_category")
        @Expose
        private String sub_category;
        @SerializedName("cat_name")
        @Expose
        private String catName;
        @SerializedName("product_image")
        @Expose
        private String productImage;
        @SerializedName("flag")
        @Expose
        private String flag;
        @SerializedName("product_desc")
        @Expose
        private String productDesc;
        @SerializedName("product_price")
        @Expose
        private String productPrice;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("name")
        @Expose
        private String name;

        public String getSubCat() {
            return sub_category;
        }

        public void setSubCat(String subCat) {
            this.sub_category = subCat;
        }

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

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getProductDesc() {
            return productDesc;
        }

        public void setProductDesc(String productDesc) {
            this.productDesc = productDesc;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
