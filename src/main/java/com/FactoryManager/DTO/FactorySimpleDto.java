package com.FactoryManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactorySimpleDto {
    private Long id;
    private String name;
    private String location;
}
