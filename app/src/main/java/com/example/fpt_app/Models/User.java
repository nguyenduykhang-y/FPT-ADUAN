package com.example.fpt_app.Models;

public class User {
    private String full_name, email, password, confirm_password;

    public User() {
    }

    public User(String full_name, String email, String password, String confirm_password) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
    }

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
