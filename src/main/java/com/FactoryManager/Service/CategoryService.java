package com.FactoryManager.Service;

import com.FactoryManager.DTO.AdCategoryDTO;
import com.FactoryManager.DTO.AdCategoryResDTO;
import com.FactoryManager.Entity.Category;
import com.FactoryManager.Repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repo;


    public AdCategoryResDTO createCategory(AdCategoryDTO dto) {

        if (repo.existsByCategoryNameIgnoreCase(dto.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category already exists");
        }

        Category category = new Category();
        category.setCategoryName(dto.getName());

         repo.save(category);

         AdCategoryResDTO adCategoryResDTO = new AdCategoryResDTO();
         adCategoryResDTO.setId(category.getId());
         adCategoryResDTO.setName(category.getCategoryName());

         return adCategoryResDTO;
    }


    public List<AdCategoryResDTO> getAllCategoryNames() {
        List<Category> categories = new ArrayList<>();
        categories = repo.findAll();

        List<AdCategoryResDTO> dtoList = new ArrayList<>();

        for (Category c : categories) {
            AdCategoryResDTO dto = new AdCategoryResDTO();
            dto.setName(c.getCategoryName());
            dto.setId(c.getId());
            dtoList.add(dto);
        }

        return dtoList;

    }


    }