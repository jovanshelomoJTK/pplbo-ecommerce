package com.pplbo.orderservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.pplbo.orderservice.command.ProductAvailableCommand;
import com.pplbo.orderservice.command.ProductOutOfStockCommand;
import com.pplbo.orderservice.service.OrderService;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = "product-available")
    public void listen(ProductAvailableCommand command) {
        orderService.approveOrder(command.getOrderId());
        logger.info("Receive message: {}", command);
    }

    @KafkaListener(topics = "product-out-of-stock")
    public void listen(ProductOutOfStockCommand command) {
        orderService.cancelOrder(command.getOrderId());
        logger.info("Receive message: {}", command);
    }

}
