package com.example.marsamaroc.controllers;

import com.example.marsamaroc.dao.entities.Demande;
import com.example.marsamaroc.dao.entities.User;
import com.example.marsamaroc.dao.repositories.DemandeRepository;
import com.example.marsamaroc.dao.repositories.UserRepository;
import com.example.marsamaroc.dtos.DemandeDto;
import com.example.marsamaroc.service.DemandeManager;
import com.example.marsamaroc.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demandes")
public class DemandeController {

    @Autowired
    private DemandeService demandeService;


    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private UserRepository userRepository;

    // Créer une nouvelle demande
    @PostMapping
    public ResponseEntity<DemandeDto> createDemande(@RequestBody DemandeDto demandeDto) {
        DemandeDto createdDemande = demandeService.createDemande(demandeDto);
        return new ResponseEntity<>(createdDemande, HttpStatus.CREATED);
    }

    // Obtenir toutes les demandes
    @GetMapping
    public ResponseEntity<List<DemandeDto>> getAllDemandes() {
        List<DemandeDto> demandes = demandeService.getAllDemandes();
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    // Obtenir une demande par ID
    @GetMapping("/{id}")
    public ResponseEntity<DemandeDto> getDemandeById(@PathVariable("id") Long id) {
        DemandeDto demande = demandeService.getDemandeById(id);
        return demande != null ? new ResponseEntity<>(demande, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Mettre à jour une demande
    @PutMapping("/{id}")
    public ResponseEntity<DemandeDto> updateDemande(@PathVariable("id") Long id, @RequestBody DemandeDto demandeDto) {
        DemandeDto updatedDemande = demandeService.updateDemande(id, demandeDto);
        return updatedDemande != null ? new ResponseEntity<>(updatedDemande, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Supprimer une demande
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemande(@PathVariable("id") Long id) {
        boolean isDeleted = demandeService.deleteDemande(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DemandeDto>> getDemandesByUser(@PathVariable Long userId) {
        List<DemandeDto> demandes = demandeService.getDemandesByUser(userId);
        return ResponseEntity.ok(demandes);
    }
}