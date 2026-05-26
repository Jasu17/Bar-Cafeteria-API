package com.barcafe.product.repository;

import com.barcafe.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findAllByDeletedFalse();

    List<Product> findAllByAvailableTrueAndDeletedFalse();

    List<Product> findAllByCategoryIdAndDeletedFalse(UUID categoryId);

    Optional<Product> findByIdAndDeletedFalse(UUID id);
}
