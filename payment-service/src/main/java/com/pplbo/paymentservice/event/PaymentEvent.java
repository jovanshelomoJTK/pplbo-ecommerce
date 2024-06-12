package com.pplbo.paymentservice.event;

public class PaymentEvent {
    private Long orderId;
    private String status;
    private String kodePayment;

    public PaymentEvent() {
    }

    public PaymentEvent(Long orderId, String status, String kodePayment) {
        this.orderId = orderId;
        this.status = status;
        this.kodePayment = kodePayment;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getKodePayment() {
        return kodePayment;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setKodePayment(String kodePayment) {
        this.kodePayment = kodePayment;
    }
}
