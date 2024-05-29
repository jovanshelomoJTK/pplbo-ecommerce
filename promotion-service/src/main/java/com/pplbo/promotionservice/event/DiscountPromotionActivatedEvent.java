package com.pplbo.promotionservice.event;

import com.pplbo.promotionservice.model.Promotion;
import org.springframework.context.ApplicationEvent;

public class DiscountPromotionActivatedEvent extends ApplicationEvent {
    private final Promotion promotion;

    public DiscountPromotionActivatedEvent(Object source, Promotion promotion) {
        super(source);
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
