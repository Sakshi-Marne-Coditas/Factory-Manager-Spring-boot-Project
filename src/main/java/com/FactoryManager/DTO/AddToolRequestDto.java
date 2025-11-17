package com.FactoryManager.DTO;

import com.FactoryManager.Constatnts.ToolType;
import com.FactoryManager.Constatnts.UseCase;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddToolRequestDto {

    private String toolName;
    private String toolDescription;

    private MultipartFile toolImage;

    private UseCase useCase;       // string from frontend → enum automatically (Perishable / Non-Perishable)
    private ToolType toolType;     // string from frontend → enum automatically (Expensive / Normal)

    private Integer thresholdQty;

    private Long categoryId;
}
