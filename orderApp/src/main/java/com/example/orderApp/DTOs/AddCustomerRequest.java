package com.example.orderApp.DTOs;

public class AddCustomerRequest {
    private String email;

    public AddCustomerRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
