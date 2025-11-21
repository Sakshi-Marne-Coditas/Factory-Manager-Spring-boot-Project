package com.FactoryManager.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddBayReqDto {
    @NotBlank(message = "Quantity is required")
    @Positive(message = "Quantity should be greater than zero")
    Long factory_id;

    @Pattern(
            regexp = "^[A-Za-z]+( [A-Za-z]+)*$",
            message = "Bay name must start with a letter and contain only letters"
    )
    @NotNull(message = "Bay name cannot be null")
    @NotBlank(message = "Bay name cannot be blank")
    String bay_Name;
}
