package com.pplbo.promotionservice.command;

public class ValidateFreeShippingPromotion {
    private long promotionId;

    public ValidateFreeShippingPromotion(){

    }

    public ValidateFreeShippingPromotion(Long promotionId){
        this.promotionId = promotionId;
    }

    public void setPromotionId(Long promotionId){
        this.promotionId = promotionId;
    }

    public Long getPromotionId(){
        return promotionId;
    }

    @Override
    public String toString() {
        return "promotionId="+promotionId;
    }
}
