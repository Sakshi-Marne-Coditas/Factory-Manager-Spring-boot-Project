package com.FactoryManager.Controller;

import com.FactoryManager.DTO.*;
import com.FactoryManager.Service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/factory")
public class FactoryController {
    @Autowired
    private FactoryService factoryService;
    @PostMapping("/newfactory")
    public ResponseEntity<FactoryResponseDto> createFactory(@RequestBody FactoryRequestDto factoryRequestDto) {
        FactoryResponseDto factoryResponseDto= factoryService.createFactory(factoryRequestDto);
        return ResponseEntity.ok(factoryResponseDto);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<FactoryResponseDto> updateFactory(
            @PathVariable Long id,
            @RequestBody FactoryRequestDto factoryUpdateRequestDto) {

        FactoryResponseDto updatedFactory = factoryService.updateFactory(id, factoryUpdateRequestDto);
        return ResponseEntity.ok(updatedFactory);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AllFactoryResponseDto>> getAllFactories(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AllFactoryResponseDto> factories = factoryService.getAllFactories(pageable);
        return ResponseEntity.ok(factories);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<FactoryResponseDto> deleteFactory(
            @PathVariable Long id) {

        FactoryResponseDto updatedFactory = factoryService.deleteFactory(id);
        return ResponseEntity.ok(updatedFactory);
    }


    @GetMapping("/location-count")
    public ResponseEntity<Map<String, Object>> getLocationWiseFactoryCount() {
        List<LocationFactoryCountResponseDto> data = factoryService.getLocationWiseFactoryCount();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getFactories(
            @RequestParam(defaultValue = "all") String type) {

        Map<String, Object> response = factoryService.getFactories(type);
        return ResponseEntity.ok(response);
    }




}
