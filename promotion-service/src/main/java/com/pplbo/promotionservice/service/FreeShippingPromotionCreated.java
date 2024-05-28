package com.pplbo.promotionservice.service;

import org.springframework.context.ApplicationEvent;

public class FreeShippingPromotionCreated implements ApplicationEvent {
    private final Promotion promotion;

    public FreeShippingPromotionCreated(Object source, Promotion promotion) {
        super(source);
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
