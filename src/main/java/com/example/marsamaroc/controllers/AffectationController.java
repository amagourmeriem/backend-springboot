package com.example.marsamaroc.controllers;

import com.example.marsamaroc.dao.entities.Affectation;
import com.example.marsamaroc.dao.entities.Demande;
import com.example.marsamaroc.dao.entities.Engin;
import com.example.marsamaroc.dao.repositories.DemandeRepository;
import com.example.marsamaroc.dao.repositories.EnginRepository;
import com.example.marsamaroc.dtos.AffectationDto;
import com.example.marsamaroc.exception.ResourceNotFoundException;
import com.example.marsamaroc.service.AffectationService;
import com.example.marsamaroc.service.EnginService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/affectations")
public class AffectationController {

    @Autowired
    private AffectationService affectationService;
    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private EnginRepository enginRepository;
    @Autowired
    private EnginService enginService;



    @GetMapping
    public List<AffectationDto> getAllAffectations() {
        return affectationService.getAllAffectations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AffectationDto> getAffectationById(@PathVariable(value = "id") Long id) {
        AffectationDto affectationDto = affectationService.getAffectationById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Affectation not found for this id :: " + id));
        return ResponseEntity.ok().body(affectationDto);
    }

    @PostMapping
    public ResponseEntity<AffectationDto> createAffectation(@RequestBody AffectationDto affectationDto) {
        // Appeler le service pour créer l'affectation avec les données du DTO
        AffectationDto createdAffectation = affectationService.createAffectation(
                affectationDto.getDemandeId(),
                affectationDto.getEnginId(),
                affectationDto.getDateEntree(),
                affectationDto.getObservationEntree()
        );

        // Retourner la réponse avec le DTO de l'affectation créée et le statut HTTP 201 CREATED
        return new ResponseEntity<>(createdAffectation, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AffectationDto> updateAffectation(@PathVariable(value = "id") Long id,
                                                            @RequestBody AffectationDto affectationDetails) {
        AffectationDto updatedAffectation = affectationService.updateAffectation(id, affectationDetails);
        return ResponseEntity.ok(updatedAffectation);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteAffectation(@PathVariable(value = "id") Long id) {
        affectationService.deleteAffectation(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    @PostMapping("/{demandeId}")
    public ResponseEntity<String> affecterEngin(@RequestBody AffectationDto affectationDto) {
        try {
            // Créer une nouvelle affectation à partir du DTO
            Affectation affectation = new Affectation();
            affectation.setDateEntree(affectationDto.getDateEntree());
            affectation.setObservationEntree(affectationDto.getObservationEntree());

            // Récupérer les entités Engin et Demande à partir de leurs IDs
            Engin engin = affectationService.findEnginById(affectationDto.getEnginId());
            Demande demande = affectationService.findDemandeById(affectationDto.getDemandeId());

            if (engin == null || demande == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Engin ou Demande introuvable.");
            }

            affectation.setEngin(engin);
            affectation.setDemande(demande);

            // Sauvegarder l'affectation
            affectationService.saveAffectation(affectation);

            return ResponseEntity.ok("Engin affecté avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'affectation de l'engin.");
        }
    }

}

