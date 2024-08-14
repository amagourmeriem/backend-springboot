package com.example.marsamaroc.mappers;

import com.example.marsamaroc.dao.entities.Affectation;
import com.example.marsamaroc.dao.entities.Demande;
import com.example.marsamaroc.dao.entities.Engin;
import com.example.marsamaroc.dtos.AffectationDto;
import com.example.marsamaroc.dao.repositories.DemandeRepository;
import com.example.marsamaroc.dao.repositories.EnginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AffectationMapperUtil {

    @Autowired
    private EnginRepository enginRepository;

    @Autowired
    private DemandeRepository demandeRepository;

    public AffectationDto toAffectationDto(Affectation affectation) {
        AffectationDto dto = new AffectationDto();
        dto.setId(affectation.getId());
        dto.setDateEntree(affectation.getDateEntree()); // Pas de conversion nécessaire
        dto.setEnginId(affectation.getEngin().getId());
        dto.setEnginCode(affectation.getEngin().getCode());
        dto.setDemandeId(affectation.getDemande().getId());
        dto.setObservationEntree(affectation.getObservationEntree());
        return dto;
    }

    public List<AffectationDto> toAffectationDtoList(List<Affectation> affectations) {
        return affectations.stream()
                .map(this::toAffectationDto)
                .collect(Collectors.toList());
    }

    public Affectation toAffectationEntity(AffectationDto dto) {
        Affectation affectation = new Affectation();
        affectation.setId(dto.getId());
        affectation.setDateEntree(dto.getDateEntree()); // Pas de conversion nécessaire

        if (dto.getEnginId() != null) {
            affectation.setEngin(enginRepository.findById(dto.getEnginId()).orElse(null));
        }
        if (dto.getDemandeId() != null) {
            affectation.setDemande(demandeRepository.findById(dto.getDemandeId()).orElse(null));
        }

        affectation.setObservationEntree(dto.getObservationEntree());
        return affectation;
    }

    public List<Affectation> toAffectationEntityList(List<AffectationDto> affectationDtos) {
        return affectationDtos.stream()
                .map(this::toAffectationEntity)
                .collect(Collectors.toList());
    }

    public Affectation toEntity(AffectationDto dto, Engin engin, Demande demande) {
        Affectation affectation = new Affectation();
        affectation.setDateEntree(dto.getDateEntree()); // Utilise directement Date
        affectation.setEngin(engin);
        affectation.setDemande(demande);
        affectation.setObservationEntree(dto.getObservationEntree());
        return affectation;
    }

    // Conversion utilitaire de Date à LocalDateTime
    private LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    // Conversion utilitaire de LocalDateTime à Date
    private Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
