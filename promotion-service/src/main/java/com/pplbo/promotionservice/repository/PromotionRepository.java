package com.pplbo.promotionservice.repository;

import com.pplbo.promotionservice.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Override
    <S extends Promotion> S save(S entity);

    @Override
    Optional<Promotion> findById(Long id);


    List<Promotion> findAllByStatus(String status);

    @Override
    void deleteById(Long id);

    // Method untuk update by id
    default void updateById(Long id, Promotion newPromotion) {
        findById(id).ifPresent(existingPromotion -> {
            existingPromotion.setName(newPromotion.getName());
            existingPromotion.setStartDate(newPromotion.getStartDate());
            existingPromotion.setEndDate(newPromotion.getEndDate());
            existingPromotion.setType(newPromotion.getType());
            existingPromotion.setDiscountPercentage(newPromotion.getDiscountPercentage());
            existingPromotion.setBuyQuantity(newPromotion.getBuyQuantity());
            existingPromotion.setGetQuantity(newPromotion.getGetQuantity());
            save(existingPromotion);
        });
    }
}
