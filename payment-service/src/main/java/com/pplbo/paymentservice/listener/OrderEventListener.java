package com.pplbo.paymentservice.listener;

import com.pplbo.paymentservice.event.OrderEvent;
import com.pplbo.paymentservice.model.Payment;
import com.pplbo.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
// import org.springframework.kafka.annotation.KafkaListener;

@Component
public class OrderEventListener {

    @Autowired
    private PaymentService paymentService;

    @EventListener
    public void handleOrderEvent(OrderEvent orderEvent) {
        Payment payment = new Payment();
        payment.setCustomerId(orderEvent.getCustomerId());
        payment.setOrderId(orderEvent.getOrderId());
        payment.setStatus("PENDING");
        payment.setPaymentMethod(orderEvent.getPaymentMethod());

        // paymentService.createPayment(payment);
    }

    // Jika Menggunakan Kafka
    // @KafkaListener(topics = "order-events", groupId = "payment-group")
    // public void handleOrderEvent(Map<String, Object> orderEvent) {
    //     Payment payment = new Payment();
    //     payment.setCustomerId((Integer) orderEvent.get("customerId"));
    //     payment.setOrderId((Integer) orderEvent.get("orderId"));
    //     payment.setTotal((Integer) orderEvent.get("total"));
    //     payment.setStatus("PENDING");
    //     payment.setPaymentMethod(PaymentMethod.valueOf(((String) orderEvent.get("paymentMethod")).toUpperCase().replace("/", "_").replace(" ", "_")));

    //     paymentService.createPayment(payment);
    // }
}
