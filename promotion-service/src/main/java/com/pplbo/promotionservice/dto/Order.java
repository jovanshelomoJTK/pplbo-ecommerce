package com.pplbo.promotionservice.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class Order {
    private Long orderId;
    private Date orderDate;
    private String orderStatus;
    private Long customerId;
    private Double orderTotal;
    private String shippingAddress;
    private Set<OrderItem> orderItems;
}
