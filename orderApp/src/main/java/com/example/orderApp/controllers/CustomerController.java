package com.example.orderApp.controllers;

import com.example.orderApp.DTOs.AddCustomerRequest;
import com.example.orderApp.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public void addCustomer(@RequestBody AddCustomerRequest addCustomerRequest){
        customerRepository.addCustomer(addCustomerRequest);
    }
}
