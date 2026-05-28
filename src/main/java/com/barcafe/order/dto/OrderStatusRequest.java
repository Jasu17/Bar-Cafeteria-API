package com.barcafe.order.dto;

import com.barcafe.order.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusRequest {

    @NotNull(message = "El estado es obligatorio")
    private OrderStatus status;
}
