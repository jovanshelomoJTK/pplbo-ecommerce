package com.pplbo.promotionservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pplbo.promotionservice.event.PromotionEvent;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC = "promotion-topic";

    @Autowired
    private KafkaTemplate<String, PromotionEvent> kafkaTemplate;

    public void sendMessage(PromotionEvent event) {
        logger.info("Sending message: {}", event);
        kafkaTemplate.send(TOPIC, event);
    }

    // public void sendPromotionEvent(Event event) {
    //     try {
    //         ObjectMapper objectMapper = new ObjectMapper();
    //         String message = objectMapper.writeValueAsString(event);
    //         kafkaTemplate.send(TOPIC, message);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}