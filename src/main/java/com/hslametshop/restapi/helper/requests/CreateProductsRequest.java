package com.hslametshop.restapi.helper.requests;

import java.util.List;

import com.hslametshop.restapi.model.interfaces.CategoryEnum;

public class CreateProductsRequest {
    private CategoryEnum category;
    private String name;
    private Double price;
    private Integer stock;
    private Double discount;
    private String description;
    private List<String> images;

    public CreateProductsRequest() {
    }

    public CreateProductsRequest(CategoryEnum category, String name, Double price, Integer stock, Double discount,
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
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
