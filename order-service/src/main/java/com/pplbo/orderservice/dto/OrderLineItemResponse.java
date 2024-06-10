package com.pplbo.orderservice.dto;

public record OrderLineItemResponse(
    Long orderLineItemId,
    int quantity,
    int productId
) {}
