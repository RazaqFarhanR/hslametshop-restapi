package com.hslametshop.restapi.model.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.hslametshop.restapi.model.entities.Product;

public interface ProductRepository extends CrudRepository<Product, UUID> {
    List<Product> findByName(String name);
}
