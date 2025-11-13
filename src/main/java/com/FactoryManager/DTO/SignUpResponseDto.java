package com.FactoryManager.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignUpResponseDto {
    private Long id;
    private String username;
    private String email;

}
