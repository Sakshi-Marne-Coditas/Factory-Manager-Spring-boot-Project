package com.FactoryManager.Controller;

import com.FactoryManager.DTO.AdCategoryDTO;
import com.FactoryManager.DTO.AdCategoryResDTO;
import com.FactoryManager.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<AdCategoryResDTO>> getAllCategoryNames() {
        List<AdCategoryResDTO> dtos = categoryService.getAllCategoryNames();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/new-category")
    public ResponseEntity<AdCategoryResDTO> addCategory(@RequestBody AdCategoryDTO dto) {
        AdCategoryResDTO dtos = categoryService.createCategory(dto);
        return ResponseEntity.ok(dtos);
    }
}
