package com.barcafe.product.controller;

import com.barcafe.product.dto.SubCategoryRequest;
import com.barcafe.product.dto.SubCategoryResponse;
import com.barcafe.product.service.SubCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subcategories")
@RequiredArgsConstructor
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @GetMapping
    public ResponseEntity<List<SubCategoryResponse>> findAll() {
        return ResponseEntity.ok(subCategoryService.findAll());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<SubCategoryResponse>> findByCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(subCategoryService.findByCategory(categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(subCategoryService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubCategoryResponse> create(@Valid @RequestBody SubCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subCategoryService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubCategoryResponse> update(@PathVariable UUID id,
                                                      @Valid @RequestBody SubCategoryRequest request) {
        return ResponseEntity.ok(subCategoryService.update(id, request));
    }

    @PatchMapping("/{id}/toggle")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> toggle(@PathVariable UUID id) {
        subCategoryService.toggle(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        subCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}