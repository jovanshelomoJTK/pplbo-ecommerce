package com.pplbo.orderservice.dto;

public record ShippingRequest(
    String shippingName,
    Double shippingPrice,
    String shippingStatus,
    String shipping,
    String shippingAddress
) {}
