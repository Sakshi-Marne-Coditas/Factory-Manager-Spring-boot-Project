package com.FactoryManager.Service;

import com.FactoryManager.Constants.CategoryOf;
import com.FactoryManager.DTO.AdCategoryDTO;
import com.FactoryManager.DTO.AdCategoryResDTO;
import com.FactoryManager.Entity.Category;
import com.FactoryManager.Repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
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
        category.setCategoryOf(CategoryOf.TOOL);

         repo.save(category);

         AdCategoryResDTO adCategoryResDTO = new AdCategoryResDTO();
         adCategoryResDTO.setId(category.getId());
         adCategoryResDTO.setName(category.getCategoryName());

         return adCategoryResDTO;
    }

    @Transactional
    public Page<AdCategoryResDTO> getAllCategoryNames(String search, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());


        List<Category> categories = repo.findAll(Sort.by("createdAt").descending());

        //  In-memory search filter
        List<Category> filtered = categories.stream()
                .filter(c -> {
                    if (search != null && !search.isBlank()) {
                        return c.getCategoryName().toLowerCase()
                                .contains(search.toLowerCase());
                    }
                    return true;
                })
                .toList();

        //  Pagination manually apply
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());

        if (start > filtered.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, filtered.size());
        }

        List<Category> paginatedList = filtered.subList(start, end);

        // Convert  DTO
        List<AdCategoryResDTO> dtoList = paginatedList.stream()
                .map(c -> new AdCategoryResDTO(c.getId(), c.getCategoryName()))
                .toList();

        return new PageImpl<>(dtoList, pageable, filtered.size());
    }



}