package com.FactoryManager.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SignUpRequestDto {
    //    @Pattern(
//            regexp = "^[A-Za-z]+(?: [A-Za-z]+)*$",
//            message = "Full name must contain only alphabets and spaces"
//    )
    private String username;

    //    @NotBlank(message = "Email is required")
//    @Email(message = "Enter a valid email address")
    private String email;

    //    @NotBlank(message = "Phone number is required")
//    @Pattern(
//            regexp = "^[6-9]\\d{9}$",
//            message = "Enter a valid 10-digit Indian phone number"
//    )
    private String phoneNo;

    //    @NotBlank(message = "Company name is required")
    private String companyName;

    //    @Pattern(
//            regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$",
//            message = "Enter a valid GST ID (e.g. 22AAAAA0000A1Z5)"
//    )
    private String gstId;

    //    @NotBlank(message = "Company address is required")
    private String companyAddress;

    //    @NotBlank(message = "City is required")
    private String city;

    //    @NotBlank(message = "State is required")
    private String state;


    private int pinCode;

    //    @NotBlank(message = "Password is required")
//    @Size(min = 6, message = "Password must be at least 6 characters long")
//    @Pattern(
//            regexp = "^(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
//            message = "Password must contain at least one number and one special character"
//    )
    private String password;

    // ✅ Multipart file for image (optional)
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