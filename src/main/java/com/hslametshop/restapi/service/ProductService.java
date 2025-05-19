package com.hslametshop.restapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hslametshop.restapi.model.entities.Product;
import com.hslametshop.restapi.model.interfaces.CategoryEnum;
import com.hslametshop.restapi.model.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllProduct() {
        return (List<Product>) productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public Product findOneProduct(UUID id) {
        try {
            return productRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Product not found with id: " + id);
        }
    }

    public List<Product> findProductsByName(String name) {
        List<Product> products = (List<Product>) productRepository.findAll();
        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found");
        }
        List<Product> foundProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().contains(name)) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    public List<Product> findProductsByCategory(String category) {
        List<Product> products = (List<Product>) productRepository.findAll();
        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found");
        }
        List<Product> foundProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().equals(CategoryEnum.valueOf(category))) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    public Product updateProduct(Product product, UUID id) {
        try {
            Product data = findOneProduct(id);
            data.setName(product.getName());
            data.setCategory(product.getCategory());
            data.setDescription(product.getDescription());
            data.setPrice(product.getPrice());
            data.setStock(product.getStock());
            data.setDiscount(product.getDiscount());

            return productRepository.save(data);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Product not found with id: " + id);
        }

    }

    public List<Product> findProductsByPriceBetween(Double minPrice, Double maxPrice) {
        List<Product> products = (List<Product>) productRepository.findAll();
        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found");
        }
        List<Product> foundProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }
}
