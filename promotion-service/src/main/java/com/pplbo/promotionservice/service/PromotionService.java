package com.pplbo.promotionservice.service;

import com.pplbo.promotionservice.event.DiscountPromotionActivatedEvent;
import com.pplbo.promotionservice.event.DiscountPromotionExpiredEvent;
import com.pplbo.promotionservice.kafka.KafkaProducerService;
import com.pplbo.promotionservice.model.Promotion;
import com.pplbo.promotionservice.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private KafkaProducerService kafkaProducer;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String productServiceUrl = "http://8084/product";

    @Transactional
    public Promotion createPromotion(Promotion promotion) {
        Date currentDate = resetTime(new Date());

        // Ensure start date is not before the current date
        if (promotion.getStartDate() == null) {
            promotion.setStartDate(currentDate);
        } else if (resetTime(promotion.getStartDate()).compareTo(currentDate) < 0) {
            throw new IllegalArgumentException("Start date must be today or later");
        }

        // Ensure end date is after start date
        if (promotion.getEndDate() != null && resetTime(promotion.getEndDate()).before(resetTime(promotion.getStartDate()))) {
            throw new IllegalArgumentException("End date must be later than start date");
        }

        // Validate promotion type
        if (promotion.getType() == null) {
            throw new IllegalArgumentException("Promotion type must be specified");
        }

        switch (promotion.getType()) {
            case DISCOUNT:
                // Ensure discount percentage is greater than 0
                if (promotion.getDiscountPercentage() <= 0) {
                    throw new IllegalArgumentException("Discount percentage must be greater than 0 for discount promotions");
                }
                break;
            case FREESHIPPING:
                // Ensure discount percentage is null for free shipping promotions
                promotion.setDiscountPercentage(0);
                break;
            default:
                throw new IllegalArgumentException("Invalid promotion type");
        }

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
        Promotion promotion = getPromotionById(id);
        if (promotion != null) {
            promotionRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("Promotion not found for id: " + id);
        }
       
    }

    @Transactional
    public Promotion addProductToPromotion(Long id, Long productId) {
        if (isValidProduct(productId)) {
            Promotion promotion = getPromotionById(id);
            if (promotion != null) {
                // Cek jika status promosi adalah EXPIRED
                if (promotion.getStatus() == PromotionStatus.EXPIRED) {
                    throw new IllegalArgumentException("Promosi berstatus Expire sehingga tidak dapat diubah");
                }
    
                promotion.applyPromotion(productId);
                promotionRepository.save(promotion);
    
                // Check if start date is now or in the past, then publish event
                if (promotion.getStartDate().compareTo(new Date()) <= 0) {
                    kafkaProducer.sendMessage(new DiscountPromotionActivatedEvent(promotion));
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
            List<Long> productIds = promotion.getProductIds();
            if (!productIds.contains(productId)) {
                throw new IllegalArgumentException("Product not found for productId: " + productId);
            }
            
            promotion.removeProduct(productId);
            promotionRepository.save(promotion);
            kafkaProducer.sendMessage(new DiscountPromotionExpiredEvent(promotion));
    
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
        // Mengecek apakah endDate lebih awal dari startDate
        if (endDate.before(startDate)) {
            throw new IllegalArgumentException("Invalid start date and end date: end date cannot be earlier than start date.");
        }
    
        Promotion promotion = getPromotionById(id);
        if (promotion != null) {
            Date currentDate = resetTime(new Date()); // Mendapatkan tanggal saat ini tanpa waktu
    
            // Mengubah status berdasarkan kondisi yang diberikan menggunakan compareTo
            if (promotion.getStatus() == PromotionStatus.ACTIVE && resetTime(startDate).compareTo(currentDate) > 0 && resetTime(startDate).compareTo(endDate) < 0) {
                promotion.setStatus(PromotionStatus.INACTIVE);
            } else if (promotion.getStatus() == PromotionStatus.EXPIRED && resetTime(startDate).compareTo(currentDate) <= 0 && resetTime(endDate).compareTo(currentDate) > 0) {
                promotion.setStatus(PromotionStatus.ACTIVE);
            } else if (promotion.getStatus() == PromotionStatus.INACTIVE && resetTime(startDate).compareTo(currentDate) == 0) {
                promotion.setStatus(PromotionStatus.ACTIVE);
            } else if (resetTime(endDate).compareTo(currentDate) < 0) {
                promotion.setStatus(PromotionStatus.EXPIRED);
            }
    
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
                    kafkaProducer.sendMessage(new DiscountPromotionActivatedEvent(promotion));
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
                    kafkaProducer.sendMessage(new DiscountPromotionExpiredEvent(promotion));
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

        private Date resetTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


}
