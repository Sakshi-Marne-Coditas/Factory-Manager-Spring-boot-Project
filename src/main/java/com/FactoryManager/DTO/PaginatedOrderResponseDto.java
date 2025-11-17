package com.FactoryManager.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedOrderResponseDto {

    private List<OrderListWithProductsDto> orders;

    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
}
