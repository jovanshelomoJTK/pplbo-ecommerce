package com.pplbo.orderservice.sagaparticipants;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

public class CartServiceProxy {
    CommandWithDestination removeProductFromCart(Long orderId, Long customerId) {
    return send(new removeProductFromCart(orderId, customerId))
            .to("cartService")
            .build();
  }
}
