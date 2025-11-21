package com.FactoryManager.Service;

import com.FactoryManager.Constatnts.RequestStatus;
import com.FactoryManager.DTO.*;
import com.FactoryManager.Entity.CentralOfficeRequest;
import com.FactoryManager.Entity.FactoryProduct;
import com.FactoryManager.Entity.Product;
import com.FactoryManager.Entity.User;
import com.FactoryManager.Repository.CentralOfficeRequestRepository;
import com.FactoryManager.Repository.FactoryProductRepository;
import com.FactoryManager.Repository.ProductRepository;
import com.FactoryManager.Repository.UserRepository;
import com.FactoryManager.exceptionHandling.ElementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CentralOfficeRequestService {

    @Autowired
    private CentralOfficeRequestRepository centralOfficeRequestRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FactoryProductRepository factoryProductRepository;

    public CentralOfficeRequestResponseDto createRequest(CentralOfficeRequestDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User centralOfficer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + dto.getProductId()));

        CentralOfficeRequest request = new CentralOfficeRequest();
        request.setProduct(product);
        request.setQty(dto.getRequiredQuantity());
        request.setRequestedBy(centralOfficer);

        request.setRequestStatus(RequestStatus.PENDING);


        CentralOfficeRequest saved = centralOfficeRequestRepository.save(request);


        CentralOfficeRequestResponseDto response = new CentralOfficeRequestResponseDto();
        response.setId(saved.getId());
        response.setProductName(saved.getProduct().getProductName());
        response.setRequiredQuantity(saved.getQty());
        response.setImage(saved.getProduct().getProductImage());
        response.setStatus(saved.getRequestStatus().getValue());

        return response;
    }

    public List<CentralOfficeRequestResponseDto> getAllPendingRequests() {
        List<CentralOfficeRequest> pendingRequests =
                centralOfficeRequestRepository.findByRequestStatus(RequestStatus.PENDING);

        return pendingRequests.stream()
                .map(req -> new CentralOfficeRequestResponseDto(
                        req.getId(),
                        req.getProduct().getProductName(),
                        req.getQty(),
                        req.getRequestStatus().getValue(),
                        req.getProduct().getProductImage()
                ))
                .collect(Collectors.toList());
    }

    public UpdateResponseStatus updateRequestStatus(Long requestId, UpdateRequestStatusDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        CentralOfficeRequest request = centralOfficeRequestRepository.findById(requestId)
                .orElseThrow(() -> new ElementNotFoundException("Request not found with ID: " + requestId));

        User approver = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (dto.getStatus() == RequestStatus.APPROVED) {
            request.setRequestStatus(RequestStatus.APPROVED);
            request.setApprovedBy(approver);
        } else if (dto.getStatus() == RequestStatus.REJECTED) {
            request.setRequestStatus(RequestStatus.REJECTED);
            request.setApprovedBy(approver);
            request.setReason(dto.getReason());
        } else {
            throw new RuntimeException("Invalid status value!");
        }

        CentralOfficeRequest updated = centralOfficeRequestRepository.save(request);

        UpdateResponseStatus response = new UpdateResponseStatus();
        response.setId(updated.getId());
        response.setProductName(updated.getProduct().getProductName());
        response.setStatus(updated.getRequestStatus().name());
        response.setReqQty(request.getQty());
        response.setRequestedBy(request.getRequestedBy().getUsername());
        response.setReason(request.getReason());
        response.setApproveBy(approver.getUsername());
        return response;
    }

    public Page<CentralOfficeRequestResponseDto> getRequestsForPlantHead(
            Pageable pageable,
            String status,
            String search
    ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User plantHead = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));


        Page<CentralOfficeRequest> requests;

        if (status == null || status.equalsIgnoreCase("all")) {
            requests = centralOfficeRequestRepository.findAllPendingOrApprovedBy(plantHead, pageable);
        } else {

            RequestStatus requestStatus;
            try {
                requestStatus = RequestStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid status: " + status);
            }

            if (requestStatus == RequestStatus.PENDING) {
                requests = centralOfficeRequestRepository.findByRequestStatus(RequestStatus.PENDING, pageable);
            } else {
                requests = centralOfficeRequestRepository.findByRequestStatusAndApprovedBy(requestStatus, plantHead, pageable);
            }
        }



        List<CentralOfficeRequest> list = requests.getContent();



        if (search != null && !search.trim().isEmpty()) {
            String s = search.toLowerCase();

            list = list.stream()
                    .filter(r ->
                            r.getProduct().getProductName().toLowerCase().contains(s)
                    )
                    .toList();
        }


        //Apply Sorting (In-Memory)
        if (pageable.getSort().isSorted()) {

            Comparator<CentralOfficeRequest> comparator = null;

            for (Sort.Order order : pageable.getSort()) {
                String property = order.getProperty();
                boolean asc = order.isAscending();

                Comparator<CentralOfficeRequest> temp = switch (property) {
                    case "qty" ->
                            Comparator.comparing(CentralOfficeRequest::getQty);
                    case "productName" ->
                            Comparator.comparing(r -> r.getProduct().getProductName().toLowerCase());
                    case "requestStatus" ->
                            Comparator.comparing(r -> r.getRequestStatus().name());
                    default ->
                            null; // ignore if unknown
                };

                if (temp != null) {
                    if (!asc) temp = temp.reversed();

                    comparator = (comparator == null) ? temp : comparator.thenComparing(temp);
                }
            }

            if (comparator != null) {
                list = list.stream().sorted(comparator).toList();
            }
        }


        // 5️⃣ Convert back to Page manually
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), list.size());

        List<CentralOfficeRequest> finalList =
                (start <= end) ? list.subList(start, end) : List.of();

        return new PageImpl<>(
                finalList.stream()
                        .map(req -> new CentralOfficeRequestResponseDto(
                                req.getId(),
                                req.getProduct().getProductName(),
                                req.getQty(),
                                req.getRequestStatus().name(),
                                req.getProduct().getProductImage()
                        )).toList(),
                pageable,
                list.size()
        );
    }


    public Page<ProductTotalQuantityResDto> getAllProductTotals(String search, int page, int size) {

        List<FactoryProduct> factoryProducts = factoryProductRepository.findAll();

        Map<Long, Long> totalQtyMap = factoryProducts.stream()
                .collect(Collectors.groupingBy(
                        fp -> fp.getProduct().getId(),
                        Collectors.summingLong(FactoryProduct::getQuantity)
                ));

        List<ProductTotalQuantityResDto> dtoList = totalQtyMap.entrySet().stream()
                .map(entry -> {
                    FactoryProduct sample = factoryProducts.stream()
                            .filter(fp -> fp.getProduct().getId().equals(entry.getKey()))
                            .findFirst()
                            .orElse(null);

                    if (sample == null) return null;

                    return new ProductTotalQuantityResDto(
                            entry.getKey(),
                            sample.getProduct().getProductName(),
                            sample.getProduct().getCategory().getCategoryName(),
                            sample.getProduct().getProductPrice(),
                            sample.getProduct().getProductImage(),
                            entry.getValue()
                    );
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (search != null && !search.isBlank()) {
            dtoList = dtoList.stream()
                    .filter(dto -> dto.getProductName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        dtoList.sort(Comparator.comparing(ProductTotalQuantityResDto::getProductName));


        int start = Math.min(page * size, dtoList.size());
        int end = Math.min(start + size, dtoList.size());
        List<ProductTotalQuantityResDto> paginated = dtoList.subList(start, end);

        return new PageImpl<>(paginated, PageRequest.of(page, size), dtoList.size());
    }

    private CentralOfficeRequestResponseDto mapToDto(CentralOfficeRequest req) {
        return new CentralOfficeRequestResponseDto(
                req.getId(),
                req.getProduct().getProductName(),
                req.getQty(),
                req.getRequestStatus().getValue(),
                req.getProduct().getProductImage()
        );
    }

    public Page<CentralOfficeRequestResponseDto> getRequests(RequestStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<CentralOfficeRequest> requestsPage;

        if (status != null) {
            requestsPage = centralOfficeRequestRepository.findByRequestStatus(status, pageable);
        } else {
            requestsPage = centralOfficeRequestRepository.findAll(pageable);
        }

        List<CentralOfficeRequestResponseDto> dtoList = requestsPage.getContent()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, requestsPage.getTotalElements());
    }




}
