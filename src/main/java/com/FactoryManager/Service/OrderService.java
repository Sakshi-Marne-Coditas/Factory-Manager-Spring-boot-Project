package com.FactoryManager.Service;

import com.FactoryManager.Config.OrderConstants;
import com.FactoryManager.Constatnts.RequestStatus;
import com.FactoryManager.DTO.CheckoutPreviewItemDto;
import com.FactoryManager.DTO.CheckoutPreviewResponseDto;
import com.FactoryManager.Entity.CartItem;
import com.FactoryManager.Entity.Order;
import com.FactoryManager.Entity.User;
import com.FactoryManager.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private UserRepository userRepository;

    public CheckoutPreviewResponseDto getCheckoutPreview() {

        //  Logged-in distributor
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail("ram.bhosale@gmail.com")
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<CartItem> cartItems = currentUser.getCartItems();
        if (cartItems.isEmpty()) {
            throw new EntityNotFoundException("Your cart is empty!");
        }

        List<CheckoutPreviewItemDto> itemDtos = cartItems.stream()
                .map(item -> new CheckoutPreviewItemDto(
                        item.getProduct().getId(),
                        item.getProduct().getProductName(),
                        item.getProduct().getProductPrice(),
                        item.getQuantity(),
                        item.getProduct().getProductPrice() * item.getQuantity()
                ))
                .collect(Collectors.toList());

        double subtotal = itemDtos.stream()
                .mapToDouble(CheckoutPreviewItemDto::getItemTotal)
                .sum();

        double gst = (subtotal * OrderConstants.GST_PERCENT) / 100;
        double delivery = OrderConstants.DELIVERY_CHARGE;
        double grandTotal = subtotal + gst + delivery;

        return new CheckoutPreviewResponseDto(itemDtos, subtotal, gst, delivery, grandTotal);
    }

    public void requestOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail("ram.bhosale@gmail.com")
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<CartItem> cartItems = currentUser.getCartItems();
        Order order = new Order();
        order.setStatus(RequestStatus.PENDING);
        order.setDistributor(currentUser);
        order.setItems(cartItems);
        order.setTotal_amount(getCheckoutPreview().getGrandTotal());

    }


    public void approveOrder(int orderid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail("ram.bhosale@gmail.com")
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        Order order = new Order();
        order.setStatus(RequestStatus.APPROVED);
        order.setApprovedBy(currentUser);
//        order.setBatches();




    }
}
