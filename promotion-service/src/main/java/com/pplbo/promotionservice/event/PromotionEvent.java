package com.pplbo.promotionservice.event;

import java.util.List;

public interface PromotionEvent {
    public double getDiscountPercentage();

    public List<Long> getProductIds();
}
