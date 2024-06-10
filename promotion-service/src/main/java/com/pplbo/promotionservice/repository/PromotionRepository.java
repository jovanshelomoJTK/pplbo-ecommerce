package com.pplbo.promotionservice.repository;

import com.pplbo.promotionservice.model.Promotion;
import com.pplbo.promotionservice.service.PromotionStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
      List<Promotion> findByStatus(PromotionStatus status);


}
