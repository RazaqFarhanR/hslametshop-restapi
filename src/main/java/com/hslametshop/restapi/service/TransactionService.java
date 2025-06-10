package com.hslametshop.restapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hslametshop.restapi.helper.requests.CheckoutRequest;
import com.hslametshop.restapi.helper.responses.OrderItemResponse;
import com.hslametshop.restapi.helper.responses.OrderResponse;
import com.hslametshop.restapi.model.entities.Member;
import com.hslametshop.restapi.model.entities.Transaction;
import com.hslametshop.restapi.model.entities.TransactionDetail;
import com.hslametshop.restapi.model.interfaces.TransactionStatusEnum;
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

        // Cek stok sebelum membuat transaksi
        for (var detailRequest : checkoutRequest.getCheckoutDetailRequests()) {
            var product = productRepository.findById(detailRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            if (detailRequest.getQty() > product.getStock()) {
                throw new IllegalArgumentException("Stock for product " + product.getName() + " is not enough.");
            }
        }

        Transaction transaction = new Transaction();
        transaction.setMember(member);
        transaction.setTotalAmount(checkoutRequest.getTotalAmount());
        transaction.setStatus(checkoutRequest.getStatus());
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Save the transaction details
        checkoutRequest.getCheckoutDetailRequests().forEach(detailRequest -> {
            TransactionDetail transactionDetail = new TransactionDetail();
            var product = productRepository.findById(detailRequest.getProductId()).get();
            transactionDetail.setProduct(product);
            transactionDetail.setQty(detailRequest.getQty());
            transactionDetail.setSubtotal(detailRequest.getSubtotal());
            transactionDetail.setNotes(detailRequest.getNotes());
            transactionDetail.setTransaction(savedTransaction);

            transactionDetailRepository.save(transactionDetail);

            // Kurangi stok produk
            product.setStock(product.getStock() - detailRequest.getQty());
            productRepository.save(product);
        });

        return savedTransaction;
    }

    public Transaction findTransactionById(UUID id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public Transaction updateStatus(UUID id) {
        Transaction order = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        switch (order.getStatus()) {
            case PROSES:
                order.setStatus(TransactionStatusEnum.DIKIRIM);
                break;
            case DIKIRIM:
                order.setStatus(TransactionStatusEnum.SELESAI);
                break;
            case SELESAI:
                throw new IllegalStateException("Order sudah selesai, tidak bisa diubah lagi");
            default:
                throw new IllegalStateException("Status tidak dikenali");
        }

        return transactionRepository.save(order);
    }

    public List<OrderResponse> findTransactionByMemberId(UUID memberId) {
        List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
        if (transactions.isEmpty()) {
            throw new RuntimeException("No transactions found in the system");
        }
        List<OrderResponse> filteredTransactions = new ArrayList<OrderResponse>();
        for (Transaction transaction : transactions) {
            if (transaction.getMember().getId().equals(memberId)) {
                OrderResponse orderResponse = new OrderResponse();
                orderResponse.setInvoiceId(transaction.getInvoiceId());
                orderResponse.setMemberId(transaction.getMember().getId());
                orderResponse.setTotal(transaction.getTotalAmount());
                orderResponse.setStatus(transaction.getStatus());
                orderResponse.setCreatedAt(transaction.getTrxDate());
                orderResponse.setUpdatedAt(transaction.getUpdatedAt());

                List<OrderItemResponse> items = new ArrayList<>();
                for (TransactionDetail detail : transaction.getDetails()) {
                    OrderItemResponse item = new OrderItemResponse();
                    item.setProductId(detail.getProduct().getProductId());
                    item.setQuantity(detail.getQty());
                    item.setProductName(detail.getProduct().getName());
                    item.setPrice(detail.getSubtotal() / detail.getQty());
                    items.add(item);
                }
                orderResponse.setItems(items);
                filteredTransactions.add(orderResponse);
            }
        }
        if (filteredTransactions.isEmpty()) {
            throw new RuntimeException("No transactions found for member with ID: " + memberId);
        }
        return filteredTransactions;
    }
}
