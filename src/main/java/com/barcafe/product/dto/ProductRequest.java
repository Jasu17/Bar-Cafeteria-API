package com.barcafe.product.dto;

import com.barcafe.product.entity.Temperature;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String description;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal price;

    @NotNull(message = "La temperatura es obligatoria")
    private Temperature temperature;

    @NotNull(message = "La categoría es obligatoria")
    private UUID categoryId;

    private UUID subCategoryId;
}
