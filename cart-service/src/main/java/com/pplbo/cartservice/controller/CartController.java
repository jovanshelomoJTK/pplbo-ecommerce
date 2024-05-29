package com.pplbo.cartservice.controller;

import com.pplbo.cartservice.model.Cart;
import com.pplbo.cartservice.service.CartService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/")
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @PostMapping("/add")
    public Cart addItemToCart(@RequestBody Cart cart) {
        return cartService.addItemToCart(cart);
    }

    @DeleteMapping("/remove/{id}")
    public void removeItemFromCart(@PathVariable Long id) {
        cartService.removeItemFromCart(id);
    }

    @PutMapping("/update")
    public Cart updateItemInCart(@RequestBody Cart cart) {
        return cartService.updateItemInCart(cart);
    }

    @PatchMapping("/update/{id}")
    public Cart partiallyUpdateItemInCart(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return cartService.partiallyUpdateItemInCart(id, updates);
    }

    @GetMapping("/total")
    public Double getTotal() {
        return cartService.getTotal();
    }
}
