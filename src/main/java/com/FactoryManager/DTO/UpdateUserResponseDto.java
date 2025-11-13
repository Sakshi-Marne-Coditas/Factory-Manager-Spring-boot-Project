package com.FactoryManager.DTO;

import lombok.Data;

@Data
public class UpdateUserResponseDto {
    private Long id;
    private String userName;
    private String email;
    private String photo;

}
