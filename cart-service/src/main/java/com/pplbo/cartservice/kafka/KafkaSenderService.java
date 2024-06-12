package com.pplbo.cartservice.kafka;

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

    public void sendProductUpdatedMessage(ProductUpdated event) {
        kafkaTemplateProduct.send("ProductUpdated", event);
    }

    public void sendOrderApprovedMessage(OrderApproved event) {
        kafkaTemplateOrder.send("OrderApproved", event);
    }
}
