package com.pplbo.promotionservice.service;

import org.springframework.context.ApplicationEvent;

public class DiscountPromotionCreated implements ApplicationEvent {
    private final Promotion promotion;

    public DiscountPromotionCreated(Object source, Promotion promotion) {
        super(source);
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
