package com.pplbo.orderservice.dto;

import java.util.Date;
import java.util.List;

public record OrderRequest(
    Date orderDate,
    String orderStatus,
    Double totalPrice,
    List<OrderLineItemRequest> orderLineItems,
    ShippingRequest shipping,
    CustomerRequest customer,
    Long paymentId
) {}
