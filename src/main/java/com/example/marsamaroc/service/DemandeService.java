package com.example.marsamaroc.service;

import com.example.marsamaroc.dao.entities.Demande;
import com.example.marsamaroc.dao.repositories.DemandeRepository;
import com.example.marsamaroc.dtos.DemandeDto;
import com.example.marsamaroc.service.DemandeManager;
import com.example.marsamaroc.mappers.DemandeMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeService implements DemandeManager {

    private final DemandeRepository demandeRepository;
    private final DemandeMapperUtil demandeMapperUtil;

    @Autowired
    public DemandeService(DemandeRepository demandeRepository, DemandeMapperUtil demandeMapperUtil) {
        this.demandeRepository = demandeRepository;
        this.demandeMapperUtil = demandeMapperUtil;
    }

    @Override
    public List<DemandeDto> getAllDemandes() {
        List<Demande> demandes = demandeRepository.findAll();
        return demandeMapperUtil.toDemandeDtoList(demandes);
    }

    @Override
    public DemandeDto getDemandeById(Long id) {
        Optional<Demande> demande = demandeRepository.findById(id);
        return demande.map(demandeMapperUtil::toDemandeDto).orElse(null);
    }

    @Override
    public DemandeDto createDemande(DemandeDto demandeDto) {
        Demande demande = demandeMapperUtil.toDemande(demandeDto);
        demande = demandeRepository.save(demande);
        return demandeMapperUtil.toDemandeDto(demande);
    }

    @Override
    public DemandeDto updateDemande(Long id, DemandeDto demandeDto) {
        if (demandeRepository.existsById(id)) {
            Demande demande = demandeMapperUtil.toDemande(demandeDto);
            demande.setId(id); // Assurez-vous de définir l'ID pour la mise à jour
            demande = demandeRepository.save(demande);
            return demandeMapperUtil.toDemandeDto(demande);
        } else {
            return null;
        }
    }


    @Override
    public boolean deleteDemande(Long id) {
        if (demandeRepository.existsById(id)) {
            demandeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<DemandeDto> getDemandesByUser(Long userId) {
        List<Demande> demandes = demandeRepository.findByUserId(userId);
        return demandeMapperUtil.toDemandeDtoList(demandes);
    }
}