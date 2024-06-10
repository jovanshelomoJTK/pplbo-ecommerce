package com.pplbo.orderservice.dto;

public record ShippingResponse(
    Long shippingId,
    String shippingName,
    Double shippingPrice,
    String shippingStatus,
    String shippingAddress
) {}
