package com.pplbo.productservice.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pplbo.productservice.model.Review;
import com.pplbo.productservice.repository.ReviewRepository;

import jakarta.validation.ValidationException;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public Review addReview(Review review) {
        validateRating(review.getRating());
        validateRecommendationReview(review.getRecommendationReview());
        return reviewRepository.save(review);
    }

    public Review updateReview(Long reviewId, Map<String, Object> updates){
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        updates.forEach((key, value) -> {
            switch (key) {
                case "productId":
                    existingReview.setProductId(((Integer) value).longValue());
                    break;
                case "rating":
                    validateRating((Integer) value);
                    existingReview.setRating((Integer) value);
                    break;
                case "recommendationReview":
                    validateRecommendationReview((String) value);
                    existingReview.setRecommendationReview((String) value);
                    break;
                case "review":
                    existingReview.setReview((String) value);
                    break;
                case "reviewMedia":
                    existingReview.setReviewMedia((String) value);
                    break;
                case "reviewDate":
                    existingReview.setReviewDate(convertStringToTimestamp((String) value));;
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid field: " + key);
            }
        });
        return reviewRepository.save(existingReview);
    }

    public void deleteReview(Long reviewId) {
        Review reviewToDelete = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        reviewRepository.delete(reviewToDelete);
    }

    private Timestamp convertStringToTimestamp(String strDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return new Timestamp(dateFormat.parse(strDate).getTime());
        } catch (ParseException e) {
            throw new ValidationException("Invalid date format. Please use 'yyyy-MM-dd'T'HH:mm:ss'");
        }
    }

    private void validateRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }

    private void validateRecommendationReview(String recommendationReview) {
        if (!recommendationReview.equals("yes") && !recommendationReview.equals("no")) {
            throw new IllegalArgumentException("Recommendation review must be either 'yes' or 'no'");
        }
    }
}