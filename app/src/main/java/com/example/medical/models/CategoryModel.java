package com.example.medical.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel {

    @SerializedName("httpMethod")
    public String httpMethod;
    @SerializedName("TableName")
    public String TableName;

    public CategoryModel(String httpMethod, String tableName) {
        this.httpMethod = httpMethod;
        TableName = tableName;
    }

    @SerializedName("Items")
    @Expose
    private ArrayList<Item> items = null;

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public class Item implements Serializable{

        @SerializedName("cat_name")
        @Expose
        private String catName;
        @SerializedName("sub_category")
        @Expose
        private ArrayList<SubCategory> subCategory = null;

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

        public ArrayList<SubCategory> getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(ArrayList<SubCategory> subCategory) {
            this.subCategory = subCategory;
        }

        public class SubCategory implements Serializable {

            @SerializedName("cat_name")
            @Expose
            private String catName;
            @SerializedName("sub_category")
            @Expose
            private String sub_category;
            @SerializedName("cat_id")
            @Expose
            private String catId;

            public String getCatName() {
                return catName;
            }

            public void setCatName(String catName) {
                this.catName = catName;
            }

            public String getSubCat() {
                return sub_category;
            }

            public void setSubCat(String subCat) {
                this.sub_category = subCat;
            }

            public String getCatId() {
                return catId;
            }

            public void setCatId(String catId) {
                this.catId = catId;
            }

        }

    }

}
