package com.pplbo.productservice.command;

import com.pplbo.productservice.dto.Order;

public class OrderCreated {
    private Long orderId;
    private Order order;

    public OrderCreated() {
    }

    public OrderCreated(Long orderId, Order order) {
        this.orderId = orderId;
        this.order = order;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Order getOrder() {
        return order;
    }
}
