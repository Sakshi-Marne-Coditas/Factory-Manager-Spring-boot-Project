package com.FactoryManager.DTO;

import com.FactoryManager.Constatnts.RequestStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateRequestStatusDto {
    @NotNull(message = "Status cannot be null")
    @NotBlank(message = "Status cannot be blank")
    private RequestStatus status;  // APPROVED / REJECTED
    private String reason;         // only required if REJECTED
}