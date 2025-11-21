package com.FactoryManager.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CentralOfficerReqDto {
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z0-9 ]*$",
            message = "Username must start with a letter and contain only letters, numbers or spaces"
    )
    @NotNull(message = "username cannot be null")
    @NotBlank(message = "name cannot be blank")
    private String username;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    @NotBlank(message = "email cannot be blank")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Email must be valid and contain @ and a proper domain like .com"
    )
    private String email;

    private MultipartFile photo;
}
