package com.barcafe.order.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {

    @NotEmpty(message = "El pedido debe tener al menos un producto")
    private List<OrderItemRequest> items;
}
