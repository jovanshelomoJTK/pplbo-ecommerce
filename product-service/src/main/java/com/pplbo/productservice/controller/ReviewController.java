package com.pplbo.productservice.controller;

import com.pplbo.productservice.dto.CreateReviewRequest;
import com.pplbo.productservice.model.Review;
import com.pplbo.productservice.service.ReviewService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("review")
public class ReviewController {
    
    @Autowired
    ReviewService reviewService;

    @GetMapping("/")
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping("/product/{id}")
    public List<Review> getReviewByProductId(@PathVariable Long id) {
        return reviewService.getReviewsByProductId(id);
    }

    @PostMapping("/")
    public ResponseEntity<Review> addReview(@RequestBody CreateReviewRequest request) {
        Review review = new Review();
        review.setProductId(request.productId());
        review.setReview(request.review());
        review.setRecommendationReview(request.recommendationReview());
        review.setRating(request.rating());
        review.setReviewMedia(request.reviewMedia());
        review.setReviewDate(request.reviewDate());

        Review addedReview = reviewService.addReview(review);
        return ResponseEntity.ok(addedReview);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}