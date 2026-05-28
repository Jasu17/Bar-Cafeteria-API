package com.barcafe.ingredient.service;

import com.barcafe.ingredient.dto.IngredientRequest;
import com.barcafe.ingredient.dto.IngredientResponse;
import com.barcafe.ingredient.entity.Ingredient;
import com.barcafe.ingredient.repository.IngredientRepository;
import com.barcafe.product.entity.Product;
import com.barcafe.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final ProductRepository productRepository;

    public List<IngredientResponse> findAll() {
        return ingredientRepository.findAllByDeletedFalse()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public IngredientResponse findById(UUID id) {
        Ingredient ingredient = ingredientRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));
        return toResponse(ingredient);
    }

    public IngredientResponse create(IngredientRequest request) {
        if (ingredientRepository.existsByNameAndDeletedFalse(request.getName())) {
            throw new RuntimeException("Ya existe un ingrediente con ese nombre");
        }

        Ingredient ingredient = Ingredient.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        return toResponse(ingredientRepository.save(ingredient));
    }

    public IngredientResponse update(UUID id, IngredientRequest request) {
        Ingredient ingredient = ingredientRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        ingredient.setName(request.getName());
        ingredient.setDescription(request.getDescription());

        return toResponse(ingredientRepository.save(ingredient));
    }

    public void toggle(UUID id) {
        Ingredient ingredient = ingredientRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        ingredient.setAvailable(!ingredient.isAvailable());
        ingredientRepository.save(ingredient);

        // Si se deshabilita, deshabilitar productos afectados
        if (!ingredient.isAvailable()) {
            disableAffectedProducts(ingredient);
        }
    }

    private void disableAffectedProducts(Ingredient ingredient) {
        List<Product> affected = productRepository.findAllByDeletedFalse()
                .stream()
                .filter(p -> p.getIngredients().contains(ingredient))
                .collect(Collectors.toList());

        affected.forEach(p -> p.setAvailable(false));
        productRepository.saveAll(affected);
    }

    public void delete(UUID id) {
        Ingredient ingredient = ingredientRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        ingredient.setDeleted(true);
        ingredientRepository.save(ingredient);
    }

    private IngredientResponse toResponse(Ingredient ingredient) {
        return IngredientResponse.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .description(ingredient.getDescription())
                .available(ingredient.isAvailable())
                .build();
    }
}