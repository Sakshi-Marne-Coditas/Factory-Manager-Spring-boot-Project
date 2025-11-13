package com.FactoryManager.Controller;

import com.FactoryManager.Constatnts.RequestStatus;
import com.FactoryManager.DTO.*;
import com.FactoryManager.Service.CentralOfficeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.FactoryManager.DTO.CentralOfficeRequestResponseDto;


import java.util.List;

@RestController
@RequestMapping("/api/central-office-request")
public class CentralOfficeRequestController {

    @Autowired
    private CentralOfficeRequestService centralOfficeRequestService;

    @PostMapping("/create")
    public ResponseEntity<CentralOfficeRequestResponseDto> createRequest(
            @RequestBody CentralOfficeRequestDto dto) {

        CentralOfficeRequestResponseDto response = centralOfficeRequestService.createRequest(dto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<CentralOfficeRequestResponseDto>> getAllPendingRequests() {
        List<CentralOfficeRequestResponseDto> response = centralOfficeRequestService.getAllPendingRequests();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update-status/{id}")
    public ResponseEntity<UpdateResponseStatus> updateRequestStatus(
            @PathVariable Long id,
            @RequestBody UpdateRequestStatusDto dto) {

        UpdateResponseStatus response = centralOfficeRequestService.updateRequestStatus(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/plant-head/req")
    public ResponseEntity<Page<CentralOfficeRequestResponseDto>> getAllForPlantHeadReq(
            @RequestParam(defaultValue = "all") String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CentralOfficeRequestResponseDto> response =
                centralOfficeRequestService.getRequestsForPlantHead(pageable, status);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/total-products-qty")
    public ResponseEntity<Page<ProductTotalQuantityResDto>> getTotalProductQuantities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search
    ) {
        Page<ProductTotalQuantityResDto> totals = centralOfficeRequestService.getAllProductTotals(search, page, size);
        return ResponseEntity.ok(totals);
    }

    @GetMapping("/a")
    public ResponseEntity<Page<CentralOfficeRequestResponseDto>> getRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) RequestStatus status
    ) {
        Page<CentralOfficeRequestResponseDto> result = centralOfficeRequestService.getRequests(status, page, size);
        return ResponseEntity.ok(result);
    }
}
