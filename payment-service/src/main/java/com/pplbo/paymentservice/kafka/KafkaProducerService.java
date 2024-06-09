// package com.pplbo.paymentservice.kafka;

// // import com.pplbo.paymentservice.event.PaymentStatusUpdatedEvent;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.stereotype.Service;

// @Service
// public class KafkaProducerService {

//     private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
//     private static final String PAYMENT_STATUS_UPDATED_TOPIC = "payment_status_updated";

//     @Autowired
//     private KafkaTemplate<String, Object> kafkaTemplate;

//     public void sendPaymentStatusUpdateEvent(PaymentStatusUpdatedEvent event) {
//         LOGGER.info(String.format("Sending payment status update event: %s", event));
//         kafkaTemplate.send(PAYMENT_STATUS_UPDATED_TOPIC, event);
//     }
// }
