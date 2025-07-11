package com.cavcav.Eventify.category.repository;

import com.cavcav.Eventify.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
