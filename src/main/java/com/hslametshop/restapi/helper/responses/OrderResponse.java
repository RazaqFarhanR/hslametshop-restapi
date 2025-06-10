package com.hslametshop.restapi.helper.responses;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.hslametshop.restapi.model.interfaces.TransactionStatusEnum;

public class OrderResponse {
    public UUID invoiceId;
    public UUID memberId;
    public double total;
    public TransactionStatusEnum status;
    public List<OrderItemResponse> items;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public OrderResponse(UUID invoiceId, UUID memberId, double total, TransactionStatusEnum status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt, List<OrderItemResponse> items) {
        this.invoiceId = invoiceId;
        this.memberId = memberId;
        this.total = total;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.items = items;
    }

    public OrderResponse() {
        // Default constructor
    }

    public UUID getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(UUID invoiceId) {
        this.invoiceId = invoiceId;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

}
