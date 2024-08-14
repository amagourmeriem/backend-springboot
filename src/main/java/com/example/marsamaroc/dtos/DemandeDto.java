package com.example.marsamaroc.dtos;

import com.example.marsamaroc.dao.entities.Demande;
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
    private String CategorieEnginNom;
    private boolean affecte;


    private DemandeDto mapToDto(Demande demande) {
        DemandeDto demandeDto = new DemandeDto();
        demandeDto.setId(demande.getId());
        demandeDto.setNumeroBCI(demande.getNumeroBCI());
        demandeDto.setNomDepartement(demande.getNomDepartement());
        demandeDto.setNomDemandeur(demande.getNomDemandeur());
        demandeDto.setDateSortie(demande.getDateSortie());
        demandeDto.setShift(demande.getShift());
        demandeDto.setSaul(demande.getSaul());
        demandeDto.setObservations(demande.getObservations());
        demandeDto.setStatus(demande.getStatus());
        demandeDto.setCategorieEnginId(demande.getCategorieEngin() != null ? demande.getCategorieEngin().getId() : null);
        demandeDto.setCategorieEnginNom(demande.getCategorieEngin() != null ? demande.getCategorieEngin().getNom() : null);
        demandeDto.setEnginId(demande.getEngin() != null ? demande.getEngin().getId() : null);
        demandeDto.setUserId(demande.getUser() != null ? demande.getUser().getId() : null);
        demandeDto.setAffecte(demande.getStatus() != Status.PENDING); // Just an example, adjust as needed
        return demandeDto;
    }

}
