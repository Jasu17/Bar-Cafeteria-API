package com.barcafe.product.repository;

import com.barcafe.product.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID>{

    List<SubCategory> findAllByDeletedFalse();

    List<SubCategory> findAllByCategoryIdAndDeletedFalse(UUID categoryId);

    Optional<SubCategory> findByIdAndDeletedFalse(UUID id);
}
