package com.pplbo.productservice.event;

public class ProductAvailableEvent implements ProductEvent{
    private Long orderId;

    public ProductAvailableEvent() {
    }

    public ProductAvailableEvent(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

}
