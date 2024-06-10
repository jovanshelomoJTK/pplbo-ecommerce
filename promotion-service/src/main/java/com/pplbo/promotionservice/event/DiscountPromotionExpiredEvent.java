package com.pplbo.promotionservice.event;

import com.pplbo.promotionservice.model.Promotion;

public class DiscountPromotionExpiredEvent implements PromotionEvent {
    private final Promotion promotion;

    public DiscountPromotionExpiredEvent(Promotion promotion) {
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
