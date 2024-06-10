package com.pplbo.cartservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pplbo.cartservice.event.ProductUpdated;
import com.pplbo.cartservice.event.OrderApproved;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSenderService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendProductUpdatedMessage(ProductUpdated event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("ProductUpdated", message);
        } catch (Exception e) {
            e.printStackTrace(); // Handle error appropriately
        }
    }

    public void sendOrderApprovedMessage(OrderApproved event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("OrderApproved", message);
        } catch (Exception e) {
            e.printStackTrace(); // Handle error appropriately
        }
    }
}
