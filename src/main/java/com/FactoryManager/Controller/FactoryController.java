package com.FactoryManager.Controller;

import com.FactoryManager.DTO.*;
import com.FactoryManager.Service.FactoryService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/factory")
public class FactoryController {
    @Autowired
    private FactoryService factoryService;
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/newfactory")
    public ResponseEntity<FactoryResponseDto> createFactory(@RequestBody FactoryRequestDto factoryRequestDto) {
        FactoryResponseDto factoryResponseDto= factoryService.createFactory(factoryRequestDto);
        return ResponseEntity.ok(factoryResponseDto);
    }
    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<FactoryResponseDto> updateFactory(
            @PathVariable Long id,
            @RequestBody FactoryRequestDto factoryUpdateRequestDto) {

        FactoryResponseDto updatedFactory = factoryService.updateFactory(id, factoryUpdateRequestDto);
        return ResponseEntity.ok(updatedFactory);
    }
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/all")
    public ResponseEntity<Page<AllFactoryResponseDto>> getAllFactories(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page number cannot be negative")int page,
            @RequestParam(defaultValue = "5") @Min(value = 1, message = "Size must be at least 1")int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String location
    ) {
        Page<AllFactoryResponseDto> factories =
                factoryService.getAllFactories(search, location, page, size);

        return ResponseEntity.ok(factories);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<FactoryResponseDto> deleteFactory(
            @PathVariable Long id) {

        FactoryResponseDto updatedFactory = factoryService.deleteFactory(id);
        return ResponseEntity.ok(updatedFactory);
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/location-count")
    public ResponseEntity<Map<String, Object>> getLocationWiseFactoryCount() {
        List<LocationFactoryCountResponseDto> data = factoryService.getLocationWiseFactoryCount();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getFactories(
            @RequestParam(defaultValue = "all") String type) {

        Map<String, Object> response = factoryService.getFactories(type);
        return ResponseEntity.ok(response);
    }




}
