package com.pplbo.orderservice.command;

public class ProductAvailableCommand {
    private Long orderId;

    public ProductAvailableCommand(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}