package com.pplbo.productservice.controller;

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
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review addReview = reviewService.addReview(review);
        return ResponseEntity.ok(addReview);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Review updatedReview = reviewService.updateReview(id, updates);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
