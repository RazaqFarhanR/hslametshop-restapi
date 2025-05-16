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
    @JoinColumn(name = "invoice_id")
    private Transaction transaction;

    @Column(name = "quantity")
    private int qty;

    @Column(name = "subtotal")
    private double subtotal;

    @Column(name = "notes")
    private String notes;

    public TransactionDetail(UUID detailId, UUID invoiceId, int qty, double subtotal, String notes) {
        this.detailId = detailId;
        this.qty = qty;
        this.subtotal = subtotal;
        this.notes = notes;
    }

    public UUID getDetailId() {
        return detailId;
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
