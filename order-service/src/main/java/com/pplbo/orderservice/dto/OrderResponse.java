package com.pplbo.orderservice.dto;

import java.util.Date;
import java.util.List;

public record OrderResponse(
    Long orderId,
    Date orderDate,
    String orderStatus,
    Double totalPrice,
    List<OrderLineItemResponse> orderLineItems,
    ShippingResponse shipping,
    CustomerResponse customer,
    Long paymentId
) {}
