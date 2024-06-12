package com.pplbo.promotionservice.command;

import com.pplbo.promotionservice.dto.Order;

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
