package com.barcafe.ingredient.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String description;
}