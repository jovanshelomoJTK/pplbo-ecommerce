package com.pplbo.orderservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pplbo.orderservice.common.OrderDetails;

@Table(name = "Orders")
@Entity
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @PrePersist
    protected void onCreate() {
        orderDate = new Date();
    }

    private Long customerId;

    private Double orderTotal;

    private String shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<OrderItem> orderItems;

    public Order() {
    }

    public Order(Long customerId, Double orderTotal, String shippingAddress, Set<OrderItem> orderItems) {
        this.orderStatus = OrderStatus.PENDING;
        this.customerId = customerId;
        this.orderTotal = orderTotal;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(this);
        }
    }

    public static Order createOrder(OrderDetails orderDetails) {
        return new Order(orderDetails.getCustomerId(), orderDetails.getOrderTotal(), orderDetails.getShippingAddress(), orderDetails.getOrderItems());
    }

    public void paidOrder() {
        this.orderStatus = OrderStatus.PAID;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCELLED;
    }

}
