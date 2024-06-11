package com.pplbo.promotionservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.pplbo.promotionservice.command.ValidateFreeShippingPromotion;
import com.pplbo.promotionservice.event.PromotionEvent;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, PromotionEvent> kafkaTemplate;

    public void sendMessage(PromotionEvent event) {
        try {
            logger.info("Sending message: {}", event);
            kafkaTemplate.send("promotion", event);
        } catch (Exception e) {
            logger.error("Error sending message: {}", e.getMessage(), e);
        }
    }

    @Autowired
    private KafkaTemplate<String, ValidateFreeShippingPromotion> kafkaCommandTemplate;

    public void sendReply(ValidateFreeShippingPromotion command) {
        try {
            logger.info("Sending reply: {}", command);
            kafkaCommandTemplate.send("promotion-response", command);
        } catch (Exception e) {
            logger.error("Error sending reply: {}", e.getMessage(), e);
        }
    }
}