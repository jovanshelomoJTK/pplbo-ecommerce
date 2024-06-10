package com.pplbo.productservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;

@Table(name = "review")
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewid")
    private Long reviewId;

    @Column(name = "productid")
    private Long productId;

    @Column(name = "review")
    private String review;

    @Column(name = "recommendationreview")
    private String recommendationReview;

    @Column(name = "rating")
    private int rating;

    @Column(name = "reviewmedia")
    private String reviewMedia;

    @Column(name = "reviewdate")
    private Timestamp reviewDate;

    // Getters and Setters
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getRecommendationReview() {
        return recommendationReview;
    }

    public void setRecommendationReview(String recommendationReview) {
        this.recommendationReview = recommendationReview;
    }

    public String getReviewMedia() {
        return reviewMedia;
    }

    public void setReviewMedia(String reviewMedia) {
        this.reviewMedia = reviewMedia;
    }

    public Timestamp getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Timestamp reviewDate) {
        this.reviewDate = reviewDate;
    }
}