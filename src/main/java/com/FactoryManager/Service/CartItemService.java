package com.FactoryManager.Service;

import com.FactoryManager.DTO.*;
import com.FactoryManager.Entity.CartItem;
import com.FactoryManager.Entity.Product;
import com.FactoryManager.Entity.User;
import com.FactoryManager.Repository.CartItemRepository;
import com.FactoryManager.Repository.ProductRepository;
import com.FactoryManager.Repository.UserRepository;
import com.FactoryManager.exceptionHandling.ElementNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Transactional
    public String addToCart(AddToCartRequestDto request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();


        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        Long productId = request.getProductId();

        if (!productRepository.existsById(productId)) {
            throw new ElementNotFoundException("Product not found with id: " + productId);
        }

        Product product = productRepository.findById(productId).get();

        var existingItemOpt = cartItemRepository.findByDistributorAndProduct(currentUser, product);

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(existingItem);
            return "Product quantity updated in cart!";
        }

        CartItem cartItem = new CartItem();
        cartItem.setDistributor(currentUser);
        cartItem.setProduct(product);
        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);

        return "Product added to cart successfully!";
    }


    public CartResponseDto getAllCartItemsForCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<CartItem> cartItems = currentUser.getCartItems();

        if (cartItems.isEmpty()) {
            throw new EntityNotFoundException("No items found in your cart");
        }

        List<CartItemResponseDto> itemDtos = cartItems.stream().map(item -> {
            Double price = item.getProduct().getProductPrice();
            int qty = item.getQuantity();
            Double subtotal = price * qty;

            return new CartItemResponseDto(
                    item.getProduct().getId(),
                    item.getProduct().getProductName(),
                    price,
                    qty,
                    subtotal
            );
        }).collect(Collectors.toList());

        Double total = itemDtos.stream()
                .mapToDouble(CartItemResponseDto::getItemSubtotal)
                .sum();

        return new CartResponseDto(itemDtos, total);
    }


    @Transactional
    public String removeProductFromCart(Long productId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (!productRepository.existsById(productId)) {
            throw new ElementNotFoundException("Product not found with id: " + productId);
        }

        Product product = productRepository.findById(productId).get();

        CartItem cartItem = cartItemRepository.findByDistributorAndProduct(currentUser, product)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product not found in your cart with id: " + productId)
                );

        cartItemRepository.delete(cartItem);

        return "Product removed from cart successfully!";
    }

}
