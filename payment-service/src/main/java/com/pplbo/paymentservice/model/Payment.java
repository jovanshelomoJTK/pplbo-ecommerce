package com.pplbo.paymentservice.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Table(name = "payment", uniqueConstraints = @UniqueConstraint(columnNames = "kodePayment"))
@Entity
public class Payment {

    @Id
    private String kodePayment;
    private Integer customerId;
    private Integer orderId;
    private String status;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = true)
    private PaymentMethod paymentMethod;

    public Payment() {
    }

    public Payment(String kodePayment, Integer customerId, Integer orderId, String status, PaymentMethod paymentMethod) {
        this.kodePayment = kodePayment;
        this.customerId = customerId;
        this.orderId = orderId;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    // Ensure kodePayment is set before persisting the entity
    @PrePersist
    public void prePersist() {
        if (kodePayment == null) {
            kodePayment = UUID.randomUUID().toString();
        }
    }

    public String getKodePayment() {
        return kodePayment;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setKodePayment(String kodePayment) {
        this.kodePayment = kodePayment;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}