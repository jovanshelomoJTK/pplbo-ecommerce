package com.pplbo.orderservice.service;

import com.pplbo.orderservice.dto.OrderRequest;
import com.pplbo.orderservice.dto.OrderResponse;
import com.pplbo.orderservice.dto.OrderItemRequest;
import com.pplbo.orderservice.dto.OrderItemResponse;
import com.pplbo.orderservice.dto.ShippingRequest;
import com.pplbo.orderservice.dto.ShippingResponse;
import com.pplbo.orderservice.model.Order;
import com.pplbo.orderservice.model.OrderItem;
import com.pplbo.orderservice.model.Shipping;
import com.pplbo.orderservice.model.ShippingStatus;
import com.pplbo.orderservice.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Optional<OrderResponse> findById(Long id) {
        return orderRepository.findById(id).map(this::convertToDto);
    }

    // public OrderResponse save(OrderRequest orderRequest) {
    //     Order order = convertToEntity(orderRequest);
    //     return convertToDto(orderRepository.save(order));
    // }

    public Order createOrder(OrderDetails orderDetails) {
        System.out.println("Order created event sent");
        Order order = Order.createOrder(orderDetails);
        return orderRepository.save(order);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    private OrderResponse convertToDto(Order order) {
        List<OrderItemResponse> orderItems = order.getOrderItems().stream()
                .map(item -> new OrderItemResponse(item.getOrderItemId(), item.getQuantity(), item.getProductId()))
                .collect(Collectors.toList());

        Shipping shipping = order.getShipping();
        ShippingResponse shippingResponse = new ShippingResponse(
                shipping.getShippingId(),
                shipping.getShippingName(),
                shipping.getShippingPrice(),
                shipping.getShippingStatus().name(),
                shipping.getShippingAddress()
        );

        return new OrderResponse(
                order.getOrderId(),
                order.getOrderDate(),
                order.getOrderStatus(),
                order.getTotalPrice(),
                orderItems,
                shippingResponse
        );
    }

    private Order convertToEntity(OrderRequest orderRequest) {
        List<OrderItem> orderItems = orderRequest.orderItems().stream()
                .map(item -> new OrderItem(null, item.quantity(), item.productId(), null))
                .collect(Collectors.toList());

        ShippingRequest shippingRequest = orderRequest.shipping();
        Shipping shipping = new Shipping(
                null,
                shippingRequest.shippingName(),
                shippingRequest.shippingPrice(),
                ShippingStatus.valueOf(shippingRequest.shippingStatus()),
                shippingRequest.shippingAddress()
        );

        Order order = new Order(
                null,
                orderRequest.orderDate(),
                orderRequest.orderStatus(),
                orderRequest.totalPrice(),
                orderItems,
                shipping
        );

        orderItems.forEach(item -> item.setOrder(order));
        return order;
    }

    public void approveOrder(Long orderId) {
        orderRepository.findById(orderId).get().paidOrder();
    }

    public void cancelOrder(Long orderId) {
        orderRepository.findById(orderId).get().cancelOrder();
    }
}
