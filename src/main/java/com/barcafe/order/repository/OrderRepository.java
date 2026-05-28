package com.barcafe.order.repository;


import com.barcafe.order.entity.Order;
import com.barcafe.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByDeletedFalse();

    List<Order> findAllByUserIdAndDeletedFalse(UUID userId);

    List<Order> findAllByStatusAndDeletedFalse(OrderStatus status);

    List<Order> findAllByStatusNotAndDeletedFalse(OrderStatus status);

    Optional<Order> findByIdAndDeletedFalse(UUID id);
}
