package com.pplbo.orderservice.event;

import com.pplbo.orderservice.model.Order;

public class OrderCreated implements OrderEvent{
    private Long orderId;

    private Order order;

    public OrderCreated(Long orderId, Order order) {
        this.orderId = orderId;
        this.order = order;
    }

    @Override
    public Long getOrderId() {
        return orderId;
    }

    public Order getOrder() {
        return order;
    }
}
