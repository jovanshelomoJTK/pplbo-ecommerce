package com.pplbo.orderservice.dto;

public record OrderLineItemRequest(
    int quantity,
    int productId
) {}
