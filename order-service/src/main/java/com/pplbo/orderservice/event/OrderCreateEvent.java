package com.pplbo.orderservice.event;

import com.pplbo.orderservice.model.Order;
import com.pplbo.orderservice.dto.OrderResponse;

public class OrderCreateEvent {
    private OrderResponse order;

    public OrderCreateEvent() {
    }

    public OrderCreateEvent(OrderResponse order) {
        this.order = order;
    }

    public OrderResponse getOrder() {
        return order;
    }

    public void setOrder(OrderResponse order) {
        this.order = order;
    }
}
