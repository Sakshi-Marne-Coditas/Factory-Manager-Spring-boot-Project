package com.FactoryManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutPreviewResponseDto {
    private List<CheckoutPreviewItemDto> items;
    private Double subtotal;
    private Double gstAmount;
    private Double deliveryCharge;
    private Double grandTotal;
}
