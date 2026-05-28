package com.barcafe.ingredient.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class IngredientResponse {
    private UUID id;
    private String name;
    private String description;
    private boolean available;
}
