package com.FactoryManager.DTO;

import com.FactoryManager.Constatnts.Role;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserRequestDto {

    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Username must start with a letter and contain only alphabets"
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

    @NotNull(message = "Email cannot be null")
    private Role role;

    @Positive(message = "Factory ID must be greater than 0")
    private Long fcatory_id;

    private MultipartFile photo;
}
