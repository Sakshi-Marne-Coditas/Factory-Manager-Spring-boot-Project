package com.FactoryManager.Service;

import com.FactoryManager.DTO.MerchandiseRequestDto;
import com.FactoryManager.DTO.MerchandiseResponseDto;
import com.FactoryManager.Entity.Merchandise;
import com.FactoryManager.Repository.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MerchandiseService {
    @Autowired
    private MerchandiseRepository merchandiseRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public MerchandiseResponseDto addMerchandise(MerchandiseRequestDto dto) {
        String imageUrl = null;
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            try {
                imageUrl = cloudinaryService.uploadFile(dto.getImage());
            } catch (IOException e) {
                throw new RuntimeException("Error uploading image to Cloudinary", e);
            }
        }

        Merchandise merchandise = new Merchandise();
        merchandise.setName(dto.getName());
        merchandise.setImage(imageUrl);
        merchandise.setRequiredPts(dto.getRequiredPts());

        Merchandise saved = merchandiseRepository.save(merchandise);

        MerchandiseResponseDto merchandiseResponseDto= new MerchandiseResponseDto();
        merchandiseResponseDto.setId(saved.getId());
        merchandiseResponseDto.setName(saved.getName());
        merchandiseResponseDto.setImage(saved.getImage());
        merchandiseResponseDto.setRequiredPts(saved.getRequiredPts());
        return merchandiseResponseDto;
    }


    public MerchandiseResponseDto updateMerchandise(Long id, MerchandiseRequestDto dto) {
        Merchandise existing = merchandiseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchandise not found with id: " + id));

        if (dto.getRequiredPts() != 0) {
            existing.setRequiredPts(dto.getRequiredPts());
        }

        if (dto.getName() != null && !dto.getName().isEmpty()) {
            existing.setName(dto.getName());
        }

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            try {
                String newImageUrl = cloudinaryService.uploadFile(dto.getImage());
                existing.setImage(newImageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Error uploading image to Cloudinary", e);
            }
        }

        Merchandise updated = merchandiseRepository.save(existing);

        MerchandiseResponseDto response = new MerchandiseResponseDto();
        response.setId(updated.getId());
        response.setName(updated.getName());
        response.setImage(updated.getImage());
        response.setRequiredPts(updated.getRequiredPts());

        return response;
    }

    public Page<MerchandiseResponseDto> getAllMerchandise(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Merchandise> merchandisePage;

        if (search != null && !search.trim().isEmpty()) {
            merchandisePage = merchandiseRepository.findByNameContainingIgnoreCase(search, pageable);
        } else {
            merchandisePage = merchandiseRepository.findAll(pageable);
        }

        // Convert entity → DTO
        List<MerchandiseResponseDto> dtoList = merchandisePage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, merchandisePage.getTotalElements());
    }

    private MerchandiseResponseDto mapToResponse(Merchandise merchandise) {
        MerchandiseResponseDto dto = new MerchandiseResponseDto();
        dto.setId(merchandise.getId());
        dto.setName(merchandise.getName());
        dto.setImage(merchandise.getImage());
        dto.setRequiredPts(merchandise.getRequiredPts());

        return dto;
    }

    public void deleteMerchandise(Long id) {
        Merchandise merchandise = merchandiseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchandise not found with ID: " + id));

        merchandiseRepository.delete(merchandise);
    }
}
