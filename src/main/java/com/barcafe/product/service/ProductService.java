package com.barcafe.product.service;

import com.barcafe.product.dto.ProductRequest;
import com.barcafe.product.dto.ProductResponse;
import com.barcafe.product.entity.Category;
import com.barcafe.product.entity.Product;
import com.barcafe.product.entity.SubCategory;
import com.barcafe.product.repository.CategoryRepository;
import com.barcafe.product.repository.ProductRepository;
import com.barcafe.product.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public List<ProductResponse> findAll() {
        return productRepository.findAllByDeletedFalse()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findAvailable() {
        return productRepository.findAllByAvailableTrueAndDeletedFalse()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategory(UUID categoryId) {
        return productRepository.findAllByCategoryIdAndDeletedFalse(categoryId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse findById(UUID id) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toResponse(product);
    }

    public ProductResponse create(ProductRequest request) {
        Category category = categoryRepository.findByIdAndDeletedFalse(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        SubCategory subCategory = null;
        if (request.getSubCategoryId() != null) {
            subCategory = subCategoryRepository.findByIdAndDeletedFalse(request.getSubCategoryId())
                    .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));
        }

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .temperature(request.getTemperature())
                .category(category)
                .subCategory(subCategory)
                .build();

        return toResponse(productRepository.save(product));
    }

    public ProductResponse update(UUID id, ProductRequest request) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Category category = categoryRepository.findByIdAndDeletedFalse(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        SubCategory subCategory = null;
        if (request.getSubCategoryId() != null) {
            subCategory = subCategoryRepository.findByIdAndDeletedFalse(request.getSubCategoryId())
                    .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setTemperature(request.getTemperature());
        product.setCategory(category);
        product.setSubCategory(subCategory);

        return toResponse(productRepository.save(product));
    }

    public void toggle(UUID id) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setAvailable(!product.isAvailable());
        productRepository.save(product);
    }

    public void delete(UUID id) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setDeleted(true);
        productRepository.save(product);
    }

    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .temperature(product.getTemperature())
                .categoryName(product.getCategory().getName())
                .subCategoryName(product.getSubCategory() != null
                        ? product.getSubCategory().getName()
                        : null)
                .available(product.isAvailable())
                .build();
    }

}
