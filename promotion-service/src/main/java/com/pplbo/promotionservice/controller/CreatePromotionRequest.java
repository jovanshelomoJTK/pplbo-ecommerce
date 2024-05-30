package com.pplbo.promotionservice.controller;

import java.util.Date;
import java.util.List;

import com.pplbo.promotionservice.service.PromotionStatus;
import com.pplbo.promotionservice.service.PromotionType;

public class CreatePromotionRequest {
    private Date startDate;
    private Date endDate;
    private PromotionStatus status;
    private PromotionType type;
    private double discountPercentage;
    private List<Long> productIds;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public PromotionStatus getStatus() {
        return status;
    }

    public void setStatus(PromotionStatus status) {
        this.status = status;
    }

    public PromotionType getType() {
        return type;
    }

    public void setType(PromotionType type) {
        this.type = type;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}

