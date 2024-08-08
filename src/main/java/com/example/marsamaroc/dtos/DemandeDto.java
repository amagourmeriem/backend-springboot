package com.example.marsamaroc.dtos;

import com.example.marsamaroc.dao.entities.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class DemandeDto {
    private Long id;
    private String numeroBCI;
    private String nomDepartement;
    private String nomDemandeur;
    private Date dateSortie;
    private String shift;
    private String saul;
    private String observations;
    private Long categorieEnginId;
    private Long enginId;
    private Status status;
    private Long userId;
    private String login;
    // Ajout du champ login


    // Getters et setters

    // Autres getters et setters
}
