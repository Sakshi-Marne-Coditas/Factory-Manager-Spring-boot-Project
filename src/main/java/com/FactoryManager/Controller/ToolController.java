package com.FactoryManager.Controller;

import com.FactoryManager.DTO.*;
import com.FactoryManager.Service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tools")
public class ToolController {
    @Autowired
    ToolService toolService;

    @PostMapping("/add")
    public ResponseEntity<AddToolResponseDto> addTool(@ModelAttribute AddToolRequestDto dto) {
        return ResponseEntity.ok(toolService.addTool(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ToolResponseDto>> getAllTools(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(toolService.getAllTools(search, categoryId, page, size));
    }

    @PutMapping("/update/{toolId}")
    public ResponseEntity<AddToolResponseDto> updateTool(
            @PathVariable Long toolId,
            @ModelAttribute AddToolRequestDto dto) {

        return ResponseEntity.ok(toolService.updateTool(toolId, dto));
    }

    @PatchMapping("/increase-qty")
    public ResponseEntity<UpdateToolQtyResponse> increaseQty(
            @RequestBody UpdateToolQtyRequest req) {

        return ResponseEntity.ok(toolService.increaseToolQty(req));
    }



}
