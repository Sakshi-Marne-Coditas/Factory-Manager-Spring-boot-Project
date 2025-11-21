package com.FactoryManager.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToolRequestItemDetails {
    @Min(value = 0, message = "Id must be greater than 0")
    @NotNull(message = "Id cannot be null")
    @NotBlank(message = "Tool name cannot be blank")
    private Long toolId;

    @Pattern(
            regexp = "^[A-Za-z]+$",
            message = "Tool name must contain only alphabets without numbers or special characters"
    )
    private String toolName;

    @NotNull(message = "ToolType cannot be null")
    @NotBlank(message = "Tool name cannot be blank")
    private String toolType;  // EXPENSIVE / NORMAL

    @Min(value = 0, message = "Quantity must be greater than 0")
        @NotNull(message = "Quantity cannot be null")
    private int quantity;

    @NotNull(message = "UseCase cannot be null")
    @NotBlank(message = "Tool name cannot be blank")
    private String useCase;
}
