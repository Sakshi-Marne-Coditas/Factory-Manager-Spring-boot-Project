package com.FactoryManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {
    private List<CartItemResponseDto> cartItems;
    private Double subtotal; // sum of all item subtotals
}
