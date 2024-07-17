package com.example.marsamaroc.service;

import com.example.marsamaroc.dao.entities.CategorieEngin;
import com.example.marsamaroc.dao.repositories.CategoryEnginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryEnginService implements CategoryEnginManager {

    private final CategoryEnginRepository categoryEnginRepository;

    @Override
    public List<CategorieEngin> getAllCategories() {
        return categoryEnginRepository.findAll();
    }

    @Override
    public CategorieEngin addCategory(CategorieEngin category) {
        return categoryEnginRepository.save(category);
    }

    @Override
    public CategorieEngin updateCategory(CategorieEngin categoryDetails, Long id) {
        Optional<CategorieEngin> optionalCategory = categoryEnginRepository.findById(id);
        if (optionalCategory.isPresent()) {
            CategorieEngin category = optionalCategory.get();
            category.setNom(categoryDetails.getNom());
            return categoryEnginRepository.save(category);
        } else {
            throw new RuntimeException("Category not found with id " + id);
        }
    }

    @Override
    public void deleteCategory(Long id) {
        categoryEnginRepository.deleteById(id);
    }

    @Override
    public Optional<CategorieEngin> getCategoryById(Long id) {
        return categoryEnginRepository.findById(id);
    }

    @Override
    public CategorieEngin saveCategory(CategorieEngin category) {
        return categoryEnginRepository.save(category);
    }
}
