package com.pplbo.orderservice.command;

public class ProductOutOfStockCommand {
    private Long orderId;

    public ProductOutOfStockCommand(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
    
}
