package com.barcafe.order.service;

import com.barcafe.order.dto.*;
import com.barcafe.order.entity.Order;
import com.barcafe.order.entity.OrderDetail;
import com.barcafe.order.entity.OrderStatus;
import com.barcafe.order.repository.OrderRepository;
import com.barcafe.product.entity.Product;
import com.barcafe.product.repository.ProductRepository;
import com.barcafe.user.entity.User;
import com.barcafe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderResponse create(OrderRequest request, String email) {
        User user = userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Order order = Order.builder()
                .user(user)
                .build();

        List<OrderDetail> details = request.getItems().stream()
                .map(item -> {
                    Product product = productRepository.findByIdAndDeletedFalse(item.getProductId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getProductId()));

                    if (!product.isAvailable()) {
                        throw new RuntimeException("Producto no disponible: " + product.getName());
                    }

                    return OrderDetail.builder()
                            .order(order)
                            .product(product)
                            .quantity(item.getQuantity())
                            .unitPrice(product.getPrice())
                            .build();
                })
                .collect(Collectors.toList());

        order.setDetails(details);
        return toResponse(orderRepository.save(order));
    }

    public List<OrderResponse> findMyOrders(String email) {
        User user = userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return orderRepository.findAllByUserIdAndDeletedFalse(user.getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> findActive() {
        return orderRepository.findAllByStatusNotAndDeletedFalse(OrderStatus.DELIVERED)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(UUID id) {
        Order order = orderRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return toResponse(order);
    }

    public OrderResponse updateStatus(UUID id, OrderStatusRequest request, String email) {
        Order order = orderRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        validateStatusTransition(order.getStatus(), request.getStatus());

        order.setStatus(request.getStatus());
        return toResponse(orderRepository.save(order));
    }

    private void validateStatusTransition(OrderStatus current, OrderStatus next) {
        boolean valid = switch (current) {
            case PENDING -> next == OrderStatus.IN_PREPARATION;
            case IN_PREPARATION -> next == OrderStatus.DONE;
            case DONE -> next == OrderStatus.DELIVERED;
            case DELIVERED -> false;
        };

        if (!valid) {
            throw new RuntimeException(
                    "Transición inválida: " + current + " → " + next
            );
        }
    }

    private OrderResponse toResponse(Order order) {
        List<OrderDetailResponse> details = order.getDetails().stream()
                .map(d -> OrderDetailResponse.builder()
                        .productId(d.getProduct().getId())
                        .productName(d.getProduct().getName())
                        .quantity(d.getQuantity())
                        .unitPrice(d.getUnitPrice())
                        .subtotal(d.getUnitPrice().multiply(BigDecimal.valueOf(d.getQuantity())))
                        .build())
                .collect(Collectors.toList());

        BigDecimal total = details.stream()
                .map(OrderDetailResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getUser().getName())
                .status(order.getStatus())
                .details(details)
                .total(total)
                .createdAt(order.getCreatedAt())
                .build();
    }
}
