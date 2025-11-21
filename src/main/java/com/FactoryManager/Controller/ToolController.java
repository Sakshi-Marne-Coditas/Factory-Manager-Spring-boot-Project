package com.FactoryManager.Controller;

import com.FactoryManager.DTO.*;
import com.FactoryManager.Service.ToolService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tools")
public class ToolController {
    @Autowired
    ToolService toolService;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/add")
    public ResponseEntity<AddToolResponseDto> addTool(@ModelAttribute AddToolRequestDto dto) {
        return ResponseEntity.ok(toolService.addTool(dto));
    }

    @PreAuthorize("hasAnyRole('PLANT_HEAD','CHIEF_SUPERVISOR','OWNER','WORKER')")
    @GetMapping("/all")
    public ResponseEntity<Page<ToolResponseDto>> getAllTools(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0")@Min(value = 0, message = "Page number cannot be negative") int page,
            @RequestParam(defaultValue = "10")  @Min(value = 1, message = "Size must be at least 1") int size
    ) {
        return ResponseEntity.ok(toolService.getAllTools(search, categoryId, page, size));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/update/{toolId}")
    public ResponseEntity<AddToolResponseDto> updateTool(
            @PathVariable Long toolId,
            @ModelAttribute AddToolRequestDto dto) {

        return ResponseEntity.ok(toolService.updateTool(toolId, dto));
    }

    @PreAuthorize("hasAnyRole('PLANT_HEAD','CHIEF_SUPERVISOR')")
    @PatchMapping("/increase-qty")
    public ResponseEntity<UpdateToolQtyResponse> increaseQty(
            @RequestBody UpdateToolQtyRequest req) {

        return ResponseEntity.ok(toolService.increaseToolQty(req));
    }




}
