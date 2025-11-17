package com.FactoryManager.Controller;

import com.FactoryManager.DTO.*;
import com.FactoryManager.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
   private OrderService orderService;

    @PostMapping("/approve")
    public ResponseEntity<ApproveOrderBatchesResponseDto> createBatches(
            @RequestBody ApproveOrderBatchesRequestDto dto) {

        ApproveOrderBatchesResponseDto response = orderService.approveOrder(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/request")
    public ResponseEntity<OrderResponseDto> requestOrder() {
        OrderResponseDto response = orderService.requestOrder();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/co/orders")
    public ResponseEntity<PaginatedOrderResponseDto> getOrdersForCentralOfficer(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PaginatedOrderResponseDto response =
                orderService.getOrdersForCentral(status, page, size);

        return ResponseEntity.ok(response);
    }

        @GetMapping("/distributor/orders")
    public ResponseEntity<PaginatedOrderResponseDto> getDistributorOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginatedOrderResponseDto response = orderService.getOrdersForDistributor(page, size);
        return ResponseEntity.ok(response);
    }


}
