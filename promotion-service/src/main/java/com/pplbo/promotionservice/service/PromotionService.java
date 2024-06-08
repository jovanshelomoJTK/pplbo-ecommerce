package com.pplbo.promotionservice.service;

import com.pplbo.promotionservice.event.DiscountPromotionActivatedEvent;
import com.pplbo.promotionservice.event.DiscountPromotionExpiredEvent;
import com.pplbo.promotionservice.model.Promotion;
import com.pplbo.promotionservice.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private PromotionRepository promotionRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String productServiceUrl = "http://8084/product";

    @Transactional
    public Promotion createPromotion(Promotion promotion) {
        Promotion savedPromotion = promotionRepository.save(promotion);
        return savedPromotion;
    }

    public Promotion getPromotionById(Long id) {
        Optional<Promotion> promotion = promotionRepository.findById(id);
        return promotion.orElse(null);
    }

    public List<Promotion> getAllActivePromotion() {
        try {
            List<Promotion> promotions = promotionRepository.findByStatus(PromotionStatus.ACTIVE);
            return promotions;
        } catch (Exception e) {
            System.err.println("Error fetching active promotions: ");
            e.printStackTrace();
            throw new RuntimeException("Error fetching active promotions", e);
        }
    }

    public List<Promotion> getAllPromotion() {
        List<Promotion> promotions = promotionRepository.findAll();
        return promotions;
    }

    @Transactional
    public void deletePromotion(Long id) {
        promotionRepository.deleteById(id);
    }

    @Transactional
    public Promotion addProductToPromotion(Long id, Long productId) {
        if (isValidProduct(productId)) {
            Promotion promotion = getPromotionById(id);
            if (promotion != null) {
                promotion.applyPromotion(productId);
                
                // Set start date if it's not already set
                if (promotion.getStartDate() == null) {
                    promotion.setStartDate(new Date());
                }
                
                promotionRepository.save(promotion);
                
                // Check if start date is now or in the past, then publish event
                if (promotion.getStartDate().compareTo(new Date()) <= 0) {
                    eventPublisher.publishEvent(new DiscountPromotionActivatedEvent(this, promotion));
                }
                
                return promotion;
            }
            throw new IllegalArgumentException("Promotion not found for id: " + id);
        } else {
            throw new IllegalArgumentException("Invalid product ID");
        }
    }
    
    @Transactional
    public Promotion removeProductFromPromotion(Long id, Long productId) {
        Promotion promotion = getPromotionById(id);
        if (promotion != null) {
            promotion.removeProduct(productId);
            promotionRepository.save(promotion);
            eventPublisher.publishEvent(new DiscountPromotionExpiredEvent(this, promotion));

            return promotion;
        }
        throw new IllegalArgumentException("Promotion not found for id: " + id);
    }

    // @Transactional
    // public void applyFreeShipping(Long id, Order order){
    // Promotion promotion = getPromotionById(id);
    // if (promotion != null) {
    // order.setFeeShipping(0);
    // }
    // }

    @Transactional
    public Promotion schedulePromotion(Long id, Date startDate, Date endDate) {
        Promotion promotion = getPromotionById(id);
        if (promotion != null) {
            promotion.setStartDate(startDate);
            promotion.setEndDate(endDate);
            promotionRepository.save(promotion);
            return promotion;
        }
        throw new IllegalArgumentException("Promotion not found for id: " + id);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateActivePromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        Date currentDate = new Date();

        for (Promotion promotion : promotions) {
            if (promotion.getStartDate() != null && promotion.getStartDate().compareTo(currentDate) <= 0 &&
                    !PromotionStatus.ACTIVE.equals(promotion.getStatus())) {
                promotion.setStatus(PromotionStatus.ACTIVE);
                promotionRepository.save(promotion);

                if ((PromotionType.DISCOUNT.equals(promotion.getType()))) {
                    eventPublisher.publishEvent(new DiscountPromotionActivatedEvent(this, promotion));
                }
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateExpiredPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        Date currentDate = new Date();

        for (Promotion promotion : promotions) {
            if (promotion.getEndDate() != null && promotion.getEndDate().compareTo(currentDate) < 0 &&
                    !PromotionStatus.EXPIRED.equals(promotion.getStatus())) {
                promotion.setStatus(PromotionStatus.EXPIRED);
                promotionRepository.save(promotion);
                if ((PromotionType.DISCOUNT.equals(promotion.getType()))) {
                    eventPublisher.publishEvent(new DiscountPromotionExpiredEvent(this, promotion));
                }
            }
        }
    }

    private boolean isValidProduct(Long productId) {
        // try {
        // restTemplate.getForObject(productServiceUrl + "/" + productId, String.class);
        // return true;
        // } catch (Exception e) {
        // return false;
        // }
        return true;
    }
}
