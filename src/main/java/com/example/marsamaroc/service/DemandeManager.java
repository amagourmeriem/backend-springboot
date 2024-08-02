package com.example.marsamaroc.service;

import com.example.marsamaroc.dao.entities.Demande;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface DemandeManager {

    Demande saveDemande(Demande demande);

    Optional<Demande> findById(Long id);

    List<Demande> findAll();

    List<Demande> findByNomDemandeur(String nomDemandeur);

    List<Demande> findByDateSortieBetween(Date startDate, Date endDate);

    void deleteById(Long id);

}
