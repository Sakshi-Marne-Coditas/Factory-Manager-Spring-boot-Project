package com.FactoryManager.DTO;

import com.FactoryManager.Constants.ToolType;
import com.FactoryManager.Constants.UseCase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddToolRequestDto {
    @Pattern(
            regexp = "^[A-Za-z]+( [A-Za-z]+)*$",
            message = "tool must start with a letter and contain only letters"
    )
    @NotNull(message = "tool name cannot be null")
    @NotBlank(message = "tool name cannot be blank")
    private String toolName;

    @Pattern(
            regexp = "^[A-Za-z]+( [A-Za-z]+)*$",
            message = "Tool description must start with a letter and contain only letters, numbers, and spaces"
    )
    @NotNull(message = "Tool description cannot be null")
    private String toolDescription;


    private MultipartFile toolImage;

    @NotNull(message = "tool use case is required")
    private UseCase useCase;

    @NotNull(message = "tool type is required")
    private ToolType toolType;

    @NotNull(message = "tool threshold qty is required")
    private Integer thresholdQty;

    @NotNull(message = "Id is required")
    @Positive(message = "Id should be greater than zero")
    private Long categoryId;
}
