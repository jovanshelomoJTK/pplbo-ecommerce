package com.pplbo.orderservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pplbo.orderservice.service.CustomerService;
import com.pplbo.orderservice.dto.CustomerRequest;
import com.pplbo.orderservice.dto.CustomerResponse;


@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

  @Autowired
  private final CustomerService customerService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String createCustomer(@RequestBody CustomerRequest customerRequest) {
    customerService.createCustomer(customerRequest);
    return "Customer created successfully";
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
    Optional<CustomerResponse> customerResponseOptional = customerService.findById(id);

    if (customerResponseOptional.isPresent()) {
      CustomerResponse customerResponse = customerResponseOptional.get();
      return ResponseEntity.ok(customerResponse);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}
