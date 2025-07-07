package com.cavcav.Eventify.category.mapper;

import com.cavcav.Eventify.category.dto.CategoryDto;
import com.cavcav.Eventify.category.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto dto);
}