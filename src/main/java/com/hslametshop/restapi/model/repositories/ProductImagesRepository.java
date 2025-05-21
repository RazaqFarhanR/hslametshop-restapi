package com.hslametshop.restapi.model.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.hslametshop.restapi.model.entities.ProductImages;

public interface ProductImagesRepository extends CrudRepository<ProductImages, UUID> {

}
