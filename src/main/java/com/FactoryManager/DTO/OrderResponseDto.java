package com.FactoryManager.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {

    private Long orderId;
    private String status;

    private Double totalAmount;

    private LocalDateTime orderDate;

    private List<OrderItemResponseDto> items;
}