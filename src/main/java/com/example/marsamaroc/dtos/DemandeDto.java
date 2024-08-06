package com.example.marsamaroc.dtos;

import com.example.marsamaroc.dao.entities.CategorieEngin;
import com.example.marsamaroc.dao.entities.Demande;
import com.example.marsamaroc.dao.entities.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Date;

@Data
public class DemandeDto {
    private Long id;
    private String numeroBCI;
    private String nomDepartement;
    private String nomDemandeur;
    private Date dateSortie;
    private String shift;
    private String saul;
    private String observations;
    private Long categorieEnginId; // Utiliser l'ID de l'engin
    private Status status = Status.PENDING;
    private Long enginId;
    private UserDto user; // Utiliser UserDto pour représenter l'utilisateur
    private Long userId;
    private String login;
    private EnginDto enginDto;
    private CategorieEnginDTO categorieEnginDTO; // Utiliser CategorieEnginDTO pour représenter la catégorie d'engin

}
