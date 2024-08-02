package com.example.marsamaroc.dtos;

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
}