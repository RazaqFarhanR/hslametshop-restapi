package com.hslametshop.restapi.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hslametshop.restapi.helper.requests.CheckoutRequest;
import com.hslametshop.restapi.model.entities.Member;
import com.hslametshop.restapi.model.entities.Transaction;
import com.hslametshop.restapi.model.entities.TransactionDetail;
import com.hslametshop.restapi.model.repositories.ProductRepository;
import com.hslametshop.restapi.model.repositories.TransactionDetailRepository;
import com.hslametshop.restapi.model.repositories.TransactionRepository;
import com.hslametshop.restapi.model.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Transaction> findAllTransaction() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    public Transaction createTransaction(CheckoutRequest checkoutRequest) {
        Member member = (Member) userRepository.findById(checkoutRequest.getMember()).get();
        member.setAddress(checkoutRequest.getCustomerAddress());
        userRepository.save(member);

        Transaction transaction = new Transaction();
        transaction.setMember(member);
        transaction.setTotalAmount(checkoutRequest.getTotalAmount());
        transaction.setStatus(checkoutRequest.getStatus());
        // Save the transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Save the transaction details
        checkoutRequest.getCheckoutDetailRequests().forEach(detailRequest -> {
            TransactionDetail transactionDetail = new TransactionDetail();
            transactionDetail.setProduct(productRepository.findById(detailRequest.getProductId()).get());
            transactionDetail.setQty(detailRequest.getQty());
            transactionDetail.setSubtotal(detailRequest.getSubtotal());
            transactionDetail.setNotes(detailRequest.getNotes());
            transactionDetail.setTransaction(savedTransaction);

            transactionDetailRepository.save(transactionDetail);
        });

        return savedTransaction;
    }

    public Transaction findTransactionById(UUID id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }
}
