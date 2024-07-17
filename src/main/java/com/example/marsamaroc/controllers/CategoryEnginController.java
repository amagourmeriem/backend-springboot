package com.example.marsamaroc.controllers;

import com.example.marsamaroc.dao.entities.CategorieEngin;
import com.example.marsamaroc.service.CategoryEnginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:3000") // Allowing client application to consume the backend
@RestController
@RequestMapping("/categories_engins")
@RequiredArgsConstructor
public class CategoryEnginController {

    private final CategoryEnginService categoryEnginService;

    @GetMapping
    public ResponseEntity<List<CategorieEngin>> getAllCategories() {
        List<CategorieEngin> categories = categoryEnginService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieEngin> getCategoryById(@PathVariable Long id) {
        Optional<CategorieEngin> category = categoryEnginService.getCategoryById(id);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategorieEngin> addCategory(@RequestBody CategorieEngin category) {
        CategorieEngin createdCategory = categoryEnginService.addCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategorieEngin> updateCategory(@RequestBody CategorieEngin category, @PathVariable Long id) {
        CategorieEngin updatedCategory = categoryEnginService.updateCategory(category, id);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryEnginService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
