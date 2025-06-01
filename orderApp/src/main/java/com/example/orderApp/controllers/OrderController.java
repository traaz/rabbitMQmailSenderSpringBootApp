package com.example.orderApp.controllers;

import com.example.orderApp.DTOs.AddOrderRequest;
import com.example.orderApp.repositories.OrderRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @PostMapping
    public void addOrder(@RequestBody AddOrderRequest addOrderRequest){
        orderRepository.addOrder(addOrderRequest);
    }
}
