package com.pplbo.productservice.dto;


import java.util.Date;
import java.util.Set;

public class Order {
    private Long orderId;
    private Date orderDate;
    private String orderStatus;
    private Long customerId;
    private Double orderTotal;
    private String shippingAddress;
    private Set<OrderItem> orderItems;

    public Order() {
    }

    public Order(Long orderId, Date orderDate, String orderStatus, Long customerId, Double orderTotal, String shippingAddress, Set<OrderItem> orderItems) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.customerId = customerId;
        this.orderTotal = orderTotal;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
