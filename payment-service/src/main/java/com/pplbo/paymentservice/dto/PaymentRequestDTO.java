package com.pplbo.paymentservice.dto;

public class PaymentRequestDTO {
    private Integer customerId;
    private Integer orderId;

    // Getters and Setters

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
