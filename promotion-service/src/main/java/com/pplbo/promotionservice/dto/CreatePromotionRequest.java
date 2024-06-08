package com.pplbo.promotionservice.dto;

import java.util.Date;
import java.util.List;

import com.pplbo.promotionservice.service.PromotionStatus;
import com.pplbo.promotionservice.service.PromotionType;

public record CreatePromotionRequest (
    Date startDate,
    Date endDate,
    PromotionStatus status,
    PromotionType type,
    double discountPercentage,
    List<Long> productIds
){}
