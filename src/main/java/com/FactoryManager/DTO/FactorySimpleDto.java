package com.FactoryManager.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactorySimpleDto {

    @NotNull(message = "Required points cannot be null")
    @NotBlank(message = "Required points cannot be blank")
    @Positive(message = "Value must be greater than 0")
    private Long id;

    @Pattern(
            regexp = "^[A-Za-z]+$",
            message = "Tool name must contain only alphabets without numbers or special characters"
    )
    private String name;

    @Pattern(
            regexp = "^[A-Za-z]+$",
            message = "Tool name must contain only alphabets without numbers or special characters"
    )
    private String location;
}
