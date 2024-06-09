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
import com.pplbo.productservice.repository.ProductRepository;

import jakarta.validation.ValidationException;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Review> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        if (reviews.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reviews found");
        }
        return reviews;
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
    }

    public List<Review> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        if (reviews.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reviews found for product with ID " + productId);
        }
        return reviews;
    }

    public Review addReview(Review review) {
        validateProductExists(review.getProductId());
        validateRating(review.getRating());
        validateRecommendationReview(review.getRecommendationReview());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        Review reviewToDelete = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        reviewRepository.delete(reviewToDelete);
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

    private void validateProductExists(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + productId + " does not exist");
        }
    }
     
}