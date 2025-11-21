package com.FactoryManager.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

    @Data
    public class UpdateUserRequestDto {
        @NotNull(message = "Id cannot be null")
        @Positive(message = "ID must be greater than 0")
        private Long id;

        @Pattern(
                regexp = "^[A-Za-z][A-Za-z0-9 ]*$",
                message = "Username must start with a letter and contain only letters, numbers or spaces"
        )
        private String username;

        @Email(message = "Invalid email format")
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                message = "Email must be valid and contain @ and a proper domain like .com"
        )
        private String email;

        private MultipartFile photo;
    }

