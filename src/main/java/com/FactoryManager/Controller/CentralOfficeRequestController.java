package com.FactoryManager.Controller;

import com.FactoryManager.Constatnts.RequestStatus;
import com.FactoryManager.DTO.*;
import com.FactoryManager.Service.CentralOfficeRequestService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.FactoryManager.DTO.CentralOfficeRequestResponseDto;


import java.util.List;

@RestController
@Validated
@RequestMapping("/api/central-office-request")
public class CentralOfficeRequestController {

    @Autowired
    private CentralOfficeRequestService centralOfficeRequestService;

    @PreAuthorize("hasRole('CENTRAL_OFFICER')")
    @PostMapping("/create")
    public ResponseEntity<CentralOfficeRequestResponseDto> createRequest(
            @RequestBody CentralOfficeRequestDto dto) {

        CentralOfficeRequestResponseDto response = centralOfficeRequestService.createRequest(dto);

        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAnyRole('CENTRAL_OFFICER', 'PLANT_HEAD')")
    @GetMapping("/pending")
    public ResponseEntity<List<CentralOfficeRequestResponseDto>> getAllPendingRequests() {
        List<CentralOfficeRequestResponseDto> response = centralOfficeRequestService.getAllPendingRequests();
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('PLANT_HEAD')")
    @PatchMapping("/update-status/{id}")
    public ResponseEntity<UpdateResponseStatus> updateRequestStatus(
            @PathVariable @Min(value = 1, message = "ID must be 1 or greater")Long id,
            @RequestBody UpdateRequestStatusDto dto) {

        UpdateResponseStatus response = centralOfficeRequestService.updateRequestStatus(id, dto);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('PLANT_HEAD')")
    @GetMapping("/plant-head/req")
    public ResponseEntity<Page<CentralOfficeRequestResponseDto>> getAllForPlantHeadReq(

            @RequestParam(defaultValue = "all") String status,
            @RequestParam(required = false) String search,
            Pageable pageable
    ) {

        Page<CentralOfficeRequestResponseDto> response =
                centralOfficeRequestService.getRequestsForPlantHead(pageable, status, search);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CENTRAL_OFFICER')")
    @GetMapping("/total-products-qty")
    public ResponseEntity<Page<ProductTotalQuantityResDto>> getTotalProductQuantities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search
    ) {
        Page<ProductTotalQuantityResDto> totals = centralOfficeRequestService.getAllProductTotals(search, page, size);
        return ResponseEntity.ok(totals);
    }

    @PreAuthorize("hasRole('CENTRAL_OFFICER')")
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
