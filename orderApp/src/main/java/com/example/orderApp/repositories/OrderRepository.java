package com.example.orderApp.repositories;

import com.example.orderApp.DTOs.AddOrderRequest;
import com.example.orderApp.DTOs.OrderMessage;
import com.example.orderApp.RabbitMQConfig;
import com.example.orderApp.models.Customer;
import com.example.orderApp.models.Order;
import com.example.orderApp.models.Product;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;

    public OrderRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                           CustomerRepository customerRepository,
                           ProductRepository productRepository,
                           RabbitTemplate rabbitTemplate,
                           DirectExchange directExchange) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.customerRepository= customerRepository;
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;

    }

    public void addOrder(AddOrderRequest addOrderRequest){
        //orderdate db tarafından eklencek
        String addOrderQuery = "INSERT INTO ORDERS (product_id,customer_id) VALUES (:productIdValue, :customerIdValue)";

        Map<String, Object> params = new HashMap<>();
        params.put("productIdValue", addOrderRequest.getProductId());
        params.put("customerIdValue", addOrderRequest.getCustomerId());

        MapSqlParameterSource insertParamSource = new MapSqlParameterSource(params);

        namedParameterJdbcTemplate.update(addOrderQuery, insertParamSource);

        Customer customer = customerRepository.getCustomerById(addOrderRequest.getCustomerId());
        Product product = productRepository.getCProductById(addOrderRequest.getProductId());

        /*ya da soyle yapabilirdik burada joinlerle birbirine baglayacak bir sql
        sonrasında order tablosuna son eklenen idyi alma ve sql'e where orderId olarak verme
        donen sonucu ordermessage icine mapleyerek sonuc dondurma ve mesaj iletme kuyruga*/

        /*SELECT o.id as order_id , o.order_date ,c.email, p.product_name, p.price
        FROM public.orders o
        inner join  public.products p on p.id = o.product_id
        inner join  public.customers c on c.id = o.customer_id WHERE o.id = ? */


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
