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
        try {
            List<ProductResponse> products = productService.findAllProduct();
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> create(@RequestBody CreateProductsRequest product) {
        try {
            Product p = productService.createProduct(product);
            if (p == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().body(p);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> update(@RequestBody CreateProductsRequest product, @PathVariable("id") UUID id) {
        try {
            Product p = productService.updateProduct(product, id);
            if (p == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().body(p);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().body("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<ProductResponse> findOne(@PathVariable("id") UUID id) {
        try {
            ProductResponse product = productService.findOneProduct(id);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().body(product);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<ProductResponse>> findByName(@PathVariable("name") String name) {
        try {
            List<ProductResponse> products = productService.findProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponse>> findByCategory(@PathVariable("category") String category) {
        try {
            List<ProductResponse> products = productService.findProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/price/{minPrice}/{maxPrice}")
    public ResponseEntity<List<ProductResponse>> findByPriceBetween(@PathVariable("minPrice") Double minPrice,
            @PathVariable("maxPrice") Double maxPrice) {
        try {
            if (minPrice < 0 || maxPrice < 0) {
                return ResponseEntity.badRequest().build();
            }
            if (minPrice > maxPrice) {
                return ResponseEntity.badRequest().build();
            }
            if (minPrice == maxPrice) {
                return ResponseEntity.badRequest().build();
            }
            if (minPrice == null || maxPrice == null) {
                return ResponseEntity.badRequest().build();
            }
            List<ProductResponse> products = productService.findProductsByPriceBetween(minPrice, maxPrice);
            if (products.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(products);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
