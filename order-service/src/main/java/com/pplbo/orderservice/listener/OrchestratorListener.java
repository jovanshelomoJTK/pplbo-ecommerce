package main.java.com.pplbo.orderservice.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.pplbo.orderservice.event.OrderCreateEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.pplbo.orderservice.service.OrderService;

@Component
public class OrchestratorListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper; // Define or Autowire the ObjectMapper

    @KafkaListener(topics = "orderReplyEvent", groupId = "group_id")
    public void handleReply(String message) {
        // Tangani balasan dari service lain
        try {
            OrderCreateEvent event = objectMapper.readValue(message, OrderCreateEvent.class);
            orderService.handleReply(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
