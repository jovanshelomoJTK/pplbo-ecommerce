package com.pplbo.orderservice.event;

import com.pplbo.orderservice.model.Order;
import com.pplbo.orderservice.model.OrderStatus;

import lombok.Data;

@Data
public class OrderCreatedEvent {
    private Order order;
    private OrderStatus orderStatus;

    public OrderCreatedEvent(Order order, OrderStatus orderStatus) {
        this.order= order;
        this.orderStatus = orderStatus;
    }
}