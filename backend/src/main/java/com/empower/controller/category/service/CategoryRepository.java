package com.empower.controller.category.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empower.controller.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    Category findByName(String name);
    boolean existsByName(String category);
}
