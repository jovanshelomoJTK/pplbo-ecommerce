package com.pplbo.promotionservice.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pplbo.promotionservice.model.Promotion;

public class DiscountPromotionActivatedEvent implements PromotionEvent {
    private String message = "Discount Activated";

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final Promotion promotion;

    public DiscountPromotionActivatedEvent(Promotion promotion) {
        this.promotion = promotion;
    }

    public String getMessage() {
        return message;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    @Override
    public String toString() {
        return "DiscountPromotionActivatedEvent{" +
                "promotion=" + promotion +
                '}';
    }
}
