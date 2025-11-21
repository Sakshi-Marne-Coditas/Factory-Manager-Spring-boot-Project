package com.FactoryManager.Controller;

import com.FactoryManager.DTO.*;
import com.FactoryManager.Service.OrderService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
   private OrderService orderService;

    @PreAuthorize("hasRole('CENTRAL_OFFICER')")
    @PostMapping("/approve")
    public ResponseEntity<ApproveOrderBatchesResponseDto> createBatches(
            @RequestBody ApproveOrderBatchesRequestDto dto) {

        ApproveOrderBatchesResponseDto response = orderService.approveOrder(dto);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('DISTRIBUTOR')")
    @PostMapping("/request")
    public ResponseEntity<OrderResponseDto> requestOrder() {
        OrderResponseDto response = orderService.requestOrder();
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('CENTRAL_OFFICER')")
    @GetMapping("/co/orders")
    public ResponseEntity<PaginatedOrderResponseDto> getOrdersForCentralOfficer(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0")@Min(value = 0, message = "Page number cannot be negative") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "Size must be at least 1") int size) {

        PaginatedOrderResponseDto response =
                orderService.getOrdersForCentral(status, page, size);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('DISTRIBUTOR')")
        @GetMapping("/distributor/orders")
    public ResponseEntity<PaginatedOrderResponseDto> getDistributorOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginatedOrderResponseDto response = orderService.getOrdersForDistributor(page, size);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('DISTRIBUTOR')")
    @PostMapping("/confirm-payment")
    public ResponseEntity<String> confirmPayment(@RequestBody OrderPaymentRequestDto dto) {
        String msg = orderService.confirmPaymentAndReduceStock(dto);
        return ResponseEntity.ok(msg);
    }



}
