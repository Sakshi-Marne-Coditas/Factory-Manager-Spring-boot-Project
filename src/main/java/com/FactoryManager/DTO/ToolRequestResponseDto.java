package com.FactoryManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToolRequestResponseDto {
    private Long requestId;
    private String workerName;
    private String factoryName;
    private String status;
    private LocalDateTime reqDate;

    private List<ToolRequestItemDetails> items;
}
