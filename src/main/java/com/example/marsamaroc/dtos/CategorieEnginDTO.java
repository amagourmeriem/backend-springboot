package com.example.marsamaroc.dtos;

import com.example.marsamaroc.dao.entities.CategorieEngin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorieEnginDTO {
    private Long id;
    private String nom;
    private int nbrEngin;

    public CategorieEnginDTO(CategorieEngin categorieEngin) {
        this.id = categorieEngin.getId();
        this.nom = categorieEngin.getNom();
    }
}