package com.cavcav.Eventify.category.dto;

import lombok.Data;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private String color;
}