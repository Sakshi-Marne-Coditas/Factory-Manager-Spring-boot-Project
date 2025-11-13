package com.FactoryManager.Controller;

import com.FactoryManager.DTO.*;
import com.FactoryManager.Service.CartItemService;
import com.FactoryManager.Service.FactoryProductService;
import com.FactoryManager.Service.OrderService;
import com.FactoryManager.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/newproduct")
    public ResponseEntity<ProductResponseDto> addProduct(@ModelAttribute ProductRequestDto dto) {
        ProductResponseDto response = productService.addProduct(dto);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/update-quantity")
    public ResponseEntity<ProductResponseDto> updateFactoryProductQuantity(@Valid @RequestBody UpdateFactoryProductQuantityDto dto) {
        ProductResponseDto productResponseDto = factoryProductService.updateFactoryProductQuantity(dto);
        return ResponseEntity.ok(productResponseDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        String message = productService.deleteProduct(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AllProductResponseDto>> getAllProducts(@RequestParam(required = false) Long categoryId,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        Page<AllProductResponseDto> products = productService.getAllProducts(categoryId,page, size);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody AddToCartRequestDto request) {

        String message = cartItemService.addToCart(request);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/all-cart-item")
    public ResponseEntity<CartResponseDto> getAllCartItems() {
        CartResponseDto response = cartItemService.getAllCartItemsForCurrentUser();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Long productId) {
        String message = cartItemService.removeProductFromCart(productId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/checkout-preview")
    public ResponseEntity<CheckoutPreviewResponseDto> getCheckoutPreview() {
        CheckoutPreviewResponseDto response = orderService.getCheckoutPreview();
        return ResponseEntity.ok(response);
    }




}
