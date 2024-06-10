package com.pplbo.orderservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.pplbo.orderservice.dto.ShippingRequest;
import com.pplbo.orderservice.dto.ShippingResponse;
import com.pplbo.orderservice.model.Shipping;
import com.pplbo.orderservice.repository.ShippingRepository;

@Service
@RequiredArgsConstructor
public class ShippingService {

  @Autowired
  private final ShippingRepository shippingRepository;

  public void createShipping(ShippingRequest shippingRequest) {
    Shipping shipping = new Shipping();
    shipping.setShippingName(shippingRequest.shippingName());
    shipping.setShippingPrice(shippingRequest.shippingPrice());
    shipping.setShippingStatus(shippingRequest.shippingStatus());
    shipping.setShippingAddress(shippingRequest.shippingAddress());

    shippingRepository.save(shipping);
  }

  public Optional<ShippingResponse> findById(Long id) {
    Optional<Shipping> shippingOptional = shippingRepository.findById(id);

    if (shippingOptional.isPresent()) {
      Shipping shipping = shippingOptional.get();
      ShippingResponse shippingResponse = new ShippingResponse(shipping.getShippingId(), shipping.getShippingName(), shipping.getShippingPrice(), shipping.getShippingStatus(), shipping.getShippingAddress());
      return Optional.of(shippingResponse);
    }
    else {
      return Optional.empty();
    }
  }
  
}
