package com.hslametshop.restapi.helper.requests;

import java.util.UUID;

public class CheckoutDetailRequest {
    private UUID productId;
    private int qty;
    private double subtotal;
    private String notes;

    public CheckoutDetailRequest(UUID productId, int qty, double subtotal, String notes) {
        this.productId = productId;
        this.qty = qty;
        this.subtotal = subtotal;
        this.notes = notes;
    }

    public CheckoutDetailRequest() {
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
