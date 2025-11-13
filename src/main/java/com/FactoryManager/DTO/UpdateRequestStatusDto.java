package com.FactoryManager.DTO;

import com.FactoryManager.Constatnts.RequestStatus;
import lombok.Data;

@Data
public class UpdateRequestStatusDto {
    private RequestStatus status;  // APPROVED / REJECTED
    private String reason;         // only required if REJECTED
}