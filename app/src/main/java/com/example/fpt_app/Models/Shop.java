package com.example.fpt_app.Models;

public class Shop {
    private String storeId;
    private String storeName;
    private String storeAddress;
    private int storePhone;
    private String storeImage;
    private String storeEmail;

    public Shop() {
    }

    public Shop(String storeId, String storeName, String storeAddress, int storePhone, String storeImage, String storeEmail) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storePhone = storePhone;
        this.storeImage = storeImage;
        this.storeEmail = storeEmail;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoteName(String stoteName) {
        this.storeName = stoteName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }



    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(int storePhone) {
        this.storePhone = storePhone;
    }

    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }
}
