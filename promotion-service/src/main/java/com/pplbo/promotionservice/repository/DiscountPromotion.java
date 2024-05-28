package com.pplbo.promotionservice.repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountPromotion extends Promotion {
    private double discountPercentage;

    public DiscountPromotion() {
        super();
    }

    @Override
    public void applyPromotion(Product product) {
        getProducts().add(product);
        double originalPrice = product.getPrice();
        double discountedPrice = originalPrice - (originalPrice * (discountPercentage / 100));
        product.setPrice(discountedPrice);
    }

    @Override
    public void removeProduct(Product product){
        promotion.getProducts().remove(product);
        double discountedPrice = product.getPrice();
        double originalPrice = discountedPrice * 100 / (100 - (discountPercentage * 100));
        product.setPrice(originalPrice);
    }
}
