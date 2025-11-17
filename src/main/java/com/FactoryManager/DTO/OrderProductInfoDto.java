package com.FactoryManager.DTO;

import lombok.Data;

@Data
public class OrderProductInfoDto {
    private String productName;
    private String productCategory;
    private int quantity;
    private String productImage;
}
