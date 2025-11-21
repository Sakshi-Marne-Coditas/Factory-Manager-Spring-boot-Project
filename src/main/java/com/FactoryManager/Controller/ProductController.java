package com.FactoryManager.Controller;

import com.FactoryManager.DTO.*;
import com.FactoryManager.Service.CartItemService;
import com.FactoryManager.Service.FactoryProductService;
import com.FactoryManager.Service.OrderService;
import com.FactoryManager.Service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private FactoryProductService factoryProductService;
    @Autowired
    private  CartItemService cartItemService;

    @Autowired
    OrderService orderService;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/newproduct")
    public ResponseEntity<ProductResponseDto> addProduct(@ModelAttribute ProductRequestDto dto) {
        ProductResponseDto response = productService.addProduct(dto);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('PLANT_HEAD')")
    @PutMapping("/update-quantity")
    public ResponseEntity<ProductResponseDto> updateFactoryProductQuantity(@Valid @RequestBody UpdateFactoryProductQuantityDto dto) {
        ProductResponseDto productResponseDto = factoryProductService.updateFactoryProductQuantity(dto);
        return ResponseEntity.ok(productResponseDto);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        String message = productService.deleteProduct(id);
        return ResponseEntity.ok(message);
    }
    @PreAuthorize("hasAnyRole('OWNER','PLANT_HEAD','DISTRIBUTOR','CENTRAL_OFFICER','CHIEF_SUPERVISOR')")
    @GetMapping("/all")
    public ResponseEntity<Page<AllProductResponseDto>> getAllProducts(@RequestParam(required = false) Long categoryId,
                                                                      @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page number cannot be negative")int page,
                                                                      @RequestParam(defaultValue = "10") @Min(value = 1, message = "Size must be at least 1")int size) {
        Page<AllProductResponseDto> products = productService.getAllProducts(categoryId,page, size);
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasRole('DISTRIBUTOR')")
    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody AddToCartRequestDto request) {

        String message = cartItemService.addToCart(request);
        return ResponseEntity.ok(message);
    }

    @PreAuthorize("hasRole('DISTRIBUTOR')")
    @GetMapping("/all-cart-item")
    public ResponseEntity<CartResponseDto> getAllCartItems() {
        CartResponseDto response = cartItemService.getAllCartItemsForCurrentUser();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('DISTRIBUTOR')")
    @DeleteMapping("/cart/remove/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Long productId) {
        String message = cartItemService.removeProductFromCart(productId);
        return ResponseEntity.ok(message);
    }

    @PreAuthorize("hasRole('DISTRIBUTOR')")
    @GetMapping("/checkout-preview")
    public ResponseEntity<CheckoutPreviewResponseDto> getCheckoutPreview() {
        CheckoutPreviewResponseDto response = orderService.getCheckoutPreview();
        return ResponseEntity.ok(response);
    }





}
