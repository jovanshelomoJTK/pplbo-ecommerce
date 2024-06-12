package com.pplbo.productservice.kafka;

import com.pplbo.productservice.event.ProductOutOfStockEvent;
import com.pplbo.productservice.event.OrderNotValidatedEvent;
import com.pplbo.productservice.event.OrderValidatedEvent;
import com.pplbo.productservice.event.ValidateStockEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC = "product-out-of-stock-topic";
    private static final String ORDER_VALIDATED_TOPIC = "OrderValidatedEvent";
    private static final String ORDER_NOT_VALIDATED_TOPIC = "OrderNotValidatedEvent";
    private static final String VALIDATE_STOCK_TOPIC = "ValidateStockEvent";

    @Autowired
    private KafkaTemplate<String, ProductOutOfStockEvent> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, OrderValidatedEvent> orderValidatedKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, OrderNotValidatedEvent> orderNotValidatedKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, ValidateStockEvent> validateStockKafkaTemplate;

    public void sendMessage(ProductOutOfStockEvent event) {
        try {
            logger.info("Sending message: {}", event);
            kafkaTemplate.send(TOPIC, event);
        } catch (Exception e) {
            logger.error("Error sending message: {}", e.getMessage(), e);
        }
    }

    public void sendOrderValidatedEvent(OrderValidatedEvent event) {
        try {
            logger.info("Sending Order Validated Event: {}", event);
            orderValidatedKafkaTemplate.send(ORDER_VALIDATED_TOPIC, event);
        } catch (Exception e) {
            logger.error("Error sending Order Validated Event: {}", e.getMessage(), e);
        }
    }
    
    public void sendOrderNotValidatedEvent(OrderNotValidatedEvent event) {
        try {
            logger.info("Sending Order Not Validated Event: {}", event);
            orderNotValidatedKafkaTemplate.send(ORDER_NOT_VALIDATED_TOPIC, event);
        } catch (Exception e) {
            logger.error("Error sending Order Not Validated Event: {}", e.getMessage(), e);
        }
    }

    public void sendValidateStockEvent(ValidateStockEvent event) {
        try {
            logger.info("Sending Validate Stock Event: {}", event);
            validateStockKafkaTemplate.send(VALIDATE_STOCK_TOPIC, event);
        } catch (Exception e) {
            logger.error("Error sending Validate Stock Event: {}", e.getMessage(), e);
        }
    }
}
