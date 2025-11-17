package com.FactoryManager.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderListWithProductsDto {

    private Long orderId;
    private String orderStatus;
    private LocalDateTime requestedOn;

    private String distributorName;
    private String distributorEmail;

    private List<OrderProductInfoDto> products;
}
