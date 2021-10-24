package com.example.fpt_app.Models;

import java.io.Serializable;

public class ListSP implements Serializable {
    public static final int TYPE_ASUS = 1;
    public static final int TYPE_HP = 2;
    public static final int TYPE_GM = 3;


    private int img;
    private String name;
    private String gia;
    private int type;

    public ListSP(int img, String name, String gia, int type) {
        this.img = img;
        this.name = name;
        this.gia = gia;
        this.type = type;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
