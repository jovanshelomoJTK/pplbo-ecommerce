package com.pplbo.orderservice.sagas.createorder;

import com.pplbo.orderservice.common.OrderDetails;

public class CreateOrderSagaData {
    
    private OrderDetails orderDetails;
    private Long orderId;

    public CreateOrderSagaData(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public CreateOrderSagaData() {
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

}
