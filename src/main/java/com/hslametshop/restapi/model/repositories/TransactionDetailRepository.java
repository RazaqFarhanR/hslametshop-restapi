package com.hslametshop.restapi.model.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.hslametshop.restapi.model.entities.TransactionDetail;

public interface TransactionDetailRepository extends CrudRepository<TransactionDetail, UUID> {

}
