package com.example.SimpleSpringApp.web.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpsertOrderRequest {
    private Long clientId;
    private String product;
    private BigDecimal cost;
}
