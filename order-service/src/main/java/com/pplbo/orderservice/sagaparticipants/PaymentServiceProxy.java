package com.pplbo.orderservice.sagaparticipants;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

public class PaymentServiceProxy {
  CommandWithDestination createPayment(Long orderId) {
    return send(new createPayment(orderId))
            .to("paymentService")
            .build();
  }
}
