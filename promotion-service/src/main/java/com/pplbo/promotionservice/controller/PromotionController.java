package com.pplbo.promotionservice.controller;

import com.pplbo.promotionservice.command.ValidateFreeShippingPromotion;
import com.pplbo.promotionservice.dto.CreatePromotionRequest;
import com.pplbo.promotionservice.event.DiscountPromotionActivatedEvent;
import com.pplbo.promotionservice.kafka.KafkaProducerService;
import com.pplbo.promotionservice.model.Promotion;
import com.pplbo.promotionservice.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private KafkaProducerService kafkaProducer;

    @PostMapping
    public ResponseEntity<?> createPromotion(@RequestBody CreatePromotionRequest request) {
        try {
            Promotion promotion = Promotion.fromRequest(request);
            Promotion savedPromotion = promotionService.createPromotion(promotion);
            return new ResponseEntity<>(savedPromotion, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Failed to create promotion: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create promotion: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getPromotions() {
        List<Promotion> promotion = promotionService.getAllPromotion();
        return new ResponseEntity<>(promotion, HttpStatus.OK);

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
    
    @GetMapping("/active")
    public ResponseEntity<?> getAllActivePromotions() {
        try {
            List<Promotion> promotions = promotionService.getAllActivePromotion();
            if (promotions.isEmpty()) {
                return new ResponseEntity<>("No active promotions available", HttpStatus.OK);
            }
            return new ResponseEntity<>(promotions, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error in getAllActivePromotions: ");
            e.printStackTrace();
            return new ResponseEntity<>("Failed to retrieve active promotions: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @PutMapping("/{id}/product/{productId}")
    public ResponseEntity<?> addProductToPromotion(@PathVariable Long id, @PathVariable Long productId) {
        try {
            Promotion savedPromotion = promotionService.addProductToPromotion(id, productId);
            return new ResponseEntity<>(savedPromotion, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add product to promotion: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}/product/{productId}")
    public ResponseEntity<?> removeProductFromPromotion(@PathVariable Long id, @PathVariable Long productId) {
        try {
            Promotion savedPromotion = promotionService.removeProductFromPromotion(id, productId);
            return new ResponseEntity<>(savedPromotion, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Failed to create promotion: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("Failed to remove product from promotion: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PostMapping("/{id}/applyFreeShipping")
    // public void applyFreeShipping(@PathVariable Long id, @RequestBody Order
    // order) {
    // promotionService.applyFreeShipping(id, order);
    // }

    @PutMapping("/schedule/{id}")
    public ResponseEntity<?> schedulePromotion(@PathVariable Long id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            Promotion savedPromotion = promotionService.schedulePromotion(id, startDate, endDate);
            return new ResponseEntity<>(savedPromotion, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to schedule promotion: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/testKafka/{id}")
    public void testKafka(@PathVariable Long id){
        kafkaProducer.sendReply(new ValidateFreeShippingPromotion(id));
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = "The input data is invalid. Please check the data and try again.";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    

}