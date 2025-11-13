package com.FactoryManager.DTO;

import com.FactoryManager.Constatnts.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CentralOfficeRequestResponseDto {
    private Long id;
    private String productName;
    private int requiredQuantity;
    private String status;
    private String image;


}