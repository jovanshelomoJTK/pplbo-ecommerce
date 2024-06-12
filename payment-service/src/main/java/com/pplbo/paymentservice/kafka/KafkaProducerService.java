package com.pplbo.paymentservice.kafka;

import com.pplbo.paymentservice.event.PaymentEvent;
import com.pplbo.paymentservice.event.PaymentRequestEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentUpdateEvent(PaymentEvent event) {
        LOGGER.info(String.format("Payment status update event => %s", event.toString()));
        kafkaTemplate.send("paymentReplyTopic", event);
    }

    public void sendPaymentRequestEvent(PaymentRequestEvent event) {
        LOGGER.info(String.format("Payment request event => %s", event.toString()));
        kafkaTemplate.send("paymentRequestEvent", event);
    }
}
