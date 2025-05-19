package com.hslametshop.restapi.helper.requests;

import java.util.List;
import java.util.UUID;

import com.hslametshop.restapi.model.interfaces.TransactionStatusEnum;

public class CheckoutRequest {
    private UUID member;
    private String customerAddress;
    private double totalAmount;
    private TransactionStatusEnum status;
    private List<CheckoutDetailRequest> checkoutDetailRequests;

    public CheckoutRequest(UUID member, String customerAddress, double totalAmount, TransactionStatusEnum status,
            List<CheckoutDetailRequest> checkoutDetailRequests) {
        this.member = member;
        this.customerAddress = customerAddress;
        this.totalAmount = totalAmount;
        this.status = status;
        this.checkoutDetailRequests = checkoutDetailRequests;
    }

    public CheckoutRequest() {
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }

    public List<CheckoutDetailRequest> getCheckoutDetailRequests() {
        return checkoutDetailRequests;
    }

    public void setCheckoutDetailRequests(List<CheckoutDetailRequest> checkoutDetailRequests) {
        this.checkoutDetailRequests = checkoutDetailRequests;
    }

    public UUID getMember() {
        return member;
    }

    public void setMember(UUID member) {
        this.member = member;
    }

}
