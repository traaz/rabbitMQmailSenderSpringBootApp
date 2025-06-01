package com.example.orderApp.controllers;

import com.example.orderApp.DTOs.AddOrderRequest;
import com.example.orderApp.repositories.OrderRepository;
import com.example.orderApp.services.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping
    public void addOrder(@RequestBody AddOrderRequest addOrderRequest){
        orderService.addOrder(addOrderRequest);
    }
}
