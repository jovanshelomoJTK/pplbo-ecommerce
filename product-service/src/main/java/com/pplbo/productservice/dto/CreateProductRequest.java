package com.pplbo.productservice.dto;

public record CreateProductRequest(

    String productName,
    Integer price,
    Integer stock,
    Long categoryId,
    Long brandId,
    String size,
    String color,
    String material,
    String productDesc,
    String productImage
){}
