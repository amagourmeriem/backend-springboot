package com.example.marsamaroc.service;

import com.example.marsamaroc.dao.entities.CategorieEngin;

import java.util.List;
import java.util.Optional;

public interface CategoryEnginManager {
    List<CategorieEngin> getAllCategories();
    CategorieEngin addCategory(CategorieEngin category);
    CategorieEngin updateCategory(CategorieEngin category, Long id);
    void deleteCategory(Long id);
    Optional<CategorieEngin> getCategoryById(Long id);
    CategorieEngin saveCategory(CategorieEngin category);
}

