package com.pplbo.promotionservice.event;

import com.pplbo.promotionservice.model.Promotion;

public class DiscountPromotionExpiredEvent implements PromotionEvent {
    private String message = "Discount Activated";
    private final Promotion promotion;

    public DiscountPromotionExpiredEvent(Promotion promotion) {
        this.promotion = promotion;
    }

    public String getMessage() {
        return message;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
