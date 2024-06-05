package com.pplbo.orderservice.dto;

public record OrderItemResponse(
    Long orderItemId,
    int quantity,
    int productId
) {}