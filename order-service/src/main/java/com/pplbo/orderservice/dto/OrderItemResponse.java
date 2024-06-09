package com.pplbo.orderservice.dto;

//import java.util.Date;
//import java.util.List;

public record OrderItemResponse(
    Long orderItemId,
    int quantity,
    int productId
) {}