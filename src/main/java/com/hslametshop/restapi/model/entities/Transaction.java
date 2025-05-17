package com.hslametshop.restapi.model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hslametshop.restapi.model.interfaces.TransactionStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "invoice_id")
    private UUID invoiceId;

    @Column(name = "transaction_date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime trxDate;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum status;

    @OneToMany(mappedBy = "transaction")
    private List<TransactionDetail> details;

    public Transaction(UUID invoiceId, LocalDateTime trxDate, double totalAmount, TransactionStatusEnum status) {
        this.invoiceId = invoiceId;
        this.trxDate = trxDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public UUID getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(UUID invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LocalDateTime getTrxDate() {
        return trxDate;
    }

    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }

    public void setTrxDate(LocalDateTime trxDate) {
        this.trxDate = trxDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
