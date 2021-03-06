package com.example.fpt_app.Models;

import java.io.Serializable;
import java.util.Date;

public class OderCT implements Serializable {
        private String oderctId;
        private int idUser;
//        private int oderId;
        private int productId;
//        private String namePr;
        private int quantity;
        private double price;
        private String address;
        private String date;

    public OderCT() {
    }

//    public OderCT(int oderctId, int userId, int productId, String namePr, int quantity, double price, String address,String date) {
//        this.oderctId = oderctId;
//        this.userId = userId;
//        this.productId = productId;
//        this.namePr = namePr;
//        this.quantity = quantity;
//        this.price = price;
//        this.address = address;
//        this.date =date;
//    }

    public OderCT(String oderctId, int idUser, int productId, int quantity, double price, String address, String date) {
        this.oderctId = oderctId;
        this.idUser = idUser;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.address = address;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOderctId() {
        return oderctId;
    }

    public void setOderctId(String oderctId) {
        this.oderctId = oderctId;
    }

    public int getUserId() {
        return idUser;
    }

    public void setUserId(int idUser) {
        this.idUser = idUser;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

//    public String getNamePr() {
//        return namePr;
//    }
//
//    public void setNamePr(String namePr) {
//        this.namePr = namePr;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
