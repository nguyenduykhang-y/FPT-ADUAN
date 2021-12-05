package com.example.fpt_app.Models;

import java.io.Serializable;

public class Cart implements Serializable {
    private Integer id;
    private Integer idProduct;
    private String name;
    private Double price;

    private String image_url;
    private Integer category_id;

    public Cart() {
    }

    public Cart(Integer id, Integer idProduct, String name, Double price,  String image_url, Integer category_id) {
        this.id = id;
        this.idProduct = idProduct;
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

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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
