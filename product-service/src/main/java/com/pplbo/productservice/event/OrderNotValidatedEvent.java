package com.pplbo.productservice.event;

public class OrderNotValidatedEvent {
    private final Long productId;
    private final int quantity;
    private final String message = "Order Not Validated";

    public OrderNotValidatedEvent(Long productId, int quantity) {
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
        return "OrderNotValidatedEvent{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                ", message='" + message + '\'' +
                '}';
    }
}
