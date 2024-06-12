package com.pplbo.cartservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplbo.cartservice.event.ProductUpdated;
import com.pplbo.cartservice.event.OrderApproved;
import com.pplbo.cartservice.kafka.KafkaListenerService;
import com.pplbo.cartservice.model.Cart;
import com.pplbo.cartservice.dto.CartDTO;
import com.pplbo.cartservice.repository.CartRepository;

@Service
public class CartService {

    private static final int MAX_CART_ITEMS = 100;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private KafkaListenerService kafkaListenerService;

    public List<Cart> getCart(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart addItemToCart(CartDTO cartDTO, String userId) {
        if (cartDTO == null || cartDTO.getProductId() == null || cartDTO.getPrice() <= 0 || cartDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Cart fields cannot be null, zero, or negative");
        }

        List<Cart> currentCartItems = cartRepository.findByUserId(userId);
        int totalItemsInCart = currentCartItems.stream().mapToInt(Cart::getQuantity).sum();

        if (totalItemsInCart + cartDTO.getQuantity() > MAX_CART_ITEMS) {
            throw new IllegalArgumentException("Cannot add more than " + MAX_CART_ITEMS + " items to the cart");
        }

        Cart cart = new Cart();
        cart.setPrice(cartDTO.getPrice());
        cart.setProductId(cartDTO.getProductId());
        cart.setQuantity(cartDTO.getQuantity());
        cart.setUserId(userId);

        return cartRepository.save(cart);
    }

    public void removeItemFromCart(String userId, String productId) {
        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartRepository.delete(cart);
    }

    public void removeAllItemsFromCart(String userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        if (carts.isEmpty()) {
            throw new RuntimeException("No cart items found for user");
        }
        cartRepository.deleteAll(carts);
    }

    public Cart updateQuantityItemInCart(String userId, String productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        List<Cart> currentCartItems = cartRepository.findByUserId(userId);
        int totalItemsInCart = currentCartItems.stream().mapToInt(Cart::getQuantity).sum();

        if (totalItemsInCart - cart.getQuantity() + quantity > MAX_CART_ITEMS) {
            throw new IllegalArgumentException("Cannot have more than " + MAX_CART_ITEMS + " items in the cart");
        }

        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    public Double getTotal(String userId) {
        List<Cart> cart = cartRepository.findByUserId(userId);
        return cart.stream().mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();
    }

    // Methods to test Kafka listeners
    public void testHandleProductUpdated(ProductUpdated event) {
        kafkaListenerService.testHandleProductUpdated(event);
    }

    public void testHandleOrderApproved(OrderApproved event) {
        kafkaListenerService.testHandleOrderApproved(event);
    }
    
    
}
