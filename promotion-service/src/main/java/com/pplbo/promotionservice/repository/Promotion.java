package com.pplbo.promotionservice.repository;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Promotion {
    private Long id_promotion;
    private Date startDate;
    private Date endDate;
    private PromotionStatus status;
    private PromotionType type;
    private List<Product> products;

    public abstract void applyPromotion(Product product);

    public abstract void applyPromotion(Order order);

    public abstract void removeProduct(Product product);
}
