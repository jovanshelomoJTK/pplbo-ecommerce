package com.pplbo.orderservice.service;

import com.pplbo.orderservice.dto.CustomerRequest;
import com.pplbo.orderservice.dto.CustomerResponse;
import com.pplbo.orderservice.model.Customer;
import com.pplbo.orderservice.repository.CustomerRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

  @Autowired
  private final CustomerRepository customerRepository;
  
  public void createCustomer(CustomerRequest customerRequest) {
    Customer customer = new Customer();
    customer.setFirstName(customerRequest.firstName());
    customer.setLastName(customerRequest.lastName());

    customerRepository.save(customer);
  }

  public Optional<CustomerResponse> findById(Long id) {
    Optional<Customer> customerOptional = customerRepository.findById(id);
    
    if (customerOptional.isPresent()) {
      Customer customer = customerOptional.get();
      CustomerResponse customerResponse = new CustomerResponse(customer.getCustomerId(), customer.getFirstName(), customer.getLastName());
      return Optional.of(customerResponse);
    } else {
      return Optional.empty();
    }
  }

}
