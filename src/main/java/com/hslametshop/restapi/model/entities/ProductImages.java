package com.hslametshop.restapi.model.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_product_images")
public class ProductImages {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID imageId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_name")
    private String imageName;

    public ProductImages() {
    }

    public ProductImages(UUID imageId, Product product, String imageUrl, String imageName) {
        this.imageId = imageId;
        this.product = product;
        this.imageUrl = imageUrl;
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public UUID getImageId() {
        return imageId;
    }

    public void setImageId(UUID imageId) {
        this.imageId = imageId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
