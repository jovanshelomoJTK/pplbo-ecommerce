package com.pplbo.promotionservice.repository;

public class FreeShippingPromotion extends Promotion {

    @Override
    public void applyPromotion(Order order) {
        order.setShippingFee(0);
    }

}
