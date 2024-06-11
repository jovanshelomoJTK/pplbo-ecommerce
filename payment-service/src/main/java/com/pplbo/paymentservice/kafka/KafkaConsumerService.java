package com.pplbo.paymentservice.kafka;

import com.pplbo.paymentservice.event.PaymentRequestEvent;
import com.pplbo.paymentservice.event.PaymentEvent;
import com.pplbo.paymentservice.model.Payment;
import com.pplbo.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "paymentRequestEvent", groupId = "payment-group")
    public void consumePaymentRequestEvent(String message) {
        LOGGER.info(String.format("Payment request event received: %s", message));
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PaymentRequestEvent event = objectMapper.readValue(message, PaymentRequestEvent.class);
            paymentService.processPayment(event);
        } catch (Exception e) {
            LOGGER.error("Failed to process payment request event", e);
        }
    }
}
