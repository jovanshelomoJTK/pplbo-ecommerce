package com.pplbo.orderservice.dto;

//import java.util.Date;
//import java.util.List;

public record OrderItemRequest(
    int quantity,
    int productId
) {}