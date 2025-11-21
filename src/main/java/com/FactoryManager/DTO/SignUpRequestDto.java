package com.FactoryManager.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SignUpRequestDto {
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Username must start with a letter and contain only letters and numbers"
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

    @Pattern(
            regexp = "^[1-9][0-9]{9}$",
            message = "Phone number must be 10 digits, cannot start with 0, and contain only numbers"
    )
    private String phoneNo;

        @NotBlank(message = "Company name is required")
    private String companyName;

    @Pattern(
            regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[A-Z0-9]{1}Z[A-Z0-9]{1}$",
            message = "Enter a valid GST ID (e.g. 22AAAAA0000A1Z5)"
    )
    private String gstId;

        @NotBlank(message = "Company address is required")
    private String companyAddress;

        @NotBlank(message = "City is required")
    private String city;

        @NotBlank(message = "State is required")
    private String state;


    private int pinCode;

        @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
            message = "Password must contain at least one number and one special character"
    )
    private String password;


    private MultipartFile photo;

    @Override
    public String toString() {
        return "SignUpRequestDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", companyName='" + companyName + '\'' +
                ", gstId='" + gstId + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pinCode=" + pinCode +
                ", password='" + password + '\'' +
                '}';
    }
}