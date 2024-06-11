package com.pplbo.promotionservice.event;

import java.util.List;

public class DiscountPromotionExpiredEvent implements PromotionEvent {
    private String message = "Discount Expired";
    private final double discountPercentage;
    private final List<Long> productIds;

    public DiscountPromotionExpiredEvent(double discountPercentage, List<Long> productIds) {
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
        return "DiscountPromotionExpiredEvent{" +
                "message=" + message +
                "discountPercentage="+ discountPercentage +
                "productIds="+ productIds +
                '}';
    }
}
