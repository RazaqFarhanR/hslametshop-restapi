package com.hslametshop.restapi.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hslametshop.restapi.model.entities.Product;
import com.hslametshop.restapi.model.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> fAllProduct() {
        return (List<Product>) productRepository.findAll();
    }

    public Product cProduct(Product product) {
        return productRepository.save(product);
    }

    public void rProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public Product fOneProduct(UUID id) {
        return productRepository.findById(id).get();
    }

    public List<Product> fProductsByName(String name) {
        return productRepository.findByName(name);
    }
}
