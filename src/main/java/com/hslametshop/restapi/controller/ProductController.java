package com.hslametshop.restapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hslametshop.restapi.model.entities.Product;
import com.hslametshop.restapi.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> findAll() {
        System.out.println("findAll: " + productService.findAllProduct());
        return productService.findAllProduct();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product create(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Product update(@RequestBody Product product, @PathVariable("id") UUID id) {
        return productService.updateProduct(product, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable("id") UUID id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/{id}")
    public Product findOne(@PathVariable("id") UUID id) {
        return productService.findOneProduct(id);
    }

    @GetMapping("/{name}")
    public List<Product> findByName(@PathVariable("name") String name) {
        return productService.findProductsByName(name);
    }

    @GetMapping("/category/{category}")
    public List<Product> findByCategory(@PathVariable("category") String category) {
        return productService.findProductsByCategory(category);
    }

    @GetMapping("/price/{minPrice}/{maxPrice}")
    public List<Product> findByPriceBetween(@PathVariable("minPrice") Double minPrice,
            @PathVariable("maxPrice") Double maxPrice) {
        return productService.findProductsByPriceBetween(minPrice, maxPrice);
    }
}
