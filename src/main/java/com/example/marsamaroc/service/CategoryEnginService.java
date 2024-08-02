// src/main/java/com/example/marsamaroc/service/CategoryEnginService.java
package com.example.marsamaroc.service;

import com.example.marsamaroc.dao.entities.CategorieEngin;
import com.example.marsamaroc.dao.repositories.CategoryEnginRepository;
import com.example.marsamaroc.dtos.CategorieEnginDTO;
import com.example.marsamaroc.mappers.CategorieEnginMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryEnginService {
    private final CategoryEnginRepository categoryEnginRepository;

    public List<CategorieEnginDTO> getAllCategories() {
        List<CategorieEngin> categories = categoryEnginRepository.findAll();
        return categories.stream()
                .map(CategorieEnginMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CategorieEnginDTO addCategory(CategorieEnginDTO categorieEnginDTO) {
        CategorieEngin categorieEngin = new CategorieEngin();
        categorieEngin.setNom(categorieEnginDTO.getNom());
        categorieEngin.setNbrEngin(categorieEnginDTO.getNbrEngin());
        CategorieEngin savedCategory = categoryEnginRepository.save(categorieEngin);
        return CategorieEnginMapper.toDTO(savedCategory);
    }

    public CategorieEnginDTO updateCategory(Long id, CategorieEnginDTO categorieEnginDTO) {
        Optional<CategorieEngin> existingCategory = categoryEnginRepository.findById(id);
        if (existingCategory.isPresent()) {
            CategorieEngin categorieEngin = existingCategory.get();
            categorieEngin.setNom(categorieEnginDTO.getNom());
            categorieEngin.setNbrEngin(categorieEnginDTO.getNbrEngin());
            CategorieEngin updatedCategory = categoryEnginRepository.save(categorieEngin);
            return CategorieEnginMapper.toDTO(updatedCategory);
        } else {
            throw new RuntimeException("Category not found with id " + id);
        }
    }

    public void deleteCategory(Long id) {
        categoryEnginRepository.deleteById(id);
    }
}
