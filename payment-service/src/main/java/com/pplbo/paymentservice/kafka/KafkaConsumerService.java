package com.pplbo.paymentservice.kafka;

import com.pplbo.paymentservice.event.OrderCreatedEvent;
import com.pplbo.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "order-topic", groupId = "payment")
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {
        LOGGER.info(String.format("Order created event received: %s", event));
        paymentService.processPayment(event);
    }
}
