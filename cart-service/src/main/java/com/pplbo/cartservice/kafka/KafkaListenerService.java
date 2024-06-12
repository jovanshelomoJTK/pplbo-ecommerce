package com.pplbo.cartservice.kafka;

import com.pplbo.cartservice.event.ProductUpdated;
import com.pplbo.cartservice.event.OrderApproved;
import com.pplbo.cartservice.model.Cart;
import com.pplbo.cartservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class KafkaListenerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerService.class);

    @Autowired
    private CartRepository cartRepository;

    @KafkaListener(topics = "ProductUpdated", groupId = "cart", containerFactory = "kafkaListenerContainerFactoryProduct")
    public void handleProductUpdated(ProductUpdated event) {
        try {
            logger.info("Consumed ProductUpdated event: {}", event);
            // Update product information in cart
            List<Cart> carts = cartRepository.findByProductId(event.getProductId());
            for (Cart cart : carts) {
                cart.setPrice(event.getPrice());
                // Update other fields if necessary
                cartRepository.save(cart);
            }
        } catch (Exception e) {
            logger.error("Error consuming ProductUpdated event", e);
        }
    }

    @KafkaListener(topics = "OrderApproved", groupId = "cart", containerFactory = "kafkaListenerContainerFactoryOrder")
    public void handleOrderApproved(OrderApproved event) {
        try {
            logger.info("Consumed OrderApproved event: {}", event);
            // Remove cart items based on order approval
            Optional<Cart> cart = cartRepository.findByUserIdAndProductId(event.getUserId(), event.getProductId());
            cart.ifPresent(cartRepository::delete);
        } catch (Exception e) {
            logger.error("Error consuming OrderApproved event", e);
        }
    }

    // Dummy methods to test listeners
    public void testHandleProductUpdated(ProductUpdated event) {
        handleProductUpdated(event);
    }

    public void testHandleOrderApproved(OrderApproved event) {
        handleOrderApproved(event);
    }
}
