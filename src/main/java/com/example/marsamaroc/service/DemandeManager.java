package com.example.marsamaroc.service;

import com.example.marsamaroc.dtos.DemandeDto;

import java.util.List;

public interface DemandeManager {
    List<DemandeDto> getAllDemandes();
    DemandeDto getDemandeById(Long id);
    DemandeDto createDemande(DemandeDto demandeDto);
    DemandeDto updateDemande(Long id, DemandeDto demandeDto);
    boolean deleteDemande(Long id);
    List<DemandeDto> getDemandesByUser(Long userId); // Nouvelle méthode
}
