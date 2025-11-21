package com.FactoryManager.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToolRequestItemDto {
    @NotNull(message = "Id cannot be null")
    @Positive(message = "ID must be greater than 0")
    @NotBlank(message = "Tool name cannot be blank")
    private Long toolId;

    @Positive( message = "Quantity must be greater than 0")
    @NotNull(message = "Id cannot be null")
    @NotBlank(message = "Tool name cannot be blank")
    private int quantity;
}
