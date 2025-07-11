package com.cavcav.Eventify.service;

import com.cavcav.Eventify.category.dto.*;
import com.cavcav.Eventify.category.mapper.CategoryMapper;
import com.cavcav.Eventify.category.model.Category;
import com.cavcav.Eventify.category.repository.CategoryRepository;
import com.cavcav.Eventify.user.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public ApiResponse<CategoryResponse> create(CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        Category saved = categoryRepository.save(category);
        return ApiResponse.<CategoryResponse>builder()
                .success(true)
                .message("Category created")
                .data(categoryMapper.toResponse(saved))
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    public ApiResponse<List<CategoryResponse>> getAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> responses = categories.stream()
                .map(categoryMapper::toResponse)
                .toList();
        return  ApiResponse.<List<CategoryResponse>>builder()
                .success(true)
                .message("Getting All Category is Successfull")
                .data(responses)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
    public ApiResponse<CategoryResponse> getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"));
        return ApiResponse.<CategoryResponse>builder()
                .success(true)
                .message("Category fetched successfully")
                .data(categoryMapper.toResponse(category))
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    public ApiResponse<CategoryResponse> update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"));
        categoryMapper.updateEntityFromDto(request, category);
        Category updated = categoryRepository.save(category);
        return ApiResponse.<CategoryResponse>builder()
                .success(true)
                .message("Category updated successfully")
                .data(categoryMapper.toResponse(updated))
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

    public ApiResponse<Void> delete(Long id) {
        categoryRepository.deleteById(id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Category deleted successfully")
                .data(null)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
}
