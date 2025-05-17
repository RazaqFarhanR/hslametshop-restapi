package com.hslametshop.restapi.model.entities;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_detail_transaction")
public class TransactionDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "detail_id")
    private UUID detailId;

    @ManyToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int qty;

    @Column(name = "subtotal")
    private double subtotal;

    @Column(name = "notes")
    private String notes;

    public Product getProduct() {
        return product;
    }

    public TransactionDetail(UUID detailId, UUID invoiceId, int qty, double subtotal, String notes) {
        this.detailId = detailId;
        this.qty = qty;
        this.subtotal = subtotal;
        this.notes = notes;
    }

    public UUID getDetailId() {
        return detailId;
    }

    public void setDetailId(UUID detailId) {
        this.detailId = detailId;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getQty() {
        return qty;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public String getNotes() {
        return notes;
    }

}
