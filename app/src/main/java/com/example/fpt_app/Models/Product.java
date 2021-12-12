package com.example.fpt_app.Models;

import java.io.Serializable;

public class Product implements Serializable {
    private Integer id;
    private String name;
    private Integer price;
    private String image_url;
    private Integer category_id;

    public Product() {
    }

    public Product(Integer id, String name, Integer price,  String image_url, Integer category_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image_url = image_url;
        this.category_id = category_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

}
