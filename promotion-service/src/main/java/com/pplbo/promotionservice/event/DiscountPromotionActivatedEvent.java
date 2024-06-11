package com.pplbo.promotionservice.event;

import java.util.List;

public class DiscountPromotionActivatedEvent implements PromotionEvent {
    private final String message = "Discount Activated";
    private final double discountPercentage;
    private final List<Long> productIds;

    public DiscountPromotionActivatedEvent(double discountPercentage, List<Long> productIds) {
        this.discountPercentage = discountPercentage;
        this.productIds = productIds;
    }

    public String getMessage() {
        return message;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    @Override
    public String toString() {
        return "DiscountPromotionActivatedEvent{" +
                "message=" + message +
                "discountPercentage="+ discountPercentage +
                "productIds="+ productIds +
                '}';
    }
}
