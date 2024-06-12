package com.pplbo.orderservice.web;

import java.util.Set;

import com.pplbo.orderservice.model.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateOrderRequest {
    private Long customerId;

    private Double orderTotal;

    private String shippingAddress;
    
    private Set<OrderItem> orderItems;
}
