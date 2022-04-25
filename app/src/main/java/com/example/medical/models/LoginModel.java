package com.example.medical.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("httpMethod")
    public String httpMethod;
    @SerializedName("TableName")
    public String TableName;
    @SerializedName("id")
    public String id;

    public LoginModel(String httpMethod, String tableName, String id) {
        this.httpMethod = httpMethod;
        TableName = tableName;
        this.id = id;
    }

    @SerializedName("Item")
    @Expose
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public class Item {

        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("town")
        @Expose
        private String town;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("house_no")
        @Expose
        private String houseNo;
        @SerializedName("street")
        @Expose
        private String street;
        @SerializedName("state")
        @Expose
        private String state;

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHouseNo() {
            return houseNo;
        }

        public void setHouseNo(String houseNo) {
            this.houseNo = houseNo;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

    }
}
