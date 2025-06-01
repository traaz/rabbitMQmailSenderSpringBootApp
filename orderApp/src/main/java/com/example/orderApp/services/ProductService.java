package com.example.orderApp.services;

import com.example.orderApp.DTOs.AddCustomerRequest;
import com.example.orderApp.DTOs.AddProductRequest;
import com.example.orderApp.models.Customer;
import com.example.orderApp.models.Product;
import com.example.orderApp.repositories.CustomerRepository;
import com.example.orderApp.repositories.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @CacheEvict(value = "product", allEntries = true)
    public void addProduct(AddProductRequest addProductRequest){
        productRepository.addProduct(addProductRequest);
    }

    @Cacheable(value = "product", key = "#root.methodName + #id")
    public Product getProductById(int id){
        return productRepository.getProductById(id);
    }
}
