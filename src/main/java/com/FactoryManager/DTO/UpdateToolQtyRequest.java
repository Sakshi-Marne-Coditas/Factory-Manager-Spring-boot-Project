package com.FactoryManager.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateToolQtyRequest {
    @NotNull(message = "Id cannot be null")
    @Positive(message = "ID must be greater than 0")
    @NotBlank(message = "Id cannot be blank")
    private Long toolId;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be greater than 0")
    private int increaseBy;
}
