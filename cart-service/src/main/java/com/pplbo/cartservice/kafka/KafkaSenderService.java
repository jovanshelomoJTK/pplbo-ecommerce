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
    private KafkaTemplate<String, ProductUpdated> kafkaTemplateProduct;

    @Autowired
    private KafkaTemplate<String, OrderApproved> kafkaTemplateOrder;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendProductUpdatedMessage(ProductUpdated event) {
        try {
            kafkaTemplateProduct.send("ProductUpdated", event);
        } catch (Exception e) {
            e.printStackTrace(); // Handle error appropriately
        }
    }

    public void sendOrderApprovedMessage(OrderApproved event) {
        try {
            kafkaTemplateOrder.send("OrderApproved", event);
        } catch (Exception e) {
            e.printStackTrace(); // Handle error appropriately
        }
    }
}
