package com.cavcav.Eventify.category.controller;

import com.cavcav.Eventify.category.dto.*;
import com.cavcav.Eventify.category.service.CategoryService;
import com.cavcav.Eventify.user.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")

public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/createCategory")
    public ResponseEntity<ApiResponse<?>> create(@RequestBody CategoryRequest request) {
        ApiResponse<?> response = categoryService.create(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAll() {
        ApiResponse<List<CategoryResponse>> response = categoryService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getCategoryById/{id}")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable Long id) {
        ApiResponse<?> response = categoryService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        ApiResponse<?> response = categoryService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id) {
        ApiResponse<?> response = categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}