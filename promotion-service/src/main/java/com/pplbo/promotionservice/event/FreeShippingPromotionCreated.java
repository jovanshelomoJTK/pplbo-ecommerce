package com.pplbo.promotionservice.event;

import org.springframework.context.ApplicationEvent;

import com.pplbo.promotionservice.model.Promotion;

public class FreeShippingPromotionCreated extends ApplicationEvent {
    private final Promotion promotion;

    public FreeShippingPromotionCreated(Object source, Promotion promotion) {
        super(source);
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
