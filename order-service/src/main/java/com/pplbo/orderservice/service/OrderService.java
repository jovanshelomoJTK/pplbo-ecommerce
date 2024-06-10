package com.pplbo.orderservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pplbo.orderservice.dto.OrderRequest;
import com.pplbo.orderservice.dto.OrderResponse;
import com.pplbo.orderservice.dto.OrderLineItemResponse;
import com.pplbo.orderservice.dto.OrderLineItemRequest;
import com.pplbo.orderservice.dto.ShippingResponse;
import com.pplbo.orderservice.dto.ShippingRequest;
import com.pplbo.orderservice.dto.CustomerResponse;
import com.pplbo.orderservice.dto.CustomerRequest;
import com.pplbo.orderservice.model.Order;
import com.pplbo.orderservice.model.OrderLineItem;
import com.pplbo.orderservice.model.Shipping;
import com.pplbo.orderservice.model.Customer;
import com.pplbo.orderservice.repository.OrderRepository;
// import com.pplbo.orderservice.client.PaymentClient;
import com.pplbo.orderservice.event.OrderCreateEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderService {

    private static final String TOPIC = "test-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
    private OrderRepository orderRepository;

    // @Autowired
    // private PaymentClient paymentClient;

    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Optional<OrderResponse> findById(Long id) {
        return orderRepository.findById(id).map(this::convertToDto);
    }

    public OrderResponse save(OrderRequest orderRequest) {
        // Boolean isPaymentReqExist = paymentClient.isPaymentExist(orderRequest.paymentId());

        Order order = convertToEntity(orderRequest);
        return convertToDto(orderRepository.save(order));

        // if (isPaymentReqExist) {
        //     Order order = convertToEntity(orderRequest);
        //     return convertToDto(orderRepository.save(order));
        // } else {
        //     throw new RuntimeException("Payment request does not exist");
        // }
    }

    public void createEventOrder(OrderCreateEvent event) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    //olah Response
    private OrderResponse convertToDto(Order order) {
        List<OrderLineItemResponse> orderLineItems = order.getOrderLineItems().stream()
            .map(item -> new OrderLineItemResponse(item.getOrderLineItemId(), item.getQuantity(), item.getProductId()))
            .collect(Collectors.toList());

        Shipping shipping = order.getShipping();
        ShippingResponse shippingResponse = new ShippingResponse(
            shipping.getShippingId(),
            shipping.getShippingName(),
            shipping.getShippingPrice(),
            shipping.getShippingStatus(),
            shipping.getShippingAddress()
        );

        Customer customer = order.getCustomer();
        CustomerResponse customerResponse = new CustomerResponse(
            customer.getCustomerId(),
            customer.getFirstName(),
            customer.getLastName()
        );

        return new OrderResponse(
            order.getOrderId(),
            order.getOrderDate(),
            order.getOrderStatus(),
            order.getTotalPrice(),
            orderLineItems,
            shippingResponse,
            customerResponse,
            order.getPaymentId()
        );
    }

    //olah Request
    private Order convertToEntity(OrderRequest orderRequest) {
        List<OrderLineItem> orderLineItems = orderRequest.orderLineItems().stream()
            .map(item -> new OrderLineItem(null, item.quantity(), item.productId(), null))
            .collect(Collectors.toList());
    
        ShippingRequest shippingRequest = orderRequest.shipping();
        Shipping shipping = new Shipping(
            null, //id shipping set null (karna auto increment di DB)
            shippingRequest.shippingName(),
            shippingRequest.shippingPrice(),
            shippingRequest.shippingStatus(),
            shippingRequest.shippingAddress()
        );
    
        // Buat objek Customer berdasarkan nama pelanggan dari request body
        CustomerRequest customerRequest = orderRequest.customer();
        Customer customer = new Customer(
            null, //id customer set null (karna auto increment di DB)
            customerRequest.firstName(),
            customerRequest.lastName()
        );
    
        Order order = new Order(
            null, //id order set null (karna auto increment di DB)
            orderRequest.orderDate(),
            orderRequest.orderStatus(),
            orderRequest.totalPrice(),
            orderLineItems,
            shipping,
            customer,
            orderRequest.paymentId()
        );
    
        orderLineItems.forEach(item -> item.setOrder(order));
        return order;
    }
}
