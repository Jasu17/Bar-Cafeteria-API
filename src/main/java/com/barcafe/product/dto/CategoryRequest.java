package com.barcafe.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String description;
}
