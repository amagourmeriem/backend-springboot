package com.example.marsamaroc.mappers;

import com.example.marsamaroc.dao.entities.CategorieEngin;
import com.example.marsamaroc.dtos.CategorieEnginDTO;
import org.springframework.stereotype.Component;

@Component
public class CategorieEnginMapper {
    public static CategorieEnginDTO toDTO(CategorieEngin categorieEngin) {
        return new CategorieEnginDTO(
                categorieEngin.getId(),
                categorieEngin.getNom(),
                categorieEngin.getNbrEngin()
        );
    }

    public CategorieEnginDTO toCategorieEnginDto(CategorieEngin categorieEngin) {
        if (categorieEngin == null) {
            return null;
        }
        CategorieEnginDTO dto = new CategorieEnginDTO();
        dto.setId(categorieEngin.getId());
        dto.setNom(categorieEngin.getNom());
        dto.setNbrEngin(categorieEngin.getNbrEngin());
        return dto;
    }

    public CategorieEngin toCategorieEngin(CategorieEnginDTO dto) {
        if (dto == null) {
            return null;
        }
        CategorieEngin categorieEngin = new CategorieEngin();
        categorieEngin.setId(dto.getId());
        categorieEngin.setNom(dto.getNom());
        categorieEngin.setNbrEngin(dto.getNbrEngin());
        return categorieEngin;
    }
}
