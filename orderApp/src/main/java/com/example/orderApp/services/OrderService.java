package com.example.orderApp.services;

import com.example.orderApp.DTOs.AddOrderRequest;
import com.example.orderApp.DTOs.OrderMessage;
import com.example.orderApp.RabbitMQ.RabbitMQConfig;
import com.example.orderApp.models.Customer;
import com.example.orderApp.models.Product;
import com.example.orderApp.repositories.CustomerRepository;
import com.example.orderApp.repositories.OrderRepository;
import com.example.orderApp.repositories.ProductRepository;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private CustomerService customerService;
    private ProductService productService;
    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;

    public OrderService(OrderRepository orderRepository,
                        CustomerService customerService,
                        ProductService productService,
                        RabbitTemplate rabbitTemplate,
                        DirectExchange directExchange) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.productService = productService;
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }
    public void addOrder(AddOrderRequest addOrderRequest) {
        this.orderRepository.addOrder(addOrderRequest);

        Customer customer = customerService.getCustomerById(addOrderRequest.getCustomerId());
        Product product = productService.getProductById(addOrderRequest.getProductId());

        sendMessageToQueue(customer, product);
    }

    public void sendMessageToQueue(Customer customer, Product product){
        OrderMessage message= new OrderMessage();
        message.setEmail(customer.getEmail());
        message.setPrice(product.getPrice());
        message.setProductName(product.getProductName());

        // Mesaji kuyruga gonder. messagı otomatik jsona cevircek rabbitmq'daki jsonconverter
        rabbitTemplate.convertAndSend(
                directExchange.getName(),   //exchange bean oldugu icin bunu boyle de alabilirim. 2. yol RabbitMQConfig.EXCHANGE_NAME
                RabbitMQConfig.ROUTING_KEY, //routing key bean olmadigi icin bunu boyle aldim.
                message
        );
    }



}
