package com.example.fpt_app.Models;

import java.io.Serializable;

public class User implements Serializable {
    private String full_name, email, password, confirm_password, userImage;
//    private Integer id, phone;



    public User() {
    }

    public User(String full_name, String email, String password, String confirm_password) {
//        this.id = id;
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
//        this.phone = phone;
    }

//    public String getUserImage() {
//        return userImage;
//    }
//
//    public void setUserImage(String userImage) {
//        this.userImage = userImage;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getPhone() {
//        return phone;
//    }
//
//    public void setPhone(Integer phone) {
//        this.phone = phone;
//    }


    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }
}
