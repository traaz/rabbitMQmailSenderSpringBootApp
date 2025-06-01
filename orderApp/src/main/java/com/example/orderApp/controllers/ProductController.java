package com.example.orderApp.controllers;

import com.example.orderApp.DTOs.AddProductRequest;
import com.example.orderApp.repositories.OrderRepository;
import com.example.orderApp.repositories.ProductRepository;
import com.example.orderApp.services.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public void addProduct(@RequestBody AddProductRequest addProductRequest){
        productService.addProduct(addProductRequest);
    }
}
