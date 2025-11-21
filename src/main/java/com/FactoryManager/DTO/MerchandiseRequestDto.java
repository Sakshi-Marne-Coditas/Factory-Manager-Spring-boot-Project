package com.FactoryManager.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MerchandiseRequestDto {
    @Pattern(
            regexp = "^[A-Za-z]+( [A-Za-z]+)*$",
            message = "Merchandise name must contain only alphabets without numbers or special characters"
    )
    private String name;

    @NotNull(message = "Required points cannot be null")
    @NotBlank(message = "Required points cannot be blank")
    @Positive(message = "Value must be greater than 0")
    private int requiredPts;

    private MultipartFile image;
}
