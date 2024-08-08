package com.example.marsamaroc.service;

import com.example.marsamaroc.dao.entities.Demande;
import com.example.marsamaroc.dtos.DemandeDto;

import java.util.List;

public interface DemandeManager {
    List<DemandeDto> getAllDemandes();
    DemandeDto getDemandeById(Long id);
    Demande createDemande(DemandeDto demandeDto);
    boolean deleteDemande(Long id);
}