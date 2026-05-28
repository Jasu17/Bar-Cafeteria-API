package com.barcafe.product.repository;

import com.barcafe.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByDeletedFalse();

    Optional<Category> findByIdAndDeletedFalse(UUID id);

    boolean existsByNameAndDeletedFalse(String name);
}
