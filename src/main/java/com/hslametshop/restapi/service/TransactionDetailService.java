package com.hslametshop.restapi.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hslametshop.restapi.model.entities.TransactionDetail;
import com.hslametshop.restapi.model.repositories.TransactionDetailRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionDetailService {

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    public List<TransactionDetail> findAllTransactionDetailsByTransactionID(UUID transactionId) {
        List<TransactionDetail> transactionDetails = (List<TransactionDetail>) transactionDetailRepository.findAll();

        List<TransactionDetail> filteredTransactionDetails = transactionDetails.stream()
                .filter(transactionDetail -> transactionDetail.getTransaction().getInvoiceId().equals(transactionId))
                .toList();
        if (filteredTransactionDetails.isEmpty()) {
            throw new RuntimeException("Transaction details not found for transaction ID: " + transactionId);
        }
        return filteredTransactionDetails;
    }

}
