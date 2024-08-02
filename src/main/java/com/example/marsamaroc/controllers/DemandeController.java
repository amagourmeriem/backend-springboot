package com.example.marsamaroc.controllers;

import com.example.marsamaroc.dao.entities.Demande;
import com.example.marsamaroc.service.DemandeManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:3000") // allowing client application to consume the backend
@RestController
@RequestMapping("/demandes")
@RequiredArgsConstructor

public class DemandeController {

    private final DemandeManager demandeManager;
    private static final Logger logger = LoggerFactory.getLogger(EnginController.class);


    @GetMapping
    public ResponseEntity<List<Demande>> getAllDemandes() {
        logger.debug("Fetching all demandes");
        List<Demande> demandes;
        try {
            demandes = demandeManager.findAll();
        } catch (Exception e) {
            logger.error("Error fetching demandes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(demandes);
    }
    // Créer une nouvelle demande
    @PostMapping
    public ResponseEntity<Demande> createDemande(@RequestBody Demande demande) {
        Demande savedDemande = demandeManager.saveDemande(demande);
        return ResponseEntity.ok(savedDemande);
    }

    // Récupérer une demande par ID
    @GetMapping("/{id}")
    public ResponseEntity<Demande> getDemandeById(@PathVariable Long id) {
        Optional<Demande> demande = demandeManager.findById(id);
        return demande.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Récupérer toutes les demandes


    // Rechercher les demandes par nom de demandeur
//    @GetMapping("/search")
//    public ResponseEntity<List<Demande>> getDemandesByNomDemandeur(@RequestParam String nomDemandeur) {
//        List<Demande> demandes = demandeManager.findByNomDemandeur(nomDemandeur);
//        return ResponseEntity.ok(demandes);
//    }

    // Rechercher les demandes entre deux dates
//    @GetMapping("/between")
//    public ResponseEntity<List<Demande>> getDemandesByDateSortieBetween(@RequestParam Date startDate,
//                                                                        @RequestParam Date endDate) {
//        List<Demande> demandes = demandeManager.findByDateSortieBetween(startDate, endDate);
//        return ResponseEntity.ok(demandes);
//    }

    // Modifier une demande
    @PutMapping("/update/{id}")
    public ResponseEntity<Demande> updateDemande(@PathVariable Long id, @RequestBody Demande demande) {
        Optional<Demande> existingDemande = demandeManager.findById(id);
        if (existingDemande.isPresent()) {
            demande.setId(id); // Assurer que l'ID de la demande reste le même
            Demande updatedDemande = demandeManager.saveDemande(demande);
            return ResponseEntity.ok(updatedDemande);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer une demande par ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDemandeById(@PathVariable Long id) {
        if (demandeManager.findById(id).isPresent()) {
            demandeManager.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
