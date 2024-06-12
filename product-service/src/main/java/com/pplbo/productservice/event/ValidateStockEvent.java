package com.pplbo.productservice.event;

public class ValidateStockEvent {
    private final Long productId;
    private final int quantity;

    public ValidateStockEvent(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "ValidateStockEvent{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
