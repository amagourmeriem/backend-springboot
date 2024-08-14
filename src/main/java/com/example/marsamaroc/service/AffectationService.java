package com.example.marsamaroc.service;

import com.example.marsamaroc.dao.entities.Affectation;
import com.example.marsamaroc.dao.entities.Demande;
import com.example.marsamaroc.dao.entities.Engin;
import com.example.marsamaroc.dao.repositories.AffectationRepository;
import com.example.marsamaroc.dao.repositories.DemandeRepository;
import com.example.marsamaroc.dao.repositories.EnginRepository;
import com.example.marsamaroc.dtos.AffectationDto;
import com.example.marsamaroc.exception.ResourceNotFoundException;
import com.example.marsamaroc.mappers.AffectationMapperUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AffectationService {

    @Autowired
    private AffectationRepository affectationRepository;

    @Autowired
    private AffectationMapperUtil affectationMapperUtil;

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private EnginRepository enginRepository;

    public List<AffectationDto> getAllAffectations() {
        List<Affectation> affectations = affectationRepository.findAll();
        return affectationMapperUtil.toAffectationDtoList(affectations);
    }

    public Optional<AffectationDto> getAffectationById(Long id) {
        return affectationRepository.findById(id)
                .map(affectationMapperUtil::toAffectationDto);
    }

    public AffectationDto createAffectation(Long demandeId, Long enginId, Date dateEntree, String observationEntree) {
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new IllegalArgumentException("Demande not found"));
        Engin engin = enginRepository.findById(enginId)
                .orElseThrow(() -> new IllegalArgumentException("Engin not found"));

        Affectation affectation = new Affectation();
        affectation.setDateEntree(dateEntree);
        affectation.setDemande(demande);
        affectation.setEngin(engin);
        affectation.setObservationEntree(observationEntree);

        Affectation savedAffectation = affectationRepository.save(affectation);

        // Mettre à jour la demande pour marquer l'affectation comme effectuée
        demande.setAffecte(true);  // ou demande.setAffecte(1); si le champ est de type int
        demandeRepository.save(demande);

        // Convertir l'entité sauvegardée en DTO et la retourner
        return AffectationDto.builder()
                .id(savedAffectation.getId())
                .demandeId(demandeId)
                .enginId(enginId)
                .dateEntree(dateEntree)
                .observationEntree(observationEntree)
                .build();
    }


    public AffectationDto updateAffectation(Long id, AffectationDto affectationDetails) {
        Affectation affectation = affectationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Affectation not found for this id :: " + id));

        // Mettre à jour les champs de l'affectation
        Date dateEntree = affectationDetails.getDateEntree();
        if (dateEntree != null) {
            affectation.setDateEntree(dateEntree);
        }

        // Mettre à jour les autres champs de manière similaire
        affectation.setObservationEntree(affectationDetails.getObservationEntree());
        // Vous pouvez ajouter d'autres mises à jour ici selon vos besoins

        // Sauvegarder l'affectation mise à jour
        Affectation updatedAffectation = affectationRepository.save(affectation);

        // Convertir l'affectation mise à jour en DTO et la retourner
        return affectationMapperUtil.toAffectationDto(updatedAffectation);
    }
    public Engin findEnginById(Long id) {
        return enginRepository.findById(id).orElse(null);
    }

    public Demande findDemandeById(Long id) {
        return demandeRepository.findById(id).orElse(null);
    }

    public void saveAffectation(Affectation affectation) {
        affectationRepository.save(affectation);
    }

    public void deleteAffectation(Long id) {
        Affectation affectation = affectationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Affectation not found for this id :: " + id));

        affectationRepository.delete(affectation);
    }
}

