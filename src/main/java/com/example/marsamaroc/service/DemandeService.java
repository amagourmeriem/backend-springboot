package com.example.marsamaroc.service;

import com.example.marsamaroc.dao.entities.*;
import com.example.marsamaroc.dao.repositories.CategoryEnginRepository;
import com.example.marsamaroc.dao.repositories.DemandeRepository;
import com.example.marsamaroc.dao.repositories.EnginRepository;
import com.example.marsamaroc.dao.repositories.UserRepository;
import com.example.marsamaroc.dtos.AffectationDto;
import com.example.marsamaroc.dtos.DemandeDto;
import com.example.marsamaroc.exception.ResourceNotFoundException;
import com.example.marsamaroc.mappers.DemandeMapperUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class DemandeService implements DemandeManager {

    private final DemandeMapperUtil demandeMapperUtil;
    private final DemandeRepository demandeRepository;
    private final UserRepository userRepository;

    private final EnginRepository enginRepository;

    private  final CategoryEnginRepository categoryEnginRepository;

    @Autowired
    public DemandeService(DemandeRepository demandeRepository, DemandeMapperUtil demandeMapperUtil, UserRepository userRepository, EnginRepository enginRepository, CategoryEnginRepository categoryEnginRepository) {
        this.demandeRepository = demandeRepository;
        this.demandeMapperUtil = demandeMapperUtil;
        this.userRepository = userRepository;
        this.enginRepository = enginRepository;
        this.categoryEnginRepository = categoryEnginRepository;
    }

    @Override
    public List<DemandeDto> getAllDemandes() {
        List<Demande> demandes = demandeRepository.findAll();
        return demandes.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    private DemandeDto mapToDto(Demande demande) {
        DemandeDto demandeDto = new DemandeDto();
        demandeDto.setId(demande.getId());
        demandeDto.setNumeroBCI(demande.getNumeroBCI());
        demandeDto.setNomDepartement(demande.getNomDepartement());
        demandeDto.setNomDemandeur(demande.getNomDemandeur());
        demandeDto.setDateSortie(demande.getDateSortie());
        demandeDto.setShift(demande.getShift());
        demandeDto.setSaul(demande.getSaul());
        demandeDto.setObservations(demande.getObservations());
        demandeDto.setStatus(demande.getStatus());
        demandeDto.setCategorieEnginId(demande.getCategorieEngin() != null ? demande.getCategorieEngin().getId() : null);
        demandeDto.setCategorieEnginNom(demande.getCategorieEngin() != null ? demande.getCategorieEngin().getNom() : null);
        demandeDto.setEnginId(demande.getEngin() != null ? demande.getEngin().getId() : null);
        demandeDto.setUserId(demande.getUser() != null ? demande.getUser().getId() : null);
        demandeDto.setAffecte(demande.isAffecte()); // Assurez-vous que cette méthode est définie dans Demande
        return demandeDto;
    }

    @Override
    public DemandeDto getDemandeById(Long id) {
        Optional<Demande> demande = demandeRepository.findById(id);
        return demande.map(demandeMapperUtil::toDemandeDto).orElse(null);
    }
    @Override
    public Demande createDemande(DemandeDto demandeDto) {
        // Obtenir l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginInfo = authentication.getName();
        System.out.println("Authentication Name (login): " + loginInfo);

// Extraire l'ID de la chaîne
        Long userId = extractUserIdFromLoginInfo(loginInfo);
        System.out.println("User ID: " + userId);

// Trouver l'utilisateur par son ID
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Trouver la catégorie d'engin par son ID
        CategorieEngin categorieEngin = categoryEnginRepository.findById(demandeDto.getCategorieEnginId())
                .orElseThrow(() -> new RuntimeException("CategorieEngin not found"));

        // Créer la demande
        Demande demande = new Demande();
        demande.setNumeroBCI(demandeDto.getNumeroBCI());
        demande.setNomDepartement(demandeDto.getNomDepartement());
        demande.setNomDemandeur(user.getFirstName() + " " + user.getLastName()); // Utiliser le nom de l'utilisateur connecté
        demande.setDateSortie(demandeDto.getDateSortie());
        demande.setShift(demandeDto.getShift());
        demande.setObservations(demandeDto.getObservations());
        demande.setCategorieEngin(categorieEngin); // Utiliser l'objet CategorieEngin
        demande.setSaul(demandeDto.getSaul());
        demande.setUser(user); // Associe l'utilisateur à la demande

        // Sauvegarder la demande
        return demandeRepository.save(demande);
    }

    private Long extractUserIdFromLoginInfo(String loginInfo) {
        // Rechercher la position de "id="
        int idStart = loginInfo.indexOf("id=") + 3;
        int idEnd = loginInfo.indexOf(",", idStart);
        String idString = loginInfo.substring(idStart, idEnd);
        return Long.parseLong(idString);
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

    public DemandeDto updateDemande(Long id, DemandeDto demandeDto) {
        // Récupérer l'entité Demande existante depuis la base de données
        Demande demandeExistante = demandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Demande non trouvée pour cet ID : " + id));

        // Mettre à jour les champs de la demande avec les valeurs du DTO
        if (demandeDto.getNumeroBCI() != null) {
            demandeExistante.setNumeroBCI(demandeDto.getNumeroBCI());
        }
        if (demandeDto.getNomDepartement() != null) {
            demandeExistante.setNomDepartement(demandeDto.getNomDepartement());
        }
        if (demandeDto.getNomDemandeur() != null) {
            demandeExistante.setNomDemandeur(demandeDto.getNomDemandeur());
        }
        if (demandeDto.getDateSortie() != null) {
            demandeExistante.setDateSortie(demandeDto.getDateSortie());
        } else {
            // Conserver la date de sortie actuelle si elle n'est pas modifiée
            demandeDto.setDateSortie(demandeExistante.getDateSortie());
        }
        if (demandeDto.getShift() != null) {
            demandeExistante.setShift(demandeDto.getShift());
        }
        if (demandeDto.getSaul() != null) {
            demandeExistante.setSaul(demandeDto.getSaul());
        }
        if (demandeDto.getObservations() != null) {
            demandeExistante.setObservations(demandeDto.getObservations());
        }
        if (demandeDto.getStatus() != null) {
            demandeExistante.setStatus(demandeDto.getStatus());
        }

        // Mettre à jour les associations avec les autres entités si nécessaire
        if (demandeDto.getCategorieEnginId() != null) {
            CategorieEngin categorieEngin = categoryEnginRepository.findById(demandeDto.getCategorieEnginId())
                    .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée pour cet ID : " + demandeDto.getCategorieEnginId()));
            demandeExistante.setCategorieEngin(categorieEngin);
        }
        if (demandeDto.getEnginId() != null) {
            Engin engin = enginRepository.findById(demandeDto.getEnginId())
                    .orElseThrow(() -> new ResourceNotFoundException("Engin non trouvé pour cet ID : " + demandeDto.getEnginId()));
            demandeExistante.setEngin(engin);
        }

        // Sauvegarder les modifications dans la base de données
        Demande demandeMiseAJour = demandeRepository.save(demandeExistante);

        // Convertir l'entité mise à jour en DTO pour le retour
        return mapToDto(demandeMiseAJour);
    }
    private DemandeDto convertToDto(Demande demande) {
        // Convertir Demande en DemandeDto
        DemandeDto dto = new DemandeDto();
        // Mappez les champs de Demande à DemandeDto
        return dto;
    }

    public void updateStatus(Long id, Status status) {
        Demande demande = demandeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Demande not found"));
        demande.setStatus(status);
        demandeRepository.save(demande);
    }

    public void ajouterAffectation(Affectation affectation) {
        Demande demande = affectation.getDemande();
        demande.setAffecte(true);
        // Sauvegarder la demande avec l'état affecté
        demandeRepository.save(demande);
    }

    public List<DemandeDto> findDemandesNonAffectees() {
        List<Demande> demandes = demandeRepository.findByAffecteFalse();
        return demandes.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void updateAffectation(Long demandeId, AffectationDto affectationDto) {
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new EntityNotFoundException("Demande not found"));
        demande.setAffecte(affectationDto.isAffecte());
        demandeRepository.save(demande);
    }
}
