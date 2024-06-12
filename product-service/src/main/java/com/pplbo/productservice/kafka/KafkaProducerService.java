package com.pplbo.productservice.kafka;

import com.pplbo.productservice.event.ProductOutOfStockEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC = "product-out-of-stock-topic";

    @Autowired
    private KafkaTemplate<String, ProductOutOfStockEvent> kafkaTemplate;

    public void sendMessage(ProductOutOfStockEvent event) {
        try {
            logger.info("Sending message: {}", event);
            kafkaTemplate.send(TOPIC, event);
        } catch (Exception e) {
            logger.error("Error sending message: {}", e.getMessage(), e);
        }
    }
}
