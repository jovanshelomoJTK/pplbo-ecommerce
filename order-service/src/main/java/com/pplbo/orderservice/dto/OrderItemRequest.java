package com.pplbo.orderservice.dto;

public record OrderItemRequest(
    int quantity,
    int productId
) {}