package com.example.fpt_app.Models;

import java.io.Serializable;

public class Oder implements Serializable {
      private   Integer oderId;
      private   String nameuser;
      private Integer productId;
      private   String  phone;

    public Oder() {
    }

    public Oder(int oderId, String nameuser, int productId, String phone) {
        this.oderId = oderId;
        this.nameuser = nameuser;
        this.productId = productId;
        this.phone = phone;
    }

    public int getOderId() {
        return oderId;
    }

    public void setOderId(Integer oderId) {
        this.oderId = oderId;
    }

    public String getNameUser() {
        return nameuser;
    }

    public void setNameUser(String nameuser) {
        this.nameuser = nameuser;
    }

    public int getProductID() {
        return productId;
    }

    public void setProductID(Integer productID) {
        this.productId = productID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
