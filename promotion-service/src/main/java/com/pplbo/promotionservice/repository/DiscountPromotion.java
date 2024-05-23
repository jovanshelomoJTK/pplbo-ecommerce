package com.pplbo.promotionservice.repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountPromotion extends Promotion {
    private double discountPercentage;

    @Override
    public void applyPromotion(Product product) {
        double originalPrice = product.getPrice();
        double discountedPrice = originalPrice - (originalPrice * (discountPercentage / 100));
        product.setPrice(discountedPrice);
    }
}
