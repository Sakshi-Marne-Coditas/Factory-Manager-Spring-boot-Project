package com.FactoryManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AllFactoryResponseDto {
    private Long id;
    private String name;
    private String location;
    private String plantheadName;

}
