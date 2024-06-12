package com.pplbo.productservice.event;

public class ProductOutOfStockEvent implements ProductEvent{
    private Long orderId;

    public ProductOutOfStockEvent() {
    }

    public ProductOutOfStockEvent(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

}
