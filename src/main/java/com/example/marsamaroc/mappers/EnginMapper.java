package com.example.marsamaroc.mappers;

import com.example.marsamaroc.dao.entities.Engin;
import com.example.marsamaroc.dao.repositories.CategoryEnginRepository;
import com.example.marsamaroc.dtos.EnginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnginMapper {

    @Autowired
    private CategoryEnginRepository categoryEnginRepository;

    public EnginDto toEnginDto(Engin engin) {
        if (engin == null) {
            return null;
        }

        EnginDto dto = new EnginDto();
        dto.setId(engin.getId());
        dto.setCode(engin.getCode());
        dto.setMatricule(engin.getMatricule());
        dto.setCompteurHoraire(engin.getCompteurHoraire());
        dto.setImage(engin.getImage());
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

        if (engin.getCategorieEngin() != null) {
            dto.setCategorieEnginId(engin.getCategorieEngin().getId());
            dto.setCategorieEnginNom(engin.getCategorieEngin().getNom());
        }

        return dto;
    }

    public Engin toEngin(EnginDto dto) {
        if (dto == null) {
            return null;
        }

        Engin engin = new Engin();
        engin.setId(dto.getId());
        engin.setCode(dto.getCode());
        engin.setMatricule(dto.getMatricule());
        engin.setCompteurHoraire(dto.getCompteurHoraire());
        engin.setImage(dto.getImage());
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

        if (dto.getCategorieEnginId() != null) {
            engin.setCategorieEngin(categoryEnginRepository.findById(dto.getCategorieEnginId()).orElse(null));
        }

        return engin;
    }
}