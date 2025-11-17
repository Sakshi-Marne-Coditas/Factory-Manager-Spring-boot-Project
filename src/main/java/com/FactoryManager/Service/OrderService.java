package com.FactoryManager.Service;

import com.FactoryManager.Config.OrderConstants;
import com.FactoryManager.Constatnts.RequestStatus;
import com.FactoryManager.DTO.*;
import com.FactoryManager.Entity.*;
import com.FactoryManager.Repository.*;
import com.FactoryManager.exceptionHandling.ElementNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderBatchRepository orderBatchRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public CheckoutPreviewResponseDto getCheckoutPreview() {

        //  Logged-in distributor
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail("newmail@gmail.com")
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<CartItem> cartItems = currentUser.getCartItems();
        if (cartItems.isEmpty()) {
            throw new ElementNotFoundException("Your cart is empty!");
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

    public OrderResponseDto requestOrder() {

        // 1️⃣ Logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail("newmail@gmail.com")
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // 2️⃣ Get cart items
        List<CartItem> cartItems = currentUser.getCartItems();
        if (cartItems.isEmpty()) {
            throw new ElementNotFoundException("Cart is empty!");
        }

        // 3️⃣ Create order
        Order order = new Order();
        order.setStatus(RequestStatus.PENDING);
        order.setDistributor(currentUser);
        order.setOrder_date(LocalDateTime.now());
        order.setTotal_amount(getCheckoutPreview().getGrandTotal());

        // Items list already initialised in entity
        List<OrderItem> orderItems = new ArrayList<>();

        // 4️⃣ Convert CartItem → OrderItem
        for (CartItem cart : cartItems) {

            OrderItem item = new OrderItem();
            item.setOrder(order);   // VERY IMPORTANT
            item.setProduct(cart.getProduct());
            item.setQuantity(cart.getQuantity());

            item.setSubtotal(cart.getProduct().getProductPrice() * cart.getQuantity());

            orderItems.add(item);
        }

        // Attach items to order
        order.setItems(orderItems);

        // 5️⃣ Save order → cascade will save OrderItems too
        orderRepository.save(order);

        // 6️⃣ Clear cart
        cartItemRepository.deleteAll(cartItems);

        // 7️⃣ Create response DTO
        OrderResponseDto response = new OrderResponseDto();
        response.setOrderId(order.getId());
        response.setStatus(order.getStatus().name());
        response.setOrderDate(order.getOrder_date());
        response.setTotalAmount(order.getTotal_amount());

        List<OrderItemResponseDto> itemDtos = orderItems.stream().map(i -> {
            OrderItemResponseDto d = new OrderItemResponseDto();
            d.setProductId(i.getProduct().getId());
            d.setProductName(i.getProduct().getProductName());
            d.setPrice(i.getProduct().getProductPrice());
            d.setQuantity(i.getQuantity());
            d.setImage(i.getProduct().getProductImage());
            d.setSubtotal(i.getSubtotal());
            return d;
        }).toList();

        response.setItems(itemDtos);

        return response;
    }





    public ApproveOrderBatchesResponseDto approveOrder(ApproveOrderBatchesRequestDto dto) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User currentUser = userRepository.findByEmail("mit@gmail.com")
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            Order order = orderRepository.findById(dto.getOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("Order not found"));

            order.setStatus(RequestStatus.APPROVED);
            order.setApprovedBy(currentUser);

            LocalDateTime now = LocalDateTime.now();
            List<OrderBatch> savedBatches = new ArrayList<>();

            int batchIndex = 1;

            for (BatchDto batchDto : dto.getBatches()) {

                OrderBatch batch = new OrderBatch();
                batch.setOrder(order);
                batch.setStatus(RequestStatus.APPROVED);
                batch.setDispatchedAt(now);
                batch.setExpectedDeliveryDate(now.plusDays(batchIndex * 3));

                List<OrderBatchItem> batchItems = new ArrayList<>();

                for (BatchProductDto prodDto : batchDto.getProducts()) {

                    Product product = productRepository.findById(prodDto.getProductId())
                            .orElseThrow(() -> new EntityNotFoundException("Product not found"));

                    OrderBatchItem item = new OrderBatchItem();
                    item.setBatch(batch);
                    item.setProduct(product);
                    item.setQuantityDispatched(prodDto.getQuantityDispatched());

                    batchItems.add(item);
                }

                batch.setItems(batchItems);
                savedBatches.add(orderBatchRepository.save(batch));
                batchIndex++;
            }

            // FINAL DELIVERY DATE
            LocalDateTime finalDelivery =
                    savedBatches.isEmpty() ? now.plusDays(3)
                            : savedBatches.get(savedBatches.size() - 1).getExpectedDeliveryDate();

            // MAP RESPONSE
            List<OrderBatchResponseDto> batchDtos = new ArrayList<>();

            for (OrderBatch batch : savedBatches) {
                OrderBatchResponseDto dtoBatch = new OrderBatchResponseDto();
                dtoBatch.setBatchId(batch.getId());
                dtoBatch.setDispatchedAt(batch.getDispatchedAt());
                dtoBatch.setExpectedDeliveryDate(batch.getExpectedDeliveryDate());
                dtoBatch.setStatus(batch.getStatus().name());

                List<OrderBatchItemResponseDto> itemDtos = new ArrayList<>();

                for (OrderBatchItem item : batch.getItems()) {
                    OrderBatchItemResponseDto detail = new OrderBatchItemResponseDto();
                    detail.setProductId(item.getProduct().getId());
                    detail.setImage(item.getProduct().getProductImage());
                    detail.setProductName(item.getProduct().getProductName());
                    detail.setQuantityDispatched(item.getQuantityDispatched());
                    itemDtos.add(detail);
                }

                dtoBatch.setItems(itemDtos);
                batchDtos.add(dtoBatch);
            }

            ApproveOrderBatchesResponseDto response = new ApproveOrderBatchesResponseDto();
            response.setOrderId(order.getId());
            response.setBatches(batchDtos);
            response.setFinalEstimatedDelivery(finalDelivery);

            return response;
        }




    public PaginatedOrderResponseDto getOrdersForCentral(String status, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Order> orderPage;

        //  Filter by status if provided
        if (status != null && !status.isEmpty()) {
            RequestStatus reqStatus = RequestStatus.valueOf(status.toUpperCase());
            orderPage = orderRepository.findByStatus(reqStatus, pageable);
        } else {
            orderPage = orderRepository.findAll(pageable);
        }

        List<Order> orders = orderPage.getContent();
        List<OrderListWithProductsDto> dtoList = new ArrayList<>();

        for (Order order : orders) {

            OrderListWithProductsDto dto = new OrderListWithProductsDto();

            dto.setOrderId(order.getId());
            dto.setOrderStatus(order.getStatus().name());
            dto.setRequestedOn(order.getOrder_date());
            dto.setDistributorName(order.getDistributor().getUsername());
            dto.setDistributorEmail(order.getDistributor().getEmail());

            //  Build product list
            List<OrderProductInfoDto> productList = new ArrayList<>();

            for (OrderItem item : order.getItems()) {

                Product p = item.getProduct();

                OrderProductInfoDto prod = new OrderProductInfoDto();
                prod.setProductName(p.getProductName());
                prod.setQuantity(item.getQuantity());
                prod.setProductImage(p.getProductImage());

                //  NULL-SAFE category
                if (p.getCategory() != null) {
                    prod.setProductCategory(p.getCategory().getCategoryName());
                } else {
                    prod.setProductCategory("N/A");
                }

                productList.add(prod);
            }

            dto.setProducts(productList);
            dtoList.add(dto);
        }

        //  Build paginated response
        PaginatedOrderResponseDto paginated = new PaginatedOrderResponseDto();
        paginated.setOrders(dtoList);
        paginated.setPageNumber(orderPage.getNumber());
        paginated.setPageSize(orderPage.getSize());
        paginated.setTotalElements(orderPage.getTotalElements());
        paginated.setTotalPages(orderPage.getTotalPages());
        paginated.setLastPage(orderPage.isLast());

        return paginated;
    }

    public PaginatedOrderResponseDto getOrdersForDistributor(int page, int size) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User distributor = userRepository.findByEmail("newmail@gmail.com")
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Pageable pageable = PageRequest.of(page, size);

        Page<Order> orderPage = orderRepository.findByDistributor(distributor, pageable);

        List<OrderListWithProductsDto> orderDtos = new ArrayList<>();

        for (Order order : orderPage.getContent()) {

            OrderListWithProductsDto dto = new OrderListWithProductsDto();
            dto.setOrderId(order.getId());
            dto.setOrderStatus(order.getStatus().name());
            dto.setRequestedOn(order.getOrder_date());
            dto.setDistributorName(distributor.getUsername());
            dto.setDistributorEmail(distributor.getEmail());

            List<OrderProductInfoDto> productDtos = new ArrayList<>();

            for (OrderItem item : order.getItems()) {

                Product p = item.getProduct();

                OrderProductInfoDto prod = new OrderProductInfoDto();
                prod.setProductName(p.getProductName());
                prod.setProductCategory(p.getCategory().getCategoryName());
                prod.setQuantity(item.getQuantity());
                prod.setProductImage(p.getProductImage());

                productDtos.add(prod);
            }

            dto.setProducts(productDtos);
            orderDtos.add(dto);
        }

        PaginatedOrderResponseDto response = new PaginatedOrderResponseDto();
        response.setOrders(orderDtos);
        response.setPageNumber(orderPage.getNumber());
        response.setPageSize(orderPage.getSize());
        response.setTotalElements(orderPage.getTotalElements());
        response.setTotalPages(orderPage.getTotalPages());
        response.setLastPage(orderPage.isLast());

        return response;
    }


}

