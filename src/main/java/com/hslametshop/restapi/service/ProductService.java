package com.hslametshop.restapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hslametshop.restapi.helper.responses.ProductResponse;
import com.hslametshop.restapi.model.entities.Product;
import com.hslametshop.restapi.model.entities.ProductImages;
import com.hslametshop.restapi.model.interfaces.CategoryEnum;
import com.hslametshop.restapi.model.repositories.ProductImagesRepository;
import com.hslametshop.restapi.model.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImagesRepository productImagesRepository;

    public List<ProductResponse> findAllProduct() {
        List<ProductResponse> productResponses = new ArrayList<>();
        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductImages> productImages = (List<ProductImages>) productImagesRepository.findAll();
        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found");
        }
        for (Product product : products) {
            ProductResponse productResponse = new ProductResponse();
            for (ProductImages productImage : productImages) {
                if (product.getProductId().equals(productImage.getProduct().getProductId())) {
                    productResponse.getImages().add(productImage.getImageUrl());
                }
            }
            productResponse.setId(product.getProductId());
            productResponse.setName(product.getName());
            productResponse.setDescription(product.getDescription());
            productResponse.setCategory(product.getCategory().toString());

            productResponse.setDiscount(product.getDiscount());
            if (product.getDiscount() > 0) {
                productResponse.setPrice(product.getPrice() - (product.getPrice() * product.getDiscount() / 100));
                productResponse.setOldPrice(product.getPrice());
            } else {
                productResponse.setPrice(product.getPrice());
                productResponse.setOldPrice(null);
            }
            productResponse.setImageAlt("IMG:" + product.getName());
            productResponse.setNew(product.getCreatedAt().isAfter(product.getCreatedAt().minusDays(30)));
            productResponse.setStock(product.getStock());

            productResponses.add(productResponse);
        }
        return productResponses;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public ProductImages createProductImage(ProductImages productImages) {
        return productImagesRepository.save(productImages);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public ProductResponse findOneProduct(UUID id) {
        Product product = productRepository.findById(id).get();
        ProductResponse productResponse = new ProductResponse();
        List<ProductImages> productImages = (List<ProductImages>) productImagesRepository.findAll();
        if (product == null) {
            throw new NoSuchElementException("No products found");
        }
        for (ProductImages productImage : productImages) {
            if (product.getProductId().equals(productImage.getProduct().getProductId())) {
                productResponse.getImages().add(productImage.getImageUrl());
            }
        }
        productResponse.setId(product.getProductId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setCategory(product.getCategory().toString());

        productResponse.setDiscount(product.getDiscount());
        if (product.getDiscount() > 0) {
            productResponse.setPrice(product.getPrice() - (product.getPrice() * product.getDiscount() / 100));
            productResponse.setOldPrice(product.getPrice());
        } else {
            productResponse.setPrice(product.getPrice());
            productResponse.setOldPrice(null);
        }
        productResponse.setImageAlt("IMG:" + product.getName());
        productResponse.setNew(product.getCreatedAt().isAfter(product.getCreatedAt().minusDays(30)));
        productResponse.setStock(product.getStock());
        return productResponse;
    }

    public List<ProductResponse> findProductsByName(String name) {
        List<Product> products = (List<Product>) productRepository.findAll();

        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found");
        }
        List<ProductImages> productImages = (List<ProductImages>) productImagesRepository.findAll();
        List<ProductResponse> foundProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().contains(name)) {
                ProductResponse productResponse = new ProductResponse();
                for (ProductImages productImage : productImages) {
                    if (product.getProductId().equals(productImage.getProduct().getProductId())) {
                        productResponse.getImages().add(productImage.getImageUrl());
                    }
                }
                productResponse.setId(product.getProductId());
                productResponse.setName(product.getName());
                productResponse.setDescription(product.getDescription());
                productResponse.setCategory(product.getCategory().toString());

                productResponse.setDiscount(product.getDiscount());
                if (product.getDiscount() > 0) {
                    productResponse.setPrice(product.getPrice() - (product.getPrice() * product.getDiscount() / 100));
                    productResponse.setOldPrice(product.getPrice());
                } else {
                    productResponse.setPrice(product.getPrice());
                    productResponse.setOldPrice(null);
                }
                productResponse.setImageAlt("IMG:" + product.getName());
                productResponse.setNew(product.getCreatedAt().isAfter(product.getCreatedAt().minusDays(30)));
                productResponse.setStock(product.getStock());
                foundProducts.add(productResponse);
            }
        }
        return foundProducts;
    }

    public List<ProductResponse> findProductsByCategory(String category) {
        List<Product> products = (List<Product>) productRepository.findAll();
        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found");
        }
        List<ProductImages> productImages = (List<ProductImages>) productImagesRepository.findAll();
        List<ProductResponse> foundProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().equals(CategoryEnum.valueOf(category))) {
                ProductResponse productResponse = new ProductResponse();
                for (ProductImages productImage : productImages) {
                    if (product.getProductId().equals(productImage.getProduct().getProductId())) {
                        productResponse.getImages().add(productImage.getImageUrl());
                    }
                }
                productResponse.setId(product.getProductId());
                productResponse.setName(product.getName());
                productResponse.setDescription(product.getDescription());
                productResponse.setCategory(product.getCategory().toString());

                productResponse.setDiscount(product.getDiscount());
                if (product.getDiscount() > 0) {
                    productResponse.setPrice(product.getPrice() - (product.getPrice() * product.getDiscount() / 100));
                    productResponse.setOldPrice(product.getPrice());
                } else {
                    productResponse.setPrice(product.getPrice());
                    productResponse.setOldPrice(null);
                }
                productResponse.setImageAlt("IMG:" + product.getName());
                productResponse.setNew(product.getCreatedAt().isAfter(product.getCreatedAt().minusDays(30)));
                productResponse.setStock(product.getStock());
                foundProducts.add(productResponse);
            }
        }
        return foundProducts;
    }

    public Product updateProduct(Product product, UUID id) {
        try {
            Product data = productRepository.findById(id).get();
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

    public List<ProductResponse> findProductsByPriceBetween(Double minPrice, Double maxPrice) {
        List<Product> products = (List<Product>) productRepository.findAll();
        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found");
        }
        List<ProductImages> productImages = (List<ProductImages>) productImagesRepository.findAll();
        List<ProductResponse> foundProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() - (product.getPrice() * product.getDiscount()
                    / 100) >= minPrice && product.getPrice()
                            - (product.getPrice() * product.getDiscount() / 100) <= maxPrice) {
                ProductResponse productResponse = new ProductResponse();
                for (ProductImages productImage : productImages) {
                    if (product.getProductId().equals(productImage.getProduct().getProductId())) {
                        productResponse.getImages().add(productImage.getImageUrl());
                    }
                }
                productResponse.setId(product.getProductId());
                productResponse.setName(product.getName());
                productResponse.setDescription(product.getDescription());
                productResponse.setCategory(product.getCategory().toString());

                productResponse.setDiscount(product.getDiscount());
                if (product.getDiscount() > 0) {
                    productResponse.setPrice(product.getPrice() - (product.getPrice() * product.getDiscount() / 100));
                    productResponse.setOldPrice(product.getPrice());
                } else {
                    productResponse.setPrice(product.getPrice());
                    productResponse.setOldPrice(null);
                }
                productResponse.setImageAlt("IMG:" + product.getName());
                productResponse.setNew(product.getCreatedAt().isAfter(product.getCreatedAt().minusDays(30)));
                productResponse.setStock(product.getStock());
                foundProducts.add(productResponse);
            }
        }
        return foundProducts;
    }
}
