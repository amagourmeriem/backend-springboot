// src/main/java/com/example/marsamaroc/controllers/CategoryEnginController.java
package com.example.marsamaroc.controllers;

import com.example.marsamaroc.dtos.CategorieEnginDTO;
import com.example.marsamaroc.service.CategoryEnginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000") // Allowing client application to consume the backend
@RestController
@RequestMapping("/categories_engins")
@RequiredArgsConstructor
public class CategoryEnginController {

    private final CategoryEnginService categoryEnginService;

    @GetMapping
    public ResponseEntity<List<CategorieEnginDTO>> getAllCategories() {
        List<CategorieEnginDTO> categories = categoryEnginService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategorieEnginDTO> addCategory(@RequestBody CategorieEnginDTO category) {
        CategorieEnginDTO createdCategory = categoryEnginService.addCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategorieEnginDTO> updateCategory(@PathVariable Long id, @RequestBody CategorieEnginDTO category) {
        CategorieEnginDTO updatedCategory = categoryEnginService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryEnginService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
