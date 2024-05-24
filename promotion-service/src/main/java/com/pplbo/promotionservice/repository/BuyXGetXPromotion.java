package com.pplbo.promotionservice.repository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BuyXGetXPromotion extends Promotion {
    private int buyQuantity;
    private int getQuantity;

}
