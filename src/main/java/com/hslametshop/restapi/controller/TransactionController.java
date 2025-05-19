package com.hslametshop.restapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hslametshop.restapi.helper.requests.CheckoutRequest;
import com.hslametshop.restapi.model.entities.Transaction;
import com.hslametshop.restapi.model.entities.TransactionDetail;
import com.hslametshop.restapi.model.entities.Member;
import com.hslametshop.restapi.model.repositories.ProductRepository;
import com.hslametshop.restapi.model.repositories.UserRepository;
import com.hslametshop.restapi.service.TransactionDetailService;
import com.hslametshop.restapi.service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionDetailService transactionDetailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Transaction> getAllTransactions() {
        return transactionService.findAllTransaction();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Transaction getTransactionById(@PathVariable("id") UUID id) {
        return transactionService.findTransactionById(id);
    }

    @PostMapping("/checkout")
    @PreAuthorize("hasRole('MEMBER')")
    public Transaction checkoutTransaction(CheckoutRequest checkoutRequest) {

        Member member = (Member) userRepository.findById(checkoutRequest.getMember()).get();
        member.setAddress(checkoutRequest.getCustomerAddress());
        userRepository.save(member);

        Transaction transaction = new Transaction();
        transaction.setMember(member);
        transaction.setTotalAmount(checkoutRequest.getTotalAmount());
        transaction.setStatus(checkoutRequest.getStatus());
        // Save the transaction
        Transaction savedTransaction = transactionService.createTransaction(transaction);

        // Save the transaction details
        checkoutRequest.getCheckoutDetailRequests().forEach(detailRequest -> {
            TransactionDetail transactionDetail = new TransactionDetail();
            transactionDetail.setProduct(productRepository.findById(detailRequest.getProductId()).get());
            transactionDetail.setQty(detailRequest.getQty());
            transactionDetail.setSubtotal(detailRequest.getSubtotal());
            transactionDetail.setNotes(detailRequest.getNotes());
            transactionDetail.setTransaction(savedTransaction);

            transactionDetailService.createTransactionDetail(transactionDetail);
        });

        return savedTransaction;
    }
}
