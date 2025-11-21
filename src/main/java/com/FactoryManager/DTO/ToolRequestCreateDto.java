package com.FactoryManager.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToolRequestCreateDto {
    @NotNull(message = " ID cannot be null")
    @Positive(message = "ID must be greater than 0")
    @NotBlank(message = "ID cannot be blank")
    private Long factoryId;

    @NotBlank(message = "items cannot be blank")
    @NotNull(message = " ID cannot be null")
    private List<ToolRequestItemDto> items;
}