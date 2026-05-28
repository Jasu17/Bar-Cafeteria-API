package com.barcafe.order.dto;

import com.barcafe.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class OrderResponse {
    private UUID id;
    private String customerName;
    private OrderStatus status;
    private List<OrderDetailResponse> details;
    private BigDecimal total;
    private LocalDateTime createdAt;
}
