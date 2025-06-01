package com.example.orderApp.controllers;

import com.example.orderApp.DTOs.AddCustomerRequest;
import com.example.orderApp.repositories.CustomerRepository;
import com.example.orderApp.services.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public void addCustomer(@RequestBody AddCustomerRequest addCustomerRequest){
        customerService.addCustomer(addCustomerRequest);
    }
}
