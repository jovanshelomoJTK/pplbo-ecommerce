package com.pplbo.promotionservice.controller;

import com.pplbo.promotionservice.model.Promotion;
import com.pplbo.promotionservice.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping
    public ResponseEntity<?> createPromotion(@RequestBody Promotion promotion) {
        try {
            promotionService.createPromotion(promotion);
            return new ResponseEntity<>(promotion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create promotion: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPromotionById(@PathVariable Long id) {
        Promotion promotion = promotionService.getPromotionById(id);
        if (promotion != null) {
            return new ResponseEntity<>(promotion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Promotion not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Promotion>> getAllActivePromotions() {
        List<Promotion> promotions = promotionService.getAllActivePromotion();
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable Long id) {
        try {
            promotionService.deletePromotion(id);
            return new ResponseEntity<>("Promotion deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete promotion: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/product/{productId}")
    public ResponseEntity<?> addProductToPromotion(@PathVariable Long id, @PathVariable Long productId) {
        try {
            promotionService.addProductToPromotion(id, productId);
            return new ResponseEntity<>("Product added to promotion successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add product to promotion: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}/product/{productId}")
    public ResponseEntity<?> removeProductFromPromotion(@PathVariable Long id, @PathVariable Long productId) {
        try {
            promotionService.removeProductFromPromotion(id, productId);
            return new ResponseEntity<>("Product removed from promotion successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to remove product from promotion: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/schedule/{id}")
    public ResponseEntity<?> schedulePromotion(@PathVariable Long id, @RequestParam Date startDate,
            @RequestParam Date endDate) {
        try {
            promotionService.schedulePromotion(id, startDate, endDate);
            return new ResponseEntity<>("Promotion scheduled successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to schedule promotion: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
