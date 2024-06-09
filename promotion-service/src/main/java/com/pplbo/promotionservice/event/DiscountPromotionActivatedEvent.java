package com.pplbo.promotionservice.event;

import com.pplbo.promotionservice.model.Promotion;

public class DiscountPromotionActivatedEvent implements Event {
    private final Promotion promotion;

    public DiscountPromotionActivatedEvent(Promotion promotion) {
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
