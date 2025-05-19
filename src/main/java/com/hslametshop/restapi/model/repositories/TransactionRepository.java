package com.hslametshop.restapi.model.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.hslametshop.restapi.model.entities.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, UUID> {

}
