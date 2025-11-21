package com.FactoryManager.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AdCategoryDTO {
    @Pattern(
            regexp = "^[A-Za-z]+( [A-Za-z]+)*$",
            message = "Category name must start with a letter and contain only letters"
    )
    @NotNull(message = "Category name cannot be null")
    @NotBlank(message = "Category name cannot be blank")
    private String name;

    @NotNull(message = "Category of  cannot be null. It should be either product or tool")
    @NotBlank(message = "Category of cannot be blank. It should be either product or tool")
    private String categoryOf;
}
