package com.pplbo.productservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

// import com.pplbo.promotionservice.command.ValidateFreeShippingPromotion;
import com.pplbo.productservice.event.ProductEvent;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, ProductEvent> kafkaTemplate;

    public void sendMessage(String topic, ProductEvent event) {
        try {
            logger.info("Sending message: {}", event);
            kafkaTemplate.send(topic, event);
        } catch (Exception e) {
            logger.error("Error sending message: {}", e.getMessage(), e);
        }
    }

}