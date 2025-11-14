package com.FactoryManager.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdCategoryDTO {
    @NotBlank(message = "Category name cannot be blank")
    private String name;
}
