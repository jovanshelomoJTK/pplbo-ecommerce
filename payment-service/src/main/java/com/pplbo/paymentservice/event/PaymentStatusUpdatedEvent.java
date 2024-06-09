package com.pplbo.paymentservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusUpdatedEvent {
    private Long orderId;
    private String status;
}
