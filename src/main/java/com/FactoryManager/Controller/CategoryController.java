package com.FactoryManager.Controller;

import com.FactoryManager.DTO.AdCategoryDTO;
import com.FactoryManager.DTO.AdCategoryResDTO;
import com.FactoryManager.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/all")
    public ResponseEntity<Page<AdCategoryResDTO>> getAllCategoryNames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        Page<AdCategoryResDTO> dtos = categoryService.getAllCategoryNames(search, page, size);
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/new-category")
    public ResponseEntity<AdCategoryResDTO> addCategory(@RequestBody AdCategoryDTO dto) {
        AdCategoryResDTO dtos = categoryService.createCategory(dto);
        return ResponseEntity.ok(dtos);
    }
}
