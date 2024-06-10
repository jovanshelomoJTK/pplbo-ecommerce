package com.pplbo.promotionservice.repository;

import com.pplbo.promotionservice.model.Promotion;
import com.pplbo.promotionservice.service.PromotionStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
      List<Promotion> findByStatus(PromotionStatus status);

    // default void updateById(Long id, Promotion newPromotion) {
    //     findById(id).ifPresent(existingPromotion -> {
    //         existingPromotion.setName(newPromotion.getName());
    //         existingPromotion.setStartDate(newPromotion.getStartDate());
    //         existingPromotion.setEndDate(newPromotion.getEndDate());
    //         existingPromotion.setType(newPromotion.getType());
    //         existingPromotion.setDiscountPercentage(newPromotion.getDiscountPercentage());
    //         existingPromotion.setBuyQuantity(newPromotion.getBuyQuantity());
    //         existingPromotion.setGetQuantity(newPromotion.getGetQuantity());
    //         save(existingPromotion);
    //     });
    // }
}
