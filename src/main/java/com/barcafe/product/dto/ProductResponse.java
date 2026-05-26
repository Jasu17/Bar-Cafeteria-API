package com.barcafe.product.dto;

import com.barcafe.product.entity.Temperature;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class ProductResponse {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Temperature temperature;
    private String categoryName;
    private String subCategoryName;
    private boolean available;
}
