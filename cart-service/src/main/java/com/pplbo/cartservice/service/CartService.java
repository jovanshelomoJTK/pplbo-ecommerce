package com.pplbo.cartservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplbo.cartservice.model.Cart;
import com.pplbo.cartservice.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart addItemToCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void removeItemFromCart(Long id) {
        cartRepository.deleteById(id);
    }

    public Cart updateItemInCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart partiallyUpdateItemInCart(Long id, Map<String, Object> updates) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));

        if (updates.containsKey("quantity")) {
            cart.setQuantity((Integer) updates.get("quantity"));
        }
        if (updates.containsKey("price")) {
            cart.setPrice((Double) updates.get("price"));
        }

        return cartRepository.save(cart);
    }

    public Double getTotal() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream().mapToDouble(cart -> cart.getPrice() * cart.getQuantity()).sum();
    }
}
