package com.barcafe.ingredient.repository;

import com.barcafe.ingredient.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

    List<Ingredient> findAllByDeletedFalse();

    Optional<Ingredient> findByIdAndDeletedFalse(UUID id);

    boolean existsByNameAndDeletedFalse(String name);
}
