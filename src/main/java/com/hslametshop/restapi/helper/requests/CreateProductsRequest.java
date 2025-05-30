package com.hslametshop.restapi.helper.requests;

import java.util.List;

import com.hslametshop.restapi.model.interfaces.CategoryEnum;

public class CreateProductsRequest {
    private CategoryEnum category;
    private String name;
    private double price;
    private int stock;
    private double discount;
    private String description;
    private List<String> images;

    public CreateProductsRequest() {
    }

    public CreateProductsRequest(CategoryEnum category, String name, double price, int stock, double discount,
            String description, List<String> images) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.discount = discount;
        this.description = description;
        this.images = images;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}
