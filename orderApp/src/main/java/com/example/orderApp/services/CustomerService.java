package com.example.orderApp.services;


import com.example.orderApp.DTOs.AddCustomerRequest;
import com.example.orderApp.models.Customer;
import com.example.orderApp.repositories.CustomerRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @CacheEvict(value = "customer", allEntries = true)
    public void addCustomer(AddCustomerRequest addCustomerRequest){
      customerRepository.addCustomer(addCustomerRequest);
    }

    @Cacheable(value = "customer", key = "#root.methodName + #id")
    public Customer getCustomerById(int id){
       return customerRepository.getCustomerById(id);
    }
}
