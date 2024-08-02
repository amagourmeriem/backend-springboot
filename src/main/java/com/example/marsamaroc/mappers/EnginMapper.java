package com.example.marsamaroc.mappers;

import com.example.marsamaroc.dao.entities.CategorieEngin;
import com.example.marsamaroc.dao.entities.Engin;
import com.example.marsamaroc.dtos.EnginDto;

public class EnginMapper {
    public static EnginDto toDto(Engin engin) {
        EnginDto dto = new EnginDto();
        dto.setId(engin.getId());
        dto.setCode(engin.getCode());
        dto.setMatricule(engin.getMatricule());
        dto.setCompteurHoraire(engin.getCompteurHoraire());
        dto.setEtatFrein(engin.getEtatFrein());
        dto.setEtatBatterie(engin.getEtatBatterie());
        dto.setEtatEclairage(engin.getEtatEclairage());
        dto.setEtatEssuieGlace(engin.getEtatEssuieGlace());
        dto.setEtatTracteur(engin.getEtatTracteur());
        dto.setEtatPneumatique(engin.getEtatPneumatique());
        dto.setEtatTransmission(engin.getEtatTransmission());
        dto.setEtatFreinService(engin.getEtatFreinService());
        dto.setEtatFreinParking(engin.getEtatFreinParking());
        dto.setEtatKlaxon(engin.getEtatKlaxon());
        dto.setEtatCablage(engin.getEtatCablage());
        dto.setEtatVitesse(engin.getEtatVitesse());
        dto.setObservationsGenerales(engin.getObservationsGenerales());
        dto.setImage(engin.getImage());
        dto.setCategorieEnginId(engin.getCategorieEngin().getId());
        return dto;
    }

    public static Engin toEntity(EnginDto dto) {
        Engin engin = new Engin();
        engin.setId(dto.getId());
        engin.setCode(dto.getCode());
        engin.setMatricule(dto.getMatricule());
        engin.setCompteurHoraire(dto.getCompteurHoraire());
        engin.setEtatFrein(dto.getEtatFrein());
        engin.setEtatBatterie(dto.getEtatBatterie());
        engin.setEtatEclairage(dto.getEtatEclairage());
        engin.setEtatEssuieGlace(dto.getEtatEssuieGlace());
        engin.setEtatTracteur(dto.getEtatTracteur());
        engin.setEtatPneumatique(dto.getEtatPneumatique());
        engin.setEtatTransmission(dto.getEtatTransmission());
        engin.setEtatFreinService(dto.getEtatFreinService());
        engin.setEtatFreinParking(dto.getEtatFreinParking());
        engin.setEtatKlaxon(dto.getEtatKlaxon());
        engin.setEtatCablage(dto.getEtatCablage());
        engin.setEtatVitesse(dto.getEtatVitesse());
        engin.setObservationsGenerales(dto.getObservationsGenerales());
        engin.setImage(dto.getImage());
        // Assurez-vous de charger l'entité CategorieEngin correctement
        CategorieEngin categorieEngin = new CategorieEngin();
        categorieEngin.setId(dto.getCategorieEnginId());
        engin.setCategorieEngin(categorieEngin);
        return engin;
    }
}
