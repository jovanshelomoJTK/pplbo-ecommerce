package com.pplbo.paymentservice.event;

import org.springframework.context.ApplicationEvent;

import com.pplbo.paymentservice.model.PaymentMethod;

public class OrderEvent extends ApplicationEvent {

    private Integer customerId;
    private Integer orderId;
    private Integer total;
    private PaymentMethod paymentMethod;

    public OrderEvent(Object source, Integer customerId, Integer orderId, Integer total, PaymentMethod paymentMethod) {
        super(source);
        this.customerId = customerId;
        this.orderId = orderId;
        this.total = total;
        this.paymentMethod = paymentMethod;
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
