package com.pplbo.cartservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pplbo.cartservice.event.ProductUpdated;
import com.pplbo.cartservice.event.OrderApproved;
import com.pplbo.cartservice.model.Cart;
import com.pplbo.cartservice.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KafkaListenerService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "ProductUpdated", groupId = "cart-group")
    public void handleProductUpdated(String message) {
        try {
            ProductUpdated event = objectMapper.readValue(message, ProductUpdated.class);

            // Update product information in cart
            List<Cart> carts = cartRepository.findByProductId(event.getProductId());
            for (Cart cart : carts) {
                cart.setPrice(event.getPrice());
                // Update other fields if necessary
                cartRepository.save(cart);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle error appropriately
        }
    }

    @KafkaListener(topics = "OrderApproved", groupId = "cart-group")
    public void handleOrderApproved(String message) {
        try {
            OrderApproved event = objectMapper.readValue(message, OrderApproved.class);

            // Remove cart items based on order approval
            Optional<Cart> cart = cartRepository.findByUserIdAndProductId(event.getUserId(), event.getProductId());
            cart.ifPresent(cartRepository::delete);
        } catch (Exception e) {
            e.printStackTrace(); // Handle error appropriately
        }
    }
}
