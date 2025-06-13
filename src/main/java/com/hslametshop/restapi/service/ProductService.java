package com.hslametshop.restapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hslametshop.restapi.helper.requests.CreateProductsRequest;
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
            return productResponses;
        }
        for (Product product : products) {
            if (product.getStock() <= 0)
                continue; // filter stock 0
            ProductResponse productResponse = new ProductResponse();
            productResponse.setImages(new ArrayList<>());
            for (ProductImages productImage : productImages) {
                if (product.getProductId().equals(productImage.getProduct().getProductId())) {
                    productResponse.getImages().add(productImage.getImageUrl());
                }
            }
            productResponse.setId(product.getProductId());
            productResponse.setName(product.getName());
            productResponse.setDescription(product.getDescription());
            productResponse.setCategory(product.getCategory() != null ? product.getCategory().toString() : null);
            productResponse.setDiscount(product.getDiscount());
            if (product.getDiscount() > 0) {
                productResponse.setPrice(product.getPrice() - (product.getPrice() * product.getDiscount() / 100));
                productResponse.setOldPrice(product.getPrice());
            } else {
                productResponse.setPrice(product.getPrice());
                productResponse.setOldPrice(null);
            }
            productResponse.setImageAlt("IMG:" + product.getName());
            productResponse.setIsNew(product.getCreatedAt() != null
                    && product.getCreatedAt().isBefore(product.getCreatedAt().plusDays(2)));
            productResponse.setStock(product.getStock());
            productResponses.add(productResponse);
        }
        return productResponses;
    }

    public List<ProductResponse> findAllProductForAdmin() {
        List<ProductResponse> productResponses = new ArrayList<>();
        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductImages> productImages = (List<ProductImages>) productImagesRepository.findAll();
        if (products.isEmpty()) {
            return productResponses;
        }
        for (Product product : products) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setImages(new ArrayList<>());
            for (ProductImages productImage : productImages) {
                if (product.getProductId().equals(productImage.getProduct().getProductId())) {
                    productResponse.getImages().add(productImage.getImageUrl());
                }
            }
            productResponse.setId(product.getProductId());
            productResponse.setName(product.getName());
            productResponse.setDescription(product.getDescription());
            productResponse.setCategory(product.getCategory() != null ? product.getCategory().toString() : null);
            productResponse.setDiscount(product.getDiscount());
            if (product.getDiscount() > 0) {
                productResponse.setPrice(product.getPrice() - (product.getPrice() * product.getDiscount() / 100));
                productResponse.setOldPrice(product.getPrice());
            } else {
                productResponse.setPrice(product.getPrice());
                productResponse.setOldPrice(null);
            }
            productResponse.setImageAlt("IMG:" + product.getName());
            productResponse.setIsNew(product.getCreatedAt() != null
                    && product.getCreatedAt().isBefore(product.getCreatedAt().plusDays(2)));
            productResponse.setStock(product.getStock());
            productResponses.add(productResponse);
        }
        return productResponses;
    }

    public Product createProduct(CreateProductsRequest product) {
        Product newProduct = new Product();
        newProduct.setCategory(product.getCategory());
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct.setStock(product.getStock());
        newProduct.setDiscount(product.getDiscount());
        newProduct.setDescription(product.getDescription());

        Product savedProduct = productRepository.save(newProduct);

        for (String imageUrl : product.getImages()) {
            ProductImages productImage = new ProductImages();
            productImage.setImageUrl(imageUrl);
            productImage.setImageName("Img:" + savedProduct.getName());
            productImage.setProduct(savedProduct);
            productImagesRepository.save(productImage);
        }

        return savedProduct;

    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
        List<ProductImages> existingImages = (List<ProductImages>) productImagesRepository.findAll();
        for (ProductImages image : existingImages) {
            if (image.getProduct().getProductId().equals(id)) {
                productImagesRepository.delete(image);
            }
        }
    }

    public ProductResponse findOneProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No product found"));
        if (product.getStock() <= 0) {
            throw new NoSuchElementException("No product found"); // Atau return null sesuai kebutuhan
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setImages(new ArrayList<>());
        List<ProductImages> productImages = (List<ProductImages>) productImagesRepository.findAll();
        for (ProductImages productImage : productImages) {
            if (product.getProductId().equals(productImage.getProduct().getProductId())) {
                productResponse.getImages().add(productImage.getImageUrl());
            }
        }
        productResponse.setId(product.getProductId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setCategory(product.getCategory() != null ? product.getCategory().toString() : null);
        productResponse.setDiscount(product.getDiscount());
        if (product.getDiscount() > 0) {
            productResponse.setPrice(product.getPrice() - (product.getPrice() * product.getDiscount() / 100));
            productResponse.setOldPrice(product.getPrice());
        } else {
            productResponse.setPrice(product.getPrice());
            productResponse.setOldPrice(null);
        }
        productResponse.setImageAlt("IMG:" + product.getName());
        productResponse.setIsNew(
                product.getCreatedAt() != null && product.getCreatedAt().isBefore(product.getCreatedAt().plusDays(2)));
        productResponse.setStock(product.getStock());
        return productResponse;
    }

    public List<ProductResponse> findProductsByName(String name) {
        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductImages> productImages = (List<ProductImages>) productImagesRepository.findAll();
        List<ProductResponse> foundProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getStock() <= 0)
                continue; // filter stock 0
            if (product.getName() != null && product.getName().toLowerCase().startsWith(name)) {
                ProductResponse productResponse = new ProductResponse();
                productResponse.setImages(new ArrayList<>());
                for (ProductImages productImage : productImages) {
                    if (product.getProductId().equals(productImage.getProduct().getProductId())) {
                        productResponse.getImages().add(productImage.getImageUrl());
                    }
                }
                productResponse.setId(product.getProductId());
                productResponse.setName(product.getName());
                productResponse.setDescription(product.getDescription());
                productResponse.setCategory(product.getCategory() != null ? product.getCategory().toString() : null);
                productResponse.setDiscount(product.getDiscount());
                if (product.getDiscount() > 0) {
                    productResponse.setPrice(product.getPrice() - (product.getPrice() * product.getDiscount() / 100));
                    productResponse.setOldPrice(product.getPrice());
                } else {
                    productResponse.setPrice(product.getPrice());
                    productResponse.setOldPrice(null);
                }
                productResponse.setImageAlt("IMG:" + product.getName());
                productResponse.setIsNew(product.getCreatedAt() != null
                        && product.getCreatedAt().isBefore(product.getCreatedAt().plusDays(2)));
                productResponse.setStock(product.getStock());
                foundProducts.add(productResponse);
            }
        }
        return foundProducts;
    }

    public List<ProductResponse> findProductsByCategory(String category) {
        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductImages> productImages = (List<ProductImages>) productImagesRepository.findAll();
        List<ProductResponse> foundProducts = new ArrayList<>();
        CategoryEnum categoryEnum = null;
        try {
            categoryEnum = CategoryEnum.valueOf(category);
        } catch (Exception e) {
            return foundProducts;
        }
        for (Product product : products) {
            if (product.getStock() <= 0)
                continue; // filter stock 0
            if (product.getCategory() != null && product.getCategory().equals(categoryEnum)) {
                ProductResponse productResponse = new ProductResponse();
                productResponse.setImages(new ArrayList<>());
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
                productResponse.setIsNew(product.getCreatedAt() != null
                        && product.getCreatedAt().isBefore(product.getCreatedAt().plusDays(2)));
                productResponse.setStock(product.getStock());
                foundProducts.add(productResponse);
            }
        }
        return foundProducts;
    }

    public Product updateProduct(CreateProductsRequest product, UUID id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        // Hanya update jika field tidak null
        if (product.getCategory() != null) {
            existingProduct.setCategory(product.getCategory());
        }
        if (product.getName() != null) {
            existingProduct.setName(product.getName());
        }
        if (product.getPrice() != null) {
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getStock() != null) {
            existingProduct.setStock(product.getStock());
        }
        if (product.getDiscount() != null) {
            existingProduct.setDiscount(product.getDiscount());
        }
        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }

        Product updatedProduct = productRepository.save(existingProduct);

        // Update gambar hanya jika images tidak null
        if (product.getImages() != null) {
            List<ProductImages> existingImages = (List<ProductImages>) productImagesRepository.findAll();
            for (ProductImages image : existingImages) {
                if (image.getProduct().getProductId().equals(id)) {
                    productImagesRepository.delete(image);
                }
            }
            for (String imageUrl : product.getImages()) {
                ProductImages productImage = new ProductImages();
                productImage.setImageUrl(imageUrl);
                productImage.setProduct(updatedProduct);
                productImage.setImageName("Img:" + updatedProduct.getName());
                productImagesRepository.save(productImage);
            }
        }

        return updatedProduct;
    }

    public List<ProductResponse> findProductsByPriceBetween(Double minPrice, Double maxPrice) {
        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductImages> productImages = (List<ProductImages>) productImagesRepository.findAll();
        List<ProductResponse> foundProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getStock() <= 0)
                continue; // filter stock 0
            double finalPrice = product.getPrice() - (product.getPrice() * product.getDiscount() / 100);
            if (finalPrice >= minPrice && finalPrice <= maxPrice) {
                ProductResponse productResponse = new ProductResponse();
                productResponse.setImages(new ArrayList<>());
                for (ProductImages productImage : productImages) {
                    if (product.getProductId().equals(productImage.getProduct().getProductId())) {
                        productResponse.getImages().add(productImage.getImageUrl());
                    }
                }
                productResponse.setId(product.getProductId());
                productResponse.setName(product.getName());
                productResponse.setDescription(product.getDescription());
                productResponse.setCategory(product.getCategory() != null ? product.getCategory().toString() : null);
                productResponse.setDiscount(product.getDiscount());
                if (product.getDiscount() > 0) {
                    productResponse.setPrice(finalPrice);
                    productResponse.setOldPrice(product.getPrice());
                } else {
                    productResponse.setPrice(product.getPrice());
                    productResponse.setOldPrice(null);
                }
                productResponse.setImageAlt("IMG:" + product.getName());
                productResponse.setIsNew(product.getCreatedAt() != null
                        && product.getCreatedAt().isBefore(product.getCreatedAt().plusDays(2)));
                productResponse.setStock(product.getStock());
                foundProducts.add(productResponse);
            }
        }
        return foundProducts;
    }
}
