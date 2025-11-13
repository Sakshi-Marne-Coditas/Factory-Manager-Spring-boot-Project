package com.FactoryManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResponseStatus {
    private Long id;
    private String productName;
    private String reason;
    private String requestedBy;
    private String approveBy;
    private String status;
    private int reqQty;
}
