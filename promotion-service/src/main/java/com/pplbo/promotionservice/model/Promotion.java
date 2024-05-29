package com.pplbo.promotionservice.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import com.pplbo.promotionservice.service.PromotionStatus;
import com.pplbo.promotionservice.service.PromotionType;

@Entity
@Table(name = "Promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_promotion;

    @Column(nullable = true)
    private Date startDate;

    @Column(nullable = true)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PromotionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PromotionType type;

    private double discountPercentage;

    @ElementCollection
    @CollectionTable(name = "promotion_products", joinColumns = @JoinColumn(name = "promotion_id"))
    @Column(name = "product_id")
    private List<Long> productIds;

    public Promotion() {
    }

    public Promotion(Long id_promotion, Date startDate, Date endDate, PromotionStatus status, PromotionType type, List<Long> productIds, double discountPercentage) {
        this.id_promotion = id_promotion;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.type = type;
        this.productIds = productIds;
        this.discountPercentage = discountPercentage;
    }

    public Long getId_promotion() {
        return id_promotion;
    }

    public void setId_promotion(Long id_promotion) {
        this.id_promotion = id_promotion;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public PromotionStatus getStatus() {
        return status;
    }

    public void setStatus(PromotionStatus status) {
        this.status = status;
    }

    public PromotionType getType() {
        return type;
    }

    public void setType(PromotionType type) {
        this.type = type;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void applyPromotion(Long productId) {
        getProductIds().add(productId);
    }

    public void removeProduct(Long productId) {
        getProductIds().remove(productId);
    }
}
