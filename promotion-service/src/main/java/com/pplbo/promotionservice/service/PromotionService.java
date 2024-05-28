package com.pplbo.promotionservice.service;

import com.pplbo.promotionservice.event.DiscountPromotionCreated;
import com.pplbo.promotionservice.event.FreeShippingPromotionCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;

public class PromotionService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private  PromotionRepository promotionRepository;

    @Autowired
    private Product product;

    @Transactional
    public void createPromotion(Promotion promotion){
        promotionRepository.save(promotion);
        if (promotion.type==PromotionType.FREESHIPPING) {
            eventPublisher.publishEvent(new FreeShippingPromotionCreated(this, promotion));
        } else if (promotion.type==PromotionType.DISCOUNT) {
            eventPublisher.publishEvent(new DiscountPromotionCreated(this, promotion));
        }
    }

    public Promotion getPromotionById(int id){
        Optional<Promotion> promotion = promotionRepository.findById(id);
        return promotion.orElse(null);
    }

    public List<Promotion> getAllActivePromotion(){
        return promotionRepository.findAllByStatus(PromotionStatus.ACTIVE);
    }

    @Transactional
    public void deletePromotion(int id){
        promotionRepository.deleteById(id);
    }

    @Transactional
    public void addProductToPromotion(int id, Product product) {
        Promotion promotion = getPromotionById(id);
        if (promotion != null) {
            promotion.applyPromotion(product);
            promotionRepository.save(promotion);
        }
    }

    @Transactional
    public void removeProductFromPromotion(int id_product){
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

    @Transactional
    public void applyFreeShipping(int id, Order order){
        Promotion promotion = getPromotionById(id);
        if (promotion != null) {
            order.setFeeShipping(0);
        }
    }

    @Transactional
    public void schedulePromotion(int id, Date startDate, Date endDate) {
        Promotion promotion = getPromotionById(id);
        if (promotion != null) {
            promotion.setStartDate(startDate);
            promotion.setEndDate(endDate);
            promotionRepository.save(promotion);
        }
    }

    public void updateStatusExpiredPromotion(int id) {
        Promotion promotion = getPromotionById(id);
        if (promotion != null && promotion.getEndDate().before(new Date())) {
            promotion.setStatus(PromotionStatus.EXPIRED);
            promotionRepository.save(promotion);
        }
    }

    @Scheduled(fixedRate = 3600000)
    public void updateExpiredPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        for (Promotion promotion : promotions) {
            Promotion promotion = getPromotionById(promotion.getId);
            if (promotion.getEndDate().before(new Date())) {
                promotion.setStatus(PromotionStatus.EXPIRED);
                promotionRepository.save(promotion);
            }
        }
    }
}


