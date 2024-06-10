package com.pplbo.orderservice.model;

import jakarta.persistence.*;
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long customerId;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    
    private String orderStatus;

    @Embedded
    private OrderDetails orderDetails;

    public Order(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
        this.orderStatus = OrderStatus.PENDING;
    }
    
    // @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<OrderItem> orderItems = new ArrayList<>();

    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "shipping_id", referencedColumnName = "shippingId")
    // private Shipping shipping;

    @PrePersist
    protected void onCreate() {
        orderDate = new Date();
    }

    public void paidOrder() {
        this.orderStatus = OrderStatus.PAID;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCELLED;
    }
}