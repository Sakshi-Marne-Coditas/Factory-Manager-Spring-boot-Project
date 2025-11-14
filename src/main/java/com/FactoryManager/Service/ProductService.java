package com.FactoryManager.Service;

import com.FactoryManager.Config.OrderConstants;
import com.FactoryManager.DTO.*;
import com.FactoryManager.Entity.*;
import com.FactoryManager.Repository.*;
import com.FactoryManager.exceptionHandling.ElementAlreadyExistException;
import com.FactoryManager.exceptionHandling.ElementNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private FactoryRepository factoryRepository;

    @Autowired
    private FactoryProductRepository factoryProductRepository;
    @Autowired
    private UserRepository userRepository;


    public ProductResponseDto addProduct(ProductRequestDto dto) {
        if (productRepository.findByProductName(dto.getName()).isPresent()) {
            throw new ElementAlreadyExistException("Product with name '" + dto.getName() + " already exists!");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));

        String imageUrl = null;
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            try {
                imageUrl = cloudinaryService.uploadFile(dto.getImage());
            } catch (IOException e) {
                throw new RuntimeException("Error uploading image to Cloudinary", e);
            }
        }


        Product product = new Product();
        product.setProductName(dto.getName());
        product.setQtyAvailable(dto.getQuantity());
        product.setProductImage(imageUrl);
        product.setCategory(category);
        product.setProductDescription(dto.getDescription());
        product.setProductPrice(dto.getPrice());
        productRepository.save(product);

        List<Factory> allFactories = factoryRepository.findAll();

        List<FactoryProduct> factoryProducts = new ArrayList<>();
        int defaultQty = dto.getQuantity() ;

        for (Factory factory : allFactories) {
            FactoryProduct factoryProduct = new FactoryProduct();
            factoryProduct.setFactory(factory);
            factoryProduct.setProduct(product);
            factoryProduct.setQuantity(defaultQty);
            factoryProducts.add(factoryProduct);
        }
        factoryProductRepository.saveAll(factoryProducts);


       ProductResponseDto productResponseDto = new ProductResponseDto();
       productResponseDto.setName(product.getProductName());
       productResponseDto.setId(product.getId());
      productResponseDto.setDescription(product.getProductDescription());
      productResponseDto.setPrice(product.getProductPrice());
      productResponseDto.setQuantity(product.getQtyAvailable());
      productResponseDto.setImageUrl(product.getProductImage());
      productResponseDto.setCategoryName(product.getCategory().getCategoryName());
      productResponseDto.setImageUrl(product.getProductImage());

      return productResponseDto;
    }


    public String deleteProduct(Long productId) {

        productRepository.findById(productId)
                .orElseThrow(() -> new ElementNotFoundException("Product not found with id: " + productId));

        productRepository.deleteById(productId);



        return "Product deleted successfully with id: " + productId;
    }

    public Page<AllProductResponseDto> getAllProducts(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsPage;

        if (categoryId != null) {
            productsPage = productRepository.findByCategoryId(categoryId, pageable);
        } else {
            productsPage = productRepository.findAll(pageable);
        }

        return productsPage.map(p -> new AllProductResponseDto(
                p.getId(),
                p.getProductName(),
                (p.getCategory() != null) ? p.getCategory().getCategoryName() : null,
                p.getProductDescription(),
                p.getProductPrice(),
                p.getProductImage()
        ));
    }



}
