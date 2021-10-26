package com.example.fpt_app.Models;

public class Shop {


    private String stoteName;
    private String storeAddress;
    private int storePhone;
    private int storeImage;

    public Shop() {
    }

    public Shop(int storeImage, String stoteName, String storeAddress ) {
        this.storeImage = storeImage;
        this.stoteName = stoteName;
        this.storeAddress = storeAddress;

    }

    public String getStoteName() {
        return stoteName;
    }

    public void setStoteName(String stoteName) {
        this.stoteName = stoteName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }



    public int getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(int storeImage) {
        this.storeImage = storeImage;
    }
}
