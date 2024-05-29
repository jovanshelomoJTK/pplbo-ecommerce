package com.pplbo.promotionservice.event;

import org.springframework.context.ApplicationEvent;

import com.pplbo.promotionservice.model.Promotion;

public class DiscountPromotionCreated extends ApplicationEvent {
    private final Promotion promotion;

    public DiscountPromotionCreated(Object source, Promotion promotion) {
        super(source);
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
