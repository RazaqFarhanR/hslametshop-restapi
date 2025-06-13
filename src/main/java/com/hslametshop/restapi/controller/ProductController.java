package com.hslametshop.restapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hslametshop.restapi.helper.requests.CreateProductsRequest;
import com.hslametshop.restapi.helper.responses.LogoutResponse;
import com.hslametshop.restapi.helper.responses.ProductResponse;
import com.hslametshop.restapi.model.entities.Product;
import com.hslametshop.restapi.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> products = productService.findAllProduct();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductResponse>> findAllForAdmin() {
        List<ProductResponse> products = productService.findAllProductForAdmin();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(products);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> create(@RequestBody CreateProductsRequest product) {

        Product p = productService.createProduct(product);
        if (p == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(p);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> update(@RequestBody CreateProductsRequest product, @PathVariable("id") UUID id) {

        Product p = productService.updateProduct(product, id);
        if (p == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(p);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LogoutResponse> delete(@PathVariable("id") UUID id) {
        try {
            productService.deleteProduct(id);
            LogoutResponse deleteProductResponse = new LogoutResponse("Product deleted successfully");
            return ResponseEntity.ok(deleteProductResponse);
        } catch (Exception e) {
            LogoutResponse errorRes = new LogoutResponse("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorRes);
        }
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<ProductResponse> findOne(@PathVariable("id") UUID id) {

        ProductResponse product = productService.findOneProduct(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<ProductResponse>> findByName(@PathVariable("name") String name) {

        List<ProductResponse> products = productService.findProductsByName(name);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponse>> findByCategory(@PathVariable("category") String category) {

        List<ProductResponse> products = productService.findProductsByCategory(category);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/price/{minPrice}/{maxPrice}")
    public ResponseEntity<List<ProductResponse>> findByPriceBetween(@PathVariable("minPrice") Double minPrice,
            @PathVariable("maxPrice") Double maxPrice) {

        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Min price cannot be greater than max price");
        }
        if (minPrice == maxPrice) {
            throw new IllegalArgumentException("Min price cannot be equal to max price");
        }
        if (minPrice == null || maxPrice == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        List<ProductResponse> products = productService.findProductsByPriceBetween(minPrice, maxPrice);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(products);

    }
}
