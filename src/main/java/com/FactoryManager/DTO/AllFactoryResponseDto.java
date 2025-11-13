package com.FactoryManager.DTO;

import com.FactoryManager.Constatnts.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AllFactoryResponseDto {
    private Long id;
    private String name;
    private String location;
    private String plantheadName;

}
