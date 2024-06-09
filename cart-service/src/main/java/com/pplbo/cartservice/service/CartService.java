package com.pplbo.cartservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplbo.cartservice.model.Cart;
import com.pplbo.cartservice.dto.CartDTO;
import com.pplbo.cartservice.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getCart(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart addItemToCart(CartDTO cartDTO, String userId) {
        try {
            if (cartDTO == null || cartDTO.getProductId() == null || cartDTO.getPrice() == 0 || cartDTO.getPrice() <= 0
                    || cartDTO.getQuantity() == 0 || cartDTO.getQuantity() <= 0) {
                throw new IllegalArgumentException("Cart fields cannot be null, zero, or negative");
            }

            Cart cart = new Cart();
            cart.setPrice(cartDTO.getPrice());
            cart.setProductId(cartDTO.getProductId());
            cart.setQuantity(cartDTO.getQuantity());
            cart.setUserId(userId);

            return cartRepository.save(cart);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add item to cart: " + e.getMessage());
        }
    }

    public void removeItemFromCart(String userId, String productId) {
        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartRepository.delete(cart);
    }

     public Cart updateQuantityItemInCart(String userId, String productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be zero or negative");
        }

        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cart.setQuantity(quantity);
         
        return cartRepository.save(cart);
    }

    public Double getTotal(String userId) {
        List<Cart> cart = cartRepository.findByUserId(userId);
        return cart.stream().mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();
    }
}
