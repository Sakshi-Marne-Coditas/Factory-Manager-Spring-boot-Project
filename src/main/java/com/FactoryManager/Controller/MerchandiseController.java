package com.FactoryManager.Controller;

import com.FactoryManager.DTO.MerchandiseRequestDto;
import com.FactoryManager.DTO.MerchandiseResponseDto;
import com.FactoryManager.Service.MerchandiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/merchandise")
public class MerchandiseController {

    @Autowired
    private MerchandiseService merchandiseService;

    @PostMapping("/add")
    public ResponseEntity<MerchandiseResponseDto> addMerchandise(@ModelAttribute MerchandiseRequestDto dto) {
        MerchandiseResponseDto response = merchandiseService.addMerchandise(dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<MerchandiseResponseDto> updateMerchandise(
            @PathVariable Long id,
            @ModelAttribute MerchandiseRequestDto dto) {
        MerchandiseResponseDto response = merchandiseService.updateMerchandise(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<MerchandiseResponseDto>> getAllMerchandise(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search) {

        Page<MerchandiseResponseDto> response = merchandiseService.getAllMerchandise(page, size, search);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMerchandise(@PathVariable Long id) {
        merchandiseService.deleteMerchandise(id);
        return ResponseEntity.ok("Merchandise deleted successfully with ID: " + id);
    }
}
