package com.pplbo.orderservice.event;

import com.pplbo.orderservice.model.Order;
import com.pplbo.orderservice.model.OrderStatus;

public class OrderCreatedEvent {
    private Order order;
    private OrderStatus orderStatus;

    public OrderCreatedEvent(Order order, OrderStatus orderStatus) {
        this.order = order;
        this.orderStatus = orderStatus;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
