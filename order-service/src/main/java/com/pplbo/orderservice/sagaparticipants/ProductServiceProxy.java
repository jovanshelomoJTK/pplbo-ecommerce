package com.pplbo.orderservice.sagaparticipants;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

public class ProductServiceProxy {
  CommandWithDestination validateStock(Long orderId, Long productId) {
    return send(new ValidateStockCommand(orderId, productId))
            .to("productService")
            .build();
  }
}
