package com.example.marsamaroc.service;

import com.example.marsamaroc.dao.entities.*;
import com.example.marsamaroc.dao.repositories.*;
import com.example.marsamaroc.dtos.DemandeDto;
import com.example.marsamaroc.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private CategorieEnginRepository categorieEnginRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnginRepository enginRepository;

    public Demande addDemande(DemandeDto demandeDto) {
        Demande demande = convertToEntity(demandeDto);
        demande.setStatus(Status.PENDING);
        return demandeRepository.save(demande);
    }

    public Demande updateDemande(Long id, DemandeDto demandeDto) {
        Demande existingDemande = demandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Demande not found with id: " + id));

        existingDemande.setNumeroBCI(demandeDto.getNumeroBCI());
        existingDemande.setNomDepartement(demandeDto.getNomDepartement());
        existingDemande.setNomDemandeur(demandeDto.getNomDemandeur());
        existingDemande.setCategorieEngin(convertCategorieEngin(demandeDto.getCategoieEnginId()));
        existingDemande.setDateSortie(demandeDto.getDateSortie());
        existingDemande.setShift(demandeDto.getShift());
        existingDemande.setSaul(demandeDto.getSaul());
        existingDemande.setObservations(demandeDto.getObservations());
        existingDemande.setUser(convertUser(demandeDto.getUserId()));
        existingDemande.setEngin(convertEngin(demandeDto.getCategoieEnginId()));

        return demandeRepository.save(existingDemande);
    }

    public Demande updateStatus(Long id, Status status) {
        Demande demande = demandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Demande not found with id: " + id));
        demande.setStatus(status);
        return demandeRepository.save(demande);
    }

    private Demande convertToEntity(DemandeDto demandeDto) {
        Demande demande = new Demande();
        demande.setNumeroBCI(demandeDto.getNumeroBCI());
        demande.setNomDepartement(demandeDto.getNomDepartement());
        demande.setNomDemandeur(demandeDto.getNomDemandeur());
        demande.setCategorieEngin(convertCategorieEngin(demandeDto.getCategoieEnginId()));
        demande.setDateSortie(demandeDto.getDateSortie());
        demande.setShift(demandeDto.getShift());
        demande.setSaul(demandeDto.getSaul());
        demande.setObservations(demandeDto.getObservations());
        demande.setUser(convertUser(demandeDto.getUserId()));
        demande.setEngin(convertEngin(demandeDto.getCategoieEnginId()));
        return demande;
    }

    private CategorieEngin convertCategorieEngin(Long categorieEnginId) {
        if (categorieEnginId == null) {
            return null;
        }
        Optional<CategorieEngin> categorieEngin = categorieEnginRepository.findById(categorieEnginId);
        return categorieEngin.orElse(null);
    }

    private User convertUser(Long userId) {
        if (userId == null) {
            return null;
        }
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    private Engin convertEngin(Long enginId) {
        if (enginId == null) {
            return null;
        }
        Optional<Engin> engin = enginRepository.findById(enginId);
        return engin.orElse(null);
    }
}
