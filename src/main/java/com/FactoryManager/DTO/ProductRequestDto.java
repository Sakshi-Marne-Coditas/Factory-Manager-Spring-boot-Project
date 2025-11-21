package com.FactoryManager.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductRequestDto {
    @Pattern(
            regexp = "^[A-Za-z]+( [A-Za-z]+)*$",
            message = "Tool name must contain only alphabets without numbers or special characters"
    )
    private String name;

    private MultipartFile image;

    @Positive(message = "Value must be greater than 0")
    private int quantity;

    @NotNull(message = "Id cannot be null")
    @NotBlank(message = "Id cannot be blank")
    @Positive(message = "Value must be greater than 0")
    private Long categoryId;

    @NotNull(message = "price cannot be null")
    @NotBlank(message = "price cannot be blank")
    @Positive(message = "Value must be greater than 0")
    private Double price;

    @NotBlank(message = "Id cannot be blank")
    private String description;
}