package com.example.marsamaroc.controllers;

import com.example.marsamaroc.dao.entities.Demande;
import com.example.marsamaroc.dao.entities.User;
import com.example.marsamaroc.dao.repositories.DemandeRepository;
import com.example.marsamaroc.dao.repositories.UserRepository;
import com.example.marsamaroc.dtos.AffectationDto;
import com.example.marsamaroc.dtos.DemandeDto;
import com.example.marsamaroc.exception.ResourceNotFoundException;
import com.example.marsamaroc.mappers.StatusUpdateRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.marsamaroc.service.DemandeManager;
import com.example.marsamaroc.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<?> createDemande(@RequestBody DemandeDto demandeDto) {
        try {
            Demande createdDemande = demandeService.createDemande(demandeDto);
            return new ResponseEntity<>(createdDemande, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<DemandeDto>> getAllDemandes() {
        List<DemandeDto> demandes = demandeService.getAllDemandes();
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    // Obtenir toutes les demandes
    @GetMapping("/user/{id}")
    public ResponseEntity<List<DemandeDto>> getDemandesByUser() {
        // Obtenir l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginInfo = authentication.getName();
        System.out.println("Authentication Name (login): " + loginInfo);

// Extraire l'ID de la chaîne
        Long userId = extractUserIdFromLoginInfo(loginInfo);
        System.out.println("User ID: " + userId);

        // Obtenir les demandes pour l'utilisateur connecté
        List<DemandeDto> demandes = demandeService.getDemandesByUser(userId);
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    private Long extractUserIdFromLoginInfo(String loginInfo) {
        // Rechercher la position de "id="
        int idStart = loginInfo.indexOf("id=") + 3;
        int idEnd = loginInfo.indexOf(",", idStart);
        String idString = loginInfo.substring(idStart, idEnd);
        return Long.parseLong(idString);
    }

    // Obtenir une demande par ID
    @GetMapping("/{id}")
    public ResponseEntity<DemandeDto> getDemandeById(@PathVariable("id") Long id) {
        DemandeDto demande = demandeService.getDemandeById(id);
        return demande != null ? new ResponseEntity<>(demande, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Endpoint pour obtenir toutes les demandes non affectées
    @GetMapping("/non-affectees")
    public ResponseEntity<?> getDemandesNonAffectees() {
        try {
            List<DemandeDto> demandesNonAffectees = demandeService.findDemandesNonAffectees();
            return ResponseEntity.ok(demandesNonAffectees);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la récupération des demandes non affectées");
        }
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
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<DemandeDto>> getDemandesByUser(@PathVariable Long userId) {
//        List<DemandeDto> demandes = demandeService.getDemandesByUser(userId);
//        return ResponseEntity.ok(demandes);
//    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateDemandeStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest statusUpdateRequest) {
        try {
            // Appel du service pour mettre à jour le statut
            demandeService.updateStatus(id, statusUpdateRequest.getStatus());
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PatchMapping("/{id}/affectation")
    public ResponseEntity<Void> updateAffectation(@PathVariable Long id, @RequestBody AffectationDto affectationDto) {
        demandeService.updateAffectation(id, affectationDto);
        return ResponseEntity.noContent().build();
    }
}