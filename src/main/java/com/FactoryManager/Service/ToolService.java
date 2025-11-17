package com.FactoryManager.Service;

import com.FactoryManager.DTO.*;
import com.FactoryManager.Entity.*;
import com.FactoryManager.Repository.*;
import com.FactoryManager.exceptionHandling.ElementAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ToolService {
    @Autowired
    FactoryRepository factoryRepository;
    @Autowired
   private ToolRepository toolRepository;
    @Autowired
   private CategoryRepository categoryRepository;
    @Autowired
   private CloudinaryService cloudinaryService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FactoryToolRepository factoryToolRepository;

    public AddToolResponseDto addTool(AddToolRequestDto dto) {

        // Check if tool already exists
        toolRepository.findByToolName(dto.getToolName())
                .ifPresent(t -> {
                    throw new ElementAlreadyExistException(
                            "Tool with name '" + dto.getToolName() + "' already exists!"
                    );
                });

        // Validate category
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));

        // Upload image to Cloudinary
        String imageUrl = null;
        if (dto.getToolImage() != null && !dto.getToolImage().isEmpty()) {
            try {
                imageUrl = cloudinaryService.uploadFile(dto.getToolImage());
            } catch (Exception e) {
                throw new RuntimeException("Error uploading tool image", e);
            }
        }

        // Create new Tool object
        Tool tool = new Tool();
        tool.setToolName(dto.getToolName());
        tool.setToolDescription(dto.getToolDescription());
        tool.setToolImage(imageUrl);
        tool.setUseCase(dto.getUseCase());     //  Strings auto convert due to @JsonCreator
        tool.setToolType(dto.getToolType());
        tool.setThresholdQty(dto.getThresholdQty());
        tool.setCategory(category);

        Tool saved = toolRepository.save(tool);

        // get all factories
        List<Factory> factories = factoryRepository.findAll();

        List<FactoryTool> factoryTools = new ArrayList<>();

        for (Factory factory : factories) {

            FactoryTool ft = new FactoryTool();
            ft.setFactory(factory);
            ft.setTool(saved);

            ft.setCurrentAvailable(0);   // default always 0


            factoryTools.add(ft);
        }

        factoryToolRepository.saveAll(factoryTools);

        // Prepare response DTO
        AddToolResponseDto response = new AddToolResponseDto();
        response.setId(saved.getId());
        response.setToolName(saved.getToolName());
        response.setToolDescription(saved.getToolDescription());
        response.setToolImage(saved.getToolImage());
        response.setThresholdQty(saved.getThresholdQty());
        response.setCategoryName(saved.getCategory().getCategoryName());
        response.setUseCase(saved.getUseCase().getValue());
        response.setToolType(saved.getToolType().getValue());

        return response;
    }

    public Page<ToolResponseDto> getAllTools(String search, Long categoryId, int page, int size) {

        // Logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail("tushar@gmail.com")
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Long factoryId = user.getFactory().getId();

        Pageable pageable = PageRequest.of(page, size, Sort.by("toolName").ascending());

        Page<Tool> tools = toolRepository.searchTools(
                (search == null || search.isBlank()) ? null : search,
                categoryId,
                pageable
        );

        return tools.map(t -> {

            ToolResponseDto dto = new ToolResponseDto();
            dto.setId(t.getId());
            dto.setToolName(t.getToolName());
            dto.setToolDescription(t.getToolDescription());
            dto.setToolImage(t.getToolImage());

            dto.setCategoryName(t.getCategory().getCategoryName());
            dto.setStorageCode(t.getUniqueLocationCode());
            dto.setUseCase(t.getUseCase().getValue());
            dto.setToolType(t.getToolType().getValue());
            dto.setThresholdQty(t.getThresholdQty());
            dto.setTotalStock(t.getTotalStock());

            // 🔥 Get currentAvailable for this tool for logged-in user's factory
            FactoryTool factoryTool = factoryToolRepository
                    .findByFactoryIdAndToolId(factoryId, t.getId())
                    .orElse(null);

            dto.setCurrentAvailable(factoryTool != null ? factoryTool.getCurrentAvailable() : 0);

            return dto;
        });
    }

    public AddToolResponseDto updateTool(Long toolId, AddToolRequestDto dto) {

        // 1️⃣ Fetch existing tool
        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new RuntimeException("Tool not found with id: " + toolId));

        // 2️⃣ Update toolName
        if (dto.getToolName() != null && !dto.getToolName().isBlank()) {
            tool.setToolName(dto.getToolName());
        }

        // 3️⃣ Update toolDescription
        if (dto.getToolDescription() != null) {
            tool.setToolDescription(dto.getToolDescription());
        }

        // 4️⃣ Update image if new one is uploaded
        if (dto.getToolImage() != null && !dto.getToolImage().isEmpty()) {
            try {
                String newImageUrl = cloudinaryService.uploadFile(dto.getToolImage());
                tool.setToolImage(newImageUrl);
            } catch (Exception e) {
                throw new RuntimeException("Error uploading new tool image", e);
            }
        }

        // 5️⃣ Update useCase (ENUM)
        if (dto.getUseCase() != null) {
            tool.setUseCase(dto.getUseCase());
        }

        // 6️⃣ Update toolType (ENUM)
        if (dto.getToolType() != null) {
            tool.setToolType(dto.getToolType());
        }

        // 7️⃣ Update thresholdQty
        if (dto.getThresholdQty() != null) {
            tool.setThresholdQty(dto.getThresholdQty());
        }

        // 8️⃣ Update category if provided
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));
            tool.setCategory(category);
        }

        // 9️⃣ Save updated tool
        Tool saved = toolRepository.save(tool);

        // 🔟 Prepare Response DTO
        AddToolResponseDto resp = new AddToolResponseDto();
        resp.setId(saved.getId());
        resp.setToolName(saved.getToolName());
        resp.setToolDescription(saved.getToolDescription());
        resp.setToolImage(saved.getToolImage());
        resp.setUseCase(saved.getUseCase().getValue());
        resp.setToolType(saved.getToolType().getValue());
        resp.setThresholdQty(saved.getThresholdQty());
        resp.setCategoryName(saved.getCategory() != null ? saved.getCategory().getCategoryName() : null);


        return resp;
    }

    public UpdateToolQtyResponse increaseToolQty(UpdateToolQtyRequest req) {

        // 1️⃣ Get logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail("planthead2.bhosale@gmail.com")
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        Long factoryId = user.getFactory().getId();

        // 2️⃣ Find the tool
        Tool tool = toolRepository.findById(req.getToolId())
                .orElseThrow(() -> new RuntimeException("Tool not found with id: " + req.getToolId()));

        // 3️⃣ Find FactoryTool record for this logged factory
        FactoryTool factoryTool = factoryToolRepository
                .findByFactoryIdAndToolId(factoryId, req.getToolId())
                .orElseThrow(() -> new RuntimeException("Tool is not assigned to this factory"));

        // 4️⃣ Increase currentAvailable
        int newAvailable = factoryTool.getCurrentAvailable() + req.getIncreaseBy();
        factoryTool.setCurrentAvailable(newAvailable);

        // 5️⃣ Increase tool totalStock
        int newTotalStock = tool.getTotalStock() + req.getIncreaseBy();
        tool.setTotalStock(newTotalStock);

        factoryToolRepository.save(factoryTool);
        toolRepository.save(tool);

        // 6️⃣ Prepare response
        UpdateToolQtyResponse resp = new UpdateToolQtyResponse();
        resp.setToolId(tool.getId());
        resp.setToolName(tool.getToolName());
        resp.setFactoryId(factoryId);
        resp.setUpdatedCurrentAvailable(newAvailable);
        resp.setUpdatedTotalStock(newTotalStock);
        resp.setMessage("Tool quantity increased successfully!");

        return resp;
    }



}
