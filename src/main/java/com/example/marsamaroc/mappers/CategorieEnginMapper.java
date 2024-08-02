package com.example.marsamaroc.mappers;

import com.example.marsamaroc.dao.entities.CategorieEngin;
import com.example.marsamaroc.dtos.CategorieEnginDTO;

public class CategorieEnginMapper {
    public static CategorieEnginDTO toDTO(CategorieEngin categorieEngin) {
        return new CategorieEnginDTO(
                categorieEngin.getId(),
                categorieEngin.getNom(),
                categorieEngin.getNbrEngin()
        );
    }
}