package com.pplbo.productservice.kafka;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.pplbo.productservice.service.ProductService;
import com.pplbo.productservice.command.OrderCreated;
import com.pplbo.productservice.dto.OrderItem;

import org.springframework.messaging.handler.annotation.Payload;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private ProductService productService;

    @KafkaListener(topics = "order-created", groupId = "product-service")
    public void listen(@Payload OrderCreated event) {
        System.out.println("Received event: " + event);
    }

    // public void listen(OrderCreated command) {
    //     List<Long> productIds = command.getOrder().getOrderItems()
    //             .stream()
    //             .map(OrderItem::getProductId)
    //             .map(Long::valueOf)
    //             .collect(Collectors.toList());

    //     boolean isStockAvailable = productService.productsStockAvailable(productIds);

    //     if (isStockAvailable) {
    //         productService.decreaseStock(command.getOrderId(), productIds);
    //     } else {
    //         productService.reverseStock(command.getOrderId(), productIds);
    //     }

    //     logger.info("Receive message: {}", command);
    // }

}

