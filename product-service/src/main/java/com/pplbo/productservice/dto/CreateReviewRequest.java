package com.pplbo.productservice.dto;

import java.sql.Timestamp;

public record CreateReviewRequest (
    Long productId,
    String review,
    String recommendationReview,
    int rating,
    String reviewMedia,
    Timestamp reviewDate
){}