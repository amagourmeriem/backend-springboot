package com.example.marsamaroc.service;

import com.example.marsamaroc.dao.entities.Demande;
import com.example.marsamaroc.dao.repositories.DemandeRepository;
import com.example.marsamaroc.service.DemandeManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DemandeService implements DemandeManager {

    private final DemandeRepository demandeRepository;

    @Autowired
    public DemandeService(DemandeRepository demandeRepository) {
        this.demandeRepository = demandeRepository;
    }

    @Override
    public Demande saveDemande(Demande demande) {
        return demandeRepository.save(demande);
    }

    @Override
    public Optional<Demande> findById(Long id) {
        return demandeRepository.findById(id);
    }

    @Override
    public List<Demande> findAll() {
        return demandeRepository.findAll();
    }

    @Override
    public List<Demande> findByNomDemandeur(String nomDemandeur) {
        return demandeRepository.findByNomDemandeur(nomDemandeur);
    }

    @Override
    public List<Demande> findByDateSortieBetween(Date startDate, Date endDate) {
        return demandeRepository.findByDateSortieBetween(startDate, endDate);
    }

    @Override
    public void deleteById(Long id) {
        demandeRepository.deleteById(id);
    }
}
