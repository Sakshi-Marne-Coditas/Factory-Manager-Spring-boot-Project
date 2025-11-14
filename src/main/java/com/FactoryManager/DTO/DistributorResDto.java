package com.FactoryManager.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributorResDto {

    private Long id;
    private String username;
    private String email;
    private String companyName;
    private String state;

}
