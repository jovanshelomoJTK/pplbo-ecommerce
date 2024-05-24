package com.pplbo.promotionservice.service;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {
    private PromotionService promotionService;

    @PostMapping
    public void createPromotion(@RequestBody Promotion promotion){
        System.out.println("Hello World from createPromotion endpoint");
    }

    @PutMapping
    public void updatePromotion(@RequestBody Promotion promotion){
        System.out.println("Hello World from updatePromotion endpoint");
    }

    @DeleteMapping
    public void deletePromotion(@RequestParam int id){
        System.out.println("Hello World from deletePromotion endpoint");
    }

    @GetMapping
    public List<Promotion> getAllPromotions(){
        System.out.println("Hello World from getAllPromotions endpoint");
        return null; // Dummy return, actual implementation required
    }

    @PostMapping("/product/{productID}")
    public void addProductToPromotion(@PathVariable int productID, @RequestBody Promotion promotion){
        System.out.println("Hello World from addProductToPromotion endpoint");
    }

    @DeleteMapping("/product/{productID}")
    public void removeProductFromPromotion(@PathVariable int productID, @RequestBody Promotion promotion){
        System.out.println("Hello World from removeProductFromPromotion endpoint");
    }

    @PutMapping("/schedule/{productID}")
    public void schedulePromotion(@PathVariable int productID, @RequestParam Date startDate, @RequestParam Date endDate){
        System.out.println("Hello World from schedulePromotion endpoint");
    }

    @PostMapping("/order/{orderID}")
    public void applyPromotionToOrder(@PathVariable int orderID, @RequestBody Order order){
        System.out.println("Hello World from applyPromotionToOrder endpoint");
    }
}
