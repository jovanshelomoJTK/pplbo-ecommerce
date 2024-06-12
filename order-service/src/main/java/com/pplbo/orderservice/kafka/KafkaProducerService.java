package com.pplbo.orderservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

// import com.pplbo.promotionservice.command.ValidateFreeShippingPromotion;
import com.pplbo.orderservice.event.OrderEvent;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendMessage(String topic, OrderEvent event) {
        try {
            logger.info("Sending message: {}", event);
            kafkaTemplate.send(topic, event);
        } catch (Exception e) {
            logger.error("Error sending message: {}", e.getMessage(), e);
        }
    }

    // @Autowired
    // private KafkaTemplate<String, ValidateFreeShippingPromotion> kafkaCommandTemplate;

    // public void sendReply(ValidateFreeShippingPromotion command) {
    //     try {
    //         logger.info("Sending reply: {}", command);
    //         kafkaCommandTemplate.send("promotion-response", command);
    //     } catch (Exception e) {
    //         logger.error("Error sending reply: {}", e.getMessage(), e);
    //     }
    // }
}