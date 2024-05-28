package com.pplbo.promotionservice.controller;

import com.pplbo.promotionservice.model.Promotion;
import com.pplbo.promotionservice.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping
    public Promotion createPromotion(@RequestBody Promotion promotion) {
        promotionService.createPromotion(promotion);
        return promotion;
    }

    @GetMapping("/{id}")
    public Promotion getPromotionById(@PathVariable int id) {
        return promotionService.getPromotionById(id);
    }

    @GetMapping
    public List<Promotion> getAllActivePromotions() {
        return promotionService.getAllActivePromotion();
    }

    @DeleteMapping("/{id}")
    public void deletePromotion(@PathVariable int id) {
        promotionService.deletePromotion(id);
    }

    @PostMapping("/{id}/product")
    public void addProductToPromotion(@PathVariable int id, @RequestBody Product product) {
        promotionService.addProductToPromotion(id, product);
    }

    @DeleteMapping("/product/{id}")
    public void removeProductFromPromotion(@PathVariable int id) {
        promotionService.removeProductFromPromotion(id);
    }

    @PostMapping("/{id}/applyFreeShipping")
    public void applyFreeShipping(@PathVariable int id, @RequestBody Order order) {
        promotionService.applyFreeShipping(id, order);
    }

    @PostMapping("/{id}/schedule")
    public void schedulePromotion(@PathVariable int id, @RequestParam Date startDate, @RequestParam Date endDate) {
        promotionService.schedulePromotion(id, startDate, endDate);
    }
}
