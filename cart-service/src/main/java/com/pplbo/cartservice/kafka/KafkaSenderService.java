package com.pplbo.cartservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pplbo.cartservice.event.ProductUpdated;
import com.pplbo.cartservice.event.OrderApproved;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSenderService.class);

    @Autowired
    private KafkaTemplate<String, ProductUpdated> kafkaTemplateProduct;

    @Autowired
    private KafkaTemplate<String, OrderApproved> kafkaTemplateOrder;

    public void sendProductUpdatedMessage(ProductUpdated event) {
        LOGGER.info(String.format("Product updated event => %s", event.toString()));
        kafkaTemplateProduct.send("ProductUpdated", event);
    }

    public void sendOrderApprovedMessage(OrderApproved event) {
        LOGGER.info(String.format("Order approved event => %s", event.toString()));
        kafkaTemplateOrder.send("OrderApproved", event);
    }
}
