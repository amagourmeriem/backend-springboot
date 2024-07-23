package com.example.marsamaroc.service;

import com.example.marsamaroc.controllers.EnginController;
import com.example.marsamaroc.dao.entities.Engin;
import com.example.marsamaroc.dao.repositories.EnginRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnginService implements EnginManager {

    private final EnginRepository enginRepository;
    private static final Logger logger = LoggerFactory.getLogger(EnginController.class);


    public EnginService(EnginRepository enginRepository) {
        this.enginRepository = enginRepository;
    }

    public List<Engin> getAllEngins() {
        return enginRepository.findAll();
    }
    @Override
    public Engin addEngin(Engin engin) {
        return enginRepository.save(engin);
    }

    @Override
    public Engin updateEngin(Engin enginDetails, Long id) {
        Optional<Engin> optionalEngin = enginRepository.findById(id);
        if (optionalEngin.isPresent()) {
            Engin engin = optionalEngin.get();
            engin.setCode(enginDetails.getCode());
            engin.setMatricule(enginDetails.getMatricule());
            engin.setCompteurHoraire(enginDetails.getCompteurHoraire());
            engin.setEtatFrein(enginDetails.getEtatFrein());
            engin.setEtatBatterie(enginDetails.getEtatBatterie());
            engin.setEtatEclairage(enginDetails.getEtatEclairage());
            engin.setEtatEssuieGlace(enginDetails.getEtatEssuieGlace());
            engin.setEtatTracteur(enginDetails.getEtatTracteur());
            engin.setEtatPneumatique(enginDetails.getEtatPneumatique());
            engin.setEtatTransmission(enginDetails.getEtatTransmission());
            engin.setEtatFreinService(enginDetails.getEtatFreinService());
            engin.setEtatFreinParking(enginDetails.getEtatFreinParking());
            engin.setEtatKlaxon(enginDetails.getEtatKlaxon());
            engin.setEtatCablage(enginDetails.getEtatCablage());
            engin.setEtatVitesse(enginDetails.getEtatVitesse());
            engin.setObservationsGenerales(enginDetails.getObservationsGenerales());
            engin.setCategorieEngin(enginDetails.getCategorieEngin());
            return enginRepository.save(engin);
        } else {
            throw new RuntimeException("Engin not found with id " + id);
        }
    }

    @Override
    public void deleteEngin(Long id) {
        enginRepository.deleteById(id);
    }

    @Override
    public Optional<Engin> getEnginById(Long id) {
        return enginRepository.findById(id);
    }

    @Override
    public Engin saveEngin(Engin engin) {
        return enginRepository.save(engin);
    }
}
