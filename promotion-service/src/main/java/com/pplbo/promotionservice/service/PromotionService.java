package com.pplbo.promotionservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pplbo.promotionservice.event.DiscountPromotionCreated;
import com.pplbo.promotionservice.event.FreeShippingPromotionCreated;
import com.pplbo.promotionservice.model.Product;
import com.pplbo.promotionservice.model.Promotion;
import com.pplbo.promotionservice.repository.ProductRepository;
import com.pplbo.promotionservice.repository.PromotionRepository;

@Service
public class PromotionService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private  PromotionRepository promotionRepository;

    @Autowired
    private  ProductRepository productRepository;

    @Transactional
    public void createPromotion(Promotion promotion){
        promotionRepository.save(promotion);
        if (promotion.getType()==PromotionType.FREESHIPPING) {
            eventPublisher.publishEvent(new FreeShippingPromotionCreated(this, promotion));
        } else if (promotion.getType()==PromotionType.DISCOUNT) {
            eventPublisher.publishEvent(new DiscountPromotionCreated(this, promotion));
        }
    }

    public Promotion getPromotionById(Long id){
        Optional<Promotion> promotion = promotionRepository.findById(id);
        return promotion.orElse(null);
    }

    public List<Promotion> getAllActivePromotion(){
        return promotionRepository.findAllByStatus(PromotionStatus.ACTIVE);
    }

    @Transactional
    public void deletePromotion(Long id){
        promotionRepository.deleteById(id);
    }

    @Transactional
    public void addProductToPromotion(Long id, Product product) {
        Promotion promotion = getPromotionById(id);
        if (promotion != null) {
            promotion.applyPromotion(product);
            promotionRepository.save(promotion);
        }
    }

    @Transactional
    public void removeProductFromPromotion(Long id_product){
        Optional<Product> productOpt = productRepository.findById(id_product);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            List<Promotion> promotions = promotionRepository.findAll();
            for (Promotion promotion : promotions) {
                if (promotion.getProducts().contains(product)) {
                    promotion.removeProduct(product);
                    promotionRepository.save(promotion);
                }
            }
        }
    }

    // @Transactional
    // public void applyFreeShipping(Long id, Order order){
    //     Promotion promotion = getPromotionById(id);
    //     if (promotion != null) {
    //         order.setFeeShipping(0);
    //     }
    // }

    @Transactional
    public void schedulePromotion(Long id, Date startDate, Date endDate) {
        Promotion promotion = getPromotionById(id);
        if (promotion != null) {
            promotion.setStartDate(startDate);
            promotion.setEndDate(endDate);
            promotionRepository.save(promotion);
        }
    }

    public void updateStatusExpiredPromotion(Long id) {
        Promotion promotion = getPromotionById(id);
        if (promotion != null && promotion.getEndDate().before(new Date())) {
            promotion.setStatus(PromotionStatus.EXPIRED);
            promotionRepository.save(promotion);
        }
    }

    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void updateActivePromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        Date currentDate = new Date();

        for (Promotion promotion : promotions) {
            if (promotion.getStartDate() != null && promotion.getStartDate().compareTo(currentDate) <= 0 &&
                !PromotionStatus.ACTIVE.equals(promotion.getStatus())) {
                promotion.setStatus(PromotionStatus.ACTIVE);
                promotionRepository.save(promotion);
            }
        }
    }

    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void updateExpiredPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        Date currentDate = new Date();

        for (Promotion promotion : promotions) {
            if (promotion.getEndDate() != null && promotion.getEndDate().compareTo(currentDate) < 0 &&
                !PromotionStatus.EXPIRED.equals(promotion.getStatus())) {
                promotion.setStatus(PromotionStatus.EXPIRED);
                promotionRepository.save(promotion);
            }
        }
    }
}


