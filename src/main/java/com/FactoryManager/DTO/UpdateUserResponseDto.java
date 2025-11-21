package com.FactoryManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserResponseDto {
    private Long id;
    private String userName;
    private String email;
    private String photo;

}
