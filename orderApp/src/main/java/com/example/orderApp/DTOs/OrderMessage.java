package com.example.orderApp.DTOs;

import java.io.Serializable;
import java.time.LocalDate;

public class OrderMessage implements Serializable {
    private String email;
    private String productName;
    private Double price;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
