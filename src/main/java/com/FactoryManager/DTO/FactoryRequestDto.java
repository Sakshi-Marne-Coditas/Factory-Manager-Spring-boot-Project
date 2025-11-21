package com.FactoryManager.DTO;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FactoryRequestDto {
    @Pattern(
            regexp = "^[A-Za-z]+( [A-Za-z]+)*$",
            message = "Factory name must contain only alphabets without numbers or special characters"
    )
    private String name;

    @Pattern(
            regexp = "^[A-Za-z]+( [A-Za-z]+)*$",
            message = "Factory location must contain only alphabets without numbers or special characters"
    )
    private String location;

    @Positive(message = "id can't be null")
    private Long plantHead_id;
}
