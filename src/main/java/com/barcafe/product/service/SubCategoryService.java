package com.barcafe.product.service;

import com.barcafe.product.dto.SubCategoryRequest;
import com.barcafe.product.dto.SubCategoryResponse;
import com.barcafe.product.entity.Category;
import com.barcafe.product.entity.SubCategory;
import com.barcafe.product.repository.CategoryRepository;
import com.barcafe.product.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    public List<SubCategoryResponse> findAll() {
        return subCategoryRepository.findAllByDeletedFalse()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<SubCategoryResponse> findByCategory(UUID categoryId) {
        return subCategoryRepository.findAllByCategoryIdAndDeletedFalse(categoryId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SubCategoryResponse findById(UUID id) {
        SubCategory subCategory = subCategoryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));
        return toResponse(subCategory);
    }

    public SubCategoryResponse create(SubCategoryRequest request) {
        Category category = categoryRepository.findByIdAndDeletedFalse(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        SubCategory subCategory = SubCategory.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(category)
                .build();

        return toResponse(subCategoryRepository.save(subCategory));
    }

    public SubCategoryResponse update(UUID id, SubCategoryRequest request) {
        SubCategory subCategory = subCategoryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));

        Category category = categoryRepository.findByIdAndDeletedFalse(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        subCategory.setName(request.getName());
        subCategory.setDescription(request.getDescription());
        subCategory.setCategory(category);

        return toResponse(subCategoryRepository.save(subCategory));
    }

    public void toggle(UUID id) {
        SubCategory subCategory = subCategoryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));

        subCategory.setActive(!subCategory.isActive());
        subCategoryRepository.save(subCategory);
    }

    public void delete(UUID id) {
        SubCategory subCategory = subCategoryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));

        subCategory.setDeleted(true);
        subCategoryRepository.save(subCategory);
    }

    private SubCategoryResponse toResponse(SubCategory subCategory) {
        return SubCategoryResponse.builder()
                .id(subCategory.getId())
                .name(subCategory.getName())
                .description(subCategory.getDescription())
                .categoryId(subCategory.getCategory().getId())
                .categoryName(subCategory.getCategory().getName())
                .active(subCategory.isActive())
                .build();
    }
}