package com.FactoryManager.Service;

import com.FactoryManager.DTO.ProductResponseDto;
import com.FactoryManager.exceptionHandling.ElementNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.FactoryManager.DTO.UpdateFactoryProductQuantityDto;
import com.FactoryManager.Entity.FactoryProduct;
import com.FactoryManager.Entity.User;
import com.FactoryManager.Repository.FactoryProductRepository;
import com.FactoryManager.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FactoryProductService {

    private final FactoryProductRepository factoryProductRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProductResponseDto updateFactoryProductQuantity(UpdateFactoryProductQuantityDto dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail("sam.bhosale@gmail.com")
                .orElseThrow(() -> new UsernameNotFoundException(("User not found with email: " + email)));

        Long factoryId = currentUser.getFactory().getId();

        FactoryProduct factoryProduct = factoryProductRepository.findByProductIdAndFactoryId(dto.getProductId(), factoryId)
                .orElseThrow(() -> new ElementNotFoundException(
                        "Product not found in your factory with id: " + dto.getProductId()));


        factoryProduct.setQuantity(factoryProduct.getQuantity()+dto.getQuantity());
        factoryProductRepository.save(factoryProduct);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(factoryProduct.getProduct().getId());
        productResponseDto.setQuantity(factoryProduct.getQuantity());
        productResponseDto.setName(factoryProduct.getProduct().getProductName());
        productResponseDto.setImageUrl(factoryProduct.getProduct().getProductImage());
        productResponseDto.setPrice(factoryProduct.getProduct().getProductPrice());
        productResponseDto.setCategoryName(factoryProduct.getProduct().getCategory().getCategoryName());
        productResponseDto.setDescription(factoryProduct.getProduct().getProductDescription());

        return productResponseDto;
    }
}

