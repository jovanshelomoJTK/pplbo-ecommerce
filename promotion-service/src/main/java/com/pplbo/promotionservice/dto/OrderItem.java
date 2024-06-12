package com.pplbo.promotionservice.dto;

import lombok.Data;

@Data
public class OrderItem {
    private Integer orderItemId;
    private Integer productId;
    private Integer quantity;
    private Double price;
}
