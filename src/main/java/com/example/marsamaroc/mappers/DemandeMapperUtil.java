package com.example.marsamaroc.mappers;

import com.example.marsamaroc.dao.entities.Demande;
import com.example.marsamaroc.dao.repositories.CategoryEnginRepository;
import com.example.marsamaroc.dao.repositories.DemandeRepository;
import com.example.marsamaroc.dao.repositories.EnginRepository;
import com.example.marsamaroc.dao.repositories.UserRepository;
import com.example.marsamaroc.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DemandeMapperUtil {

    @Autowired
    private CategoryEnginRepository categoryEnginRepository;

    @Autowired
    private EnginRepository enginRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private CategorieEnginMapper categorieEnginMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EnginMapper enginMapper;

    public DemandeDto toDemandeDto(Demande demande) {
        if (demande == null) {
            return null;
        }
        DemandeDto dto = new DemandeDto();
        dto.setId(demande.getId());
        dto.setNumeroBCI(demande.getNumeroBCI());
        dto.setNomDepartement(demande.getNomDepartement());
        dto.setNomDemandeur(demande.getNomDemandeur());
        dto.setDateSortie(demande.getDateSortie());
        dto.setShift(demande.getShift());
        dto.setSaul(demande.getSaul());
        dto.setObservations(demande.getObservations());
        dto.setCategorieEnginId(demande.getCategorieEngin() != null ? demande.getCategorieEngin().getId() : null);
        dto.setStatus(demande.getStatus());
        dto.setEnginId(demande.getEngin() != null ? demande.getEngin().getId() : null);
        dto.setUserId(demande.getUser() != null ? demande.getUser().getId() : null);
        dto.setLogin(demande.getUser() != null ? demande.getUser().getLogin() : null);

        // Set CategorieEnginDTO
        if (demande.getCategorieEngin() != null) {
            dto.setCategorieEnginDTO(categorieEnginMapper.toCategorieEnginDto(demande.getCategorieEngin()));
        }

        // Set UserDTO
        if (demande.getUser() != null) {
            dto.setUser(userMapper.toUserDto(demande.getUser()));
        }

        // Set EnginDTO
        if (demande.getEngin() != null) {
            dto.setEnginDto(enginMapper.toEnginDto(demande.getEngin()));
        }

        return dto;
    }

    public Demande toDemande(DemandeDto dto) {
        if (dto == null) {
            return null;
        }

        Demande demande = new Demande();
        demande.setNumeroBCI(dto.getNumeroBCI());
        demande.setNomDepartement(dto.getNomDepartement());
        demande.setNomDemandeur(dto.getNomDemandeur());
        demande.setDateSortie(dto.getDateSortie());
        demande.setShift(dto.getShift());
        demande.setSaul(dto.getSaul());
        demande.setObservations(dto.getObservations());

        // Manage categoryEngin
        Optional.ofNullable(dto.getCategorieEnginId())
                .ifPresent(id -> demande.setCategorieEngin(categoryEnginRepository.findById(id).orElse(null)));

        // Manage status
        try {
            demande.setStatus(dto.getStatus());
        } catch (IllegalArgumentException e) {
            // Log or handle the error as needed
            demande.setStatus(null);
        }

        // Manage engin
        Optional.ofNullable(dto.getEnginId())
                .ifPresent(id -> demande.setEngin(enginRepository.findById(id).orElse(null)));

        // Manage user
        Optional.ofNullable(dto.getUserId())
                .ifPresent(id -> demande.setUser(userRepository.findById(id).orElse(null)));

        return demande;
    }

    public List<DemandeDto> toDemandeDtoList(List<Demande> demandes) {
        if (demandes == null) {
            return null;
        }
        return demandes.stream()
                .map(this::toDemandeDto)
                .collect(Collectors.toList());
    }
}