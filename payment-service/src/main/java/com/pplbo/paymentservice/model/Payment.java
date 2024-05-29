package com.pplbo.paymentservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "payment")
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer customerId;
    private Integer orderId;
    private Integer total;
    private String status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    public Payment() {
    }

    public Payment(Long id, Integer customerId, Integer orderId, Integer total, String status, PaymentMethod paymentMethod) {
        this.id = id;
        this.customerId = customerId;
        this.orderId = orderId;
        this.total = total;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    public Long getId() {
        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Integer getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
