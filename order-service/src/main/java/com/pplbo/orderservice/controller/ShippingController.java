package com.pplbo.orderservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.pplbo.orderservice.service.ShippingService;
import com.pplbo.orderservice.dto.ShippingRequest;
import com.pplbo.orderservice.dto.ShippingResponse;

@RestController
@RequestMapping("/api/shipping")
@RequiredArgsConstructor
public class ShippingController {

  @Autowired
  private final ShippingService shippingService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String createShipping(@RequestBody ShippingRequest shippingRequest) {
    shippingService.createShipping(shippingRequest);
    return "Shipping created successfully";
  }

  @GetMapping("/{id}")
  public ResponseEntity<ShippingResponse> getShippingById(@PathVariable Long id) {
    Optional<ShippingResponse> shippingResponseOptional = shippingService.findById(id);

    if (shippingResponseOptional.isPresent()) {
      ShippingResponse shippingResponse = shippingResponseOptional.get();
      return ResponseEntity.ok(shippingResponse);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  
}
