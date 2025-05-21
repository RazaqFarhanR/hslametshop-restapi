package com.hslametshop.restapi.helper.responses;

import java.util.List;
import java.util.UUID;

public class ProductResponse {
    private UUID id;
    private String name;
    private String description;
    private String category;
    private double price;
    private double discount;
    private Double oldPrice;
    private String imageAlt;
    private List<String> images;
    private boolean isNew;
    private int stock;

    public ProductResponse() {
    }

    public ProductResponse(UUID id, String name, String description, String category, double price, double discount,
            Double oldPrice, String imageAlt, List<String> images, boolean isNew, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.discount = discount;
        this.oldPrice = oldPrice;
        this.imageAlt = imageAlt;
        this.images = images;
        this.isNew = isNew;
        this.stock = stock;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}
