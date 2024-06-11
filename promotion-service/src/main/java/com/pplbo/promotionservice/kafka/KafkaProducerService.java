package com.pplbo.promotionservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.pplbo.promotionservice.event.PromotionEvent;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC = "promotion-topic";

    @Autowired
    private KafkaTemplate<String, PromotionEvent> kafkaTemplate;

    public void sendMessage(PromotionEvent event) {
        try {
            logger.info("Sending message: {}", event);
            kafkaTemplate.send(TOPIC, event);
        } catch (Exception e) {
            logger.error("Error sending message: {}", e.getMessage(), e);
        }
    }
}