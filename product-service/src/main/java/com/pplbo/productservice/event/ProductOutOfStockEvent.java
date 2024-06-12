package com.pplbo.productservice.event;

public class ProductOutOfStockEvent {
    private Long productId;
    private String productName;
    private Integer stock;

    // Constructor, getters, and setters
    public ProductOutOfStockEvent(Long productId, String productName, Integer stock) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return "ProductOutOfStockEvent{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", stock=" + stock +
                '}';
    }
}
