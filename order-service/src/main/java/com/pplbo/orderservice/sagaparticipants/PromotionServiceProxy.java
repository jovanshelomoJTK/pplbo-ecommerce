package com.pplbo.orderservice.sagaparticipants;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

public class PromotionServiceProxy {
  CommandWithDestination validateFreeShippingPromotion(Long orderId, Long shippingId) {
    return send(new validateFreeShippingPromotion(orderId, shippingId))
            .to("promotionService")
            .build();
  }
  
  CommandWithDestination applyFreeShipping(Long orderId, Long shippingId) {
    return send(new applyFreeShipping(orderId, shippingId))
            .to("promotionService")
            .build();
  }
}
