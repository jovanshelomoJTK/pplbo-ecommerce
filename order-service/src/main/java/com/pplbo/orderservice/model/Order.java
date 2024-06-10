package com.pplbo.orderservice.model;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.util.Date;

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

    // private Long customerId;

    // private String shippingAddress;

    // private Double orderTotal;

    @PrePersist
    protected void onCreate() {
        orderDate = new Date();
    }

    @Embedded
    private OrderDetails orderDetails;

    public Order() {
    }

    public Order(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
        this.orderStatus = OrderStatus.PENDING;
    }

    public static Order createOrder(OrderDetails orderDetails) {
        return new Order(orderDetails);
    }

    public void paidOrder() {
        this.orderStatus = OrderStatus.PAID;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCELLED;
    }



}
