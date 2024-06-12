package com.pplbo.productservice.dto;

import lombok.Data;

public class OrderItem {
    private Integer orderItemId;
    private Integer productId;
    private Integer quantity;
    private Double price;

    public OrderItem() {
    }

    public OrderItem(Integer orderItemId, Integer productId, Integer quantity, Double price) {
        this.orderItemId = orderItemId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
