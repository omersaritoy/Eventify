package com.cavcav.Eventify.category.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private String color;
    private boolean active;
    private LocalDateTime createdAt;
}
