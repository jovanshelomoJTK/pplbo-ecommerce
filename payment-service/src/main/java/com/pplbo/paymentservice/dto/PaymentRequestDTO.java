package com.pplbo.paymentservice.dto;

public class PaymentRequestDTO {
    private Long customerId;
    private Long orderId;
    private Double totalPrice;

    // Getters and Setters

    public Long getCustomerId() {
        return customerId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
