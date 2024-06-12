package com.pplbo.productservice.event;

public class OrderValidatedEvent {
    private final Long productId;
    private final int quantity;
    private final String message = "Order Validated";

    public OrderValidatedEvent(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "OrderValidatedEvent{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                ", message='" + message + '\'' +
                '}';
    }
}
