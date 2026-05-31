package com.barcafe.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderItemRequest {

    @NotNull(message = "El producto es obligatorio")
    private UUID productId;

    @Min(value = 1, message = "La cantidad mínima es 1")
    private int quantity;
}